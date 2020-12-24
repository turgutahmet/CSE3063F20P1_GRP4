import java.util.ArrayList;

public class SetupDataset {
    private Config config;
    private int currentDatasetID;
    private ArrayList<DatasetInfo> datasetsInfo;
    private DatasetInfo currentDatasetInfo;

    public SetupDataset(Config config) {
        this.config = config;
    }

    public int getCurrentDatasetID() {
        return currentDatasetID;
    }

    public ArrayList<DatasetInfo> getDatasetsInfo() {
        return datasetsInfo;
    }

    public DatasetInfo getCurrentDatasetInfo() {
        return currentDatasetInfo;
    }

    public SetupDataset invoke() {
        currentDatasetID = config.getCurrentDatasetID();
        datasetsInfo = config.getDatasetInfos();
        currentDatasetInfo = new DatasetInfo();
        for (DatasetInfo datasetInfo : datasetsInfo) {
            if (currentDatasetID == datasetInfo.getDatasetID()) {
                currentDatasetInfo = datasetInfo;
                break;
            }
        }
        return this;
    }
}