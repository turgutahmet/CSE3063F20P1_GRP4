import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.log4j.Logger;

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


    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public int getID() { return id; }
    @JsonProperty("id")
    public void setID(int value) { this.id = value; }

    public String getInstance() { return instance; }
    @JsonProperty("instance")
    public void setInstance(String value) { this.instance = value; }

    public boolean isCanLabeled() {
        return canLabeled;
    }

    public void setCanLabeled(boolean canLabeled) {
        this.canLabeled = canLabeled;
    }

    public int getMaxNumberOfLabel() {
        return maxNumberOfLabel;
    }

    public void setMaxNumberOfLabel(int maxNumberOfLabel) {
        this.maxNumberOfLabel = maxNumberOfLabel;
    }

    public ArrayList<LabeledInstance> getLabelPairs() {
        return userLabels;
    }


}