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
    //Updates number of unique users who are labeled that instance.
    public void updateNumberOfUniqueUsers(int usersSize) {
        int[] usersTable = new int[usersSize];
        for (int i : usersTable) {
            i = 0;
        }

        for (LabelAssignment labelAssignment : allLabelAssignments) {
            usersTable[labelAssignment.getUserID() - 1]++;
        }

        int count = 0;
        for (int i : usersTable) {
            if (i > 0) {
                count++;
            }
        }

        numberOfUniqueUsers = count;
    }

    //Updates finalLabel and it's percentage.
    public void updateFinalLabelAndPercentage(ArrayList<ClassLabel> labels) {
        int[] labelAssignmentTable = getLabelAssignmentTable(labels.size());

        //Get count most assigned label(s).
        int max = 0;
        for (int i : labelAssignmentTable) {
            if (i > max) {
                max = i;
            }
        }

        //Get most assigned label(s).
        ArrayList<Integer> mostAssignedLabels = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            if (labelAssignmentTable[i] == max) {
                mostAssignedLabels.add(i);
            }
        }

        //If there is more than one label in mostAssignedLabelsList select one randomly.
        int finalLabelID = mostAssignedLabels.get((int) (Math.random() * mostAssignedLabels.size()));
        //Calculate percentage.
        float percentage = (float) (max * 1.0 / totalNumberOfLabelAssignments) * 100;

        //Create FinalLabel object.
        ClassLabelAndPercentage finalLabelAndPercentage = new ClassLabelAndPercentage(labels.get(finalLabelID), percentage);
        this.finalLabelAndPercentage = finalLabelAndPercentage;
    }
}