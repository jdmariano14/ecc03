import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.function.*;

public class Exercise3 {

  private static final Consumer<String> DEFAULT_OUTPUT = System.out::println;

  public static void main(String [] args) {
    Set<Horse> healthyHorses = new HashSet();

    Stream.generate(() -> new Horse())
          .limit(30)
          .peek(h -> h.determineHealth(DEFAULT_OUTPUT))
          .filter(h -> h.isHealthy())
          .forEach(h -> healthyHorses.add(h));

    System.out.println("");

    ExecutorService pool = Executors.newFixedThreadPool(healthyHorses.size());

    Set<Callable<HorseTime>> startingLineMovers =
      healthyHorses.stream()
      .map(h -> new HorseMover(h, 10, false, DEFAULT_OUTPUT))
      .collect(Collectors.toSet());

    try {
      pool.invokeAll(startingLineMovers);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("");
    System.out.println("All horses at starting line");
    System.out.println("");

    Set<Callable<HorseTime>> finishLineMovers = 
      healthyHorses.stream()
      .peek(h -> HorsePlaces.submit(h.getId(), h.getPosition()))
      .map(h -> new HorseMover(h, 10 + 20, true, DEFAULT_OUTPUT))
      .collect(Collectors.toSet());

    Function<Future<HorseTime>, HorseTime> getHorseTime = f -> {
      try {
        return f.get();
      }
      catch (Exception e) {
        return null;
      }
    };

    SortedSet<HorseTime> times = null;
    try {
      times = pool.invokeAll(finishLineMovers)
              .stream()
              .map(getHorseTime)
              .collect(Collectors.toCollection(TreeSet::new));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println();
    System.out.println("Results: ");

    int place = 1;
    long bestTime = times.first().getTime();
    for (HorseTime time : times) {
      StringBuilder result = new StringBuilder();
      result.append(place + ". ");
      result.append("Horse " + time.getId() + " - +");
      result.append(time.getTime() - bestTime);
      result.append("ns");

      System.out.println(result);

      place++;
    }

    pool.shutdown();
  }
}