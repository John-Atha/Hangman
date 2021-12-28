package helpers;

import main.hangman.Game;

public class HeaderState {
    private Game game;
    private int total_words;
    private int words_left;
    private int points;
    private float rate;
    private String hidden_word;

    public HeaderState(Game game) {
        this.setGame(game);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        this.hidden_word =this.game.getWord();
        this.total_words = this.game.getWords().size();
        this.words_left = this.game.getWords_left().size();
        this.points = this.game.getPoints();
        float rate = this.game.getMoves()==0 ? 0f : (this.game.getMoves()-(6-this.game.getChances_remaining()))/((float) this.game.getMoves());
        this.rate = rate;
        // System.out.println("I am the header state, updated to: " + this.total_words);
    }

    public int getTotal_words() {
        return total_words;
    }
    public void setTotal_words(int total_words) {
        this.total_words = total_words;
    }

    public int getWords_left() {
        return words_left;
    }
    public void setWords_left(int words_left) {
        this.words_left = words_left;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getHidden_word() {
        return hidden_word;
    }
    public void setHidden_word(String hidden_word) {
        this.hidden_word = hidden_word;
    }

}
