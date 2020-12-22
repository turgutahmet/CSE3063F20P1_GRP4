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

    //Adds new label assignment into allLabelAssignments list.
    public void addNewLabelAssignment(String username, String label, int labelID, int userID) {
        allLabelAssignments.add(new LabelAssignment(username, label, labelID, userID));
    }

    //Updates total number of label assignments.
    public void updateTotalNumberOfLabelAssignments() {
        totalNumberOfLabelAssignments = allLabelAssignments.size();
    }

    //Updates number of unique label assignments.
    public void updateNumberOfUniqueLabelAssignments(int labelsSize) {
        int[] labelAssignmentsTable = getLabelAssignmentTable(labelsSize);

        //Count number of unique label assignments.
        int count = 0;
        for (int i : labelAssignmentsTable) {
            if (i > 0) {
                count++;
            }
        }

        numberOfUniqueLabelAssignments = count;
    }

    //Generates label assignments table.
    private int[] getLabelAssignmentTable(int labelsSize) {
        //Create label assignments table and set all indexes to zero.
        int[] labelAssignmentsTable = new int[labelsSize];
        for (int i : labelAssignmentsTable) {
            i = 0;
        }

        //Iterate all label assignments and fill the table.
        for (LabelAssignment labelAssignment : allLabelAssignments) {
            int labelID = labelAssignment.getLabelID();
            labelAssignmentsTable[labelID - 1]++;
        }
        return labelAssignmentsTable;
    }

}