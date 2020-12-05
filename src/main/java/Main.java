import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		//Creating arraylists of classLabel and instance output format.
		ArrayList<LinkedHashMap> classLabelList = new ArrayList<>();
		ArrayList<LinkedHashMap> instancesList = new ArrayList<>();
		//Creating mapper object for writing json file.
		ObjectMapper mapper = new ObjectMapper();
		//Creating LinkedHashMap object named jsonObj to help giving output in desired way.
		LinkedHashMap jsonObj = new LinkedHashMap();

		//create dataset object for keep input's information.
		Dataset data = (Dataset) read("src/input-2.json", new Dataset());
		//Putting the needed variables in jsonObj.
		jsonObj.put("dataset id", data.getDatasetID());
		jsonObj.put("dataset name", data.getDatasetName());
		jsonObj.put("maximum number of labels per instance", data.getMaximumNumberOfLabelsPerInstance());
		jsonObj.put("class labels", data.getClassLabels());

		//create user object for keep user's information.
		User user = (User) read("src/users-2.json", new User());
		//create logger object for log records
		Logger logger = Logger.getLogger(User.class.getName());
		//set logger configurations
		PropertyConfigurator.configure("log4j.properties");

		//Get all users in the system
		ArrayList<UserInfo> users = user.getUserInfos();
		for (UserInfo userInfo : users) {
			//print user log records check log.txt
			logger.info("user: created " + userInfo.getUserName() + " as " + userInfo.getUserType());
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
			//Created another object and put variables in it to give output in desired way.
			LinkedHashMap jsonObj1 = new LinkedHashMap();
			jsonObj1.put("id", instance.getID());
			jsonObj1.put("instance", instance.getInstance());
			instancesList.add(jsonObj1);
		}
		jsonObj.put("instances", instancesList);

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

					//Get available instances for labelling
					ArrayList<Instance> availableInstances = new ArrayList<>();
					for (Instance instance : instances) {
						if (instance.isCanLabeled()) {
							availableInstances.add(instance);
						}
					}

					for (int i = 0; i < numberOfLabel; i++) {

						//If there is no available instance to labeled break
						if (availableInstances.isEmpty()) {
							break;
						}
						//Select a random instance from available instances
						Instance randomInstance = availableInstances.get((int) (Math.random() * availableInstances.size()));
						//Calling the labelInstanceWithUser method to make labeling.
						randomLabelling.labelInstanceWithUser(userInfo, randomInstance, classLabels,logger);
						//Deleting the labeled instance from abailableInstances array.
						availableInstances.remove(randomInstance);
					}
					break;
			}

		}
		//Getting the labelId list for output.
		for (Instance instance : instances) {
			for (LabeledInstance labelPair : instance.getLabelPairs()) {
				ArrayList<Integer> listOfLabel = new ArrayList<Integer>();
				for (ClassLabel label : labelPair.getLabels()) {
					listOfLabel.add(label.getLabelID());
				}
				//Putting the desired variables in output object.
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LinkedHashMap jsonObj1 = new LinkedHashMap();
				jsonObj1.put("instance id", labelPair.getID());
				jsonObj1.put("class label ids", listOfLabel);
				jsonObj1.put("user id", labelPair.getWhoLabeled().getUserID());
				jsonObj1.put("datetime", labelPair.getDate().format(formatter)+"");
				classLabelList.add(jsonObj1);
			}
		}

		//Getting users variables in output object.
		jsonObj.put("class label assignments", classLabelList);
		ArrayList<LinkedHashMap> usersList = new ArrayList<LinkedHashMap>();
		for (UserInfo userInfo : users) {
			LinkedHashMap jsonObj1 = new LinkedHashMap();
			jsonObj1.put("user id", userInfo.getUserID());
			jsonObj1.put("user name", userInfo.getUserName());
			jsonObj1.put("user type", userInfo.getUserType());
			usersList.add(jsonObj1);
		}
		jsonObj.put("users", usersList);
		//Writing the output in json file.
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), jsonObj);
	}


	public static Object read(String fileName, Object object) {

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
