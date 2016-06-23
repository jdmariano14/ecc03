import java.util.function.*;

public class LastPlaceBoostHorseMover extends HorseMover {

  public LastPlaceBoostHorseMover(Horse horse, HorseRace race, int dest, Consumer<String> out) {
    super(horse, race, dest, out);
  }

  @Override
  public void moveHorse() {
    race.update(horse);
    
    synchronized (race) {
      horse.moveWithLastPlaceBoost(destination, output);
    } 
  }
}