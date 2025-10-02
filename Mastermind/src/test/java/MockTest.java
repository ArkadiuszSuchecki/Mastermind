import example.MastermindApp;
import example.core.CodeComparer;
import example.core.CodeGenerator;
import example.core.stubs.StubOutputProvider;
import example.game.MastermindGame.Color;
import example.game.MastermindGame.GuessResult;
import example.core.InputProvider;
import example.core.OutputProvider;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MockTest {

    private final int CODE_LENGTH = 4;
    private final int MAX_ATTEMPTS = 3;
    private final int SCORE_PER_ATTEMPT = 100;

    private MastermindApp game;
    private CodeGenerator codeGen;
    private InputProvider input;
    private OutputProvider output;
    private CodeComparer comparer;

    private final Color[] SECRET_CODE = new Color[] {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW
    };

    @Before
    public void setup() {
        codeGen = mock(CodeGenerator.class);
        input = mock(InputProvider.class);
        output = mock(OutputProvider.class);
        comparer = mock(CodeComparer.class);

        when(codeGen.generateSecretCode(CODE_LENGTH)).thenReturn(SECRET_CODE);

        game = new MastermindApp(
                codeGen,
                input,
                output,
                CODE_LENGTH,
                MAX_ATTEMPTS,
                SCORE_PER_ATTEMPT,
                comparer
        );
    }

    @Test
    public void winGameAtFirstAttemptSimulation() {
        when(input.getUserInput()).thenReturn("RED BLUE GREEN YELLOW");
        when(comparer.compare(any(), any())).thenReturn(new GuessResult(4, 0));

        game.start();

        verify(output).displayWin(eq(1), anyInt(), eq(SECRET_CODE));
        verify(comparer, times(1)).compare(any(), any());
    }

    @Test
    public void wholeGameSimulation_LoseScenario() {
        when(input.getUserInput())
                .thenReturn("ORANGE ORANGE ORANGE ORANGE")
                .thenReturn("PURPLE PURPLE PURPLE PURPLE")
                .thenReturn("GREEN GREEN GREEN GREEN");

        when(comparer.compare(any(), any())).thenReturn(new GuessResult(0, 0));

        game.start();

        verify(comparer, times(3)).compare(any(), any());
        verify(output).displayLose(eq(SECRET_CODE));
    }

    @Test
    public void testAgentInputProviderIncorrectThenCorrectSequence() {

        when(input.getUserInput())
                .thenReturn("RED RED RED RED")   // incorrect guess
                .thenReturn("RED BLUE GREEN YELLOW"); // winning guess

        when(comparer.compare(any(), any()))
                .thenReturn(new GuessResult(0, 0))  // incorrect guess result
                .thenReturn(new GuessResult(4, 0)); // winning guess result

        game.start();

        verify(input, times(2)).getUserInput();
        verify(comparer, times(2)).compare(any(), any());
        verify(output).displayWin(eq(2), anyInt(), eq(SECRET_CODE));
    }

    @Test
    public void testAgentInputProvideCall() {

        when(input.getUserInput())
                .thenReturn("RED BLUE GREEN YELLOW") // or 1 2 3 4 is winning input
                .thenReturn("RED RED RED RED"); // this will not even be called since game should shut at that point

        when(comparer.compare(any(), any())).thenReturn(new GuessResult(4, 0));

        game.start();

        // Verify input.getUserInput was called exactly once since game ends immediately on win
        verify(input, times(1)).getUserInput();

        // Verify output called displayWin
        verify(output).displayWin(eq(1), anyInt(), eq(SECRET_CODE));
    }

    @Test
    public void testUsingSpyOutputProvider() {
        // Instead of mock output, create a real StubOutputProvider and spy it
        StubOutputProvider realOutput = new StubOutputProvider();
        OutputProvider spyOutput = spy(realOutput);

        // Use realOutput spy in game instance
        game = new MastermindApp(
                codeGen,
                input,
                spyOutput,
                CODE_LENGTH,
                MAX_ATTEMPTS,
                SCORE_PER_ATTEMPT,
                comparer
        );

        // Setup input and comparer as usual
        when(input.getUserInput()).thenReturn("RED BLUE GREEN YELLOW");
        when(comparer.compare(any(), any())).thenReturn(new GuessResult(4, 0));

        game.start();

        // Verify spy called real method displayWin (which should set internal state)
        verify(spyOutput).displayWin(eq(1), anyInt(), eq(SECRET_CODE));

        // Check that real output's internal state changed accordingly
        assertThat(realOutput.containsMessage("You won"), is(true));
    }

}


//    @Test
//    //pointless test just for the record checking out if mockito works not the logic itself
//    public void testMockStaticCompareCodes() {
//        // Create dummy result to return
//        GuessResult dummyResult = new GuessResult(2, 1);
//
//        // Mock static methods of MastermindGame class
//        try (MockedStatic<MastermindGame> mockedStatic = mockStatic(MastermindGame.class)) {
//            // Setup the static method mock
//            mockedStatic.when(() -> MastermindGame.compareCodes((Color[]) any(), (Color[]) any()))
//                    .thenReturn(dummyResult);
//
//            // Call the static method (this will use mocked version)
//            Color[] secret = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
//            Color[] guess = new Color[] {Color.BLUE, Color.RED, Color.GREEN};
//
//            GuessResult result = MastermindGame.compareCodes(secret, guess);
//
//            // Verify the returned dummy result
//            assertThat(result.correctPosition, is(2));
//            assertThat(result.correctColorWrongPosition, is(1));
//
//            // Optional: verify the static method was called once with any params
//            mockedStatic.verify(() -> MastermindGame.compareCodes((Color[]) any(), (Color[]) any()), times(1));
//        }
//    }