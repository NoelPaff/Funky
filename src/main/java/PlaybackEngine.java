import java.io.File;
import javazoom.jl.player.Player;

public interface PlaybackEngine {

    void open(File file);

    void play(Track track);

    void pause();

    void stop();

    void seek(double position);

    void next();

    void previous();

    void cleanup();
}
