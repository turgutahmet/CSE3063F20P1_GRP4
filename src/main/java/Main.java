import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Main {
	public static void main(String[] args) {

		//create dataset object for keep input's information.
		Dataset data = (Dataset) read("src/input-1.json",new Dataset());

		//Testing -> System.out.println(data.getInstances()[0].getInstance());
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