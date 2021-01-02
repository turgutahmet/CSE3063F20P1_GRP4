public class LabelAndNumberOfUniqueInstance {
    private final ClassLabel classLabel;
    private final int count;

    public LabelAndNumberOfUniqueInstance(ClassLabel classLabel, int count) {
        this.classLabel = classLabel;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public ClassLabel getClassLabel() {
        return classLabel;
    }
}
