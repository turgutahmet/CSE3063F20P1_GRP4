import java.lang.Math;
import java.util.*;
import org.apache.log4j.Logger;

public class RandomLabelling extends LabellingMechanism {
    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels, Logger logger) {

        int howManyLabels = (int) (Math.random() * classLabels.size()) + 1;

        if(howManyLabels > instance.getAmountOfLabels())
            howManyLabels = (int) (Math.random() * instance.getMaxNumberOfLabel()) + 1;

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
        //TODO: copy instance object and check amount of labels
        //TODO: if amount of labels is less than max number of labels save changes
        //TODO: if amount of labels is greater than max number of labels rollback labeling process and re-calculate howManyLabels
        //TODO: if amount of labels is equal to max number of labels save changes
        Collections.sort(labelArray , Comparator.comparing(ClassLabel::getLabelID));
        instance.addLabel(userInfo, labelArray,logger);
    }
}