import java.io.File;
import java.io.FileInputStream;
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

    boolean getIsPaused();

    // Getters and setters added for controller state
    double getVolume();

    void setVolume(double volume);

    boolean isRepeat();

    void setRepeat(boolean repeat);

    Player getCurrentPlayer();

    void setCurrentPlayer(Player currentPlayer);

    Thread getPlaybackThread();

    void setPlaybackThread(Thread playbackThread);

    String getCurrentFilePath();

    void setCurrentFilePath(String currentFilePath);

    FileInputStream getFis();

    void setFis(FileInputStream fis);

    void setIsPaused(boolean isPaused);

    Track getCurrentTrack();

    void setCurrentTrack(Track currentTrack);

    Library getLibrary();

}
