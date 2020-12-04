
import java.time.LocalDateTime;

public class LabelledInstance extends Instance {

    private UserInfo whoLabelled;
    private ClassLabel label ;
    private LocalDateTime date;

    LabelledInstance(int id, String instance, UserInfo userInfo , ClassLabel classLabel , LocalDateTime localDate) {
        this.setID(id);
        this.setInstance(instance);
        this.whoLabelled = userInfo;
        this.label = classLabel;
        this.date = localDate;
    }

    public UserInfo getWhoLabelled() {
        return whoLabelled;
    }

    public void setWhoLabelled(UserInfo whoLabelled) {
        this.whoLabelled = whoLabelled;
    }

    public ClassLabel getLabel() {
        return label;
    }

    public void setLabel(ClassLabel label) {
        this.label = label;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
