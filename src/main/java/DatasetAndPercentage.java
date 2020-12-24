public class DatasetAndPercentage {
    private String datasetName;
    private float percentage;

    public DatasetAndPercentage(String datasetName, float percentage) {
        this.datasetName = datasetName;
        this.percentage = percentage;
    }

    public DatasetAndPercentage() {
    }

    public String getDatasetName() {
        return datasetName;
    }

    public float getPercentage() {
        return percentage;
    }
}