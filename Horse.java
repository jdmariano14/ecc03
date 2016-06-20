public class Horse {
  public static final double HEALTHY_CHANCE = 0.7;
  private static int nextId = 1;

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