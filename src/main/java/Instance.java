import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Instance {
    //Define basic Instance variable
    private int id;
    private String instance;
    private boolean canLabeled = true;
    private int maxNumberOfLabel;
    private ArrayList<LabelledInstance> userLabels = new ArrayList<LabelledInstance>();

    public void addLabel(UserInfo userInfo, ArrayList<ClassLabel> classLabels){
        LabelledInstance labelledInstance = new LabelledInstance(id, instance, userInfo, classLabels, LocalDateTime.now());
        labelledInstance.setInstance(this.instance);
        userLabels.add(labelledInstance);
        userInfo.addLabelledInstance(labelledInstance);
        if (maxNumberOfLabel == userLabels.size()) {
            canLabeled = false;
        }
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
        return userLabels;
    }

    public void setLabelPairs(ArrayList<LabelledInstance> labelPairs) {
        this.userLabels = labelPairs;
    }
}