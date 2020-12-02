import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		//create dataset object for keep input's information.
		Dataset data = (Dataset) read("src/input-1.json",new Dataset());

		//Testing data set ->System.out.println(data.getInstances().get(0).getInstance());

		//create user object for keep user's information.
		User user = (User) read("src/users-1.json",new User());
		//create logger object for log records
		Logger logger = Logger.getLogger(User.class.getName());
		//set logger configurations
		PropertyConfigurator.configure("log4j.properties");

		//Testing user set -> System.out.println(user.getUsers().get(0).getUserName());

		//Get all users in the system
		ArrayList<UserInfo> users = user.getUserInfos();
		for (UserInfo userInfo : users) {
			//print user log records check log.txt
			logger.info("user: created "+userInfo.getUserName()+" as "+userInfo.getUserType());
		}

		//Get all labels in the system
		ArrayList<ClassLabel> classLabels = data.getClassLabels();

		//Get all instances in the system
		ArrayList<Instance> instances = data.getInstances();

		//Get maximum number of label per instance attribute
		int maxLabelPerInstance = data.getMaximumNumberOfLabelsPerInstance();
		//Assign maxLabelPerInstance to all instances
		for (Instance instance : instances) {
			instance.setMaxNumberOfLabel(maxLabelPerInstance);
		}

		//Create labelling mechanisms
		LabellingMechanism randomLabelling = new RandomLabelling();

		//Random Labelling Simulation
		//Iterate all users
		for (UserInfo userInfo : users) {
			//Check the user's type
			switch (userInfo.getUserType()) {
				case "RandomBot":
					//Get how many instances this user will label
					Scanner scan = new Scanner(System.in);
					System.out.print("Please enter how many instances " + userInfo.getUserName() + " will label:\t");
					int numberOfLabel = scan.nextInt();

					//Copy all classLabels to availableLabels
					ArrayList<ClassLabel> availableLabels = new ArrayList<>();
					for (ClassLabel classLabel : classLabels) {
						availableLabels.add(classLabel);
					}

					for (int i = 0; i < numberOfLabel; i++) {	
						//Get available instances for labelling
						ArrayList<Instance> availableInstances = new ArrayList<>();
						for (Instance instance : instances) {
							if (instance.isCanLabeled()) {
								availableInstances.add(instance);
							}
						}

						//If there is no available instance to labeled break
						if (availableInstances.isEmpty()) {
							break;
						}

						//Select a random instance from available instances
						Instance randomInstance = availableInstances.get((int)(Math.random() * availableInstances.size()));

						//Get available class labels for that instance
						for (LabelledInstance labelPair : randomInstance.getLabelPairs()) {
							if (labelPair.getWhoLabelled() == userInfo) {
								availableLabels.remove(labelPair.getLabel());
							}
						}

						userInfo.labelInstance(randomInstance, availableLabels, randomLabelling);
					}
					break;
			}
		}

		//Print all labelled instances
		logger = Logger.getLogger(LabelledInstance.class.getName());
		for (Instance instance : instances) {
			if (!instance.getLabelPairs().isEmpty()) {
				for (LabelledInstance labelPair : instance.getLabelPairs()) {
					//Text is separated for log recording.
					String userIdText = "user id:" + labelPair.getWhoLabelled().getUserID() +" ";
					String userNameText = labelPair.getWhoLabelled().getUserName() + " ";
					String labelledInstanceIdText = "labelled instance id:" + instance.getID() + " ";
					String withClassText= "with class label " +labelPair.getLabel().getLabelID() + " :";
					String labelText = labelPair.getLabel().getLabelText() + " ";
					String getInstanceText = "instance :" + instance.getInstance();
					//Print log records check log.txt
					logger.info(userIdText + userNameText + labelledInstanceIdText + withClassText + labelText + getInstanceText);


				}
			}
		}
	}

	public static Object read(String fileName,Object object){

		//Create JsonParser object for read json file
		JSONParser jsonParser = new JSONParser();

		//Create FileReader
		try (FileReader reader = new FileReader(fileName)) {
			//Define object for take information from json file
			Object obj = jsonParser.parse(reader);
			//Convert object to String
			String jsonString = obj.toString();
			//return object for keep information of dataset via Object Mapper
			return new ObjectMapper().readValue(jsonString, object.getClass());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}