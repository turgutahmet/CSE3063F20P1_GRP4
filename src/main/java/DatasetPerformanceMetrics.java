import java.util.*;
public class DatasetPerformanceMetrics {
    private float percentage;
    private ArrayList<ClassLabelAndPercentage> distributionOfFinalInstanceLabels;
    private ArrayList<LabelAndNumberOfUniqueInstance> labelAndNumberOfUniqueInstances;
    private int numberOfUserAssigned;
    private ArrayList<UserAndPercentage> assignedUsersAndCompletenessPercentage;
    private ArrayList<UserAndPercentage> assignedUsersAndConsistencyPercentage;

    public DatasetPerformanceMetrics(int numberOfUserAssigned) {
        this.percentage = 0;
        this.distributionOfFinalInstanceLabels = new ArrayList<>();
        this.labelAndNumberOfUniqueInstances = new ArrayList<>();
        this.numberOfUserAssigned = numberOfUserAssigned;
        this.assignedUsersAndCompletenessPercentage = new ArrayList<>();
        this.assignedUsersAndConsistencyPercentage = new ArrayList<>();
    }


    public float getPercentage() {
        return percentage;
    }
    public ArrayList<ClassLabelAndPercentage> getDistributionOfFinalInstanceLabels() { return distributionOfFinalInstanceLabels; }
    public ArrayList<LabelAndNumberOfUniqueInstance> getLabelAndNumberOfUniqueInstances() { return labelAndNumberOfUniqueInstances; }
    public int getNumberOfUserAssigned() {
        return numberOfUserAssigned;
    }
    public ArrayList<UserAndPercentage> getAssignedUsersAndCompletenessPercentage() { return assignedUsersAndCompletenessPercentage; }
    public ArrayList<UserAndPercentage> getAssignedUsersAndConsistencyPercentage() { return assignedUsersAndConsistencyPercentage; }
}
