import java.lang.Math;
import java.util.*;
import org.apache.log4j.Logger;

public class RandomLabelling extends LabellingMechanism {
    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels, Logger logger) {

        int howManyLabels = (int) (Math.random() * instance.getMaxNumberOfLabel()) + 1;

        int filledLabels = 0;
        ArrayList<ClassLabel> labelArray = new ArrayList<>();
        ArrayList<ClassLabel> availableLabels = new ArrayList<>();
        for (ClassLabel label : classLabels) {
            availableLabels.add(label);
        }

        for (int i = 0 ; i<instance.getLabelPairs().size(); i++) {
            filledLabels += instance.getLabelPairs().get(i).getLabels().size();
        }

        for (int i = 0; i < howManyLabels; i++) {
            if(filledLabels == instance.getMaxNumberOfLabel())
                if(i == 0)
                    return;
                else
                    break;

            int random_index = (int) (Math.random() * availableLabels.size());

            labelArray.add(availableLabels.get(random_index));

            availableLabels.remove(availableLabels.get(random_index));
            filledLabels++;

        }

        Collections.sort(labelArray , Comparator.comparing(ClassLabel::getLabelID));
        instance.addLabel(userInfo, labelArray,logger);
        System.out.println(filledLabels);
    }
}