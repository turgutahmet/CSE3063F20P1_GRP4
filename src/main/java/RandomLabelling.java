import java.lang.Math;
import java.util.*;
public class RandomLabelling extends LabellingMechanism{
    @Override
    public Instance labelInstanceWithUser( UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabel) {
        int random_index= (int)(Math.random() * classLabel.size()) ;
        Instance labelledInstance = instance.addLabel(userInfo, classLabel.get(random_index));
        return labelledInstance;
    }
}
