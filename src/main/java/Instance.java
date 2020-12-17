import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

public class Instance {
    //Define basic Instance variable
    private int id;
    private String instance;
    private boolean canLabeled = true;
    private int maxNumberOfLabel;
    private int amountOfLabels;
    private ArrayList<LabeledInstance> userLabels = new ArrayList<LabeledInstance>();
    private Label finalLabel;

    public void addLabel(UserInfo userInfo, Label label, Logger logger){
        boolean exist = false;
        //create new labeledInstance
        LabeledInstance labeledInstance = null;
        for (LabeledInstance userLabel : userLabels) {
            if(userLabel.getWhoLabeled() == userInfo ){
                exist = true;
                labeledInstance = userLabel ;
                break;
            }
        }
        if(!exist){
            labeledInstance = new LabeledInstance(this.id,this.instance,userInfo,label,LocalDateTime.now(),logger);
        }
        else{
            labeledInstance.updateLabel(label,logger);

        }
        userLabels.add(labeledInstance);
        userInfo.addLabeledInstance(labeledInstance);
        updateFinalLabel();
    }
    private void updateFinalLabel(){
        // each label  with number of uses
        HashMap<Label, Integer> instanceLabels = new HashMap<Label, Integer>();
        ArrayList<Label> allLabels = new ArrayList<Label>();
        for (LabeledInstance i: userLabels){
            for (Label l : i.getLabels()){
                if (instanceLabels.containsKey(l)){
                    //if the label already in the hashmap then it will increment the value with the count of the label
                    instanceLabels.merge(l,l.getCount(),Integer::sum);
                }
                else {
                    instanceLabels.put(l,l.getCount());
                    allLabels.add(l);
                } } }

        if (isAllEqual(instanceLabels)){
            //choose random label as final label
            int random_index = (int) (Math.random() * allLabels.size());
            this.finalLabel=allLabels.get(random_index);
        }
        else{
            //choose the label with the largest count
            Map.Entry<Label, Integer> max = null;
            for (Map.Entry<Label,Integer> entry : instanceLabels.entrySet()){
                if (max==null || entry.getValue().compareTo(max.getValue()) > 0){
                    max=entry;
                }
            this.finalLabel= max.getKey();
        } }}
    //check if all equal
    private boolean isAllEqual(HashMap instanceLabels) {
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.addAll(instanceLabels.values());
        for (Integer i : values) {
            if (values.get(0) != i) {
                return false;
            }}
            return true;
         }
    //Json property: The feature in which variables in json file which variables we should assign in our model.

    //Variables getter setter methods
    public int getID() { return id; }
    @JsonProperty("id")
    public void setID(int value) { this.id = value; }

    public String getInstance() { return instance; }
    @JsonProperty("instance")
    public void setInstance(String value) { this.instance = value; }

    public boolean isCanLabeled() {
        return canLabeled;
    }

    public void setCanLabeled(boolean canLabeled) {
        this.canLabeled = canLabeled;
    }

    public int getMaxNumberOfLabel() {
        return maxNumberOfLabel;
    }

    public void setMaxNumberOfLabel(int maxNumberOfLabel) {
        this.maxNumberOfLabel = maxNumberOfLabel;
    }

    public ArrayList<LabeledInstance> getLabelPairs() {
        return userLabels;
    }

    public void setLabelPairs(ArrayList<LabeledInstance> labelPairs) {
        this.userLabels = labelPairs;
    }

    public int getAmountOfLabels() {
        return amountOfLabels;
    }

    public void setAmountOfLabels(int amountOfLabels) {
        this.amountOfLabels = amountOfLabels;
    }
    public Label getFinalLabel(){return finalLabel;}
}