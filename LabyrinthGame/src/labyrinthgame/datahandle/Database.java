package labyrinthgame.datahandle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private final String tableName = "scores";
    private final Connection conn;
    private ArrayList<HighScore> hs;

    public Database() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost/labyrinth?"
                    + "serverTimezone=UTC&user=root&password=Fuvola2000");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.conn = c;
        hs = new ArrayList<>();
        loadHighScores();
    }

    public void loadHighScores() {
        hs.clear();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT level, min(time) as time FROM " + tableName + " GROUP BY level ORDER BY level");
            while (rs.next()) {
                int level = rs.getInt("level");
                int time = rs.getInt("time");
                hs.add(new HighScore(level, time));
            }
        } catch (Exception e) {
            System.out.println("loadHighScores error: " + e.getMessage());
        }
    }

    public ArrayList<HighScore> getHighScores() {
        return hs;
    }

    public int storeToDatabase(int level, long time) {

        try (Statement stmt = conn.createStatement()) {
            String s = "INSERT INTO " + tableName
            + " (level, time) "
            + "VALUES ('" + level + "', " + time + ")";
            return stmt.executeUpdate(s);
        } catch (Exception e) {
            System.out.println("storeToDatabase error");
        }
    
        loadHighScores();

        return 0;
    }
}
