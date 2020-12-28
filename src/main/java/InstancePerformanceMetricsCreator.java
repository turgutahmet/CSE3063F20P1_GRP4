import java.io.File;
import java.util.ArrayList;

public class InstancePerformanceMetricsCreator {
    private int currentDatasetID;
    private ArrayList<BotInfo> allUsers;
    private ArrayList<ClassLabel> classLabels;
    private ArrayList<Instance> instances;

    public InstancePerformanceMetricsCreator(int currentDatasetID, ArrayList<BotInfo> allUsers, ArrayList<ClassLabel> classLabels, ArrayList<Instance> instances) {
        this.currentDatasetID = currentDatasetID;
        this.allUsers = allUsers;
        this.classLabels = classLabels;
        this.instances = instances;
    }

    public void invoke() {
        for (Instance instance : instances) {
            //Create a a File object related to that instance's InstancePerformanceMetrics.
            File dir = new File("./database/");
            File file = new File(dir, "Instance"+ instance.getID() + "_Dataset" + currentDatasetID + ".json");
            InstancePerformanceMetrics instancePerformanceMetrics;
            if (file.exists()) {
                instancePerformanceMetrics =  new InstancePerformanceMetrics();
                instancePerformanceMetrics = (InstancePerformanceMetrics) FileReader.getReader().read(new File(dir, file.getName()), instancePerformanceMetrics);
                instancePerformanceMetrics.updateProperties(classLabels, allUsers.size());
            } else {
                instancePerformanceMetrics = new InstancePerformanceMetrics(true);
            }
            //Set user's user performance metrics.
            instance.setInstancePerformanceMetrics(instancePerformanceMetrics);
            instance.updateUserLabels(allUsers,classLabels);
        }
    }
}
