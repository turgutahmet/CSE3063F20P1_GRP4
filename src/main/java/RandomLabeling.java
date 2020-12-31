import java.lang.Math;
import java.util.*;
import org.apache.log4j.Logger;

public class RandomLabeling extends LabelingMechanism {
    public RandomLabeling() {
        this.setLabelingMechanismType("Random Labeling");
    }

    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels) {
        //Select a random label from class labels
        int random_index = (int) (Math.random() * classLabels.size());
        ClassLabel randomLabel = classLabels.get(random_index);

        //Create a Label object
        LabelCounter labelCounter = new LabelCounter(randomLabel);

        //Add label to instance
        LabeledInstance labeledInstance = instance.createLabeledInstance(userInfo);

        //Add new label assignment into instance performance metrics
        instance.getInstancePerformanceMetrics().addNewLabelAssignment(userInfo, randomLabel);

        //Update instance
        instance.updateInstance(labeledInstance, labelCounter);

        //Logger class initialize
        Logger logger = Logger.getLogger(this.getClass().getName());

        //Print log check
        logger.info("user id:" + userInfo.getUserID() + " " + userInfo.getUsername() + " " + "labeled instance id:" + instance.getID() + " "
                + "with class label " + labelCounter.getLabel().getLabelID() + " :" + labelCounter.getLabel().getLabelText() + " " + "instance :" + instance.getInstance());
    }
}
