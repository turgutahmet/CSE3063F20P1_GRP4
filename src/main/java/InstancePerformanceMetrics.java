import com.fasterxml.jackson.annotation.JsonProperty;

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

    //Update properties of that object.
    public void updateProperties(ArrayList<ClassLabel> labels, int usersSize) {
        updateTotalNumberOfLabelAssignments();
        updateNumberOfUniqueLabelAssignments(labels.size());
        updateNumberOfUniqueUsers(usersSize);
        updateFinalLabelAndPercentage(labels);
        updateLabelsAndPercentage(labels);
        updateEntropy(labels.size());
    }

    //Adds new label assignment into allLabelAssignments list.
    public void addNewLabelAssignment(UserInfo user, ClassLabel label) {
        allLabelAssignments.add(new LabelAssignment(user, label));
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

    //Updates all labels percentages which are labeled.
    public void updateLabelsAndPercentage(ArrayList<ClassLabel> labels) {
        classLabelsAndPercentages.clear();
        int[] labelAssignmentTable = getLabelAssignmentTable(labels.size());

        for (int i = 0; i < labelAssignmentTable.length; i++) {
            float percentage = (float) (labelAssignmentTable[i] * 1.0 / totalNumberOfLabelAssignments) * 100;
            classLabelsAndPercentages.add(new ClassLabelAndPercentage(labels.get(i), percentage));
        }
    }

    //Updates entropy.
    public void updateEntropy(int labelsSize) {
        int[] labelAssignmentTable = getLabelAssignmentTable(labelsSize);
        int countOfUniqueLabelAssignments = 0;
        for (int i : labelAssignmentTable) {
            if (i > 0) {
                countOfUniqueLabelAssignments++;
            }
        }

        entropy = 0;
        for (int i : labelAssignmentTable) {
            if (i > 0) {
                float percentage = (float) (i * 1.0 / totalNumberOfLabelAssignments);
                if (percentage == 1 || countOfUniqueLabelAssignments == 1) {
                    entropy = 0;
                } else {
                    entropy -= (percentage) * (Math.log(percentage) / Math.log(countOfUniqueLabelAssignments));
                }

            }
        }
    }

    //Getter methods.
    public ArrayList<LabelAssignment> getAllLabelAssignments() {
        return allLabelAssignments;
    }

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("all label assignments")
    public void setAllLabelAssignments(ArrayList<LabelAssignment> allLabelAssignments) {
        this.allLabelAssignments = allLabelAssignments;
    }

    public int getTotalNumberOfLabelAssignments() {
        return totalNumberOfLabelAssignments;
    }

    @JsonProperty("total number of label assignments")
    public void setTotalNumberOfLabelAssignments(int totalNumberOfLabelAssignments) {
        this.totalNumberOfLabelAssignments = totalNumberOfLabelAssignments;
    }

    public int getNumberOfUniqueLabelAssignments() {
        return numberOfUniqueLabelAssignments;
    }

    @JsonProperty("total number of unique label assignments")
    public void setNumberOfUniqueLabelAssignments(int numberOfUniqueLabelAssignments) {
        this.numberOfUniqueLabelAssignments = numberOfUniqueLabelAssignments;
    }

    public int getNumberOfUniqueUsers() {
        return numberOfUniqueUsers;
    }

    @JsonProperty("number of unique users")
    public void setNumberOfUniqueUsers(int numberOfUniqueUsers) {
        this.numberOfUniqueUsers = numberOfUniqueUsers;
    }

    public ClassLabelAndPercentage getFinalLabelAndPercentage() {
        return finalLabelAndPercentage;
    }

    @JsonProperty("final label and percentage")
    public void setFinalLabelAndPercentage(ClassLabelAndPercentage finalLabelAndPercentage) {
        this.finalLabelAndPercentage = finalLabelAndPercentage;
    }

    public ArrayList<ClassLabelAndPercentage> getClassLabelsAndPercentages() {
        return classLabelsAndPercentages;
    }

    @JsonProperty("class labels and percentages")
    public void setClassLabelsAndPercentages(ArrayList<ClassLabelAndPercentage> classLabelsAndPercentages) {
        this.classLabelsAndPercentages = classLabelsAndPercentages;
    }

    public float getEntropy() {
        return entropy;
    }

    @JsonProperty("entropy")
    public void setEntropy(float entropy) {
        this.entropy = entropy;
    }
}