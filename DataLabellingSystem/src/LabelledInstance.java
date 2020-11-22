import java.time.LocalDate;

public class LabelledInstance extends Instance {
    private User whoLabeled;
    private ClassLabel label;
    private LocalDate date;

    LabelledInstance(int instanceID, String instance, int maxNumberOfLabels, User whoLabeled, ClassLabel label, LocalDate date) {
        super(instanceID, instance, maxNumberOfLabels);
        this.whoLabeled = whoLabeled;
        this.label = label;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
