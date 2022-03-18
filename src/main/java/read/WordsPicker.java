package read;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import org.json.*;

import exceptions.NotFoundException;

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
        // split, capitalize, cut words with length<6
        System.out.println("Filtering description...");
        String[] parts = description.split("[^a-zA-Z]+");
        ArrayList<String> words = new ArrayList<String>();
        Set<String> parts_set = new HashSet<String>(Arrays.asList(parts));
        for (String word : parts_set) {
            if (word.length()>=6) {
                words.add(word.toUpperCase());
            }
        }
        return words;
    }
    
    public WordsPicker(String requestUrl) throws NotFoundException {
        System.out.println("Initializing words picker...");
        DataFetcher fetcher = new DataFetcher(requestUrl);
        fetcher.fetchData();
        JSONObject data = fetcher.getRequestData();
        try {
            System.out.println("1");
            JSONObject value = data.getJSONObject("description");
            System.out.println("2");
            String description = value.getString("value");
            setDescription(description);
        }
        catch (JSONException e2) {
            System.out.println("e2");
            try {
                String description = data.getString("description");
                setDescription(description);
            }
            catch (Exception e3) {
                System.out.println("e3");
                System.out.println(e3);
                throw new NotFoundException();
            }
        }
        catch (Exception e) {
            System.out.println("e");
            System.out.println(e);
            throw new NotFoundException();
        }
    }
   
}
