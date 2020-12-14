import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

public class User {
    //Define basic User variable
    private ArrayList<UserInfo> userInfos;
    private Double consistencyCheckProbability;

    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public ArrayList<UserInfo> getUserInfos() {
        return userInfos;
    }
    @JsonProperty("users")
    public void setUserInfos(ArrayList<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Double getConsistencyCheckProbability() { return consistencyCheckProbability; }
    @JsonProperty("ConsistencyCheckProbability")
    public void setConsistencyCheckProbability(Double consistencyCheckProbability) { this.consistencyCheckProbability = consistencyCheckProbability; }
}
