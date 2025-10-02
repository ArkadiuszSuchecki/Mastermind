package example.runtime;

import example.core.CodeComparer;
import example.game.MastermindGame;

public class DefaultCodeComparer implements CodeComparer {
    @Override
    public MastermindGame.GuessResult compare(MastermindGame.Color[] secret, MastermindGame.Color[] guess) {
        return MastermindGame.compareCodes(secret, guess);
    }
}
