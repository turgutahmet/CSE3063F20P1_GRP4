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

		//create config object for keep config's information.
		Config config = (Config) read("src/config.json", new Config());
		String datasetFilePathName = "";
		int currentDatasetID = config.getCurrentDatasetID();
		ArrayList<DatasetInfo> datasetsInfo = config.getDatasetInfos();

		for (DatasetInfo datasetInfo : datasetsInfo) {
			if (currentDatasetID==datasetInfo.getDatasetID()){
				datasetFilePathName=datasetInfo.getDatasetFilePath();
				break;
			}
		}
		//create dataset object for keep input's information.
		Dataset data = (Dataset) read(datasetFilePathName, new Dataset());
		//Putting the needed variables in jsonObj.
		jsonObj.put("dataset id", data.getDatasetID());
		jsonObj.put("dataset name", data.getDatasetName());
		jsonObj.put("maximum number of labels per instance", data.getMaximumNumberOfLabelsPerInstance());
		jsonObj.put("class labels", data.getClassLabels());

		//create logger object for log records
		Logger logger = Logger.getLogger(Config.class.getName());
		//set logger configurations
		PropertyConfigurator.configure("log4j.properties");

		//Get all users in the system
		ArrayList<UserInfo> users = config.getUserInfos();
		for (UserInfo userInfo : users) {
			//print config log records check log.txt
			logger.info("config: created " + userInfo.getUserName() + " as " + userInfo.getUserType());
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
		LabelingMechanism randomLabelling = new RandomLabeling();

		//Random Labelling Simulation
		//Iterate all users
		for (UserInfo userInfo : users) {

			//Get consistency check probability
			double consistencyCheckProbability = userInfo.getConsistencyCheckProbability();

			//Check the config's type
			switch (userInfo.getUserType()) {
				case "RandomBot":
					//Get how many instances this config will label
					Scanner scan = new Scanner(System.in);
					System.out.print("Please enter how many instances " + userInfo.getUserName() + " will label:\t");
					int numberOfLabel = scan.nextInt();

					//Get not labeled instances by that config
					ArrayList<Instance> notLabeledInstances = new ArrayList<>();
					for (Instance instance : instances) {
						if (instance.isCanLabeled()) {
							notLabeledInstances.add(instance);
						}
					}

					ArrayList<Instance> previouslyLabeledInstances = new ArrayList<>();

					for (int i = 0; i < numberOfLabel; i++) {
						Instance randomInstance;

						if (previouslyLabeledInstances.isEmpty()) { //If that config not label an instance before select a random instance from the notLabeledInstances array
							randomInstance = notLabeledInstances.get((int) (Math.random() * notLabeledInstances.size()));
						} else {
							//According to consistency check probability select a random instance from previouslyLabeledInstance array or notLabeledInstances array
							int upperLimit = (int) consistencyCheckProbability * 100;
							int dice = (int) (Math.random() * 100);
							if (dice < upperLimit) {
								randomInstance = previouslyLabeledInstances.get((int) (Math.random() * previouslyLabeledInstances.size()));
							} else {
								//If selected instance not labeled before by that config, remove that instance form notLabeledInstances array and add that into previouslyLabeledInstances array
								randomInstance = notLabeledInstances.get((int) (Math.random() * notLabeledInstances.size()));
								notLabeledInstances.remove(randomInstance);
								previouslyLabeledInstances.add(randomInstance);
							}
						}

						randomLabelling.labelInstanceWithUser(userInfo, randomInstance, classLabels, logger);
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
				jsonObj1.put("config id", labelPair.getWhoLabeled().getUserID());
				jsonObj1.put("datetime", labelPair.getDate().format(formatter)+"");
				classLabelList.add(jsonObj1);
			}
		}

		//Getting users variables in output object.
		jsonObj.put("class label assignments", classLabelList);
		ArrayList<LinkedHashMap> usersList = new ArrayList<LinkedHashMap>();
		for (UserInfo userInfo : users) {
			LinkedHashMap jsonObj1 = new LinkedHashMap();
			jsonObj1.put("config id", userInfo.getUserID());
			jsonObj1.put("config name", userInfo.getUserName());
			jsonObj1.put("config type", userInfo.getUserType());
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
