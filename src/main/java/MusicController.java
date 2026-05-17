import java.io.File;
import javazoom.jl.player.Player;
import java.io.FileInputStream;


public class MusicController implements PlaybackEngine {

    private double volume;
    private boolean repeat;
    private Player currentPlayer;
    private Thread playbackThread;
  

    public MusicController() {
        this.volume = 0.5;
        this.repeat = false;
       
        
    }
    public void open(File file) {
        // Implementation to open a music file
    }

    public void play(Track track) {
        stop();
        String filePath = track.getFilePath();
        this.playbackThread= new Thread(() -> {
            try(FileInputStream fis = new FileInputStream(filePath)) {
                this.currentPlayer= new Player(fis);
                this.currentPlayer.play();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cleanup();
            }

        });
        playbackThread.start();
    }


    public void pause() {
        
    }

    public void stop() {
          if (currentPlayer != null) {
            this.currentPlayer.close();
        }

        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
            
        }
    

    public void seek(double position) {
        // Implementation to seek to a specific position in the current track
    }

    public void next() {
        // Implementation to play the next track in the playlist
    }

    public void previous() {
        // Implementation to play the previous track in the playlist    
    }
    public void cleanup() {
       this.currentPlayer = null;
    this.playbackThread = null;    
        
    }
}