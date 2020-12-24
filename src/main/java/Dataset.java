import com.fasterxml.jackson.annotation.*;

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
    public String getDatasetName() {
        return datasetName;
    }
    public int getMaximumNumberOfLabelsPerInstance() {
        return maximumNumberOfLabelsPerInstance;
    }
    public ArrayList<ClassLabel> getClassLabels() {
        return classLabels;
    }
    public ArrayList<Instance> getInstances() {
        return instances;
    }
    public DatasetPerformanceMetrics getDatasetPerformanceMetrics() {
        return datasetPerformanceMetrics;
    }

    //Setter methods.
    public void setDatasetPerformanceMetrics(DatasetPerformanceMetrics datasetPerformanceMetrics) { this.datasetPerformanceMetrics = datasetPerformanceMetrics; }

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("dataset id")
    public void setDatasetID(int value) { this.datasetID = value; }

    @JsonProperty("dataset name")
    public void setDatasetName(String value) {
        this.datasetName = value;
    }

    @JsonProperty("maximum number of labels per instance")
    public void setMaximumNumberOfLabelsPerInstance(int value) {
        this.maximumNumberOfLabelsPerInstance = value;
    }

    @JsonProperty("class labels")
    public void setClassLabels(ArrayList<ClassLabel> classLabels) {
        this.classLabels = classLabels;
    }

    @JsonProperty("instances")
    public void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }
}