package algorithms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import interfaces.Algorithm;
import interfaces.MatchResult;

public interface RobinRound {
    
    record RobinParticipant(String name, int wins, int losses) {
        RobinParticipant withWin() {return new RobinParticipant(name, wins + 1, losses); }
        RobinParticipant withLoss() { return new RobinParticipant(name, wins, losses + 1); }
    }
    
    Algorithm INSTANCE = (participants, decider) -> {
        List<MatchResult> results = new ArrayList<>();
        int numParticipants = participants.size();
        
        RobinParticipant[] robinParticipants = new RobinParticipant[numParticipants];
        for (int i = 0; i < numParticipants; i++) { robinParticipants[i] = new RobinParticipant(participants.get(i), 0, 0); }
        
        for (int i = 0; i < numParticipants; i++) {
            for (int j = i + 1; j < numParticipants; j++) {
                String winner = decider.decide(robinParticipants[i].name(), robinParticipants[j].name());
                
                results.add(new MatchResult(winner, 
                    winner.equals(robinParticipants[i].name()) ? 
                    robinParticipants[j].name() : robinParticipants[i].name()));
                
                if (winner.equals(robinParticipants[i].name())) {
                    robinParticipants[i] = robinParticipants[i].withWin();
                    robinParticipants[j] = robinParticipants[j].withLoss();
                } else {
                    robinParticipants[j] = robinParticipants[j].withWin();
                    robinParticipants[i] = robinParticipants[i].withLoss();
                }
            }
        }
        
        Arrays.sort(robinParticipants, (a, b) -> Integer.compare(b.wins(), a.wins()));
        
        return results;
    };
}