import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelAssignment {
    //LabelAssignment properties.
    private UserInfo user;
    private ClassLabel label;

    //LabelAssignment constructors.
    public LabelAssignment(UserInfo user, ClassLabel label) {
        this.user = user;
        this.label = label;
    }
    public LabelAssignment(){
        user = new UserInfo();
        label = new ClassLabel();
    }

    //Getter methods.
    public String getUsername() { return user.getUsername(); }
    public String getLabelText() { return label.getLabelText(); }
    public int getLabelID() { return label.getLabelID(); }
    public int getUserID() { return user.getUserID(); }

    //Setter methods.
    @JsonProperty("username")
    public void setUsername(String username) {
        this.user.setUsername(username);
    }
    @JsonProperty("label text")
    public void setLabelText(String labelText) {
        this.label.setLabelText(labelText);
    }
    @JsonProperty("label id")
    public void setLabelID(int labelID) {
        this.label.setLabelID(labelID);
    }
    @JsonProperty("user id")
    public void setUserID(int userID) {
        this.user.setUserID(userID);
    }
}
