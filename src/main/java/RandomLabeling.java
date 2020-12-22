import java.lang.Math;
import java.util.*;
import org.apache.log4j.Logger;

public class RandomLabeling extends LabelingMechanism {
    public RandomLabeling() {
        this.setLabelingMechanismType("Random Labeling");
    }

    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels, Logger logger) {
        //Randomly labeling the instance and incrementing the number of filled labels.
        int random_index = (int) (Math.random() * classLabels.size());
        Label label = new Label(classLabels.get(random_index));
    }
}