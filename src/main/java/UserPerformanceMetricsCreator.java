import java.io.File;
import java.util.ArrayList;

public class UserPerformanceMetricsCreator {
    private ArrayList<DatasetInfo> datasetsInfo;
    private ArrayList<BotInfo> currentUsers;

    public UserPerformanceMetricsCreator(ArrayList<DatasetInfo> datasetsInfo, ArrayList<BotInfo> currentUsers) {
        this.datasetsInfo = datasetsInfo;
        this.currentUsers = currentUsers;
    }
    public void invoke() {
        //Create and set all user performance metrics.
        for (BotInfo botInfo : currentUsers) {
            //Create a a File object related to that user's UserPerformanceMetrics.
            UserPerformanceMetrics userPerformanceMetrics;
            File dir = new File("./database/");
            File file = new File("./database/User"+ botInfo.getUserID() + ".json");

            //If that File object exists read from that file and set user performance metrics.
            if (file.exists()) {
                userPerformanceMetrics = (UserPerformanceMetrics) FileReader.getReader().read(new File(dir, file.getName()), new UserPerformanceMetrics());
            } else { //If it isn't exist create new one.
                int numberOfAssignedDataset = 0;
                for (DatasetInfo datasetInfo : datasetsInfo) {
                    for (Integer integer : datasetInfo.getAssignUserID()) {
                        if (botInfo.getUserID() == integer)
                            numberOfAssignedDataset++;
                    }

                }
                userPerformanceMetrics = new UserPerformanceMetrics(botInfo, numberOfAssignedDataset);
            }

            //Set user's user performance metrics.
            botInfo.setUserPerformanceMetrics(userPerformanceMetrics);
        }
    }
}