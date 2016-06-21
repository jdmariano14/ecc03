import java.util.*;

public class HorsePlaces {
  private static Map<Integer, Integer> places;

  static {
    places = new HashMap();
  }

  public static synchronized void submit(int id, int pos) {
    synchronized (places) {
      places.put(id, pos); 
    }
  }

  public static synchronized int getLastPlace() {
    int last = 0;
    synchronized (places) {
      last = places.values().stream().reduce(Integer.MAX_VALUE, Integer::min);
    }
    return last;
  }

}