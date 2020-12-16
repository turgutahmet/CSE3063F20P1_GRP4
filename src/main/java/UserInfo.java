import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

public class UserInfo {
    //Define basic UserInfo variable
    private int userID;
    private String userName;
    private String userType;
    private Double consistencyCheckProbability;
    private ArrayList<Integer> assignDatasets;
    private ArrayList<LabeledInstance> labeledInstances = new ArrayList<>() ;
    //Json property: The feature in which variables in json file which variables we should assign in our model.

    public void addLabeledInstance (LabeledInstance labeledInstance){
        labeledInstances.add(labeledInstance);
    }
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

    public ArrayList<LabeledInstance> getLabeledInstances() {
        return labeledInstances;
    }

    public void setLabeledInstances(ArrayList<LabeledInstance> labelledInstances) { this.labeledInstances = labelledInstances; }

    public String getUserType() {
        return userType;
    }
    @JsonProperty("user type")
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Double getConsistencyCheckProbability() { return consistencyCheckProbability; }
    @JsonProperty("ConsistencyCheckProbability")
    public void setConsistencyCheckProbability(Double consistencyCheckProbability) { this.consistencyCheckProbability = consistencyCheckProbability; }

    public ArrayList<Integer> getAssignDatasets() {
        return assignDatasets;
    }
    @JsonProperty("datasets")
    public void setAssignDatasets(ArrayList<Integer> assignDatasets) {
        this.assignDatasets = assignDatasets;
    }
}
