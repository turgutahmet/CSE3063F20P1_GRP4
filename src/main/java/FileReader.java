import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;

import java.io.File;

//Singleton Model
public class FileReader {
    private static FileReader reader;

    private FileReader() {

    }

    public static FileReader getReader() {
        if (reader == null) {
            reader = new FileReader();
        }
        return reader;
    }

    public Object read(File file, Object object) {

        //Create JsonParser object for read json file
        JSONParser jsonParser = new JSONParser();

        //Create FileReader
        try (java.io.FileReader reader = new java.io.FileReader(file.getPath())) {
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
