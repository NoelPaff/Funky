
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DbManager {
    public static final String DB_URL = "jdbc:sqlite:funky.db";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void SetupDatabase() {
        String createTableSQL = 
        "CREATE TABLE IF NOT EXISTS tracks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "artist TEXT," +
                "album TEXT," +
                "format TEXT," +
                "filePath TEXT," +
                "duration REAL" +
                ");";

        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace(); 
    }
    
}
public void insertTrack(Track track) {
    String insertSQL = "INSERT INTO tracks (title, artist, album, format, filePath, duration) VALUES (?, ?, ?, ?, ?, ?);";
    try (Connection conn = getConnection();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
        pstmt.setString(1, track.getTitle());
        pstmt.setString(2, track.getArtist());
        pstmt.setString(3, track.getAlbum());
        pstmt.setString(4, track.getFormat());
        pstmt.setString(5, track.getFilePath());
        pstmt.setDouble(6, track.getDuration());
        pstmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public HashSet<String> getAllStoredPaths() {
    HashSet<String> paths = new HashSet<>();
    String querySQL = "SELECT filePath FROM tracks;";
    try (Connection conn = getConnection();
         java.sql.Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(querySQL)) {
        while (rs.next()) {
            paths.add(rs.getString("filePath"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return paths;

}
public ArrayList<Track> getAllTracks() {
    ArrayList<Track> tracks = new ArrayList<>();
    String querySQL = "SELECT * FROM tracks;";
    try (Connection conn = getConnection();
         java.sql.Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(querySQL)) {
        while (rs.next()) {
            Track track = new Track();
            track.setTitle(rs.getString("title"));
            track.setArtist(rs.getString("artist"));
            track.setAlbum(rs.getString("album"));
            track.setFormat(rs.getString("format"));
            track.setFilePath(rs.getString("filePath"));
            track.setDuration(rs.getDouble("duration"));
            tracks.add(track);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tracks;
}




}
