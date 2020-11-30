import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Instance {
    //Define basic Instance variable
    private int id;
    private String instance;
    private boolean hasLabeled = false;
    private ArrayList<LabelledInstance> labelPairs = new ArrayList<LabelledInstance>();

    public void addLabel(UserInfo userInfo,ClassLabel classLabel){
        if(hasLabeled==false){
            hasLabeled=true;
        }
        LabelledInstance labelledInstance = new LabelledInstance(userInfo,classLabel, LocalDate.now());
        labelPairs.add(labelledInstance);
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

    public boolean isHasLabeled() {
        return hasLabeled;
    }

    public void setHasLabeled(boolean hasLabeled) {
        this.hasLabeled = hasLabeled;
    }

    public ArrayList<LabelledInstance> getLabelPairs() {
        return labelPairs;
    }

    public void setLabelPairs(ArrayList<LabelledInstance> labelPairs) {
        this.labelPairs = labelPairs;
    }
}