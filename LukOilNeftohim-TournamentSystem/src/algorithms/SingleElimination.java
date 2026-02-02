package algorithms;
import java.util.ArrayList;
import java.util.List;

import interfaces.Algorithm;
import interfaces.MatchResult;

public interface SingleElimination {
    Algorithm INSTANCE = (participants, decider) -> {
        List<MatchResult> results = new ArrayList<>();

        List<String> currentRound = new ArrayList<>(participants);

        while (currentRound.size() > 1) {
            List<String> nextRound = new ArrayList<>();
            int i = 0;

            if (currentRound.size() % 2 != 0) {
                nextRound.add(currentRound.get(0));
                i = 1; 
            }

            while (i < currentRound.size()) {
                String first = currentRound.get(i);
                String second = currentRound.get(i + 1);
                String winner = decider.decide(first, second);
                results.add(new MatchResult(winner, winner.equals(first) ? second : first));
                nextRound.add(winner);
                i += 2;
            }

            currentRound = nextRound;
        }
        return results;
    };
}
