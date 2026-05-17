import java.io.File;
import java.util.concurrent.atomic.AtomicLong;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class Track {
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private final long id;
    private String title, artist, album, format, filePath;
    private double duration;



    public Track() {
        this.id = NEXT_ID.getAndIncrement();
       
    }
    public void loadMetadata() {
        try {
            AudioFile f = AudioFileIO.read(new File(filePath));
            Tag tag = f.getTag();
            
            if (tag != null) {
                this.title = tag.getFirst(FieldKey.TITLE);
                this.artist = tag.getFirst(FieldKey.ARTIST);
            }
        } catch (Exception e) {
            this.title = "Unknown Title";
        }
    }
    //GETTERS
    public String getAlbum() {
        return album;
    }
    public String getArtist() {
        return artist;
    }
    public String getFilePath() {
        return filePath;
    }
    public long getId() {
        return id;
    }
    public String getFormat() {
        return format;
    }
    public String getTitle() {
        return title;   
    }
    public double getDuration() {
        return duration;
    }

    //SETTERS
    public void setAlbum(String album) {
        this.album = album;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }

}