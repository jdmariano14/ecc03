public class Horse {
  public static final int HEALTHY_CHANCE = 0.7;
  public static int nextId = 0;

  private final int id;
  private boolean healthy = true;

  public Horse() {
    id = nextId;
    nextId++;
  }

  public boolean isHealthy() {
    return healthy;
  }

  public void determineHealth() {
    healthy = Math.random() > (1.0 - HEALTHY_CHANCE);
  }

  @Override
  public String toString() {
    return "Horse " + id;
  }
}