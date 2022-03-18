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

/**
 * DictMaker is the class that writes a dictionary's words to a file
 * @author John-Atha
 */
public class DictMaker {

    /**
     * the words that will be written in the file
     */
    private ArrayList<String> words;

    /**
     * the ID of the BOOK of the Open Api that will be used as source
     */
    private String ID;

    /**
     * the BASEURL of the API
     */
    private String BASEURL;

    /**
     * <ul>
     *     <li>
     *         the ID of the dictionary that we will save
     *     </li>
     *     <li>
     *         defines the name of the file: "medialab/hangman_DICTIONARY-" + this.ID_dict + ".txt";
     *     </li>
     * </ul>
     */
    private String ID_dict;

    /**
     * constructor
     * <ul>
     *     <li>
     *         Creates a `WordsPicker` object to receive an initial list of the description's words
     *     </li>
     *     <li>
     *         Calls the `validate` method
     *     </li>
     * </ul>
     *
     * @param ID (same usage as the corresponding field)
     * @param BASEURL (same usage as the corresponding field)
     * @param ID_dict (same usage as the corresponding field)
     * @throws InvalidCountException if there are duplicates in the dictionary
     * @throws InvalidRangeException if words with less than 6 letters are included in tha dictionary
     * @throws UndersizeException if number of accepted words is less than 20
     * @throws UnbalancedException if less than 20% of the words have more than 9 letters
     * @throws NotFoundException is thrown by the wordsPicker object that is being used in the DictMaker object
     */
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

    /**
     *
     * @return current book id
     */
    public String getId() {
        return this.ID;
    }

    /**
     *
     * @param ID to be updated
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     *
     * @return current BASEURL
     */
    public String getBaseurl() {
        return this.BASEURL;
    }

    /**
     *
     * @param BASEURL to be updated
     */
    public void setBaseurl(String BASEURL) {
        this.BASEURL = BASEURL;
    }

    /**
     *
     * @return current dictionary id
     */
    public String getDictId() {
        return this.ID_dict;
    }

    /**
     *
     * @param id_dict to be updated
     */
    public void setID_dict(String id_dict) {
        this.ID_dict = id_dict;
    }

    /**
     *
     * @return current words
     */
    public ArrayList<String> getWords() {
        return this.words;
    }

    /**
     *
     * @param words to be updated
     */
    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    /**
     * validates the dictionary
     * <ul>
     *    <li>
     *        traverses the words of the dictionary
     *    </li>
     *    <li>
     *      checks if one of the four exceptions should be thrown
     *    </li>
     * </ul>
     * @throws InvalidCountException if there are duplicates in the dictionary
     * @throws InvalidRangeException if words with less than 6 letters are included in tha dictionary
     * @throws UndersizeException if number of accepted words is less than 20
     * @throws UnbalancedException if less than 20% of the words have more than 9 letters
     */
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

    /**
     * <ul>
     *     <li>
     *          creates a new file with the name "medialab/hangman_DICTIONARY-" + this.ID_dict + ".txt"
     *     </li>
     *     <li>
     *          or over-writes it if it already exists <br/>
     *     </li>
     *     <li>
     *          writes all the current words (each one in a separate line) <br/>
     *     </li>
     *     <li>
     *          or prints the exception and returns if an exception occurs <br/>
     *     </li>
     * </ul>
     */
    public void write() {
        File directory = new File("./");
        String current_dir = directory.getAbsolutePath();
        String file_path = null;
        if (current_dir.contains("Hangman")) {
            file_path = "medialab/";
        }
        else {
            file_path = "Hangman/medialab/";
        }
        System.out.println("current:" + directory.getAbsolutePath());
        System.out.println("chose:" + file_path);
        String filename = file_path + "hangman_DICTIONARY-" + this.ID_dict + ".txt";
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
