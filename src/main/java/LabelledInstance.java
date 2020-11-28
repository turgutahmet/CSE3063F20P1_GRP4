import java.time.LocalDate;

public class LabelledInstance extends Instance {

    private User whoLabelled;
    private ClassLabel label ;
    private LocalDate date;

    LabelledInstance(User user , ClassLabel classLabel , LocalDate localDate ){
        this.whoLabelled = user;
        this.label = classLabel;
        this.date = localDate;
    }

    public User getWhoLabelled() {
        return whoLabelled;
    }

    public void setWhoLabelled(User whoLabelled) {
        this.whoLabelled = whoLabelled;
    }

    public ClassLabel getLabel() {
        return label;
    }

    public void setLabel(ClassLabel label) {
        this.label = label;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
