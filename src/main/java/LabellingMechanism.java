import java.util.*;
import org.apache.log4j.Logger;

public abstract class LabellingMechanism {
    //Variable of the LabellingMechanism class.
    private String labellingMechanismType;
    //Abstract method that will be used in other labelling mechanism classes.
    public abstract void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabel, Logger logger);
    //Get&set methods.
    public String getLabellingMechanismType() {
        return labellingMechanismType;
    }

    public void setLabellingMechanismType(String labellingMechanismType) { this.labellingMechanismType = labellingMechanismType; }
}
