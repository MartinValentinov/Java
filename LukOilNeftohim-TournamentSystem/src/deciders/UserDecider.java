package deciders;

import java.util.Scanner;
import interfaces.Decider;
import cli.Style;

public interface UserDecider {
    Decider INSTANCE = (team1, team2) -> {
        final Scanner scanner = new Scanner(System.in);

        System.out.println(Style.title("\n" + Style.VS + " Match"));
        System.out.println(Style.CYAN + "  1) " + team1 + Style.RESET);
        System.out.println(Style.CYAN + "  2) " + team2 + Style.RESET);
        System.out.print(Style.YELLOW + "Choose winner (1/2): " + Style.RESET);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return team1;
            else if (input.equals("2")) return team2;

            System.out.print(Style.RED + "Invalid choice. Try again: " + Style.RESET);
        }
    };
}
