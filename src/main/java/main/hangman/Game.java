package main.hangman;

import exceptions.GameOverException;
import exceptions.LoadedDictionaryException;
import exceptions.NoDictsException;
import exceptions.ShownCharException;

import java.util.*;

public class Game {

    private ArrayList<String> words;
    private String word;
    private ArrayList<String> words_left;

    private ArrayList<Integer> shown_indexes;
    private Map<Integer, ArrayList<Character>> available_chars;
    private ArrayList<String> loaded_dicts_ids;

    private int moves;
    private float prob;
    private int round;
    private int points;
    private int chances_remaining;
    private boolean playing;
    private boolean won;
    private ArrayList<ArrayList<String>> prevRounds;
    private String prevWord;

    public String getPrevWord() {
        return prevWord;
    }
    public void setPrevWord(String prevWord) {
        this.prevWord = prevWord;
    }

    public ArrayList<String> getWords() {
        return words;
    }
    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<String> getWords_left() {
        return words_left;
    }
    public void setWords_left(ArrayList<String> same_length_words) {
        this.words_left = same_length_words;
    }

    public ArrayList<Integer> getShown_indexes() {
        return shown_indexes;
    }
    public void setShown_indexes(ArrayList<Integer> shown_indexes) {
        this.shown_indexes = shown_indexes;
    }

    public ArrayList<String> getLoaded_dicts_ids() {
        return loaded_dicts_ids;
    }
    public void setLoaded_dicts_ids(ArrayList<String> loaded_dicts_ids) {
        this.loaded_dicts_ids = loaded_dicts_ids;
    }

    public int getMoves() {
        return moves;
    }
    public void setMoves(int moves) {
        this.moves = moves;
    }

    public float getProb() {
        return prob;
    }
    public void setProb(float prob) {
        this.prob = prob;
    }

