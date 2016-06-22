import java.util.function.*;
import java.util.concurrent.Callable;

public class HorseMover implements Callable<HorseTime> {
  private Horse horse;
  private int destination;
  private boolean exceed;
  private Consumer<String> output;

  public HorseMover(Horse h, int dest, boolean exceed, Consumer<String> out) {
    this.horse = h;
    this.exceed = exceed;
    this.destination = dest;
    this.output = out;
  }

  public HorseTime call() {
    while (horse.getPosition() < destination) {
      boolean lastPlace = horse.getPosition() <= HorsePlaces.getLastPlace();
      
      if (lastPlace) {
        horse.moveLastPlace(output);
      } else {
        horse.move(output);
      }

      HorsePlaces.submit(horse.getId(), horse.getPosition());

      if (horse.getPosition() > destination && !exceed) {
        horse.setPosition(destination);
      }
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }
}