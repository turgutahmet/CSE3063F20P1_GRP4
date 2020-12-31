public class LabelCounter {

    private ClassLabel classLabel ;
    private int count;


    public LabelCounter(ClassLabel label ){
        this.classLabel = label;
        this.count = 1;
    }
    LabelCounter(){}

    public void incrementCount(){
        this.count++;
    }

    public ClassLabel getLabel() {
        return classLabel;
    }

    public void setLabel(ClassLabel label) {
        this.classLabel = label;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
