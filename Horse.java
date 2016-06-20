public class Horse {
  public static final double HEALTHY_CHANCE = 0.7;
  public static final double DEFAULT_MIN_SPEED = 1;
  public static final double DEFAULT_MAX_SPEED = 5;

  private static int nextId = 1;

  private final int id;
  private int position = 0;
  private boolean healthy = true;

  public Horse() {
    id = nextId;
    nextId++;
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

  private int move(int min, int max) {
    int displacement = min;

    displacement += (int)(Math.random() * (max - min));
    displacement = Math.min(0, displacement + offset);

    return displacement;
  }

  public int move(int offset) {
    return move(DEFAULT_MIN_SPEED + offset, DEFAULT_MAX_SPEED + offset);
  }
  
  public int move() {
    return move(0);
  }

  public boolean isHealthy() {
    return healthy;
  }

  public String determineHealth() {
    healthy = Math.random() > (1.0 - HEALTHY_CHANCE);

    String message = healthy
                     ? this + " is healthy."
                     : this + " is not healthy.";

    return message;
  }

  @Override
  public String toString() {
    return "Horse " + id;
  }
}