import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		//create dataset object for keep input's information.
		Dataset data = (Dataset) read("src/input-1.json",new Dataset());

		//Testing data set ->System.out.println(data.getInstances().get(0).getInstance());

		//create user object for keep user's information.
		User user = (User) read("src/users-1.json",new User());

		//Testing user set -> System.out.println(user.getUsers().get(0).getUserName());

		//Get all users in the system
		ArrayList<UserInfo> users = user.getUserInfos();

		//Get all labels in the system
		ArrayList<ClassLabel> classLabels = data.getClassLabels();

		//Get all instances in the system
		ArrayList<Instance> instances = data.getInstances();

		//Get maximum number of label per instance attribute
		int maxLabelPerInstance = data.getMaximumNumberOfLabelsPerInstance();

		//Create labelling mechanisms
		LabellingMechanism randomLabelling = new RandomLabelling();

		//Random Labelling Simulation
		ArrayList<Instance> labbelledInstances = new ArrayList<>();
		//Iterate all users
		for (UserInfo userInfo : users) {
			//Check the user's type
			switch (userInfo.getUserType()) {
				case "RandomBot":
					//Get how many instances this user will label
					Scanner scan = new Scanner(System.in);
					System.out.print("Please enter how many instances " + userInfo.getUserName() + " will label:\t");
					int numberOfLabel = scan.nextInt();

					//Get available instances for labeling
					ArrayList<Instance> availableInstances = instances;
					//Remove instances which has reached maximum number of label per instance
					for (Instance availableInstance : availableInstances) {
						if (availableInstance.getLabelPairs().size() > maxLabelPerInstance) {
							availableInstances.remove(availableInstance);
						}
					}

					for (int i = 0; i < numberOfLabel; i++) {
						//If there is no available instance to be labelled break this for loop
						if (availableInstances.isEmpty()) {
							break;
						}

						//Select a random instance from available instances
						Instance randomInstance = availableInstances.get((int)(Math.random() * availableInstances.size()));

						//Label this instance with randomLabelling mechanism
						Instance labelledInstance = userInfo.labelInstance(randomInstance, classLabels, randomLabelling);

						// add this instance to labelledInstances list
						labbelledInstances.add(labelledInstance);

						//Remove labelled instance from availableInstances list (A user can label an instance once.)
						availableInstances.remove(randomInstance);
					}

			}
		}

		//Print all labelled instances
		for (Instance instance : labbelledInstances) {
			System.out.println(instance.isHasLabeled());
			for (LabelledInstance labelPair : instance.getLabelPairs()) {
				System.out.println("Instance: " + labelPair.getInstance());
				System.out.println("Who labelled: " + labelPair.getWhoLabelled().getUserName());
				System.out.println("Label: " + labelPair.getLabel().getLabelText());
				System.out.println("Date: " + labelPair.getDate());
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