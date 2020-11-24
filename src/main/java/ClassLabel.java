import com.fasterxml.jackson.annotation.*;

public class ClassLabel {
    //Define basic ClassLabel variable
    private int labelID;
    private String labelText;

    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public int getLabelID() { return labelID; }
    @JsonProperty("label id")
    public void setLabelID(int value) { this.labelID = value; }

    public String getLabelText() { return labelText; }
    @JsonProperty("label text")
    public void setLabelText(String value) { this.labelText = value; }
}