import java.util.ArrayList;

public class InstancePerformanceMetrics {
    //InstancePerformanceMetrics properties.
    private ArrayList<LabelAssignment> allLabelAssignments; //Stores all label assignments.
    private int totalNumberOfLabelAssignments; //Number of all label assignments.
    private int numberOfUniqueLabelAssignments; //NUmber of unique label assignments.
    private int numberOfUniqueUsers; //Number of unique users who labeled that instance.
    private ClassLabelAndPercentage finalLabelAndPercentage; //That instance's final label and it's percentage.
    private ArrayList<ClassLabelAndPercentage> classLabelsAndPercentages; //List of all labels and theirs percentage.
    private float entropy; //Entropy of that instance.

    //InstancePerformanceMetrics constructors.
    public InstancePerformanceMetrics(boolean firstCreated) {
        this.allLabelAssignments = new ArrayList<>();
        this.totalNumberOfLabelAssignments = 0;
        this.numberOfUniqueLabelAssignments = 0;
        this.numberOfUniqueUsers = 0;
        this.classLabelsAndPercentages = new ArrayList<>();
        this.entropy = 0;
    }

    public InstancePerformanceMetrics() {
        this.allLabelAssignments = new ArrayList<>();
    }
}