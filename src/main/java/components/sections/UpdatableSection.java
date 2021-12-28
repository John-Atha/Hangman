package components.sections;

import main.hangman.Game;

public abstract class UpdatableSection {
    private Game game;

    public void update(Game game) {
        this.setGame(game);
    }

    public abstract void setGame(Game game);
}
