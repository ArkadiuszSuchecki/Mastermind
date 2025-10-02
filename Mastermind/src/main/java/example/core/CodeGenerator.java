package example.core;

import example.game.MastermindGame.Color;

public interface CodeGenerator {
    Color[] generateSecretCode(int length);
}
