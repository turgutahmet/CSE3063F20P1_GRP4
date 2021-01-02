import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        //Create config object for keep config's information.
        Config config = new Config().readConfig();

        //According config setup current dataset.
        SetupDataset currentDataset = new SetupDataset(config).invoke();
        int currentDatasetID = currentDataset.getCurrentDatasetID();
        ArrayList<DatasetInfo> datasetsInfo = currentDataset.getDatasetsInfo();
        DatasetInfo currentDatasetInfo = currentDataset.getCurrentDatasetInfo();

        //Create dataset object for keep input's information.
        Dataset data = new DatasetCreator(currentDatasetID, datasetsInfo, currentDatasetInfo).invoke();

        //Create logger object for log records and set logger configurations.
        Logger logger = Logger.getLogger(Config.class.getName());
        PropertyConfigurator.configure("log4j.properties");

        //Get all users in the system.
        AssignedUsers assignedUsers = new AssignedUsers(config, currentDatasetInfo, logger).invoke();
        ArrayList<UserInfo> allUsers = assignedUsers.getAllUsers();
        ArrayList<UserInfo> currentUsers = assignedUsers.getCurrentUsers();

        //Get all labels and all instances in the system.
        ArrayList<ClassLabel> classLabels = data.getClassLabels();
        ArrayList<Instance> instances = data.getInstances();

        //Get maximum number of label per instance property and assign it into all instances in current dataset.
        int maxLabelPerInstance = data.getMaximumNumberOfLabelsPerInstance();
        for (Instance instance : instances) {
            instance.setMaxNumberOfLabel(maxLabelPerInstance);
        }

        //Create and set all instance performance metrics.
        new InstancePerformanceMetricsCreator(currentDatasetID, allUsers, classLabels, instances).invoke();
        new UserPerformanceMetricsCreator(datasetsInfo, currentUsers).invoke();

        //Create Dataset's performance metrics.
        DatasetPerformanceMetrics datasetPerformanceMetrics = new DatasetPerformanceMetrics(datasetsInfo.get(currentDatasetID - 1).getAssignUserID().size());

        //Random Labeling Simulation
        LabelingSimulation labelingSimulation = new LabelingSimulation(data, allUsers, currentUsers, instances, classLabels);
        labelingSimulation.userLogIn();
    }
}
