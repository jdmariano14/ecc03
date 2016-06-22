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
      boolean lastPlace = horse.getPosition() <= HorsePlaces.getLastPlace();
      
      if (lastPlace) {
        horse.moveLastPlace(System.out::println);
      } else {
        horse.move(System.out::println);
      }

      HorsePlaces.submit(horse.getId(), horse.getPosition());

      if (horse.getPosition() > destination && !exceed) {
        horse.setPosition(destination);
      }
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }
}