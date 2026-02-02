import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.*;

public class PlacementTournament {

    private final Algorithm algo;
    private final Decider decider;
    private List<MatchResult> results; 

    public PlacementTournament(Algorithm algo, Decider decider) {
        this.algo = algo;
        this.decider = decider;
    }

    public void build(List<String> participants) {
        results = algo.run(participants, decider);
    }

    public void printTournament() {
        System.out.println("=== Tournament History ===");
        if (results.isEmpty()) {
            System.out.println("No matches played.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            MatchResult match = results.get(i);
            System.out.println((i + 1) + ". " + match.winner() + " defeated " + match.loser());
        }
    }
    
    public Map<String, Integer> calculatePoints() {
        Map<String, Integer> points = new HashMap<>();

        for (MatchResult match : results) {
            points.put(match.winner(), points.getOrDefault(match.winner(), 0) + 1); 
            points.put(match.loser(), points.getOrDefault(match.loser(), 0));      
        }

        return points;
    }

    public void printPoints() {
        Map<String, Integer> points = calculatePoints();
        System.out.println("=== Final Standings ===");
        points.entrySet()
              .stream()
              .sorted((a, b) -> b.getValue() - a.getValue()) 
              .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " points"));
    }

    public void printChampion() {
        if (results.isEmpty()) {
            System.out.println("No champion.");
            return;
        }
        Map<String, Integer> points = calculatePoints();
        String champion = points.entrySet()
            .stream()
            .max((a, b) -> a.getValue().compareTo(b.getValue()))
            .map(Map.Entry::getKey)
            .orElse("Unknown");
        
        System.out.println("=== Tournament Champion ===");
        System.out.println("         " + champion);
        System.out.println();
    }
}
