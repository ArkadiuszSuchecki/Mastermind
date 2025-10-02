// file: example/MastermindApp.java
package example;

import example.core.*;
import example.game.MastermindGame.Color;
import example.game.MastermindGame;

public class MastermindApp {
    private final CodeGenerator codeGen;
    private final InputProvider input;
    private final OutputProvider output;
    private final int codeLength;
    private final int maxAttempts;
    private final int scorePerAttempt;
    private final CodeComparer comparer;

    public MastermindApp(CodeGenerator cg, InputProvider ip, OutputProvider op,
                         int codeLength, int maxAttempts, int scorePerAttempt,
                         CodeComparer comparer) {
        this.codeGen = cg;
        this.input = ip;
        this.output = op;
        this.codeLength = codeLength;
        this.maxAttempts = maxAttempts;
        this.scorePerAttempt = scorePerAttempt;
        this.comparer = comparer;
    }

    public MastermindApp(CodeGenerator cg, InputProvider ip, OutputProvider op,
                         int codeLength, int maxAttempts, int scorePerAttempt) {
        this(cg, ip, op, codeLength, maxAttempts, scorePerAttempt, new CodeComparer() {
            @Override
            public MastermindGame.GuessResult compare(Color[] secret, Color[] guess) {
                return MastermindGame.compareCodes(secret, guess);
            }
        });
    }

    public void start() {
        Color[] secret = codeGen.generateSecretCode(codeLength);
        int score = maxAttempts * scorePerAttempt;
        output.displayWelcome();
        output.displayIntro(codeLength);
        output.displayOptions();

        int attempts = 0;
        while (attempts < maxAttempts) {
            attempts++;
            output.promptGuess(attempts);
            String line = input.getUserInput().trim();
            String[] tokens = line.split("\\s+");

            if (tokens.length != codeLength) {
                output.displayInvalidLength(codeLength);
                attempts--;
                continue;
            }
            Color[] guess = new Color[codeLength];
            boolean valid = true;
            for (int i = 0; i < codeLength; i++) {
                if (!Color.isValidColorInput(tokens[i])) {
                    output.displayInvalidToken(tokens[i]);
                    valid = false;
                    break;
                }
                guess[i] = Color.fromInput(tokens[i]);
            }
            if (!valid) { attempts--; output.displayRetry(); continue; }

            var result = comparer.compare(secret, guess);
            output.displayGuessResult(result);
            if (result.correctPosition == codeLength) {
                output.displayWin(attempts, score, secret);
                return;
            }
            score -= scorePerAttempt;
        }
        output.displayLose(secret);
    }

    public static void main(String[] args) {
        new MastermindApp(
                new example.runtime.ConsoleCodeGenerator(),
                new example.runtime.ConsoleInputProvider(),
                new example.runtime.ConsoleOutputProvider(),
                4, 10, 100
        ).start();
    }
}
