import java.util.*; 
import java.util.function.*;
import java.util.stream.*;

public class HorseRace {
  private PriorityQueue<Horse> leaderboard;

  public HorseRace() {
    leaderboard = new PriorityQueue();
  }

  public HorseRace(PriorityQueue<Horse> leaderboard) {
    this.leaderboard = leaderboard;
  }

  public synchronized void add(Horse horse) {
    Horse oldLastPlacer = leaderboard.isEmpty() ? horse : getLastPlacer(); 

    leaderboard.add(horse);

    updateLastPlacer(oldLastPlacer);
  }

  public synchronized void update(Horse horse) {
    Horse oldLastPlacer = getLastPlacer();

    if (leaderboard.remove(horse)) {
      leaderboard.add(horse);
    }

    updateLastPlacer(oldLastPlacer);
  }

  private synchronized Horse getLastPlacer() {
    return leaderboard.peek();
  }

  private synchronized void updateLastPlacer(Horse oldLastPlacer) {
    if (!getLastPlacer().equals(oldLastPlacer))  {
      getLastPlacer().setLastPlace(true);
      oldLastPlacer.setLastPlace(false);
    }    
  }

  public synchronized int size() {
    return leaderboard.size();
  }

  public synchronized Stream<Horse> stream() {
    return leaderboard.stream();
  }
}