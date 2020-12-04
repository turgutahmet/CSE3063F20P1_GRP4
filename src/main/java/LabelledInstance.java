import java.time.LocalDateTime;
import java.util.ArrayList;

public class LabelledInstance extends Instance {

    private UserInfo whoLabelled;
    private ArrayList<ClassLabel> labels = new ArrayList<ClassLabel>();
    private LocalDateTime date;

    LabelledInstance(int id, String instance, UserInfo userInfo , ArrayList<ClassLabel> classLabels , LocalDateTime localDate) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabelled = userInfo;
        this.labels = classLabels;
        this.date = localDate;
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
