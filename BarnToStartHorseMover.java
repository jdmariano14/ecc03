import java.util.function.*;

public class BarnToStartHorseMover extends HorseMover {

  public BarnToStartHorseMover(Horse horse, HorseLeaderboard board, int dest, Consumer<String> out) {
    super(horse, board, dest, out);
  }

  @Override
  public void moveHorse() {
    horse.defaultMove(destination, output);
  }
}