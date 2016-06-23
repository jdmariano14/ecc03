import java.util.function.*;

public class NoBoostHorseMover extends HorseMover {

  public NoBoostHorseMover(Horse horse, HorseRace race, int dest, Consumer<String> out) {
    super(horse, race, dest, out);
  }

  @Override
  public void moveHorse() {
    horse.defaultMove(destination, output);
  }
}