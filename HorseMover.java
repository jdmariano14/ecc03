import java.util.function.*;
import java.util.concurrent.Callable;

public abstract class HorseMover implements Callable<HorseTime> {
  protected Horse horse;
  protected HorseLeaderboard board;
  protected int destination;
  protected Consumer<String> output;

  public HorseMover(Horse horse, HorseLeaderboard board, int dest, Consumer<String> out) {
    this.horse = horse;
    this.board = board;
    this.destination = dest;
    this.output = out;
  }

  public HorseTime call() {
    while (horse.getPosition() < destination) {
      board.update(horse);
      moveHorse();
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }

  protected abstract void moveHorse();
}