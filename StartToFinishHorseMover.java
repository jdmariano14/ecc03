import java.util.function.*;

public class StartToFinishHorseMover extends HorseMover {

  public StartToFinishHorseMover(Horse horse, HorseRace race, int dest, Consumer<String> out) {
    super(horse, race, dest, out);
  }

  @Override
  public void moveHorse() {
    synchronized (race) {
      horse.moveWithLastPlaceBoost(destination, output);
    } 
  }
}