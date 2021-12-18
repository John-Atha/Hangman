package read;

import java.io.IOException;
import org.json.*;

public class WordsPicker {

    public static void main(String[] args) {
        String requestUrl = "https://openlibrary.org/works/OL45883W.json";
        DataFetcher fetcher = new DataFetcher(requestUrl);
        fetcher.fetchData();
        JSONObject data = fetcher.getRequestData();
        String description = data.getString("description");
        System.out.println(description);
    }
    
}
