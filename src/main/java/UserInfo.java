import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
    private String userName;
    private String password;
    private float consistentCheckProbability;

    public String getUserName() {
        return userName;
    }
    @JsonProperty("user name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public float getConsistentCheckProbability() {
        return consistentCheckProbability;
    }
    @JsonProperty("ConsistencyCheckProbability")
    public void setConsistentCheckProbability(float consistentCheckProbability) {
        this.consistentCheckProbability = consistentCheckProbability;
    }
}
