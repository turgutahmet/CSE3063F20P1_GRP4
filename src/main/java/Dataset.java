import com.fasterxml.jackson.annotation.*;

public class Dataset {
    //Define basic Dataset variable
    private int datasetID;
    private String datasetName;
    private int maximumNumberOfLabelsPerInstance;
    private ClassLabel[] classLabels;
    private Instance[] instances;

    //Json property: The feature in which variables in json file which variables we should assign in our model.


    //Variables getter setter methods
    public int getDatasetID() { return datasetID; }
    @JsonProperty("dataset id")
    public void setDatasetID(int value) { this.datasetID = value; }

    public String getDatasetName() { return datasetName; }
    @JsonProperty("dataset name")
    public void setDatasetName(String value) { this.datasetName = value; }

    public int getMaximumNumberOfLabelsPerInstance() { return maximumNumberOfLabelsPerInstance; }
    @JsonProperty("maximum number of labels per instance")
    public void setMaximumNumberOfLabelsPerInstance(int value) { this.maximumNumberOfLabelsPerInstance = value; }

    public ClassLabel[] getClassLabels() { return classLabels; }
    @JsonProperty("class labels")
    public void setClassLabels(ClassLabel[] value) { this.classLabels = value; }

    public Instance[] getInstances() { return instances; }
    @JsonProperty("instances")
    public void setInstances(Instance[] value) { this.instances = value; }
}