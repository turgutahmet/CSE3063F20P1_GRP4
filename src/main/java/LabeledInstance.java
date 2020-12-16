import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

public class LabeledInstance extends Instance {
    //Variables of the LabeledInstance class.
    private ArrayList<UserInfo> whoLabeled = new ArrayList<UserInfo>();
    private ArrayList<ClassLabel> labels = new ArrayList<ClassLabel>();
    private LocalDateTime date;

    //LabeledInstance constructor with logger.
    LabeledInstance(int id, String instance, UserInfo userInfo , ClassLabel classLabel , LocalDateTime localDate, Logger logger) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabeled.add(userInfo);
        this.labels.add(classLabel);
        this.date = localDate;
        //Logger class initialize
        logger = Logger.getLogger(this.getClass().getName());
        //Print log check
        for (ClassLabel label : labels) {
            logger.info("user id:" + whoLabeled.get(0).getUserID() +" "+ whoLabeled.get(0).getUserName() + " "+ "labeled instance id:" + this.getID() + " "
                    + "with class label " + label.getLabelID() + " :"+  label.getLabelText() + " "+ "instance :" + this.getInstance());
        }

    }

    public void updateLabel (UserInfo userInfo, ClassLabel classLabel, Logger logger){
        this.whoLabeled.add(userInfo);
        this.labels.add(classLabel);
    }
    //Get&set methods.

    public ArrayList<UserInfo> getWhoLabeled() {
        return whoLabeled;
    }

    public void setWhoLabeled(ArrayList<UserInfo> whoLabeled) {
        this.whoLabeled = whoLabeled;
    }

    public ArrayList<ClassLabel> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<ClassLabel> label) {
        this.labels = label;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
