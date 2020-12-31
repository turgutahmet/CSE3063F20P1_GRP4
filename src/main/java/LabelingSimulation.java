import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LabelingSimulation {
    private Dataset dataset;
    private ArrayList<UserInfo> allUsers;
    private ArrayList<UserInfo> currentUsers;
    private ArrayList<Instance> instances;
    private ArrayList<ClassLabel> classLabels;

    public LabelingSimulation(Dataset dataset, ArrayList<UserInfo> allUsers, ArrayList<UserInfo> currentUsers, ArrayList<Instance> instances, ArrayList<ClassLabel> classLabels) {
        this.dataset = dataset;
        this.allUsers = allUsers;
        this.currentUsers = currentUsers;
        this.instances = instances;
        this.classLabels = classLabels;
    }

    public void startSimulation() {
        //Iterate currentUsers.
        for (UserInfo user : currentUsers) {
            //Get turn number.
            int numberOfTurn = getNumberOfTurn();

            //Check user's type.
            switch (user.getUserType()) {
                case "RandomBot":
                    //Simulate turns.
                    for (int i = 0; i < numberOfTurn; i++) {
                        startTurn(user, new RandomLabeling());
                    }
                    break;
                case "SentimentBot":
                    //Simulate turns.
                    for (int i = 0; i < numberOfTurn; i++) {
                        startTurn(user, new SentimentLabeling());
                    }
                    break;
            }
        }
    }

    //Returns not labeled instances by given user.
    private ArrayList<Instance> getNotLabeledInstances(UserInfo user) {
        ArrayList<Instance> notLabeledInstances = new ArrayList<>();
        for (Instance instance : instances) {
            notLabeledInstances.add(instance);
        }
        //Iterate all instances.
        for (Instance instance : instances) {
            //If instance can't be labeled anymore drop that instance from notLabeledInstances list.
            if (!instance.isCanLabeled()) {
                notLabeledInstances.remove(instance);
                continue;
            }

            //Iterate userLabels.
            for (LabeledInstance userLabel : instance.getUserLabels()) {
                //If instance has labeled by given user drop that instance from notLabeledInstances list.
                if (userLabel.getWhoLabeled().getUserID() == user.getUserID()) {
                    notLabeledInstances.remove(instance);
                    break;
                }
            }
        }
        return notLabeledInstances;
    }

    //Returns previously labeled instances by given user
    private ArrayList<Instance> getPreviouslyLabeledInstances(UserInfo user) {
        ArrayList<Instance> previouslyLabeledInstances = new ArrayList<>();
        //Iterate all instances
        for (Instance instance : instances) {
            //If instance can be labeled and given user has labeled that instance before add that instance into previouslyLabeledInstance list.
            if (instance.isCanLabeled()) {
                //Iterate userLabels
                for (LabeledInstance userLabel : instance.getUserLabels()) {
                    if (userLabel.getWhoLabeled().getUserID() == user.getUserID()) {
                        previouslyLabeledInstances.add(instance);
                        break;
                    }
                }
            }
        }
        return previouslyLabeledInstances;
    }

    //Returns random number of turns.
    private int getNumberOfTurn() {
        return (int) (Math.random() * 10);
    }

    private void startTurn(UserInfo user, LabelingMechanism labelingMechanism) {
        //Start time for measuring evaluated time in that turn.
        long start = System.currentTimeMillis();

        //Get not labeled instances by that user.
        ArrayList<Instance> notLabeledInstances = getNotLabeledInstances(user);
        //Get previously labeled instances by that user.
        ArrayList<Instance> previouslyLabeledInstances = getPreviouslyLabeledInstances(user);

        //Select a random instance.
        Instance randomInstance = getRandomInstance(user, notLabeledInstances, previouslyLabeledInstances);

        //Label random instance with given labeling mechanism.
        if (randomInstance != null) {
            labelingMechanism.labelInstanceWithUser(user, randomInstance, classLabels);

            //Get evaluated time in that turn.
            long end = System.currentTimeMillis();
            float sec = (end - start) / 1000F;

            //Update metrics at end of each turn.
            updateUserPerformanceMetrics(user, sec);
            updateInstancePerformanceMetrics(randomInstance);
            updateDatasetPerformanceMetrics(user);

            //Write each metrics into outputs and datasets directories.
            try {
                writeUserPerformanceMetrics(user, user.getUserPerformanceMetrics());
                writeInstancePerformanceMetrics(randomInstance, randomInstance.getInstancePerformanceMetrics());
                writeDatasetPerformanceMetrics(dataset.getDatasetPerformanceMetrics());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private Instance getRandomInstance(UserInfo user, ArrayList<Instance> notLabeledInstances, ArrayList<Instance> previouslyLabeledInstances) {
        Instance randomInstance;
        String selectedList = "";

        if (previouslyLabeledInstances.isEmpty()) {//If that user didn't label an instance before select a random instance from the notLabeledInstances list.
            if (notLabeledInstances.isEmpty()) { //If there isn't any instance which can be labeled, finish labeling process.
                return null;
            }
            selectedList = "notLabeledInstances";
            incrementSpecificNumberOfInstanceLabeled(user, selectedList);
            randomInstance = notLabeledInstances.get((int) (Math.random() * notLabeledInstances.size()));
        } else {
            //According to consistency check probability select a random instance from previouslyLabeledInstance list or notLabeledInstances list.
            int upperLimit = (int) (user.getConsistencyCheckProbability() * 100);
            int dice = (int) (Math.random() * 100);
            if (dice < upperLimit) {
                selectedList = "previouslyLabeledInstances";
                incrementSpecificNumberOfInstanceLabeled(user, selectedList);
                randomInstance = previouslyLabeledInstances.get((int) (Math.random() * previouslyLabeledInstances.size()));
            } else {
                if (notLabeledInstances.isEmpty()) { //If there isn't any not labeled instance skip this labeling process.
                    return null;
                }
                selectedList = "notLabeledInstances";
                incrementSpecificNumberOfInstanceLabeled(user, selectedList);
                randomInstance = notLabeledInstances.get((int) (Math.random() * notLabeledInstances.size()));
            }
        }
        return randomInstance;
    }

    private void incrementSpecificNumberOfInstanceLabeled(UserInfo user, String selectedList) {
        //Increment total number of unique instances of given user, if random instance is selected from notLabeledInstances list.
        if (selectedList.equals("notLabeledInstances")) user.getUserPerformanceMetrics().incrementTotalUniqueNumberOfInstanceLabelled();
        //Increment count of recurrent labeled instance of given user, if random instance is selected from previouslyLabeled list.
        if (selectedList.equals("previouslyLabeledInstances")) user.getUserPerformanceMetrics().incrementCountOfRecurrentInstances();
    }

    private void updateUserPerformanceMetrics(UserInfo userInfo, float sec) {
        UserPerformanceMetrics userPerformanceMetrics = userInfo.getUserPerformanceMetrics();

        //Increment total number of instance labeled after each successful labeling process.
        userPerformanceMetrics.incrementTotalNumberOfInstanceLabelled();

        //Update dataset's completeness percentage.
        userPerformanceMetrics.updateDatasetsCompletenessPercentage(instances, dataset);

        //Update consistency percentage
        userPerformanceMetrics.updateConsistencyPercentage();

        //Store each time spent in labeling process.
        userPerformanceMetrics.addTimes(sec);

        //Update average time spent in labeling processes.
        userPerformanceMetrics.updateAverageTime();

        //Update std. dev. of time spent in labeling process.
        userPerformanceMetrics.updateStdTime();
    }

    private void updateInstancePerformanceMetrics(Instance randomInstance) {
        InstancePerformanceMetrics instancePerformanceMetrics = randomInstance.getInstancePerformanceMetrics();

        //Update total number of label assignments.
        instancePerformanceMetrics.updateTotalNumberOfLabelAssignments();

        //Update total number of unique label assignments.
        instancePerformanceMetrics.updateNumberOfUniqueLabelAssignments(classLabels.size());

        //Update number of unique currentUsers.
        instancePerformanceMetrics.updateNumberOfUniqueUsers(allUsers.size());

        //Update final label and percentage.
        instancePerformanceMetrics.updateFinalLabelAndPercentage(classLabels);

        //Update class label and percentage.
        instancePerformanceMetrics.updateLabelsAndPercentage(classLabels);

        //Update entropy.
        instancePerformanceMetrics.updateEntropy(classLabels.size());
    }

    private void updateDatasetPerformanceMetrics(UserInfo userInfo) {
        //Update dataset's completeness percentage
        dataset.getDatasetPerformanceMetrics().updatePercentage(instances);

        //Update dataset's distribution of final label instance
        dataset.getDatasetPerformanceMetrics().updateDistributionOfFinalInstanceLabels(instances, classLabels);

        //Update unique instances for each label
        dataset.getDatasetPerformanceMetrics().updateUniqueInstancesForEachLabel(classLabels, instances);

        //Update assigned user and completeness percentage
        dataset.getDatasetPerformanceMetrics().updateAssignedUsersAndCompletenessPercentage(userInfo, dataset.getDatasetName());

        //Update assigned user and consistency percentage
        dataset.getDatasetPerformanceMetrics().updateAssignedUsersAndConsistencyPercentage(userInfo);
    }

    private void writeUserPerformanceMetrics(UserInfo userInfo, UserPerformanceMetrics userPerformanceMetrics) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //Write into databases directory.
        File dir = new File("./database/");
        File fileUser = new File("./database/User"+ userInfo.getUserID() + ".json");
        LinkedHashMap jsonObjectUser = new LinkedHashMap();
        jsonObjectUser.put("user", userPerformanceMetrics.getUser());
        jsonObjectUser.put("number of datasets assigned", userPerformanceMetrics.getNumberOfDataset());
        jsonObjectUser.put("list of all datasets with their completeness percentage", userPerformanceMetrics.getDatasetsCompletenessPercentage());
        jsonObjectUser.put("total number of instances labeled", userPerformanceMetrics.getTotalNumberOfInstanceLabelled());
        jsonObjectUser.put("count of recurrent instances", userPerformanceMetrics.getCountOfRecurrentInstances());
        jsonObjectUser.put("consistency percentage", userPerformanceMetrics.getConsistencyPercentage());
        jsonObjectUser.put("total number of unique instances labeled", userPerformanceMetrics.getTotalNumberOfUniqueInstance());
        jsonObjectUser.put("average time spent in labeling an instance in seconds", userPerformanceMetrics.getAvgTime());
        jsonObjectUser.put("std. dev. of  time spent in labeling an instance in seconds", userPerformanceMetrics.getStdTime());
        jsonObjectUser.put("times", userPerformanceMetrics.getTimes());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, fileUser.getName()), jsonObjectUser);

        //Write into outputs directory.
        dir = new File("./outputs/");
        fileUser = new File("./outputs/User"+ userInfo.getUserID() + ".json");
        jsonObjectUser = new LinkedHashMap();
        jsonObjectUser.put("user", userPerformanceMetrics.getUser());
        jsonObjectUser.put("number of datasets assigned", userPerformanceMetrics.getNumberOfDataset());
        jsonObjectUser.put("list of all datasets with their completeness percentage", userPerformanceMetrics.getDatasetsCompletenessPercentage());
        jsonObjectUser.put("total number of instances labeled", userPerformanceMetrics.getTotalNumberOfInstanceLabelled());
        jsonObjectUser.put("consistency percentage", userPerformanceMetrics.getConsistencyPercentage());
        jsonObjectUser.put("total number of unique instances labeled", userPerformanceMetrics.getTotalNumberOfUniqueInstance());
        jsonObjectUser.put("average time spent in labeling an instance in seconds", userPerformanceMetrics.getAvgTime());
        jsonObjectUser.put("std. dev. of  time spent in labeling an instance in seconds", userPerformanceMetrics.getStdTime());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, fileUser.getName()), jsonObjectUser);
    }

    private void writeInstancePerformanceMetrics(Instance randomInstance, InstancePerformanceMetrics instancePerformanceMetrics) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //Write into databases directory.
        File dir = new File("./database/");
        File fileInstance = new File("./database/Instance"+ randomInstance.getID() + "_Dataset" + dataset.getDatasetID() + ".json");
        LinkedHashMap jsonObjectInstance = new LinkedHashMap();
        jsonObjectInstance.put("all label assignments", instancePerformanceMetrics.getAllLabelAssignments());
        jsonObjectInstance.put("total number of label assignments", instancePerformanceMetrics.getTotalNumberOfLabelAssignments());
        jsonObjectInstance.put("total number of unique label assignments", instancePerformanceMetrics.getNumberOfUniqueLabelAssignments());
        jsonObjectInstance.put("number of unique users", instancePerformanceMetrics.getNumberOfUniqueUsers());
        jsonObjectInstance.put("final label and percentage", instancePerformanceMetrics.getFinalLabelAndPercentage());
        jsonObjectInstance.put("class labels and percentages", instancePerformanceMetrics.getClassLabelsAndPercentages());
        jsonObjectInstance.put("entropy", instancePerformanceMetrics.getEntropy());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, fileInstance.getName()), jsonObjectInstance);

        //Write into outputs directory.
        dir = new File("./outputs/");
        fileInstance = new File("./outputs/Instance"+ randomInstance.getID() + "_Dataset" + dataset.getDatasetID() + ".json");
        jsonObjectInstance = new LinkedHashMap();
        jsonObjectInstance.put("total number of label assignments", instancePerformanceMetrics.getTotalNumberOfLabelAssignments());
        jsonObjectInstance.put("total number of unique label assignments", instancePerformanceMetrics.getNumberOfUniqueLabelAssignments());
        jsonObjectInstance.put("number of unique currentUsers", instancePerformanceMetrics.getNumberOfUniqueUsers());
        jsonObjectInstance.put("final label and percentage", instancePerformanceMetrics.getFinalLabelAndPercentage());
        jsonObjectInstance.put("class labels and percentages", instancePerformanceMetrics.getClassLabelsAndPercentages());
        jsonObjectInstance.put("entropy", instancePerformanceMetrics.getEntropy());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, fileInstance.getName()), jsonObjectInstance);
    }

    private void writeDatasetPerformanceMetrics(DatasetPerformanceMetrics datasetPerformanceMetrics) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //Write into outputs directory
        File dir = new File("./outputs/");
        File fileDataset = new File("Dataset"+ dataset.getDatasetID() + ".json");
        LinkedHashMap jsonObjectDataset = new LinkedHashMap();
        jsonObjectDataset.put("completeness percentage",datasetPerformanceMetrics.getPercentage());
        jsonObjectDataset.put("class distribution based on final instance labels", datasetPerformanceMetrics.getDistributionOfFinalInstanceLabels());
        jsonObjectDataset.put("list number of unique instances for each class label", datasetPerformanceMetrics.getLabelAndNumberOfUniqueInstances());
        jsonObjectDataset.put("number of currentUsers assigned to this dataset", datasetPerformanceMetrics.getNumberOfUserAssigned());
        jsonObjectDataset.put("list of currentUsers assigned and their completeness percentage", datasetPerformanceMetrics.getAssignedUsersAndCompletenessPercentage());
        jsonObjectDataset.put("list of currentUsers assigned and their consistency percentage", datasetPerformanceMetrics.getAssignedUsersAndConsistencyPercentage());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, fileDataset.getName()), jsonObjectDataset);
    }

}
