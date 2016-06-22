import java.util.function.*;

public class Horse {
  public static final double HEALTHY_CHANCE = 0.7;
  public static final int DEFAULT_MIN_SPEED = 1;
  public static final int DEFAULT_MAX_SPEED = 5;
  public static final int DEFAULT_BOOST = 2;

  private static int nextId = 1;

  private final int id;
  private int position = 0;
  private boolean healthy = true;

  public Horse() {
    id = nextId;
    nextId++;
  }

  public int getId() {
    return id;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int pos) throws IllegalArgumentException {
    if (pos < 0) {
      throw new IllegalArgumentException();
    }

    position = pos;
  }

  private void move(int min, int max, String msg, Consumer<String> output) {
    int displacement = min + (int)(Math.random() * (max - min));
    int oldPos = position;
    position += displacement;

    output.accept(String.format(msg, displacement, oldPos, position));
  }

  public void move(Consumer<String> output) {
    int min = DEFAULT_MIN_SPEED;
    int max = DEFAULT_MAX_SPEED;
    String msg = this + " moved %d, from %d to %d";

    move(min, max, msg, output);
  }

  public void moveLastPlace(Consumer<String> output) {
    int min = DEFAULT_MIN_SPEED + DEFAULT_BOOST;
    int max = DEFAULT_MAX_SPEED + DEFAULT_BOOST;
    String msg = this + " moved %d, from %d to %d (with last place boost)";

    move(min, max, msg, output);
  }

  public boolean isHealthy() {
    return healthy;
  }

  public void determineHealth(Consumer<String> output) {
    healthy = Math.random() > (1.0 - HEALTHY_CHANCE);

    String msg = healthy
                 ? this + " is healthy."
                 : this + " is not healthy.";

    output.accept(msg);
  }

  @Override
  public String toString() {
    return "Horse " + id;
  }
}