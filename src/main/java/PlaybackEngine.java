import java.io.File;
import javazoom.jl.player.Player;

public interface PlaybackEngine {


    void open(File file);

    void play(Track track, int position);

    void pause();

    void resume();

    void stop();

    int seek(int position);

    void next();

    void previous();

    void cleanup();
}
