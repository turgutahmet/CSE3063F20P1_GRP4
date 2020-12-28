import org.apache.log4j.Logger;

import java.util.ArrayList;

public class AssignedUsers {
    private Config config;
    private DatasetInfo currentDatasetInfo;
    private Logger logger;
    private ArrayList<BotInfo> allUsers;
    private ArrayList<BotInfo> currentUser;

    public AssignedUsers(Config config, DatasetInfo currentDatasetInfo, Logger logger) {
        this.config = config;
        this.currentDatasetInfo = currentDatasetInfo;
        this.logger = logger;
    }

    public ArrayList<BotInfo> getAllUsers() {
        return allUsers;
    }

    public ArrayList<BotInfo> getCurrentUsers() {
        return currentUser;
    }

    public AssignedUsers invoke() {
        allUsers = config.getBotInfos();
        currentUser = new ArrayList<>();
        for (BotInfo botInfo : allUsers) {
            //Print config log records check log.txt.
            if (currentDatasetInfo.getAssignUserID().contains(botInfo.getUserID())) {
                logger.info("config: created " + botInfo.getUsername() + " as " + botInfo.getUserType());
                currentUser.add(botInfo);
            }
        }
        return this;
    }
}