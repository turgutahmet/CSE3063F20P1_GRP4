import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelAssignment {
    //LabelAssignment properties.
    private final UserInfo user;
    private final ClassLabel label;

    //LabelAssignment constructors.
    public LabelAssignment(UserInfo user, ClassLabel label) {
        this.user = user;
        this.label = label;
    }

    public LabelAssignment() {
        user = new UserInfo();
        label = new ClassLabel();
    }

    //Getter methods.
    public String getUsername() {
        return user.getUsername();
    }

    //Setter methods.
    @JsonProperty("username")
    public void setUsername(String username) {
        this.user.setUsername(username);
    }

    public String getLabelText() {
        return label.getLabelText();
    }

    @JsonProperty("label text")
    public void setLabelText(String labelText) {
        this.label.setLabelText(labelText);
    }

    public int getLabelID() {
        return label.getLabelID();
    }

    @JsonProperty("label id")
    public void setLabelID(int labelID) {
        this.label.setLabelID(labelID);
    }

    public int getUserID() {
        return user.getUserID();
    }

    @JsonProperty("user id")
    public void setUserID(int userID) {
        this.user.setUserID(userID);
    }
}
