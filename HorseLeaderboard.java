import java.util.*; 
import java.util.function.*;
import java.util.stream.*;

public class HorseLeaderboard {
  private Set<Horse> board;

  public HorseLeaderboard() {
    board = new HashSet();
  }

  public synchronized void add(Horse h) {
    board.add(h);
  }

  public synchronized int size() {
    return board.size();
  }

  public synchronized Stream<Horse> stream() {
    return board.stream();
  }

  public synchronized int getLastPlacePosition() {
    int lastPos = board
                  .stream()
                  .mapToInt(h -> h.getPosition())
                  .reduce(Integer.MAX_VALUE, Integer::min);

    return lastPos;
  }

}