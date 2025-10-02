package example.core;

import example.game.MastermindGame.Color;
import example.game.MastermindGame.GuessResult;

public interface CodeComparer {
    GuessResult compare(Color[] secret, Color[] guess);
}
