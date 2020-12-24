public class Label {

    private ClassLabel classLabel ;
    private int count;


    public Label (ClassLabel label ){
        this.classLabel = label;
        this.count = 1;
    }

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
