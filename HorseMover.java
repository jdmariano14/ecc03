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
      boolean lastPlace = horse.getPosition() <= leaderboard.getLastPlacePosition();
      
      synchronized (leaderboard) {
        if (horse.getPosition() <= leaderboard.getLastPlacePosition()) {
          horse.moveLastPlace(output);
        } else {
          horse.move(output);
        }
      }

      if (horse.getPosition() > destination && !exceed) {
        horse.setPosition(destination);
      }
    }

    return new HorseTime(horse.getId(), System.nanoTime());
  }
}