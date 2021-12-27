package main.hangman;

import exceptions.GameOverException;
import exceptions.LoadedDictionaryException;
import exceptions.ShownCharException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game {

    private ArrayList<String> words;
    private String word;
    private ArrayList<String> words_left;

    private ArrayList<Integer> shown_indexes;
    private Set<Character> available_chars;
    private ArrayList<String> loaded_dicts_ids;

    private int moves;
    private float prob;
    private int round;
    private int points;
    private int chances_remaining;
    private boolean playing;
    private boolean won;
    private ArrayList<ArrayList<String>> prevRounds;

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

    private void filterWordsBySizeAndShownLetters() {
        this.words_left = new ArrayList<String>();
        for (String word : this.words) {
            if (this.isWordAccepted(word)) {
                this.words_left.add(word);
            }
        }
    }

    private void filterWordsByNewLetter(int index, char c, boolean afterCorrectGuess) {
        for (String word : this.words_left) {
            if (afterCorrectGuess && word.charAt(index)!=c) {
                this.words_left.remove(word);
            }
            else if (!afterCorrectGuess && word.charAt(index)==c) {
                this.words_left.remove(word);
            }
        }
    }

    public Set<Character> getAvailable_chars() {
        return available_chars;
    }
    public void setAvailable_chars(Set<Character> available_chars) {
        this.available_chars = available_chars;
    }

    private void initRound(ArrayList<String> words) {
        this.words = words;

        if (!this.words.isEmpty()) {
            this.pickWord();
            this.filterWordsBySizeAndShownLetters();
        }
        else {
            this.words_left = new ArrayList<String>();
        }
        this.shown_indexes = new ArrayList<Integer>();
        this.available_chars = new HashSet<Character>();
        this.moves = 0;
        this.points = 0;
        this.chances_remaining = 6;
        this.won = false;
    }

    public Game(ArrayList<String> words) {
        this.initRound(words);
        this.round = 1;
        this.prevRounds = new ArrayList<ArrayList<String>>();
        this.loaded_dicts_ids = new ArrayList<String>();
    }

    public void newRound() {
        this.initRound(this.words);
        this.round++;
        this.playing = true;
    }

    public void addDict(String dict_id, ArrayList<String> words) throws LoadedDictionaryException {
        if (this.loaded_dicts_ids.contains(dict_id)) {
            throw new LoadedDictionaryException();
        }
        this.loaded_dicts_ids.add(dict_id);
        Set<String> loaded_words = new HashSet<String>(this.words);
        for (String word : words) {
            if (!loaded_words.contains(word)) {
                this.words.add(word);
            }
        }
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

    private void updateAvailableChars() {
        this.available_chars = new HashSet<Character>();
        Set shown_chars = new HashSet<Character>();
        for (Character c: this.word.toCharArray()) {
            shown_chars.add(c);
        }
        for (String word : this.words_left) {
            for (Character c : word.toCharArray()) {
                if (!shown_chars.contains(c)) {
                    this.available_chars.add(c);
                }
            }
        }
    }

    private void saveGame() {
        ArrayList<String> last_round = new ArrayList<String>();
        last_round.add(this.word);
        last_round.add(Integer.toString(this.moves));
        last_round.add(this.won ? "Player" : "Computer");
        this.prevRounds.add(last_round);
    }

    private void forfeit() throws GameOverException{
        this.playing = false;
        this.won = false;
        this.saveGame();
        throw new GameOverException();
    }

    private void afterCheck() throws GameOverException {
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
