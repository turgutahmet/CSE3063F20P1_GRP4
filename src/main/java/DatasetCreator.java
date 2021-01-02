import java.io.File;
import java.util.ArrayList;

public class DatasetCreator {
    private final int currentDatasetID;
    private final ArrayList<DatasetInfo> datasetsInfo;
    private final DatasetInfo currentDatasetInfo;

    public DatasetCreator(int currentDatasetID, ArrayList<DatasetInfo> datasetsInfo, DatasetInfo currentDatasetInfo) {
        this.currentDatasetID = currentDatasetID;
        this.datasetsInfo = datasetsInfo;
        this.currentDatasetInfo = currentDatasetInfo;
    }

    public Dataset invoke() {
        Dataset data = (Dataset) FileReader.getReader().read(new File(currentDatasetInfo.getDatasetFilePath()), new Dataset());
        data.setDatasetPerformanceMetrics(new DatasetPerformanceMetrics(datasetsInfo.get(currentDatasetID - 1).getAssignUserID().size()));
        return data;
    }
}

