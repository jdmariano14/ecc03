import java.util.function.*;
import java.util.concurrent.Callable;

public class HorseMover implements Callable<HorseTime> {
  private Horse horse;
  private HorseLeaderboard leaderboard;
  private int destination;
  private boolean exceed;
  private Consumer<String> output;

  public HorseMover(Horse h, HorseLeaderboard board, int dest, boolean exceed, Consumer<String> out) {
    this.horse = h;
    this.leaderboard = board;
    this.exceed = exceed;
    this.destination = dest;
    this.output = out;
  }

  public HorseTime call() {
    while (horse.getPosition() < destination) {

      //synchronized (leaderboard) {
        if (horse.equals(leaderboard.getLastPlacer())) {
          horse.moveLastPlace(output);
        } else {
          horse.move(output);
        }

        leaderboard.update(horse);
      //}

      if (!exceed && horse.getPosition() > destination) {
        horse.setPosition(destination);
      }
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }
}