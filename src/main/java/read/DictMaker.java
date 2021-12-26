package read;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import exceptions.InvalidCountException;
import exceptions.InvalidRangeException;
import exceptions.NotFoundException;
import exceptions.UnbalancedException;
import exceptions.UndersizeException;

public class DictMaker {

    private ArrayList<String> words;
    private String ID;
    private String BASEURL;
    private String ID_dict;

    public DictMaker(String ID, String BASEURL, String ID_dict) throws InvalidCountException, InvalidRangeException, UndersizeException, UnbalancedException, NotFoundException {
        System.out.println("Initializing dictionary maker...");
        this.setID(ID);
        this.setBaseurl(BASEURL);
        this.setID_dict(ID_dict);
        String url = BASEURL + ID + ".json";
        WordsPicker wordsPicker = new WordsPicker(url);
        setWords(wordsPicker.getWords());
        this.validate();
    }

    public String getId() {
        return this.ID;
    }
    private void setID(String ID) {
        this.ID = ID;
    }

    public String getBaseurl() {
        return this.BASEURL;
    }
    private void setBaseurl(String BASEURL) {
        this.BASEURL = BASEURL;
    }

    public String getDictId() {
        return this.ID_dict;
    }
    private void setID_dict(String id_dict) {
        this.ID_dict = id_dict;
    }

    public ArrayList<String> getWords() {
        return this.words;
    }
    private void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public void validate() throws InvalidCountException, InvalidRangeException, UndersizeException, UnbalancedException {
        System.out.println("Validating dictionary...");
        String[] words_array = this.words.toArray(new String[0]);
        Set<String> words_set = new HashSet<String>(Arrays.asList(words_array));
        if (words_set.size() != this.words.size()) {
            throw new InvalidCountException();
        }
        int words_longer_than_9 = 0;
        for (String word : this.words) {
            if (word.length()<6) {
                throw new InvalidRangeException();
            }
            if (word.length()>=9) {
                words_longer_than_9++;
            }
        }
        if (words_longer_than_9<0.2*words.size()) {
            throw new UnbalancedException();
        }
        if (this.words.size()<20) {
            throw new UndersizeException();
        }
    }

    public void write() {
        String filename = "medialab/hangman_DICTIONARΥ-" + this.ID_dict + ".txt";
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                System.out.println("File created...");
            }
            else {
                System.out.println("File already exists, cleaning...");
            }
            System.out.println("Writting...");
            FileWriter writer = new FileWriter(file);
            for (String word : this.words) {
                writer.write(word);
                writer.write("\n");
            }
            writer.close();
            System.out.println("Dictionary written at: " + filename);
            System.out.println("You are ready to go !!");
        }
        catch (Exception e) {
            System.out.println("Error writting the file...");
            e.printStackTrace();
        }

    }
}
