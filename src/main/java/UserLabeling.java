import org.apache.log4j.Logger;

import java.util.ArrayList;

public class UserLabeling extends LabelingMechanism {
    public UserLabeling() {
        this.setLabelingMechanismType("User Labeling");
    }

    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabels) {
       }



}
