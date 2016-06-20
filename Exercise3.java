import java.util.stream.*;
import java.util.Set;
import java.util.HashSet;

public class Exercise3 {
  private static Set<Horse> healthyHorses;

  public static void main(String [] args) {
    healthyHorses = new HashSet();

    Stream.generate(() -> new Horse())
          .limit(10)
          .peek(h -> System.out.println(h.determineHealth()))
          .filter(h -> h.isHealthy())
          .forEach(h -> healthyHorses.add(h));

    System.out.println(healthyHorses.size());
  }
}