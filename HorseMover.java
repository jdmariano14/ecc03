import java.util.function.*;
import java.util.concurrent.Callable;

public class HorseMover implements Callable<HorseTime> {
  private Horse horse;
  private HorseLeaderboard board;
  private int destination;
  private Consumer<String> output;

  public HorseMover(Horse h, HorseLeaderboard board, int dest, Consumer<String> out) {
    this.horse = h;
    this.board = board;
    this.destination = dest;
    this.output = out;
  }

  public HorseTime call() {
    while (horse.getPosition() < destination) {
      board.update(horse);

      // synchronized (board) {
        horse.boundedMove(destination, output);
      // }
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }
}