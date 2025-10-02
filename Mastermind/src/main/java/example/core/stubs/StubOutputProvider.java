package example.core.stubs;

import example.core.OutputProvider;
import example.game.MastermindGame.Color;
import example.game.MastermindGame.GuessResult;

import java.util.ArrayList;
import java.util.List;

public class StubOutputProvider implements OutputProvider {

    private List<String> messages = new ArrayList<>();
    private int promptGuessCount = 0;

    private void log(String msg) {
        messages.add(msg);
    }

    public List<String> getMessages() {
        return messages;
    }

    public boolean containsMessage(String substring) {
        return messages.stream().anyMatch(msg -> msg.contains(substring));
    }

    @Override
    public void displayWelcome() {
        log("Welcome to Mastermind Game!");
    }

    @Override
    public void displayIntro(int codeLength) {
        log("Try to guess the secret code of " + codeLength + " colors.");
    }

    @Override
    public void displayOptions() {
        log("Colors available: 1 - RED, 2 - BLUE, 3 - GREEN, 4 - YELLOW, 5 - ORANGE, 6 - PURPLE");
    }

    @Override
    public void promptGuess(int attempt) {
        promptGuessCount++;
        log("Attempt " + attempt + ": Enter your guess.");
    }

    @Override
    public void displayInvalidLength(int expected) {
        log("Please enter exactly " + expected + " colors or numbers.");
    }

    @Override
    public void displayInvalidToken(String token) {
        log("Invalid input: " + token);
    }

    @Override
    public void displayRetry() {
        log("Try again using color names or numbers from 1 to 6.");
    }

    @Override
    public void displayGuessResult(GuessResult result) {
        log("Correct positions: " + result.correctPosition + ", correct color wrong position: " + result.correctColorWrongPosition);
    }

    @Override
    public void displayWin(int attempts, int score, Color[] secret) {
        log("You won in " + attempts + " attempts with score " + score + ".");
    }

    @Override
    public void displayLose(Color[] secret) {
        log("You've run out of attempts. Secret code was shown.");
    }

    public int getPromptCount() {
        return promptGuessCount;
    }
}
