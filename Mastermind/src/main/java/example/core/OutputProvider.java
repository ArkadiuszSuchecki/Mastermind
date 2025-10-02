package example.core;

import example.game.MastermindGame.GuessResult;
import example.game.MastermindGame.Color;

public interface OutputProvider {
    void displayWelcome();
    void displayIntro(int codeLength);
    void displayOptions();
    void promptGuess(int attempt);
    void displayInvalidLength(int expected);
    void displayInvalidToken(String token);
    void displayRetry();
    void displayGuessResult(GuessResult result);
    void displayWin(int attempts, int score, Color[] secret);
    void displayLose(Color[] secret);
}