    public int getRound() {
        return round;
    }
    public void setRound(int round) {
        this.round = round;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public int getChances_remaining() {
        return chances_remaining;
    }
    public void setChances_remaining(int chances_remaining) {
        this.chances_remaining = chances_remaining;
    }

    public boolean isPlaying() {
        return playing;
    }
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isWon() {
        return won;
    }
    public void setWon(boolean won) {
        this.won = won;
    }

    public ArrayList<ArrayList<String>> getPrevRounds() {
        return prevRounds;
    }
    public void setPrevRounds(ArrayList<ArrayList<String>> prevRounds) {
        this.prevRounds = prevRounds;
    }

    private void pickWord() {
        Random ran = new Random();
        int index = ran.nextInt(this.words.size());
        String word = this.words.get(index);
        this.word = word;
        System.out.println("Picked word: " + this.word);
    }

    private boolean isWordAccepted(String word) {
        int length = this.word.length();
        if (word.length() != length) return false;
        // if (word.equals(this.word)) return false;
        for (int index: this.getShown_indexes()) {
            if (! (word.charAt(index) == this.word.charAt(index)) ) {
                return false;
            }
        }
        return true;
    }

    /*private void filterWordsBySizeAndShownLetters() {
        this.words_left = new ArrayList<String>();
        for (String word : this.words) {
            if (this.isWordAccepted(word)) {
                this.words_left.add(word);
            }
        }
    }*/
    private void filterWordsBySizeAndShownLetters() {
        ArrayList<String> temp = new ArrayList<>(this.words_left);

        this.words_left = new ArrayList<String>();

        for (String word : temp) {
            if (this.isWordAccepted(word)) {
                this.words_left.add(word);
            }
        }
    }

    private void filterWordsByNewLetter(int index, char c, boolean afterCorrectGuess) {
        ArrayList<String> temp_words_left = new ArrayList<>(this.words_left);
        if (!afterCorrectGuess) {
            System.out.println("Removing words with letter " + c + " at index " + index);
        }
        for (String word : this.words_left) {
            System.out.println("Seeing: " + word.toUpperCase(Locale.ROOT).charAt(index));
            if (afterCorrectGuess && word.toUpperCase(Locale.ROOT).charAt(index)!=c) {
                temp_words_left.remove(word);
            }
            else if (!afterCorrectGuess && word.toUpperCase(Locale.ROOT).charAt(index)==c) {
                System.out.println("REMOVING --------- " + word);
                temp_words_left.remove(word);
            }
        }
        this.words_left = temp_words_left;
        System.out.println("Remaining: " + this.words_left);
    }

    public Map<Integer, ArrayList<Character>> getAvailable_chars() {
        return available_chars;
    }
    public void setAvailable_chars(Map<Integer, ArrayList<Character>> available_chars) {
        this.available_chars = available_chars;
    }

    // dummy copy constructor to use on `CharacterForm.java`
    public Game(Game another) {
        this.word = another.word;
    }

    public Game(ArrayList<String> words) {
        this.round = 0;
        this.prevRounds = new ArrayList<ArrayList<String>>();
        this.loaded_dicts_ids = new ArrayList<String>();
        this.words = words!=null ? words : new ArrayList<String>();
        this.available_chars = new HashMap<Integer, ArrayList<Character>>();
        this.shown_indexes = new ArrayList<Integer>();
        this.words_left = new ArrayList<String>(words);
        this.moves = 0;
        this.points = 0;
        this.chances_remaining = 6;
        this.won = false;
    }

    public void newRound() throws NoDictsException {
        this.prevWord = null;
        this.initRound(this.words); // exception thrown here if words are null || []
        this.round++;
        this.playing = true;
    }

    private void initRound(ArrayList<String> words) throws NoDictsException {
        this.words = words;
        this.shown_indexes = new ArrayList<Integer>();
        this.moves = 0;
        this.points = 0;
        this.chances_remaining = 6;
        this.won = false;

        if (this.available_chars == null) {
            System.out.println("Clearing available chars...");
            this.available_chars = new HashMap<Integer, ArrayList<Character>>();
        }
        if (!(this.words==null || this.words.isEmpty())) {
            this.pickWord();
            this.filterWordsBySizeAndShownLetters();
            this.words_left = new ArrayList<>(this.words);
            this.updateAvailableChars();
        }
        else {
            this.words_left = new ArrayList<String>();
            throw new NoDictsException();
        }

    }

    public void addDict(String dict_id, ArrayList<String> words) throws LoadedDictionaryException {
        if (this.loaded_dicts_ids.contains(dict_id)) {
            throw new LoadedDictionaryException();
        }
        this.loaded_dicts_ids.add(dict_id);
        Set<String> loaded_words = new HashSet<String>(this.words!=null ? this.words : new ArrayList<>());
        for (String word : words) {
            if (!loaded_words.contains(word)) {
                this.words.add(word);
            }
        }
        this.words_left = new ArrayList<>(this.words);
        //this.updateAvailableChars();
    }

    private void computeProb(int index, char c) {
        this.filterWordsBySizeAndShownLetters();
        int n = this.words_left.size();
        float m = 0;
        for (String word : this.words_left) {
            if (word.charAt(index) == c) {
                m++;
            }
        }
        this.prob = m/n;
    }

    private void goodMove(int index, char c) {
        int extra_points = 0;
        if (this.prob>=0.6) {
            extra_points = 5;
        }
        else if (0.4<=this.prob && this.prob<0.6) {
            extra_points = 10;
        }
        else if (0.25<=this.prob && this.prob<0.4) {
            extra_points = 15;
        }
        else if (this.prob<0.25) {
            extra_points = 30;
        }
        this.points += extra_points;
        this.shown_indexes.add(index);
        this.filterWordsByNewLetter(index, c, true);
    }

    private void badMove(int index, char c) {
        this.chances_remaining -= 1;
        if (this.points>15) {
            this.points -= 15;
        }
        else {
            this.points = 0;
        }
        this.filterWordsByNewLetter(index, c, false);
    }

    private void validate(int index, char c) throws ShownCharException {
        if (this.shown_indexes.contains(index)) {
            throw new ShownCharException();
        }
    }

    private void perform(int index, char c) {
        this.moves ++;
        this.computeProb(index, c);
        if ( c==this.word.charAt(index) ) {
            this.goodMove(index, c);
        }
        else {
            this.badMove(index, c);
        }
    }

    // a simple class to be used for keeping and sorting the characters at an index by their possibility
    private class Position implements  Comparable<Position> {
        private char c;
        private float p;
        public Position(char c, float p) {
            this.c = c;
            this.p = p;
        }
        public char getC() {
            return c;
        }
        public float getP() {
            return p;
        }
        // reverse sorting...
        @Override
        public int compareTo(Position position) {
            return (this.p >= position.getP()) ? -1 : 1;
        }
    }

    private void updateAvailableChars() {
        this.filterWordsBySizeAndShownLetters();
        System.out.print("SEEING words left: " + this.words_left);
        this.available_chars = new HashMap<Integer, ArrayList<Character>>();
        // Set shown_chars = new HashSet<Character>();


        // for each hidden position of the word
        for (int index=0; index<word.length(); index++) {
            if (!shown_indexes.contains(index)) {
                // count the shows of each char in the hashmap `counter`
                Map counter = new HashMap<Character,  ArrayList<Float>>();
                float all = words_left.size();
                for (String word_ : words_left) {
                    char c = word_.charAt(index);
                    if (counter.get(c)!=null) {
                        int x = (int) counter.get(c);
                        counter.put(c, x+1);
                    }
                    else {
                        counter.put(c, 1);
                    }
                }
                // turn the values of the `counter` to probabilities
                for (Object key : counter.keySet()) {
                    int x = (int) counter.get(key);
                    counter.put(key, x/all);
                }

                // turn the items to 'Position' objects, to sort them easily
                ArrayList<Character> chars = new ArrayList<>(counter.keySet());
                ArrayList<Float> ps = new ArrayList<>(counter.values());
                ArrayList<Position> positions = new ArrayList<>();
                for (int i=0 ;i <chars.size(); i++) {
                    Position pos = new Position(chars.get(i), ps.get(i));
                    positions.add(pos);
                }
                Collections.sort(positions);
                // store in an arraylist
                ArrayList<Character> sorted = new ArrayList<>();
                for (Position pos : positions) {
                    sorted.add(pos.getC());
                }
                // store in state dictionary
                this.available_chars.put(index, sorted);
            }
        }
        System.out.println(this.available_chars);
    }

    private void saveGame() {
        /* add this round to the saved */
        ArrayList<String> last_round = new ArrayList<String>();
        last_round.add(this.word);
        last_round.add(Integer.toString(this.moves));
        last_round.add(this.won ? "Player" : "Computer");
        this.prevRounds.add(last_round);
    }

    public void giveUp() throws GameOverException{
        this.playing = false;
        this.won = false;
        this.saveGame();
        throw new GameOverException();
    }

    private void afterCheck() throws GameOverException {
        // game is over...
        if (this.shown_indexes.size()==this.word.length()) {
            this.playing = false;
            this.won = true;
            this.saveGame();
            throw new GameOverException();
        }
        else if (this.chances_remaining==0) {
            this.playing = false;
            this.won = false;
            this.saveGame();
            throw new GameOverException();
        }
        // else...
        else {
            this.updateAvailableChars();
        }
    }

    public void move(int index, char c) throws GameOverException, ShownCharException {
        validate(index, c);
        perform(index, c);
        afterCheck();
    }

}
