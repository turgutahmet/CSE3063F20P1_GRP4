import java.io.File;
import java.util.ArrayList;

public class DatasetCreator {
    private int currentDatasetID;
    private ArrayList<DatasetInfo> datasetsInfo;
    private DatasetInfo currentDatasetInfo;

    public DatasetCreator(int currentDatasetID, ArrayList<DatasetInfo> datasetsInfo, DatasetInfo currentDatasetInfo) {
        this.currentDatasetID = currentDatasetID;
        this.datasetsInfo = datasetsInfo;
        this.currentDatasetInfo = currentDatasetInfo;
    }

    public Dataset invoke() {
        Dataset data = (Dataset) FileReader.getReader().read(new File(currentDatasetInfo.getDatasetFilePath()), new Dataset());
        data.setDatasetPerformanceMetrics(new DatasetPerformanceMetrics(datasetsInfo.get(currentDatasetID-1).getAssignUserID().size()));
        return data;
    }
}

