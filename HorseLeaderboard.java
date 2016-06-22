import java.util.*; 
import java.util.function.*;
import java.util.stream.*;

public class HorseLeaderboard {
  private Set<Horse> board;

  public HorseLeaderboard() {
    board = new HashSet();
  }

  public void add(Horse h) {
    board.add(h);
  }

  public int size() {
    int size = 0;

    synchronized (board) {
      size = board.size();
    }

    return size;
  }

  public synchronized Stream<Horse> stream() {
    return board.stream();
  }

  public int getLastPlacePosition() {
    int lastPos;

    synchronized (board) {
      lastPos = board.stream().mapToInt(h -> h.getPosition()).reduce(Integer.MAX_VALUE, Integer::min);
    }

    return lastPos;
  }

}