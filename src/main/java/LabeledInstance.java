import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class LabeledInstance extends Instance {

    private UserInfo whoLabeled;
    private ArrayList<ClassLabel> labels = new ArrayList<ClassLabel>();
    private LocalDateTime date;

    LabeledInstance(int id, String instance, UserInfo userInfo , ArrayList<ClassLabel> classLabels , LocalDateTime localDate, Logger logger) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabeled = userInfo;
        this.labels = classLabels;
        this.date = localDate;
        //Logger class initialize
        logger = Logger.getLogger(this.getClass().getName());
        //Print log check
        for (ClassLabel label : labels) {
            logger.info("user id:" + whoLabeled.getUserID() +" "+ whoLabeled.getUserName() + " "+ "labeled instance id:" + this.getID() + " "
                    + "with class label " + label.getLabelID() + " :"+  label.getLabelText() + " "+ "instance :" + this.getInstance());
        }

    }

    public UserInfo getWhoLabeled() {
        return whoLabeled;
    }

    public void setWhoLabelled(UserInfo whoLabelled) {
        this.whoLabeled = whoLabelled;
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
