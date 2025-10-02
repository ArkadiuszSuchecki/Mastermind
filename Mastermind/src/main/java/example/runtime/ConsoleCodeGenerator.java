package example.runtime;

import example.core.CodeGenerator;
import example.game.MastermindGame;
import example.game.MastermindGame.Color;

public class ConsoleCodeGenerator implements CodeGenerator {
    @Override
    public Color[] generateSecretCode(int length) {
        return MastermindGame.generateSecretCode(length);
    }
}