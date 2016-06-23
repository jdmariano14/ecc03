import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.function.*;

public class Exercise3 {

  private static final Consumer<String> DEFAULT_OUTPUT = System.out::println;

  public static void main(String [] args) {
    HorseLeaderboard leaderboard = new HorseLeaderboard();

    Stream.generate(() -> new Horse(-10))
          .limit(10)
          .peek(h -> h.determineHealth(DEFAULT_OUTPUT))
          .filter(h -> h.isHealthy())
          .forEach(h -> leaderboard.add(h));

    System.out.println("");

    ExecutorService pool = Executors.newFixedThreadPool(leaderboard.size());

    Set<Callable<HorseTime>> startingLineMovers =
      leaderboard.stream()
      .map(h -> new HorseMover(h, leaderboard, 0, DEFAULT_OUTPUT))
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
      leaderboard.stream()
      .map(h -> new HorseMover(h, leaderboard, 100, DEFAULT_OUTPUT))
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