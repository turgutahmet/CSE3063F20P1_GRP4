import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

public class Instance {
    //Instance properties.
    private int id; //Unique id of that instance.
    private String instance; //Instance text.
    private boolean canLabeled = true; //Is that instance can be labeled?
    private int maxNumberOfLabel; //Maximum number of label can be assigned to that instance.
    private final ArrayList<LabeledInstance> userLabels = new ArrayList<>(); //LabeledInstances records.
    private final ArrayList<ClassLabel> allLabels = new ArrayList<>(); //All assigned unique labels.
    private InstancePerformanceMetrics instancePerformanceMetrics; //Instance performance metrics.

    //Create new LabeledInstance object and return it to labeling mechanism.
    public LabeledInstance createLabeledInstance(UserInfo userInfo) {
        //Create new labeledInstance and return it
        return new LabeledInstance(this.id, this.instance, userInfo, LocalDateTime.now());
    }

    //Updates userLabels list according previous runs.
    public void updateUserLabels(ArrayList<UserInfo> userInfos,ArrayList<ClassLabel> classLabels ){
        ArrayList<LabelAssignment> allLabelAssignments = instancePerformanceMetrics.getAllLabelAssignments();
        for (LabelAssignment allLabelAssignment : allLabelAssignments) {
            UserInfo user = userInfos.get(allLabelAssignment.getUserID()-1);
            Label label = new Label(classLabels.get(allLabelAssignment.getLabelID()-1));
            LabeledInstance labeledInstance = createLabeledInstance(user);
            addUserLabel(labeledInstance,label);
        }
        checkAmountOfLabels();
    }

    //Updates that instance's properties.
    public void updateInstance(LabeledInstance labeledInstance, Label label) {
        //Add labeledInstance into userLabels list
        addUserLabel(labeledInstance, label);
        //Add label into allLabels list
        addLabel(label.getLabel());
        //Update canLabeled status of this instance
        checkAmountOfLabels();
    }

    //Adds new LabeledInstance object into userLabels list or update existing record.
    private void addUserLabel(LabeledInstance labeledInstance, Label label) {
        //Check: Is there any record with related that user?
        LabeledInstance existingLabeledInstance = checkUserLabels(labeledInstance.getWhoLabeled());
        //If there is
        if (existingLabeledInstance != null) {
            //Check: Is that user labeled this instance with same label before or not?
            existingLabeledInstance.updateLabel(label);
            //Add that labeled instance record into user
            existingLabeledInstance.getWhoLabeled().addLabeledInstance(labeledInstance);
            return;
        } else {
            labeledInstance.addLabel(label);
            userLabels.add(labeledInstance);
        }
        //Add that labeled instance record into user
        labeledInstance.getWhoLabeled().addLabeledInstance(labeledInstance);
    }

    //Checks is there any record related with that user?
    private LabeledInstance checkUserLabels(UserInfo userInfo) {
        for (LabeledInstance userLabel : userLabels) {
            if (userLabel.getWhoLabeled() == userInfo) {
                return userLabel;
            }
        }
        return null;
    }

    //Add new Label object into allLabels list.
    private void addLabel(ClassLabel label) {
        allLabels.add(label);
        removeDuplicates(allLabels);
    }

    //If there is duplicate in allLabels list remove duplicate entries.
    private void removeDuplicates(ArrayList<ClassLabel> list) {
        // Create a new LinkedHashSet
        Set<ClassLabel> set = new LinkedHashSet<>(list);
        // Clear the list
        list.clear();
        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);
    }

    //Check amount of labels and depending on it set canLabeled property.
    private void checkAmountOfLabels() {
        int count = 0;
        for (LabeledInstance userLabel : userLabels) {
            for (Label userLabelLabel : userLabel.getLabels()) {
                count += userLabelLabel.getCount();
            }
        }
        if (count >= maxNumberOfLabel) {
            canLabeled = false;
        }
    }

    //Getter methods.
    public int getID() { return id; }
    public String getInstance() { return instance; }
    public boolean isCanLabeled() { return canLabeled; }
    public ArrayList<LabeledInstance> getUserLabels() { return userLabels; }
    public InstancePerformanceMetrics getInstancePerformanceMetrics() { return instancePerformanceMetrics; }

    //Setter methods.
    public void setMaxNumberOfLabel(int maxNumberOfLabel) { this.maxNumberOfLabel = maxNumberOfLabel; }
    public void setInstancePerformanceMetrics(InstancePerformanceMetrics instancePerformanceMetrics) { this.instancePerformanceMetrics = instancePerformanceMetrics; }

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("id")
    public void setID(int value) { this.id = value; }

    @JsonProperty("instance")
    public void setInstance(String value) { this.instance = value; }
}