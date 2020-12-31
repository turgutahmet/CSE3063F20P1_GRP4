import java.io.File;

public class KeywordsCreator {

    public Keywords invoke() {
        File file = new File("src/keywords.json");
        Keywords keywords = (Keywords) FileReader.getReader().read(file, new Keywords());
        return keywords;
    }
}
