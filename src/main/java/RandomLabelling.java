import java.lang.Math;
import java.util.*;
public class RandomLabelling extends LabellingMechanism {
    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels) {

        int howManyLabels = (int) (Math.random() * classLabels.size()) + 1;
        ArrayList<ClassLabel> labelArray = new ArrayList<>();
        ArrayList<ClassLabel> availableLabels = new ArrayList<>();
        for (ClassLabel label : classLabels) {
            availableLabels.add(label);
        }

        for (int i = 0; i < howManyLabels; i++) {

            int random_index = (int) (Math.random() * availableLabels.size());

            labelArray.add(availableLabels.get(random_index));

            availableLabels.remove(availableLabels.get(random_index));

        }

        instance.addLabel(userInfo, labelArray);
    }
}
