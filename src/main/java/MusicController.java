
import java.io.File;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.util.concurrent.*;


public class MusicController implements PlaybackEngine {

    private double volume;
    private boolean repeat;
    private Player currentPlayer;
    private Thread playbackThread;
    private String currentFilePath;
    private FileInputStream fis;
    private volatile boolean isPaused;
    private Track currentTrack;
    private final Object lock = new Object();
    private final Library library;
  

    public MusicController(Library library) {
        this.library = library;
        this.volume = 0.5;
        this.repeat = false;
        

       
        
    }
    public void open(File file) {
        // Implementation to open a music file
    }

    public void play(Track track, int position) {
        this.currentFilePath = track.getFilePath();
        this.isPaused = false;
        this.currentTrack = track;
        
        
        stop(); 
        this.playbackThread= new Thread(() -> {
    
            try(FileInputStream fis = new FileInputStream(currentFilePath)) {
                fis.skip(position);
                this.currentPlayer = new Player(fis);
                while (true) {
                    synchronized (lock) {
                        while (isPaused) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    }
                    
                boolean canContinue = false;
                if (currentPlayer != null) {
                   canContinue = currentPlayer.play(1); 
                }

                if (!canContinue) {
                    break; 
                }
                }


            } catch (Exception f) {
                f.printStackTrace();
            } finally {
                cleanup();
            }

        });
        playbackThread.start();
    }



    public void pause() {
        if (playbackThread != null && playbackThread.isAlive()) {
            isPaused = true; 
        }
    }
    public void resume() {
        if (isPaused) {
            synchronized (lock) {
                isPaused = false;
                lock.notifyAll();
            }
        }
    }
            

    public void stop() {
          if (currentPlayer != null) {
            this.currentPlayer.close();
        }

        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
            
        }
    

    public int seek(int position) {
        if (currentPlayer != null) {
           return position;
            
        }
        return 0;
    }
            
    public void next() {
        try {
            play(library.getActiveTracks().get(library.getActiveTracks().indexOf(currentTrack) + 1), 0);

        }catch (IndexOutOfBoundsException e) {
            play(library.getActiveTracks().get(0), 0);
        }
        
        
    }


    public void previous() {
        try {
            play(library.getActiveTracks().get(library.getActiveTracks().indexOf(currentTrack) - 1), 0);

        }catch (IndexOutOfBoundsException e) {
            play(library.getActiveTracks().get(library.getActiveTracks().size() - 1), 0);
        } 
    }

    public void repeat() {
        if (currentTrack != null && repeat) {
           while (repeat) {
               play(currentTrack, 0);
           }  
        }
        
    }



    public void cleanup() {
       if (currentPlayer != null) {
           try {
               currentPlayer.close();
           } catch (Exception e) {
               // ignore
           }
           currentPlayer = null;
       }
       if (playbackThread != null) {
           try {
               if (playbackThread.isAlive()) {
                   playbackThread.interrupt();
               }
           } catch (Exception e) {
               // ignore
           }
           playbackThread = null;
       }
    }
}