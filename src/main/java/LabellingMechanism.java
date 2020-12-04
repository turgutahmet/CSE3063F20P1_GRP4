import java.util.*;
public abstract class LabellingMechanism {

    private String labellingMechanismType;

    public abstract void labelInstanceWithUser( UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabel);

    public String getLabellingMechanismType() {
        return labellingMechanismType;
    }

    public void setLabellingMechanismType(String labellingMechanismType) {
        this.labellingMechanismType = labellingMechanismType;
    }
}
