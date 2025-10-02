import example.core.CodeComparer;
import example.core.CodeGenerator;
import example.core.stubs.StubComparer;
import example.game.MastermindGame;
import example.MastermindApp;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import example.game.MastermindGame.Color;
import example.core.stubs.StubInputProvider;
import example.core.stubs.StubOutputProvider;


public class ManualTest {

    private final int CODE_LENGTH = 4;
    private final int MAX_ATTEMPTS = 3;
    private final int SCORE_PER_ATEMPT = 100;

    private StubInputProvider inputProvider;
    private StubOutputProvider outputProvider;
    private MastermindApp game;
    private StubComparer stubComparer;;

    private final Color[] SECRET_CODE = new Color[]{
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW
    };




    @Before
    public void setup() {
        inputProvider = new StubInputProvider();
        outputProvider = new StubOutputProvider();
        stubComparer = new StubComparer();

        CodeGenerator fixedCodeGen = new CodeGenerator() {
            @Override
            public Color[] generateSecretCode(int length) {
                return SECRET_CODE; // already defined as RED, BLUE, ...
            }
        };

        game = new MastermindApp(
                fixedCodeGen,
                inputProvider,
                outputProvider,
                CODE_LENGTH,
                MAX_ATTEMPTS,
                SCORE_PER_ATEMPT,
                stubComparer
        );
    }

     /* Use this helper method in test to simulate user input (one input per line).
     * @param inputs Each string will simulate one line of user input. */

//    protected void setUserInput(String... inputs) {
//        String joined = String.join("\n", inputs) + "\n";
//        ByteArrayInputStream testIn = new ByteArrayInputStream(joined.getBytes());
//        System.setIn(testIn);
//    }
//
//    public String getOutput() {
//        return outContent.toString();
//    }
//
//    @After
//    public void restoreSystemIO() {
//        System.setIn(originalSystemIn);
//        System.setOut(originalSystemOut);
//    }

//    @Test
//    //agent testing
//    public void agentFunctionalityTesting() throws Exception {
//        String simulatedInput = "test input line";
//        setUserInput(simulatedInput);
//
//        Scanner scanner = new Scanner(System.in);
//        String actualInput = scanner.nextLine();
//
//        assertThat(actualInput, is(simulatedInput));
//    }

    @Test
    //pointless test just for the record checking out if my stub works not the logic itself
    public void testManualStubGuessResult() {

        MastermindGame.GuessResult stubResult = new MastermindGame.GuessResult(2, 1);

        assertThat(stubResult.correctPosition, is(2));
        assertThat(stubResult.correctColorWrongPosition, is(1));
    }

    @Test
    public void testSpyCapturesExpectedOutput() {
        outputProvider.displayWin(3, 100, SECRET_CODE);
        assertThat(outputProvider.containsMessage("You won"), is(true));
    }

    @Test
    public void testStubInputIsReturned() {
        inputProvider.addInput("RED");
        String input = inputProvider.getUserInput();
        assertThat(input, is("RED"));
    }

    @Test
    public void wholeGameSimulation_LoseScenario() {
        inputProvider.addInput("ORANGE ORANGE ORANGE ORANGE"); // attempt 1
        inputProvider.addInput("PURPLE PURPLE PURPLE PURPLE"); // attempt 2
        inputProvider.addInput("GREEN GREEN GREEN GREEN");     // attempt 3

        game.start();

        assertThat(outputProvider.containsMessage("You've run out of attempts"), is(true));
        assertThat(outputProvider.containsMessage("Secret code was shown"), is(true));
        assertThat("Comparer should be called once per attempt", stubComparer.timesCalled, is(3));
    }

    @Test
    public void winGameAtFirstAttemptSimulation() {
        inputProvider.addInput("RED BLUE GREEN YELLOW");

        stubComparer.setForcedResult(new MastermindGame.GuessResult(4, 0));

        game.start();

        assertThat(outputProvider.containsMessage("You won"), is(true));
    }

}
