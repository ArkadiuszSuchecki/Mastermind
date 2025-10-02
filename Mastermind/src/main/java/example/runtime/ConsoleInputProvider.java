package example.runtime;

import example.core.InputProvider;
import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }
}
