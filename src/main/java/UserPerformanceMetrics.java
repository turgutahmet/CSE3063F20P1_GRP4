import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UserPerformanceMetrics {
    private int numberOfDataset; //User's number of assigned datasets.
    private float[] datasetsCompletenessPercentage; //Datasets' completeness percentages.
    private int totalNumberOfInstanceLabelled; //Total number of instances labeled.
    private int totalNumberOfUniqueInstance; //Total number of unique instances labeled.
    private float consistencyPercentage; //User's consistency percentage.
    private ArrayList<Float> times; //All labeling processes' finishing time.
    private float avgTime; //Average time of all labeling processes'.
    private float stdTime; //Standard dev. of all labeling processes'.

    //UserPerformanceMetrics constructors.
    public UserPerformanceMetrics(int numberOfDatasets) {
        this.numberOfDataset = numberOfDatasets;
        this.datasetsCompletenessPercentage = new float[numberOfDatasets];
        this.times = new ArrayList<>();
        this.totalNumberOfInstanceLabelled = 0;
        this.totalNumberOfUniqueInstance = 0;
        this.consistencyPercentage = 0;
        this.avgTime = 0;
        this.stdTime = 0;
    }
    //UserPerformanceMetrics default constructors.
    public UserPerformanceMetrics(){

    }
    //Updates completeness percentages of given dataset.
    public void updateDatasetsCompletenessPercentage(int numberOfLabeledInstances, int numberOfInstances, int currentDatasetID) {
        datasetsCompletenessPercentage[currentDatasetID - 1] = (float) (numberOfLabeledInstances * 1.0 / numberOfInstances) * 100;
    }

    //Increments total number of instances which are labeled.
    public void incrementTotalNumberOfInstanceLabelled(){
        totalNumberOfInstanceLabelled++;
    }

    //Increments total number of unique instances which are labeled.
    public void incrementTotalUniqueNumberOfInstanceLabelled(){
        totalNumberOfUniqueInstance++;
    }

    //Updates consistency percentage.
    public void updateConsistencyPercentage(int numberOfRecurrentInstances, int numberOfRecurrentInstancesWithSameLabel) {
        float percentage = (float) (numberOfRecurrentInstancesWithSameLabel * 1.0 / numberOfRecurrentInstances) * 100;
        consistencyPercentage = percentage;
    }

    //Adds new finishing time of labeling process.
    public void addTimes(float time){
        times.add(time);
    }

    //Updates average time spent in labeling processes'.
    public void updateAverageTime() {
        float totalTime  = 0;
        for (Float time : times) {
            totalTime += time;
        }
        avgTime = totalTime / times.size();
    }

    //Updates standart dev. of time spent in labeling processes'.
    public void updateStdTime(){
        float diffOfTimeSq = 0;
        for (Float time : times) {
            diffOfTimeSq += Math.pow((avgTime-time), 2);
        }
        stdTime = (float) Math.sqrt(diffOfTimeSq / times.size());
    }

    //Getter methods.
    public int getNumberOfDataset() {
        return numberOfDataset;
    }
    public float[] getDatasetsCompletenessPercentage() {
        return datasetsCompletenessPercentage;
    }
    public int getTotalNumberOfInstanceLabelled() {
        return totalNumberOfInstanceLabelled;
    }
    public int getTotalNumberOfUniqueInstance() {
        return totalNumberOfUniqueInstance;
    }
    public float getConsistencyPercentage() {return consistencyPercentage; }
    public float getAvgTime() {
        return avgTime;
    }
    public float getStdTime() {
        return stdTime;
    }
    public ArrayList<Float> getTimes() {
        return times;
    }

    //Json property: The feature in which variables in json file which variables we should assign in our model.
    @JsonProperty("number of datasets assigned")
    public void setNumberOfDataset(int numberOfDataset) {
        this.numberOfDataset = numberOfDataset;
    }

    @JsonProperty("list of all datasets with their completeness percentage")
    public void setDatasetsCompletenessPercentage(float[] datasetsCompletenessPercentage) {
        this.datasetsCompletenessPercentage = datasetsCompletenessPercentage;
    }

    @JsonProperty("total number of instances labeled")
    public void setTotalNumberOfInstanceLabelled(int totalNumberOfInstanceLabelled) {
        this.totalNumberOfInstanceLabelled = totalNumberOfInstanceLabelled;
    }

    @JsonProperty("total number of unique instances labeled")
    public void setTotalNumberOfUniqueInstance(int totalNumberOfUniqueInstance) {
        this.totalNumberOfUniqueInstance = totalNumberOfUniqueInstance;
    }

    @JsonProperty("consistency percentage")
    public void setConsistencyPercentage(float consistencyPercentage) {
        this.consistencyPercentage = consistencyPercentage;
    }

    @JsonProperty("average time spent in labeling an instance in seconds")
    public void setAvgTime(float avgTime) {
        this.avgTime = avgTime;
    }

    @JsonProperty("std. dev. of  time spent in labeling an instance in seconds")
    public void setStdTime(float stdTime) {
        this.stdTime = stdTime;
    }

    @JsonProperty("times")
    public void setTimes(ArrayList<Float> times) {
        this.times = times;
    }
}
