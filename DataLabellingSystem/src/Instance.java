import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Instance {
    private int instanceID;
    private String instance;
    private int maxNumberOfLabels;
    private boolean hasLabeled;
    private ArrayList<LabelledInstance> labelPairs;

    Instance(int instanceID, String instance, int maxNumberOfLabels) {
        this.instanceID = instanceID;
        this.instance = instance;
        this.hasLabeled = false;
        labelPairs = new ArrayList<>();
    }

    public void addLabel(User user, ClassLabel classLabel) {
       this.hasLabeled = true;
        labelPairs.add(new LabelledInstance(this.instanceID, this.instance, this.maxNumberOfLabels, user, classLabel, LocalDate.now()));
    }

    public int getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(int instanceID) {
        this.instanceID = instanceID;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public int getMaxNumberOfLabels() {
        return maxNumberOfLabels;
    }

    public void setMaxNumberOfLabels(int maxNumberOfLabels) {
        this.maxNumberOfLabels = maxNumberOfLabels;
    }

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
