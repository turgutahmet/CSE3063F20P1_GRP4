import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

public class UserInfo {
    //UserInfo properties.
    private int userID; //Unique id of that user.
    private String username; //Username of that user.
    private String userType; //Type of that user.
    private double consistencyCheckProbability; //Probability of labeling same instance.
    private final ArrayList<LabeledInstance> labeledInstances = new ArrayList<>(); //List of labeled instances by that user.

    //Add new entry into labeledInstances list or update an entry.
    public void addLabeledInstance (LabeledInstance labeledInstance){
        //If there is any entry related with that instance update it.
        for (int i = 0; i < labeledInstances.size(); i++) {
            if (labeledInstances.get(i).getID() == labeledInstance.getID()) {
                labeledInstances.set(i, labeledInstance);
                return;
            }
        }
        //If there isn't add new entry.
        labeledInstances.add(labeledInstance);
    }

    //Getter methods.
    public int getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getUserType() { return userType; }
    public double getConsistencyCheckProbability() { return consistencyCheckProbability; }
    public ArrayList<LabeledInstance> getLabeledInstances() { return labeledInstances; }

    //Setter methods.

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("user id")
    public void setUserID(int userID) { this.userID = userID; }

    @JsonProperty("user name")
    public void setUsername(String username) { this.username = username; }

    @JsonProperty("user type")
    public void setUserType(String userType) { this.userType = userType; }

    @JsonProperty("ConsistencyCheckProbability")
    public void setConsistencyCheckProbability(Double consistencyCheckProbability) { this.consistencyCheckProbability = consistencyCheckProbability; }
}
