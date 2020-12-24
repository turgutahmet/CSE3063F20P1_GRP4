import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DatasetInfo {
    private int datasetID;
    private String datasetName;
    private String datasetFilePath;
    private ArrayList<Integer> assignUserID;

    public int getDatasetID() {
        return datasetID;
    }
    @JsonProperty("dataset id")
    public void setDatasetID(int datasetID) {
        this.datasetID = datasetID;
    }

    public String getDatasetName() {
        return datasetName;
    }
    @JsonProperty("dataset name")
    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDatasetFilePath() {
        return datasetFilePath;
    }
    @JsonProperty("dataset file path")
    public void setDatasetFilePath(String datasetFilePath) {
        this.datasetFilePath = datasetFilePath;
    }

    public ArrayList<Integer> getAssignUserID() {
        return assignUserID;
    }
    @JsonProperty("assign users")
    public void setAssignUserID(ArrayList<Integer> assignUserID) {
        this.assignUserID = assignUserID;
    }
}
