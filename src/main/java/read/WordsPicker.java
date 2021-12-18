package read;

import java.util.ArrayList;
import org.json.*;

public class WordsPicker {

    private String description;
    private ArrayList<String> words;

    public String getDescription() {
        return this.description;
    }
    private void setDescription(String description) {
        this.description = description;
        this.words = this.filterDescription(description);
    }
    public ArrayList<String> getWords() {
        return this.words;
    }

    private ArrayList<String> filterDescription(String description) {
        String[] parts = description.split(" ");
        ArrayList<String> parts_ = new ArrayList<String>();
        for (String part : parts) {
            parts_.add(part);
        }
        return parts_;
    }
    
    public WordsPicker(String requestUrl) {
        DataFetcher fetcher = new DataFetcher(requestUrl);
        fetcher.fetchData();
        JSONObject data = fetcher.getRequestData();
        String description = data.getString("description");
        setDescription(description);
    }

    public static void main(String[] args) {
        String requestUrl = "https://openlibrary.org/works/OL45883W.json";
        WordsPicker wordsPicker = new WordsPicker(requestUrl);
        System.out.println(wordsPicker.getWords().toString());
    }
    
}
