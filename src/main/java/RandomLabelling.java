import java.lang.Math;
import java.util.*;
import org.apache.log4j.Logger;

public class RandomLabelling extends LabellingMechanism {
    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels, Logger logger) {
        //Randomly getting the number of how many labeling going to happen on instance.
        int howManyLabels = (int) (Math.random() * instance.getMaxNumberOfLabel()) + 1;
        //Tracing the labelArray to get available ones in an array.
        ArrayList<ClassLabel> labelArray = new ArrayList<>();
        ArrayList<ClassLabel> availableLabels = new ArrayList<>();
        for (ClassLabel label : classLabels) {
            availableLabels.add(label);
        }
        //Checking how many labeling operation is happened already in this instance.
        int filledLabels = 0;
        for (int i = 0 ; i<instance.getLabelPairs().size(); i++) {
            filledLabels += instance.getLabelPairs().get(i).getLabels().size();
        }
        //Labeling operation.
        for (int i = 0; i < howManyLabels; i++) {
            //Checking if max number of labels is reached.
            if(filledLabels == instance.getMaxNumberOfLabel())
                if(i == 0)
                    return;
                else
                    break;
            //Randomly labeling the instance and incrementing the number of filled labels.
            int random_index = (int) (Math.random() * availableLabels.size());
            labelArray.add(availableLabels.get(random_index));
            availableLabels.remove(availableLabels.get(random_index));
            filledLabels++;

        }
        //Sorting the labels by labelID and adding to instance.
        Collections.sort(labelArray , Comparator.comparing(ClassLabel::getLabelID));
        instance.addLabel(userInfo, labelArray,logger);
    }
}