package read;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictReader {
    private String name;
    private File file;
    private ArrayList<String> words;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = "hangman_DICTIONARY-" + name + ".txt";
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public DictReader(String name) {
        this.setName(name);
        this.words = new ArrayList<String>();
    }

    public void read() throws FileNotFoundException {
        File directory = new File("./");
        String current_dir = directory.getAbsolutePath();
        String medialab_path = null;
        if (current_dir.contains("Hangman")) {
            medialab_path = "medialab";
        }
        else {
            medialab_path = "Hangman/medialab";
        }
        System.out.println("current:" + directory.getAbsolutePath());
        System.out.println("chose:" + medialab_path);
        File folder = new File(medialab_path);
        System.out.println(folder.listFiles());
        File[] listOfFiles = folder.listFiles();
        System.out.println(listOfFiles);
        boolean found = false;
        if (listOfFiles!=null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().equals(this.getName())) {
                    this.file = listOfFiles[i];
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            Scanner myReader = new Scanner(this.file);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                this.words.add(word.trim());
            }
            myReader.close();
        }
        else {
            throw new FileNotFoundException();
        }
    }


}
