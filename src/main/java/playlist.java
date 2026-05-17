
import java.util.ArrayList;

public class playlist {
    private int id;
    private String name, description;
    private ArrayList<Track> tracks;


    public playlist(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tracks = new ArrayList<>();
    }
    
}