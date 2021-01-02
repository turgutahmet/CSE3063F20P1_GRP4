import org.apache.log4j.Logger;

import java.util.ArrayList;

public class AssignedUsers {
    private final Config config;
    private final DatasetInfo currentDatasetInfo;
    private final Logger logger;
    private ArrayList<UserInfo> allUsers;
    private ArrayList<UserInfo> currentUser;

    public AssignedUsers(Config config, DatasetInfo currentDatasetInfo, Logger logger) {
        this.config = config;
        this.currentDatasetInfo = currentDatasetInfo;
        this.logger = logger;
    }

    public ArrayList<UserInfo> getAllUsers() {
        return allUsers;
    }

    public ArrayList<UserInfo> getCurrentUsers() {
        return currentUser;
    }

    public AssignedUsers invoke() {
        allUsers = config.getUserInfos();
        currentUser = new ArrayList<>();
        for (UserInfo userInfo : allUsers) {
            //Print config log records check log.txt.
            if (currentDatasetInfo.getAssignUserID().contains(userInfo.getUserID())) {
                logger.info("config: created " + userInfo.getUsername() + " as " + userInfo.getUserType());
                currentUser.add(userInfo);
            }
        }
        return this;
    }
}