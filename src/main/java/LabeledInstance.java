import java.time.LocalDateTime;
import java.util.ArrayList;

public class LabeledInstance extends Instance {
    //LabeledInstance properties.
    private final BotInfo whoLabeled ; //Labeled by that user.
    private final ArrayList<Label> labels = new ArrayList<>(); //What labels did that user use.
    private final LocalDateTime date; //Creation date.

    //LabeledInstance constructor.
    LabeledInstance(int id, String instance, BotInfo botInfo, LocalDateTime localDate) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabeled = botInfo;
        this.date = localDate;
    }

    //Update labels list.
    public void updateLabel (Label label){
        //Check: Is that label exist in labels list?
        for (Label label1 : labels) {
            if(label1.getLabel().getLabelID() == label.getLabel().getLabelID()){ //If it exist, increment count of this label
                label1.incrementCount();
                return;
            }
        }
        //If there is not exist, add it into labels list
        labels.add(label);
    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    //Getter methods.
    public BotInfo getWhoLabeled() {
        return whoLabeled;
    }
    public ArrayList<Label> getLabels() {
        return labels;
    }
    public LocalDateTime getDate() {
        return date;
    }

}
