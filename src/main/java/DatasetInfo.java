import com.fasterxml.jackson.annotation.JsonProperty;

public class DatasetInfo {
    private int datasetID;
    private String datasetName;
    private String datasetFilePath;

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
}
