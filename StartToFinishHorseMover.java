import java.util.function.*;

public class StartToFinishHorseMover extends HorseMover {

  public StartToFinishHorseMover(Horse horse, HorseLeaderboard board, int dest, Consumer<String> out) {
    super(horse, board, dest, out);
  }

  @Override
  public void moveHorse() {
    synchronized (board) {
      horse.moveWithLastPlaceBoost(destination, output);
    } 
  }
}