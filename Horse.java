import java.util.function.*;

public class Horse implements Comparable<Horse> {
  public static final double HEALTHY_CHANCE = 0.7;
  public static final int DEFAULT_MIN_SPEED = 1;
  public static final int DEFAULT_MAX_SPEED = 5;
  public static final int DEFAULT_BOOST = 2;

  private static int nextId = 1;

  private final int id;
  private int position = 0;
  private boolean healthy = true;
  private boolean lastPlace = false;

  public Horse() {
    id = nextId;
    nextId++;
  }

  public Horse(int position) {
    this();
    this.position = position;
  }

  public int getId() {
    return id;
  }

  public int getPosition() {
    return position;
  }

  public boolean isLastPlace() {
    return lastPlace;
  }

  public synchronized void setLastPlace(boolean last) {
    lastPlace = last;
  }

  private void move(int min, int max, int bound, String msg, Consumer<String> output) {
    int displacement = min + (int)(Math.random() * (max - min + 1));
    int oldPos = position;
    position += displacement;

    if (position > bound && bound >= 0) {
      position = bound;
    }

    output.accept(String.format(msg, position - oldPos, oldPos, position));
  }

  public void defaultMove(int bound, Consumer<String> output) {
    int min = DEFAULT_MIN_SPEED;
    int max = DEFAULT_MAX_SPEED;
    String msg = this + " moved %d, from %d to %d";

    move(min, max, bound, msg, output);
  }

  public void moveWithLastPlaceBoost(int bound, Consumer<String> output) {
    if (isLastPlace()) {
      int min = DEFAULT_MIN_SPEED + DEFAULT_BOOST;
      int max = DEFAULT_MAX_SPEED + DEFAULT_BOOST;
      String msg = this + " moved %d, from %d to %d (with last place boost)";

      move(min, max, bound, msg, output);
    } else {
      defaultMove(bound, output);
    }
  }

  public boolean isHealthy() {
    return healthy;
  }

  public void determineHealth(Consumer<String> output) {
    healthy = Math.random() > (1.0 - HEALTHY_CHANCE);

    String msg = healthy
                 ? this + " is healthy"
                 : this + " is not healthy";

    output.accept(msg);
  }

  @Override
  public String toString() {
    return "Horse " + id;
  }

  @Override
  public int compareTo(Horse other) {
    return Integer.compare(this.getPosition(), other.getPosition());
  }
}