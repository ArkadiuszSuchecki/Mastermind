package example.ui;

import example.game.MastermindGame.Color;

public class Prompter {

    public static void entrancePrompt() {
        System.out.println("Welcome to Mastermind Game!");
    }

    public static void instructionIntroPrompt(int codeLength) {
        System.out.println("Try to guess the secret code of " + codeLength + " colors.");
    }

    public static void instructionColorOptionsPrompt() {
        System.out.println("Colors available: 1 - RED, 2 - BLUE, 3 - GREEN, 4 - YELLOW, 5 - ORANGE, 6 - PURPLE");
    }

    public static void guessPrompt(int attempt) {
        System.out.println("\nAttempt " + attempt + ": Enter your guess (e.g., RED BLUE GREEN or 1 2 3):");
    }

    public static void invalidLengthPrompt(int expectedLength) {
        System.out.println("Please enter exactly " + expectedLength + " colors or numbers.");
    }

    public static void invalidInputPrompt(String token) {
        System.out.println("Invalid input: " + token);
    }

    public static void retryPrompt() {
        System.out.println("Try again using color names or numbers from 1 to 6.");
    }

    public static void correctPositionPrompt(int correctPosition) {
        System.out.println("Correct positions (black pegs): " + correctPosition);
    }

    public static void correctColorWrongPositionPrompt(int whitePegs) {
        System.out.println("Correct colors, wrong positions (white pegs): " + whitePegs);
    }

    public static void winMessagePrompt() {
        System.out.println("Congratulations! You cracked the code:");
    }

    public static void winTriesPrompt(int attempts) {
        System.out.println("You won in " + attempts + (attempts == 1 ? " try!" : " tries!"));
    }

    public static void loseMessagePrompt() {
        System.out.println("\nYou've run out of attempts!");
    }

    public static void revealSecretCodePrompt() {
        System.out.print("The secret code was: ");
    }

    public static void scorePrompt(int score) {
        System.out.println("Score: " + score);
    }

    public static void printCode(Color[] code) {
        for (Color color : code) {
            System.out.print(color + " ");
        }
        System.out.println();
    }
}
