import java.io.File;
import java.util.ArrayList;

public class UserPerformanceMetricsCreator {
    private ArrayList<DatasetInfo> datasetsInfo;
    private ArrayList<UserInfo> currentUsers;

    public UserPerformanceMetricsCreator(ArrayList<DatasetInfo> datasetsInfo, ArrayList<UserInfo> currentUsers) {
        this.datasetsInfo = datasetsInfo;
        this.currentUsers = currentUsers;
    }

}