public class LabelAndNumberOfUniqueinstance {
    private ClassLabel classLabel;
    private int count;

    public LabelAndNumberOfUniqueinstance(ClassLabel classLabel,int count){
        this.classLabel=classLabel;
        this.count=count;
    }

    public int getCount() {
        return count;
    }
    public ClassLabel getClassLabel(){
        return classLabel;
    }
}
