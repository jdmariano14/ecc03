import java.util.stream.*;
import java.util.Set;
import java.util.HashSet;

public class Exercise3 {
  private static Set<Horse> healthyHorses;

  public static void main(String [] args) {
    healthyHorses = new HashSet();

    Stream.generate(() -> new Horse())
          .limit(10)
          .peek(e -> e.determineHealth())
          .forEach(h -> segregateHorseByHealth(h));

    System.out.println(healthyHorses.size());
  }

  private static void segregateHorseByHealth(Horse h) {
    if (h.isHealthy()) {
      healthyHorses.add(h); 
      System.out.println(h + " is healthy.");
    } else {
      System.out.println(h + " is not healthy.");
    }
  }
}