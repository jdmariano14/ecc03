import java.util.function.*;
import java.util.concurrent.Callable;

public abstract class HorseMover implements Callable<HorseTime> {
  protected Horse horse;
  protected HorseRace race;
  protected int destination;
  protected Consumer<String> output;

  public HorseMover(Horse horse, HorseRace race, int dest, Consumer<String> out) {
    this.horse = horse;
    this.race = race;
    this.destination = dest;
    this.output = out;
  }

  public HorseTime call() {
    while (horse.getPosition() < destination) {
      moveHorse();
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }

  protected abstract void moveHorse();
}