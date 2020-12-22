import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelAssignment {
    //LabelAssignment properties.
    private String username; //Username of who assigned label.
    private String labelText; //Assigned label text.
    private int labelID; //Assigned label id.
    private int userID; //User's id.

    //LabelAssignment constructors.
    public LabelAssignment(String username, String labelText, int labelID, int userID) {
        this.username = username;
        this.labelText = labelText;
        this.labelID = labelID;
        this.userID = userID;
    }
    public LabelAssignment(){

    }

    //Getter methods.
    public String getUsername() { return username; }
    public String getLabelText() { return labelText; }
    public int getLabelID() { return labelID; }
    public int getUserID() { return userID; }

    //Setter methods.
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }
    @JsonProperty("label text")
    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
    @JsonProperty("label id")
    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }
    @JsonProperty("user id")
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
