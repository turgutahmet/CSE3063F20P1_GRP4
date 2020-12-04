import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
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
		ArrayList<JSONObject> cla = new ArrayList<>();
		ArrayList<JSONObject> instancesList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		LinkedHashMap jsonObj = new LinkedHashMap();
		//create dataset object for keep input's information.
		Dataset data = (Dataset) read("src/input-2.json", new Dataset());
		jsonObj.put("dataset id", data.getDatasetID());
		jsonObj.put("dataset name", data.getDatasetName());
		jsonObj.put("maximum number of labels per instance", data.getMaximumNumberOfLabelsPerInstance());
		jsonObj.put("class labels", data.getClassLabels());
		//Testing data set ->System.out.println(data.getInstances().get(0).getInstance());

		//create user object for keep user's information.
		User user = (User) read("src/users-2.json", new User());
		//create logger object for log records
		Logger logger = Logger.getLogger(User.class.getName());
		//set logger configurations
		PropertyConfigurator.configure("log4j.properties");

		//Testing user set -> System.out.println(user.getUsers().get(0).getUserName());

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
			JSONObject jsonObj1 = new JSONObject();
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

						randomLabelling.labelInstanceWithUser(userInfo, randomInstance, classLabels,logger);

						availableInstances.remove(randomInstance);
					}
					break;
			}

		}

		for (Instance instance : instances) {
			for (LabelledInstance labelPair : instance.getLabelPairs()) {
				System.out.println("instance id: " + labelPair.getID());
				System.out.println("who labeled: " + labelPair.getWhoLabelled().getUserID());
				System.out.print("labels: ");
				for (ClassLabel label : labelPair.getLabels()) {
					System.out.print(label.getLabelID() + " ");
				}
				System.out.println();
				System.out.println();
			}
		}

		/*Print all labelled instances
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
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					JSONObject jsonObj1 = new JSONObject();
					jsonObj1.put("instance id", instance.getID());
					jsonObj1.put("class label ids",
							labelPair.getLabel().getLabelID());
					jsonObj1.put("user id", labelPair.getWhoLabelled().getUserID());
					jsonObj1.put("datetime", labelPair.getDate().format(formatter)+"");
					cla.add(jsonObj1);
				}
			}
		}
		jsonObj.put("class label assignments", cla);
		jsonObj.put("users", user);
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), jsonObj);



	}*/
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
