import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.util.ArrayList;

public class Keywords {
    private ArrayList<String> positives;
    private ArrayList<String> negatives;


    @JsonProperty("positives")
    public void setPositives(ArrayList<String> positives) {
        this.positives = positives;
    }

    @JsonProperty("negatives")
    public void setNegatives(ArrayList<String> negatives) {
        this.negatives = negatives;
    }
}
