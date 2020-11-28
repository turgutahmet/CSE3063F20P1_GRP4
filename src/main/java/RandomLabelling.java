import java.lang.Math;
public class RandomLabelling extends LabellingMechanism{
    @Override
    public ClassLabel labelInstanceWithUser(User user, Instance instance, ClassLabel[] classLabel) {
        int random_index= (int)(Math.random() * classLabel.length) ;
        return classLabel[random_index] ;
     
    }
}
