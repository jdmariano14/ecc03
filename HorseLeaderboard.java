import java.util.*; 
import java.util.function.*;
import java.util.stream.*;

public class HorseLeaderboard {
  private PriorityQueue<Horse> board;

  public HorseLeaderboard() {
    board = new PriorityQueue();
  }

  public synchronized void add(Horse horse) {
    Horse oldLastPlacer = size() < 1 ? horse : getLastPlacer(); 

    board.add(horse);

    updateLastPlacer(oldLastPlacer);
  }

  public synchronized void update(Horse horse) {
    Horse oldLastPlacer = getLastPlacer();

    if (board.remove(horse)) {
      board.add(horse);
    }

    updateLastPlacer(oldLastPlacer);
  }

  private synchronized Horse getLastPlacer() {
    return board.peek();
  }

  private synchronized void updateLastPlacer(Horse oldLastPlacer) {
    if (!getLastPlacer().equals(oldLastPlacer))  {
      getLastPlacer().setLastPlace(true);
      oldLastPlacer.setLastPlace(false);
    }    
  }

  public synchronized int size() {
    return board.size();
  }

  public synchronized Stream<Horse> stream() {
    return board.stream();
  }
}