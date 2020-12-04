import java.time.LocalDateTime;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class LabelledInstance extends Instance {

    private UserInfo whoLabelled;
    private ArrayList<ClassLabel> labels = new ArrayList<ClassLabel>();
    private LocalDateTime date;

    LabelledInstance(int id, String instance, UserInfo userInfo , ArrayList<ClassLabel> classLabels , LocalDateTime localDate, Logger logger) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabelled = userInfo;
        this.labels = classLabels;
        this.date = localDate;
        //Logger class initialize
        logger = Logger.getLogger(this.getClass().getName());
        //Print log check
        for (ClassLabel label : labels) {
            logger.info("user id:" + whoLabelled.getUserID() +" "+ whoLabelled.getUserName() + " "+ "labelled instance id:" + this.getID() + " "
                    + "with class label " + label.getLabelID() + " :"+  label.getLabelText() + " "+ "instance :" + this.getInstance());
        }

    }

    public UserInfo getWhoLabelled() {
        return whoLabelled;
    }

    public void setWhoLabelled(UserInfo whoLabelled) {
        this.whoLabelled = whoLabelled;
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
