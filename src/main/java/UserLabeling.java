import org.apache.log4j.Logger;

import java.util.ArrayList;

public class UserLabeling extends LabelingMechanism {
    public UserLabeling() {
        this.setLabelingMechanismType("User Labeling");
    }

    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels) {
       }

       public void labelInstanceWithUser(UserInfo userInfo, Instance instance ,ClassLabel label){

        LabelCounter labelCounter = new LabelCounter(label);

        LabeledInstance labeledInstance = instance.createLabeledInstance(userInfo);

        instance.getInstancePerformanceMetrics().addNewLabelAssignment(userInfo, label);

        instance.updateInstance(labeledInstance, labelCounter);

        Logger logger = Logger.getLogger(this.getClass().getName());

        logger.info("user id:" + userInfo.getUserID() + " " + userInfo.getUsername() + " " + "labeled instance id:" + instance.getID() + " "
                + "with class label " + labelCounter.getLabel().getLabelID() + " :" + labelCounter.getLabel().getLabelText() + " " + "instance :" + instance.getInstance());

    }


}
