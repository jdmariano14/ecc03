public class HorseTime implements Comparable<HorseTime> {
  private int id;
  private long time;

  public HorseTime(int id, long time) {
    this.id = id;
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public long getTime() {
    return time;
  }

  @Override
  public int compareTo(HorseTime other) {
    return Long.compare(time, other.time);
  }
}