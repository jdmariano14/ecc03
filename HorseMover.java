import java.util.concurrent.Callable;

public class HorseMover implements Callable<HorseTime> {
  private Horse horse;
  private int destination;
  private boolean exceed;

  public HorseMover(Horse h, int dest, boolean exceed) {
    this.horse = h;
    this.exceed = exceed;
    this.destination = dest;
  }

  public HorseTime call() {
    while (horse.getPosition() < destination) {
      horse.move();

      if (horse.getPosition() > destination && !exceed) {
        horse.setPosition(destination);
      }

      System.out.println(horse + " is at position " + horse.getPosition() + ".");
    }

    return new HorseTime(horse.getId(), System.currentTimeMillis());
  }
}