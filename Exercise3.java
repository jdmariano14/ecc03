import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.function.*;

public class Exercise3 {
  private static final int STARTING_LINE_DISTANCE = 10;
  private static final int MAX_THREADS = 100;
  private static final Scanner INPUT_SCANNER;
  private static final Consumer<String> DEFAULT_OUTPUT = System.out::println;

  static {
    INPUT_SCANNER = new Scanner(System.in);
  }

  public static void main(String [] args) {
    int horseCount = promptUserForInt("Enter the number of horses: ");
    int finishLineDistance = promptUserForInt("Enter the distance to the finish line: ");

    HorseRace race = new HorseRace(
      Stream.generate(() -> new Horse(-STARTING_LINE_DISTANCE))
            .limit(horseCount)  
            .peek(h -> h.determineHealth(DEFAULT_OUTPUT))
            .filter(h -> h.isHealthy())
            .collect(Collectors.toCollection(PriorityQueue::new))
    );

    int threadCount = Integer.min(race.size(), MAX_THREADS);
    ExecutorService exec = Executors.newFixedThreadPool(threadCount);

    while (true) {
      if (!moveFromBarnToStartingLine(race, exec)) {
        System.out.println("Something bad happened. The race is aborted.");
        break;
      }

      SortedSet<HorseTime> results = 
        moveFromStartingLineToFinishLine(race, exec, finishLineDistance);

      try {
        displayRaceResults(results);
      } catch (NullPointerException e) {
        System.out.println("Something bad happened. The race is aborted.");
      }
      
      break;
    }

    exec.shutdown();
  }

  private static boolean moveFromBarnToStartingLine(HorseRace race, ExecutorService exec) {
    System.out.println("");
    System.out.println("Healthy horses moving out from barn");
    System.out.println("");

    Set<Callable<HorseTime>> barnToStartMovers =
      race.stream()
          .map(h -> new NoBoostHorseMover(h, race, 0, DEFAULT_OUTPUT))
          .collect(Collectors.toSet());

    try {
      exec.invokeAll(barnToStartMovers);
    } catch (InterruptedException e) {
      return false;
    }

    System.out.println("");
    System.out.println("All healthy horses at starting line");
    System.out.println("");

    return true;
  }

  private static SortedSet<HorseTime> moveFromStartingLineToFinishLine(HorseRace race, 
      ExecutorService exec, int finishLineDistance) 
  {
    System.out.println("The race begins!");
    System.out.println("");

    Set<Callable<HorseTime>> finishLineMovers = 
      race.stream()
          .map(h -> new LastPlaceBoostHorseMover(h, race, finishLineDistance, DEFAULT_OUTPUT))
          .collect(Collectors.toSet());

    Function<Future<HorseTime>, HorseTime> getHorseTime = f -> {
      try {
        return f.get();
      }
      catch (Exception e) {
        return null;
      }
    };

    try {
      return exec.invokeAll(finishLineMovers)
                 .stream()
                 .map(getHorseTime)
                 .collect(Collectors.toCollection(TreeSet::new));
    } catch (InterruptedException e) {

    }

    return null;
  }

  private static void displayRaceResults(SortedSet<HorseTime> results) {
      System.out.println();
      System.out.println("The race is over!");
      System.out.println();
      System.out.println("Race Results: ");

      int place = 1;
      long bestTime = results.first().getTime();
      int digits = (int)(Math.log10(results.size())) + 1;

      for (HorseTime time : results) {
        StringBuilder result = new StringBuilder();

        result.append(String.format("%" + digits + "d. ", place));
        result.append("Horse ");
        result.append(String.format("%-" + digits + "d ", time.getId()));
        result.append(String.format("%+12d ns", time.getTime() - bestTime));

        System.out.println(result);

        place++;
      }
  }

  private static int promptUserForInt(String promptMsg) {
    int input = Integer.MIN_VALUE;

    do {
      try {
        input = Integer.parseInt(promptUserForLine(promptMsg));
      } catch (NumberFormatException e) {
        DEFAULT_OUTPUT.accept("Please enter an integer value.");
      }
    } while (input == Integer.MIN_VALUE);

    return input;
  }

  private static String promptUserForLine(String promptMsg) {
    System.out.print(promptMsg);
    return INPUT_SCANNER.nextLine();
  }
}