public abstract class LabellingMechanism {

    private String labellingMechanismType;

    public abstract ClassLabel labelInstanceWithUser( UserInfo userInfo, Instance instance, ClassLabel[] classLabel);

    public String getLabellingMechanismType() {
        return labellingMechanismType;
    }

    public void setLabellingMechanismType(String labellingMechanismType) {
        this.labellingMechanismType = labellingMechanismType;
    }
}
