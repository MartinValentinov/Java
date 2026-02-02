package algorithms;

import java.util.ArrayList;
import java.util.List;
import interfaces.Algorithm;
import interfaces.MatchResult;

public interface DoubleElimination {

    Algorithm INSTANCE = (participants, decider) -> {
        List<MatchResult> results = new ArrayList<>();

        if (participants.isEmpty() || participants.size() == 1) return results;

        List<String> winnersBracket = new ArrayList<>();
        for (String p : participants) {
            if (p != null && !p.trim().isEmpty()) {
                winnersBracket.add(p.trim());
            }
        }

        List<String> losersBracket = new ArrayList<>();

        while (winnersBracket.size() > 1) {
            List<String> nextWinners = new ArrayList<>();
            List<String> newlyLosers = new ArrayList<>();

            for (int i = 0; i < winnersBracket.size(); i += 2) {
                if (i + 1 < winnersBracket.size()) {
                    String p1 = winnersBracket.get(i).trim();
                    String p2 = winnersBracket.get(i + 1).trim();

                    String winner = decider.decide(p1, p2).trim();
                    String loser = winner.equals(p1) ? p2 : p1;

                    results.add(new MatchResult(winner, loser));
                    nextWinners.add(winner);
                    newlyLosers.add(loser);

                } else {
                    nextWinners.add(winnersBracket.get(i).trim());
                }
            }

            winnersBracket = nextWinners;
            losersBracket.addAll(newlyLosers);

            List<String> nextLosers = new ArrayList<>();
            for (int i = 0; i < losersBracket.size(); i += 2) {
                if (i + 1 < losersBracket.size()) {
                    String p1 = losersBracket.get(i).trim();
                    String p2 = losersBracket.get(i + 1).trim();

                    String winner = decider.decide(p1, p2).trim();
                    String loser = winner.equals(p1) ? p2 : p1;

                    results.add(new MatchResult(winner, loser));
                    nextLosers.add(winner);

                } else {
                    nextLosers.add(losersBracket.get(i).trim());
                }
            }
            losersBracket = nextLosers;
        }

        if (!winnersBracket.isEmpty() && !losersBracket.isEmpty()) {
            String winnerWB = winnersBracket.get(0).trim();
            String winnerLB = losersBracket.get(0).trim();

            String finalWinner = decider.decide(winnerWB, winnerLB).trim();
            String finalLoser = finalWinner.equals(winnerWB) ? winnerLB : winnerWB;

            results.add(new MatchResult(finalWinner, finalLoser));

            if (finalWinner.equals(winnerLB)) {
                finalWinner = decider.decide(winnerWB, winnerLB).trim();
                finalLoser = finalWinner.equals(winnerWB) ? winnerLB : winnerWB;

                results.add(new MatchResult(finalWinner, finalLoser));
            }
        }

        return results;
    };
}
