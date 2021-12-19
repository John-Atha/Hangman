package read;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import exceptions.InvalidCountException;
import exceptions.InvalidRangeException;
import exceptions.UnbalancedException;
import exceptions.UndersizeException;

public class DictMaker {

    private ArrayList<String> words;
    private String ID;

    public DictMaker(String ID, String url) throws InvalidCountException, InvalidRangeException, UndersizeException, UnbalancedException {
        System.out.println("Initializing dictionary maker...");
        this.setID(ID);
        WordsPicker wordsPicker = new WordsPicker(url);
        setWords(wordsPicker.getWords());
        boolean isValid = this.isValid();
        if (isValid) {
            // save to file, todo...
        }
    }

    public String getId() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<String> getWords() {
        return this.words;
    }
    private void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public boolean isValid() throws InvalidCountException, InvalidRangeException, UndersizeException, UnbalancedException {
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
        return true;
    }

    public void write() {
        String filename = "medialab/hangman_DICTIONARΥ-" + this.ID + ".txt";
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
    public static void main(String[] args) throws InvalidCountException, InvalidRangeException, UndersizeException, UnbalancedException {
        String ID = "OL45883W";
        String requestUrl = "https://openlibrary.org/works/" + ID + ".json";
        DictMaker dict = new DictMaker(ID, requestUrl);
        dict.write();
    }
}
