import java.util.*;

public abstract class LabelingMechanism {
    //Variable of the LabellingMechanism class.
    private String labelingMechanismType;
    //Abstract method that will be used in other labelling mechanism classes.
    public abstract void labelInstanceWithUser(BotInfo botInfo, Instance instance, ArrayList<ClassLabel> classLabel);
    //Get&set methods.
    public String getLabelingMechanismType() {
        return labelingMechanismType;
    }

    public void setLabelingMechanismType(String labellingMechanismType) { this.labelingMechanismType = labellingMechanismType; }
}
