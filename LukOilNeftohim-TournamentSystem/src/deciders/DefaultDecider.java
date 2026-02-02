package deciders;

import interfaces.Decider;
import java.util.Random;

public interface DefaultDecider {
  Decider INSTANCE =  (a, b) -> { return new Random().nextBoolean() ? a : b; };
}
