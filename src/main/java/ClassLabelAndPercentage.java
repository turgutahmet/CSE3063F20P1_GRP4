import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassLabelAndPercentage {
    private ClassLabel classLabel;
    private float percentage;

    //ClassLabelAndPercentage constructor.

    public ClassLabelAndPercentage(ClassLabel classLabel, float percentage) {
        this.classLabel = classLabel;
        this.percentage = percentage;
    }

    public ClassLabelAndPercentage() {

    }

    //Getter methods.

    public ClassLabel getClassLabel() {
        return classLabel;
    }

    @JsonProperty("class label")
    public void setClassLabel(ClassLabel classLabel) {
        this.classLabel = classLabel;
    }

    public float getPercentage() {
        return percentage;
    }

    @JsonProperty("percentage")
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}