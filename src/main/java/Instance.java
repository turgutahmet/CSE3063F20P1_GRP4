import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class Instance {
    //Define basic Instance variable
    private int id;
    private String instance;
    private boolean canLabeled = true;
    private int maxNumberOfLabel;
    private int amountOfLabels;
    private ArrayList<LabeledInstance> userLabels = new ArrayList<LabeledInstance>();

    public void addLabel(UserInfo userInfo, ArrayList<ClassLabel> classLabels, Logger logger){
        //create new labeledInstance
        LabeledInstance labeledInstance = new LabeledInstance(id, instance, userInfo, classLabels, LocalDateTime.now(),logger);
        labeledInstance.setInstance(this.instance);
        // add the labeledInstance to userLabels and userInfo
        userLabels.add(labeledInstance);
        userInfo.addLabeledInstance(labeledInstance);
        //control max number of label
        amountOfLabels = 0;
        for (LabeledInstance userLabel : userLabels) {
            amountOfLabels += userLabel.getLabels().size();
        }
        if (maxNumberOfLabel == amountOfLabels) {
            canLabeled = false;
        }
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

    public int getAmountOfLabels() {
        return amountOfLabels;
    }

    public void setAmountOfLabels(int amountOfLabels) {
        this.amountOfLabels = amountOfLabels;
    }
}