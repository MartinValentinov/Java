import java.util.*;

import algorithms.DoubleElimination;
import algorithms.RobinRound;
import algorithms.SingleElimination;
import cli.Style;
import interfaces.Algorithm;
import interfaces.Decider;
import deciders.UserDecider;
import deciders.DefaultDecider;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println(Style.title(Style.STAR + " Tournament Bracket CLI " + Style.STAR));
        System.out.println(Style.BLUE + "Backend: Single Elimination" + Style.RESET);

        List<String> participants = loadParticipantsMenu();
        Algorithm algo = selectAlgorithmMenu();
        Decider decider = selectDeciderMenu();

        PlacementTournament tournament = new PlacementTournament(algo, decider);

        tournament.build(participants);

        printMenu(tournament);
    }

    private static List<String> loadParticipantsMenu() {
        System.out.println();
        System.out.println(Style.title("Load Participants"));
        System.out.println(" 1) Enter manually " + Style.DOT);
        System.out.println(" 2) Load from CSV " + Style.DOT);
        System.out.print(Style.YELLOW + "Choose: " + Style.RESET);

        while (true) {
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1":
                    return enterManual();
                case "2":
                    return loadCSV();
                default:
                    System.out.print(Style.RED + "Invalid choice: " + Style.RESET);
            }
        }
    }

    private static List<String> enterManual() {
        System.out.println();
        System.out.println(Style.title("Manual Entry") + " " + Style.STAR);
        System.out.println("Enter names (empty line to finish):");

        List<String> list = new ArrayList<>();
        while (true) {
            String s = scanner.nextLine().trim();
            if (s.isEmpty())
                break;
            list.add(s);
        }
        return list;
    }

    private static List<String> loadCSV() {
        System.out.print("CSV path: ");
        String path = scanner.nextLine().trim();

        try {
            return CsvReader.readCsv(path);
        } catch (Exception e) {
            System.out.println(Style.bad("Failed to load file: " + e.getMessage()));
            return enterManual();
        }
    }

    private static Decider selectDeciderMenu() {
        System.out.println();
        System.out.println(Style.title("Select Match Decider"));
    
        // Имена + decider-и
        Map<Integer, Map.Entry<String, Decider>> deciders = new LinkedHashMap<>();
        deciders.put(1, Map.entry("Random 🎲", DefaultDecider.INSTANCE));
        deciders.put(2, Map.entry("Manual 🧑‍⚖️", UserDecider.INSTANCE));
        // можеш да добавиш още:
        // deciders.put(3, Map.entry("Best of 3 🎮", BestOf3Decider.INSTANCE));
    
        // Принтиране
        deciders.forEach((id, entry) -> {
            System.out.println(" " + id + ") " + entry.getKey()); // точката махната
        });
    
        System.out.print(Style.YELLOW + "Choose: " + Style.RESET);
    
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                Map.Entry<String, Decider> entry = deciders.get(choice);
                if (entry != null) return entry.getValue();
            } catch (NumberFormatException ignore) {}
    
            System.out.print(Style.RED + "Invalid choice. Try again: " + Style.RESET);
        }
    }
    

    private static Algorithm selectAlgorithmMenu() {
        System.out.println();
        System.out.println(Style.title("Select Tournament Algorithm"));
    
        // === Имена + алгоритми ===
        Map<Integer, Map.Entry<String, Algorithm>> algorithms = new LinkedHashMap<>();
    
        algorithms.put(1, Map.entry("Single Elimination 🗡️", SingleElimination.INSTANCE));
        algorithms.put(2, Map.entry("Double Elimination ♻️", DoubleElimination.INSTANCE));
        algorithms.put(3, Map.entry("Round Robin 🔄", RobinRound.INSTANCE));
    
        // === Принтиране ===
        algorithms.forEach((id, entry) -> {
            System.out.println(" " + id + ") " + entry.getKey());
        });
    
        System.out.print(Style.YELLOW + "Choose: " + Style.RESET);
    
        // === Input loop ===
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                Map.Entry<String, Algorithm> entry = algorithms.get(choice);
                if (entry != null) return entry.getValue();
            } catch (NumberFormatException ignore) {}
    
            System.out.print(Style.RED + "Invalid choice. Try again: " + Style.RESET);
        }
    }
    

    private static void printMenu(PlacementTournament tournament) {

        while (true) {
            System.out.println();
            System.out.println(Style.title("Tournament Menu"));
            System.out.println(" 1) Show match history");
            System.out.println(" 2) Show points");
            System.out.println(" 3) Show champion " + Style.TROPHY);
            System.out.println(" 4) Exit");
            System.out.print(Style.YELLOW + "Choose: " + Style.RESET);

            switch (scanner.nextLine().trim()) {
                case "1":
                    tournament.printTournament();
                    break;
                case "2":
                    tournament.printPoints();
                    break;
                case "3":
                    tournament.printChampion();
                    break;
                case "4":
                    System.out.println(Style.ok("Goodbye!"));
                    return;
                default:
                    System.out.println(Style.bad("Invalid option"));
            }
        }
    }
}
