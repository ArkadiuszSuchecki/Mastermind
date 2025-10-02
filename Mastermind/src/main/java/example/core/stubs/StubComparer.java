package example.core.stubs;

import example.core.CodeComparer;
import example.game.MastermindGame;
import example.game.MastermindGame.Color;

public class StubComparer implements CodeComparer {

    private MastermindGame.GuessResult forcedResult;
    public int timesCalled = 0;

    public void setForcedResult(MastermindGame.GuessResult result) {
        this.forcedResult = result;
    }

    @Override
    public MastermindGame.GuessResult compare(Color[] secret, Color[] guess) {
        timesCalled++;
        if (forcedResult != null) return forcedResult;
        return new MastermindGame.GuessResult(0, 0); // fallback default
    }
}