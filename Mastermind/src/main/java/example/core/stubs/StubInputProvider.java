package example.core.stubs;

import example.core.InputProvider;

import java.util.LinkedList;
import java.util.Queue;

public class StubInputProvider implements InputProvider {

    private Queue<String> inputs = new LinkedList<>();

    public StubInputProvider() {}

    public StubInputProvider(String... initialInputs) {
        for (String input : initialInputs) {
            inputs.add(input);
        }
    }

    public void addInput(String input) {
        inputs.add(input);
    }

    @Override
    public String getUserInput() {
        return inputs.isEmpty() ? "" : inputs.poll();
    }
}
