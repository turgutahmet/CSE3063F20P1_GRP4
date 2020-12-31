import java.time.LocalDateTime;
import java.util.ArrayList;

public class LabeledInstance extends Instance {
    //LabeledInstance properties.
    private final UserInfo whoLabeled ; //Labeled by that user.
    private final ArrayList<LabelCounter> labelCounters = new ArrayList<>(); //What labels did that user use.
    private final LocalDateTime date; //Creation date.

    //LabeledInstance constructor.
    LabeledInstance(int id, String instance, UserInfo userInfo, LocalDateTime localDate) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabeled = userInfo;
        this.date = localDate;
    }

    //Update labels list.
    public void updateLabel (LabelCounter labelCounter){
        //Check: Is that label exist in labels list?
        for (LabelCounter labelCounter1 : labelCounters) {
            if(labelCounter1.getLabel().getLabelID() == labelCounter.getLabel().getLabelID()){ //If it exist, increment count of this label
                labelCounter1.incrementCount();
                return;
            }
        }
        //If there is not exist, add it into labels list
        labelCounters.add(labelCounter);
    }

    public void addLabel(LabelCounter labelCounter) {
        labelCounters.add(labelCounter);
    }

    //Getter methods.
    public UserInfo getWhoLabeled() {
        return whoLabeled;
    }
    public ArrayList<LabelCounter> getLabels() {
        return labelCounters;
    }
    public LocalDateTime getDate() {
        return date;
    }

}
