import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Instance {
    //Define basic Instance variable
    private int id;
    private String instance;
    private boolean canLabeled = true;
    private int maxNumberOfLabel;
    private ArrayList<LabelledInstance> labelPairs = new ArrayList<LabelledInstance>();

    public Instance addLabel(UserInfo userInfo, ClassLabel classLabel){
        LabelledInstance labelledInstance = new LabelledInstance(id, instance, userInfo, classLabel, LocalDate.now());
        labelledInstance.setInstance(this.instance);
        labelPairs.add(labelledInstance);
        if (maxNumberOfLabel == labelPairs.size()) {
            canLabeled = false;
        }
        return this;
    }
    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public int getID() { return id; }
    @JsonProperty("id")
    public void setID(int value) { this.id = value; }

    public String getInstance() {

        return instance; }
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

    public ArrayList<LabelledInstance> getLabelPairs() {
        return labelPairs;
    }

    public void setLabelPairs(ArrayList<LabelledInstance> labelPairs) {
        this.labelPairs = labelPairs;
    }
}