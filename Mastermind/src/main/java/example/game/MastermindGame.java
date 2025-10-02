package example.game;

import example.core.InputProvider;
import example.core.OutputProvider;

import java.util.*;
import java.util.function.Supplier;

public class MastermindGame {

    public enum Color {
        RED, BLUE, GREEN, YELLOW, ORANGE, PURPLE;

        private static final Random random = new Random();

        public static Color getRandomColor() {
            return values()[random.nextInt(values().length)];
        }

        public static Color getColorByNumber(int number) {
            return values()[number - 1];
        }

        public static boolean isValidColorInput(String input) {
            try {
                int number = Integer.parseInt(input);
                return number >= 1 && number <= values().length;
            } catch (NumberFormatException e) {
                return Arrays.stream(values())
                        .anyMatch(color -> color.name().equalsIgnoreCase(input));
            }
        }

        public static Color fromInput(String input) {
            if (Character.isDigit(input.charAt(0))) {
                return getColorByNumber(Integer.parseInt(input));
            } else {
                return Color.valueOf(input.toUpperCase());
            }
        }
    }

//    public static Color[] generateSecretCode(int length, Supplier<Color> colorSupplier) {
//        Color[] code = new Color[length];
//        for (int i = 0; i < length; i++) {
//            code[i] = colorSupplier.get();
//        }
//        return code;
//    }
//
//    public static Color[] generateSecretCode(int length) {
//        return generateSecretCode(length, () -> Color.getRandomColor());
//    }

    public static class GuessResult {
        public int correctPosition; // black marble / peg
        public int correctColorWrongPosition; // white marble / peg

        public GuessResult(int correctPosition, int correctColorWrongPosition) {
            this.correctPosition = correctPosition;
            this.correctColorWrongPosition = correctColorWrongPosition;
        }
    }



    private final int codeLength;
    private final int maxAttempts;
    private final Color[] secretCode;
    private final InputProvider inputProvider;
    private final OutputProvider outputProvider;

    public MastermindGame(int codeLength, int maxAttempts, InputProvider inputProvider, OutputProvider outputProvider) {
        this(codeLength, maxAttempts, inputProvider, outputProvider, generateSecretCode(codeLength));
    }

    public MastermindGame(int codeLength, int maxAttempts, InputProvider inputProvider, OutputProvider outputProvider, int[] numericSecretCode) {
        this(codeLength, maxAttempts, inputProvider, outputProvider, convertToColorArray(numericSecretCode));
    }

    public MastermindGame(int codeLength, int maxAttempts, InputProvider inputProvider, OutputProvider outputProvider, Color[] secretCode) {
        this.codeLength = codeLength;
        this.maxAttempts = maxAttempts;
        this.inputProvider = inputProvider;
        this.outputProvider = outputProvider;
        this.secretCode = secretCode != null ? secretCode : generateSecretCode(codeLength);
    }

    public static Color[] generateSecretCode(int length, Supplier<Color> colorSupplier) {
        Color[] code = new Color[length];
        for (int i = 0; i < length; i++) {
            code[i] = colorSupplier.get();
        }
        return code;
    }

    public static Color[] generateSecretCode(int length) {
        return generateSecretCode(length, Color::getRandomColor);
    }

    private static Color[] convertToColorArray(int[] numericSecretCode) {
        Color[] colors = new Color[numericSecretCode.length];
        for (int i = 0; i < numericSecretCode.length; i++) {
            colors[i] = Color.getColorByNumber(numericSecretCode[i]);
        }
        return colors;
    }

    public static GuessResult compareCodes(Color[] secret, Color[] guess) {

        throw new UnsupportedOperationException(
                "compareCodes() is not implemented. Use a stub or mock in tests."
        );
//        boolean[] secretMatched = new boolean[secret.length];
//        boolean[] guessMatched = new boolean[guess.length];
//
//        int black = 0;
//        int white = 0;
//
//        for (int i = 0; i < secret.length; i++) {
//            if (secret[i] == guess[i]) {
//                black++;
//                secretMatched[i] = true;
//                guessMatched[i] = true;
//            }
//        }
//
//        for (int i = 0; i < guess.length; i++) {
//            if (guessMatched[i]) continue;
//            for (int j = 0; j < secret.length; j++) {
//                if (!secretMatched[j] && guess[i] == secret[j]) {
//                    white++;
//                    secretMatched[j] = true;
//                    break;
//                }
//            }
//        }
//        return new GuessResult(black, white);
    }
}
