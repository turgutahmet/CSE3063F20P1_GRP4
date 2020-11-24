public class Dataset {
    //Define basic Dataset variable
    private int datasetID;
    private String datasetName;
    private int maximumNumberOfLabelsPerInstance;
    private ClassLabel[] classLabels;
    private Instance[] instances;

    //Variables getter setter methods
    public int getDatasetID() { return datasetID; }
    public void setDatasetID(int value) { this.datasetID = value; }

    public String getDatasetName() { return datasetName; }
    public void setDatasetName(String value) { this.datasetName = value; }

    public int getMaximumNumberOfLabelsPerInstance() { return maximumNumberOfLabelsPerInstance; }
    public void setMaximumNumberOfLabelsPerInstance(int value) { this.maximumNumberOfLabelsPerInstance = value; }

    public ClassLabel[] getClassLabels() { return classLabels; }
    public void setClassLabels(ClassLabel[] value) { this.classLabels = value; }

    public Instance[] getInstances() { return instances; }
    public void setInstances(Instance[] value) { this.instances = value; }
}