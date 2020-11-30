import java.lang.Math;
import java.util.*;
public class RandomLabelling extends LabellingMechanism{
    @Override
    public ClassLabel labelInstanceWithUser( UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabel) {
        int random_index= (int)(Math.random() * classLabel.size()) ;
        return classLabel.get(random_index);
    }
}
