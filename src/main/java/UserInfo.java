import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UserInfo {
    private final ArrayList<LabeledInstance> labeledInstances = new ArrayList<>(); //List of labeled instances by that user.
    //BotInfo properties.
    private int userID; //Unique id of that user.
    private String username; //Username of that user.
    private String userType; //Type of that user.
    private String password; //Password of that user
    private double consistencyCheckProbability; //Probability of labeling same instance.
    private UserPerformanceMetrics userPerformanceMetrics; //User performance metrics.

    //Add new entry into labeledInstances list or update an entry.
    public void addLabeledInstance(LabeledInstance labeledInstance) {
        //If there is any entry related with that instance update it.
        for (int i = 0; i < labeledInstances.size(); i++) {
            if (labeledInstances.get(i).getID() == labeledInstance.getID()) {
                return;
            }
        }
        //If there isn't add new entry.
        labeledInstances.add(labeledInstance);
    }

    //Getter methods.
    public int getUserID() {
        return userID;
    }

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("user id")
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    @JsonProperty("user name")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    @JsonProperty("user type")
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }

    @JsonProperty("ConsistencyCheckProbability")
    public void setConsistencyCheckProbability(Double consistencyCheckProbability) {
        this.consistencyCheckProbability = consistencyCheckProbability;
    }

    @JsonBackReference(value = "user-performance-metrics")
    public UserPerformanceMetrics getUserPerformanceMetrics() {
        return userPerformanceMetrics;
    }

    //Setter methods.
    public void setUserPerformanceMetrics(UserPerformanceMetrics userPerformanceMetrics) {
        this.userPerformanceMetrics = userPerformanceMetrics;
    }

    @JsonBackReference(value = "labeled-instances")
    public ArrayList<LabeledInstance> getLabeledInstances() {
        return labeledInstances;
    }
}
