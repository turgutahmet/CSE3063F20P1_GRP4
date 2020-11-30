import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

public class Dataset {
    //Define basic Dataset variable
    private int datasetID;
    private String datasetName;
    private int maximumNumberOfLabelsPerInstance;
    private ArrayList<ClassLabel> classLabels;
    private ArrayList<Instance> instances;

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

    public ArrayList<ClassLabel> getClassLabels() {
        return classLabels;
    }
     @JsonProperty("class labels")
    public void setClassLabels(ArrayList<ClassLabel> classLabels) {
        this.classLabels = classLabels;
    }

    public ArrayList<Instance> getInstances() {
        return instances;
    }
    @JsonProperty("instances")
    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }
}