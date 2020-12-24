import com.fasterxml.jackson.annotation.*;

import java.io.File;
import java.util.ArrayList;

public class Config {
    //Define basic User variable
    private int currentDatasetID;
    private ArrayList<UserInfo> userInfos;
    private ArrayList<DatasetInfo> datasetInfos;

    public Config readConfig() {
        return (Config) FileReader.getReader().read(new File("src/config.json"), this);
    }

    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public ArrayList<UserInfo> getUserInfos() {
        return userInfos;
    }
    @JsonProperty("users")
    public void setUserInfos(ArrayList<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public int getCurrentDatasetID() {
        return currentDatasetID;
    }
    @JsonProperty("current dataset id")
    public void setCurrentDatasetID(int currentDatasetID) {
        this.currentDatasetID = currentDatasetID;
    }

    public ArrayList<DatasetInfo> getDatasetInfos() {
        return datasetInfos;
    }
    @JsonProperty("datasets")
    public void setDatasetInfos(ArrayList<DatasetInfo> datasetInfos) {
        this.datasetInfos = datasetInfos;
    }
}