import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Dataset {
    //Dataset properties.
    private int datasetID; //Unique id of that dataset.
    private String datasetName; //Name of that dataset.
    private int maximumNumberOfLabelsPerInstance; //Maximum number of label which can be assigned instances in that dataset.
    private ArrayList<ClassLabel> classLabels; //All labels in that dataset.
    private ArrayList<Instance> instances; //All instances in that dataset.
    private DatasetPerformanceMetrics datasetPerformanceMetrics; //Dataset performance metrics.

    //Getter methods.
    public int getDatasetID() {
        return datasetID;
    }

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("dataset id")
    public void setDatasetID(int value) {
        this.datasetID = value;
    }

    public String getDatasetName() {
        return datasetName;
    }

    @JsonProperty("dataset name")
    public void setDatasetName(String value) {
        this.datasetName = value;
    }

    public int getMaximumNumberOfLabelsPerInstance() {
        return maximumNumberOfLabelsPerInstance;
    }

    @JsonProperty("maximum number of labels per instance")
    public void setMaximumNumberOfLabelsPerInstance(int value) {
        this.maximumNumberOfLabelsPerInstance = value;
    }

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

    public DatasetPerformanceMetrics getDatasetPerformanceMetrics() {
        return datasetPerformanceMetrics;
    }

    //Setter methods.
    public void setDatasetPerformanceMetrics(DatasetPerformanceMetrics datasetPerformanceMetrics) {
        this.datasetPerformanceMetrics = datasetPerformanceMetrics;
    }
}