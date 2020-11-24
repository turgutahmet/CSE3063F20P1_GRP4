import com.fasterxml.jackson.annotation.*;

public class User {
    //Define basic User variable
    private UserInfo[] userInfos;

    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public UserInfo[] getUsers() {
        return userInfos;
    }
    @JsonProperty("users")
    public void setUsers(UserInfo[] userInfos) {
        this.userInfos = userInfos;
    }
}
