import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

public class UserInfo {
    //Define basic UserInfo variable
    private int userID;
    private String userName;
    private String userType;

    public Instance labelInstance(Instance instance, ArrayList<ClassLabel> classLabels, LabellingMechanism labellingMechanism){
        ClassLabel classLabel= labellingMechanism.labelInstanceWithUser(this, instance, classLabels);
        Instance labelledInstance = instance.addLabel(this, classLabel);
        return labelledInstance;
    }

    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public int getUserID() {
        return userID;
    }
    @JsonProperty("user id")
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }
    @JsonProperty("user name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }
    @JsonProperty("user type")
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
