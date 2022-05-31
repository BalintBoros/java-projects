package labyrinthgame.datahandle;

public class HighScore {
    private int level;
    private long time;

    public HighScore(int level, long time) {
        this.level = level;
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public long getTime() {
        return time;
    }
}
