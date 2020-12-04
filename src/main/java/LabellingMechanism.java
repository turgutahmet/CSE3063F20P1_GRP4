import java.util.*;
import org.apache.log4j.Logger;

public abstract class LabellingMechanism {

    private String labellingMechanismType;

    public abstract void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabel, Logger logger);

    public String getLabellingMechanismType() {
        return labellingMechanismType;
    }

    public void setLabellingMechanismType(String labellingMechanismType) {
        this.labellingMechanismType = labellingMechanismType;
    }
}
