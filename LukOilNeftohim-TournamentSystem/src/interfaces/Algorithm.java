package interfaces;
import java.util.List;

@FunctionalInterface
public interface Algorithm {
    List<MatchResult> run(List<String> participants, Decider decider);
}
