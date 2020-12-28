import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UserPerformanceMetrics {
    private BotInfo user;
    private int numberOfDataset; //User's number of assigned datasets.
    private ArrayList<DatasetAndPercentage> datasetsCompletenessPercentage; //Datasets' completeness percentages.
    private int totalNumberOfInstanceLabelled; //Total number of instances labeled.
    private int totalNumberOfUniqueInstance; //Total number of unique instances labeled.
    private int countOfRecurrentInstances; //Number of recurrent labeled instances.
    private float consistencyPercentage; //User's consistency percentage.
    private ArrayList<Float> times; //All labeling processes' finishing time.
    private float avgTime; //Average time of all labeling processes'.
    private float stdTime; //Standard dev. of all labeling processes'.

    //UserPerformanceMetrics constructors.
    public UserPerformanceMetrics(BotInfo user, int numberOfDatasets) {
        this.user = user;
        this.numberOfDataset = numberOfDatasets;
        this.datasetsCompletenessPercentage = new ArrayList<>();
        this.times = new ArrayList<>();
        this.totalNumberOfInstanceLabelled = 0;
        this.totalNumberOfUniqueInstance = 0;
        this.countOfRecurrentInstances = 0;
        this.consistencyPercentage = 0;
        this.avgTime = 0;
        this.stdTime = 0;
    }
    public UserPerformanceMetrics(){

    }

    //Updates completeness percentages of given dataset.
    public void updateDatasetsCompletenessPercentage(ArrayList<Instance> instancesInThatDataset, String currentDatasetName) {
        //Get number of instances which are labeled in current dataset.
        int numberOfLabeledInstancesInThatDataset = 0;
        for (Instance instance : instancesInThatDataset) {
            for (LabeledInstance userLabel : instance.getUserLabels()) {
                if (userLabel.getWhoLabeled().getUserID() == user.getUserID()) {
                    numberOfLabeledInstancesInThatDataset++;
                }
            }
        }

        float percentage =  (float) (numberOfLabeledInstancesInThatDataset * 1.0 / instancesInThatDataset.size()) * 100;
        DatasetAndPercentage datasetAndPercentage = new DatasetAndPercentage(currentDatasetName, percentage);

        for (DatasetAndPercentage andPercentage : datasetsCompletenessPercentage) {
            if (andPercentage.getDatasetName().equals(datasetAndPercentage.getDatasetName())) {
                datasetsCompletenessPercentage.remove(andPercentage);
                andPercentage = datasetAndPercentage;
                datasetsCompletenessPercentage.add(andPercentage);
                return;
            }
        }

        datasetsCompletenessPercentage.add(datasetAndPercentage);
    }

    //Increments total number of instances which are labeled.
    public void incrementTotalNumberOfInstanceLabelled(){
        totalNumberOfInstanceLabelled++;
    }

    //Increments total number of unique instances which are labeled.
    public void incrementTotalUniqueNumberOfInstanceLabelled(){
        totalNumberOfUniqueInstance++;
    }

    public void incrementCountOfRecurrentInstances() { countOfRecurrentInstances++; }

    //Updates consistency percentage.
    public void updateConsistencyPercentage() {
        if (countOfRecurrentInstances == 0) {
            consistencyPercentage = 0;
            return;
        }
        //Count recurrent instances which are labeled with same label.
        int countOfRecurrentInstancesWithSameLabel = 0;
        for (LabeledInstance labeledInstance : user.getLabeledInstances()) {
            //If instance is labeled with only one label and its count is more than one.
            if (labeledInstance.getLabels().size() == 1 && labeledInstance.getLabels().get(0).getCount() > 1) countOfRecurrentInstancesWithSameLabel++;
        }
        float percentage = (float) (countOfRecurrentInstancesWithSameLabel * 1.0 / countOfRecurrentInstances) * 100;
        consistencyPercentage = percentage;
    }

    //Adds new finishing time of labeling process.
    public void addTimes(float time){
        times.add(time);
    }

    //Updates average time spent in labeling processes'.
    public void updateAverageTime() {
        float totalTime  = 0;
        for (Float time : times) {
            totalTime += time;
        }
        avgTime = totalTime / times.size();
    }

    //Updates standart dev. of time spent in labeling processes'.
    public void updateStdTime(){
        float diffOfTimeSq = 0;
        for (Float time : times) {
            diffOfTimeSq += Math.pow((avgTime-time), 2);
        }
        stdTime = (float) Math.sqrt(diffOfTimeSq / times.size());
    }

    //Getter methods.


    public BotInfo getUser() {
        return user;
    }

    public int getNumberOfDataset() {
        return numberOfDataset;
    }
    public int getTotalNumberOfInstanceLabelled() {
        return totalNumberOfInstanceLabelled;
    }
    public int getTotalNumberOfUniqueInstance() {
        return totalNumberOfUniqueInstance;
    }

    public int getCountOfRecurrentInstances() {
        return countOfRecurrentInstances;
    }

    public float getConsistencyPercentage() {return consistencyPercentage; }
    public float getAvgTime() {
        return avgTime;
    }
    public float getStdTime() {
        return stdTime;
    }
    public ArrayList<Float> getTimes() {
        return times;
    }


    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("user")
    public void setUser(BotInfo user) {
        this.user = user;
    }

    @JsonProperty("number of datasets assigned")
    public void setNumberOfDataset(int numberOfDataset) {
        this.numberOfDataset = numberOfDataset;
    }

    public ArrayList<DatasetAndPercentage> getDatasetsCompletenessPercentage() {
        return datasetsCompletenessPercentage;
    }
    @JsonProperty("list of all datasets with their completeness percentage")
    public void setDatasetsCompletenessPercentage(ArrayList<DatasetAndPercentage> datasetsCompletenessPercentage) {
        this.datasetsCompletenessPercentage = datasetsCompletenessPercentage;
    }

    @JsonProperty("total number of instances labeled")
    public void setTotalNumberOfInstanceLabelled(int totalNumberOfInstanceLabelled) {
        this.totalNumberOfInstanceLabelled = totalNumberOfInstanceLabelled;
    }

    @JsonProperty("total number of unique instances labeled")
    public void setTotalNumberOfUniqueInstance(int totalNumberOfUniqueInstance) {
        this.totalNumberOfUniqueInstance = totalNumberOfUniqueInstance;
    }

    @JsonProperty("consistency percentage")
    public void setConsistencyPercentage(float consistencyPercentage) {
        this.consistencyPercentage = consistencyPercentage;
    }

    @JsonProperty("average time spent in labeling an instance in seconds")
    public void setAvgTime(float avgTime) {
        this.avgTime = avgTime;
    }

    @JsonProperty("std. dev. of  time spent in labeling an instance in seconds")
    public void setStdTime(float stdTime) {
        this.stdTime = stdTime;
    }

    @JsonProperty("times")
    public void setTimes(ArrayList<Float> times) {
        this.times = times;
    }
}
