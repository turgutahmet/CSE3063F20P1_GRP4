import java.lang.Math;
import java.util.*;
import org.apache.log4j.Logger;

public class RandomLabeling extends LabelingMechanism {
    public RandomLabeling() {
        this.setLabelingMechanismType("Random Labeling");
    }

    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels, Logger logger) {
        //Tracing the labelArray to get available ones in an array.
        ArrayList<ClassLabel> availableLabels = new ArrayList<>();
        for (ClassLabel label : classLabels) {
            availableLabels.add(label);
        }

        //Checking how many labeling operation is happened already in this instance.
        int filledLabels = 0;
        for (int i = 0 ; i<instance.getLabelPairs().size(); i++) {
            filledLabels += instance.getLabelPairs().get(i).getLabels().size();
        }
            if(filledLabels == instance.getMaxNumberOfLabel())
                    return;
            //Randomly labeling the instance and incrementing the number of filled labels.
            int random_index = (int) (Math.random() * availableLabels.size());
            Label label = new Label(availableLabels.get(random_index));
        instance.addLabel(userInfo,label,logger);
    }
}