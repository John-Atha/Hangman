package helpers;

public class Round {
    private String word;
    private int tries;
    private String winner;


    public Round(String word, int tries, String winner) {
        this.word = word;
        this.tries = tries;
        this.winner = winner;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getTries() {
        return tries;
    }
    public void setTries(int tries) {
        this.tries = tries;
    }
    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }
}
