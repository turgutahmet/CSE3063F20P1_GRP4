import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

public class Instance {
    //Define basic Instance variable
    private int id;
    private String instance;
    private boolean canLabeled = true;
    private int maxNumberOfLabel;
    private ArrayList<LabeledInstance> userLabels = new ArrayList<>();
    private ArrayList<ClassLabel> allLabels = new ArrayList<>();

    //Create new LabeledInstance object and return it to labeling mechanism.
    public LabeledInstance createLabeledInstance(UserInfo userInfo) {
        //Create new labeledInstance and return it
        return new LabeledInstance(this.id, this.instance, userInfo, LocalDateTime.now());
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

    public void setLabelPairs(ArrayList<LabeledInstance> labelPairs) {
        this.userLabels = labelPairs;
    }

}