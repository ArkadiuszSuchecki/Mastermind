package example.runtime;

import example.core.OutputProvider;
import example.game.MastermindGame.Color;
import example.game.MastermindGame.GuessResult;
import example.ui.Prompter;

public class ConsoleOutputProvider implements OutputProvider {
    @Override public void displayWelcome() { Prompter.entrancePrompt(); }
    @Override public void displayIntro(int len) { Prompter.instructionIntroPrompt(len); }
    @Override public void displayOptions() { Prompter.instructionColorOptionsPrompt(); }
    @Override public void promptGuess(int at) { Prompter.guessPrompt(at); }
    @Override public void displayInvalidLength(int exp) { Prompter.invalidLengthPrompt(exp); }
    @Override public void displayInvalidToken(String token) { Prompter.invalidInputPrompt(token); }
    @Override public void displayRetry() { Prompter.retryPrompt(); }
    @Override public void displayGuessResult(GuessResult r) {
        Prompter.correctPositionPrompt(r.correctPosition);
        Prompter.correctColorWrongPositionPrompt(r.correctColorWrongPosition);
    }
    @Override public void displayWin(int at, int score, Color[] secret) {
        Prompter.winMessagePrompt();
        Prompter.printCode(secret);
        Prompter.scorePrompt(score);
        Prompter.winTriesPrompt(at);
    }
    @Override public void displayLose(Color[] secret) {
        Prompter.loseMessagePrompt();
        Prompter.revealSecretCodePrompt();
        Prompter.printCode(secret);
        Prompter.scorePrompt(0);
    }
}
