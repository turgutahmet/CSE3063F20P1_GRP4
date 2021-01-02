import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SentimentLabeling extends LabelingMechanism {

    public SentimentLabeling() {
        this.setLabelingMechanismType("SentimentLabeling");
    }

    @Override
    public void labelInstanceWithUser(UserInfo userInfo, Instance instance, ArrayList<ClassLabel> classLabel) {
        //Get keywords
        Keywords keywords = new KeywordsCreator().invoke();
        ArrayList<String> positiveKeywords = keywords.getPositives();
        ArrayList<String> negativeKeywords = keywords.getNegatives();

        //Count occurrences of keywords in instance
        int positiveWords = 0;
        int negativeWords = 0;
        String[] wordsInInstance = instance.getInstance().split(" ");

        //Add label to instance
        LabeledInstance labeledInstance = instance.createLabeledInstance(userInfo);

        for (String s : wordsInInstance) {
            //iterate positive keywords
            for (String positiveKeyword : positiveKeywords) {
                if (s.equalsIgnoreCase(positiveKeyword)) {
                    positiveWords++;
                }
            }

            //iterate negative keywords
            for (String negativeKeyword : negativeKeywords) {
                if (s.equalsIgnoreCase(negativeKeyword)) {
                    negativeWords++;
                }
            }
        }

        //Create a LabelCounter object
        LabelCounter labelCounter;
        if (positiveWords > negativeWords) {
            labelCounter = new LabelCounter(classLabel.get(0)); //Positive
        } else if (negativeWords > positiveWords) {
            labelCounter = new LabelCounter(classLabel.get(1)); //Negative
        } else {
            labelCounter = new LabelCounter(classLabel.get(2)); //Notr
        }

        //Add new label assignment into instance performance metrics
        instance.getInstancePerformanceMetrics().addNewLabelAssignment(userInfo, labelCounter.getLabel());

        //Update instance
        instance.updateInstance(labeledInstance, labelCounter);

        //Logger class initialize
        Logger logger = Logger.getLogger(this.getClass().getName());

        //Print log check
        logger.info("user id:" + userInfo.getUserID() + " " + userInfo.getUsername() + " " + "labeled instance id:" + instance.getID() + " "
                + "with class label " + labelCounter.getLabel().getLabelID() + " :" + labelCounter.getLabel().getLabelText() + " " + "instance :" + instance.getInstance());

    }
}
