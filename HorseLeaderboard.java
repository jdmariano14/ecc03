import java.util.*; 
import java.util.function.*;
import java.util.stream.*;

public class HorseLeaderboard {
  private PriorityQueue<Horse> board;

  public HorseLeaderboard() {
    Comparator<Horse> byPosition = 
      (Horse h1, Horse h2) -> Integer.compare(h1.getPosition(), h2.getPosition());

    board = new PriorityQueue(byPosition);
  }

  public synchronized void add(Horse h) {
    board.add(h);
  }

  public synchronized void update(Horse h) {
    if (board.remove(h)) {
      board.add(h);
    }
  }

  public synchronized int size() {
    return board.size();
  }

  public synchronized Stream<Horse> stream() {
    return board.stream();
  }

  public synchronized Horse getLastPlacer() {
    return board.peek();
  }

  public synchronized int getLastPlacePosition() {
    int lastPos = board
                  .stream()
                  .mapToInt(h -> h.getPosition())
                  .reduce(Integer.MAX_VALUE, Integer::min);

    return lastPos;
  }

}