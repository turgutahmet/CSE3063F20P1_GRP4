import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

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