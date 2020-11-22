public class User {
    private int userID;
    private String username;
    private String userType;

    User(int userID, String username, String userType) {
        this.userID = userID;
        this.username = username;
        this.userType = userType;
    }

    public void labelInstance(Instance instance, ClassLabel[] classLabel, LabellingMechanism labellingMechanism) {
        ClassLabel label = labellingMechanism.labelInstanceWithUser(this, instance, classLabel);
        instance.addLabel(this, label);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
