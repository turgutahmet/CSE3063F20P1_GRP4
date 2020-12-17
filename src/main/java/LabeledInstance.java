import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;

import org.apache.log4j.Logger;

public class LabeledInstance extends Instance {
    //Variables of the LabeledInstance class.
    private UserInfo whoLabeled ;
    private ArrayList<Label> labels = new ArrayList<Label>();
    private LocalDateTime date;
    private Label finalLabel;

    //LabeledInstance constructor with logger.
    LabeledInstance(int id, String instance, UserInfo userInfo , Label newLabel , LocalDateTime localDate, Logger logger) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabeled = userInfo;
        this.labels.add(newLabel);
        this.date = localDate;

        //Logger class initialize
        logger = Logger.getLogger(this.getClass().getName());
        //Print log check
        for (Label label : labels) {
            logger.info("user id:" + whoLabeled.getUserID() +" "+ whoLabeled.getUserName() + " "+ "labeled instance id:" + this.getID() + " "
                    + "with class label " + label.getLabel().getLabelID() + " :"+  label.getLabel().getLabelText() + " "+ "instance :" + this.getInstance());
        }

    }

   public void updateLabel (Label label,Logger logger){
       for (Label label1 : labels) {
           if(label1 == label){
               label1.incrementCount();
               return;
           }
       }
       labels.add(label);
    }
   public void updateFinalLabel(){

            if (isAllEqual()){
                //choose random label as final label
                int random_index = (int) (Math.random() * labels.size());
                this.finalLabel=labels.get(random_index);
            }
            else{
                //choose the label with the largest count
                for (Label l : labels){
                    if( l.getCount()>finalLabel.getCount()){
                        this.finalLabel=l;}}
            }
   }
    //check if all equal
   private boolean isAllEqual(){
       for (Label i :labels ){
           if (labels.get(0).getCount()!=i.getCount()){
                return false;
           }}
       return true;
       }
    //Get&set methods.
    public Label getFinalLabel(){
        return finalLabel;
    }

    public UserInfo getWhoLabeled() {
        return whoLabeled;
    }

    public void setWhoLabeled(UserInfo whoLabeled) {
        this.whoLabeled = whoLabeled;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
