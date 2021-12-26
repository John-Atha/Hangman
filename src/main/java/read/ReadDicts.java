package read;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ReadDicts {

    private String dirName;
    private ArrayList<File> filenames;
    private ArrayList<String> words;

    public ReadDicts(String dirName) {
        this.setDirName(dirName);
        this.setWords(new ArrayList<String>());
        this.setFilenames(new ArrayList<File>());

        File folder = new File(dirName);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                this.filenames.add(listOfFiles[i]);
            }
        }
    }

    public String getDirName() {
        return this.dirName;
    }
    private void setDirName(String dirName) {
        this.dirName = dirName;
    }


    public ArrayList<File> getFilenames() {
        return this.filenames;
    }
    private void setFilenames(ArrayList<File> filenames) {
        this.filenames = filenames;
    }

    public ArrayList<String> getWords() {
        return this.words;
    }
    private void setWords(ArrayList<String> words) {
        this.words = words;
    }

    private void readOneFile(File file) {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                this.words.add(word.trim());
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public String pickWord() {
        for (File file : this.filenames) {
            readOneFile(file);
        }
        Random ran = new Random();
        int index = ran.nextInt(this.words.size());
        String word = this.words.get(index);
        return word;
    }
}
