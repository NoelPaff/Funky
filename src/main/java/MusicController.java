
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
        final String filePath = track.getFilePath();
        this.currentFilePath = filePath;
        this.isPaused = false;
        this.currentTrack = track;
        
        
        stop(); 
        Thread nextPlaybackThread = new Thread(() -> {
    
            Player localPlayer = null;
            try(FileInputStream fis = new FileInputStream(filePath)) {
                fis.skip(position);
                localPlayer = new Player(fis);
                this.currentPlayer = localPlayer;
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
                     if (localPlayer != null) {
                         canContinue = localPlayer.play(1); 
                }

                if (!canContinue) {
                    break; 
                }
                }


            } catch (Exception f) {
                f.printStackTrace();
            } finally {
                cleanup(localPlayer, Thread.currentThread());
            }

        });
        this.playbackThread = nextPlaybackThread;
        nextPlaybackThread.start();
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
       cleanup(currentPlayer, playbackThread);
    }

    private void cleanup(Player playerToClose, Thread threadToClear) {
       if (playerToClose != null) {
           try {
               playerToClose.close();
           } catch (Exception e) {
               // ignore
           }
           if (currentPlayer == playerToClose) {
               currentPlayer = null;
           }
       }
       if (threadToClear != null) {
           try {
               if (threadToClear.isAlive()) {
                   threadToClear.interrupt();
               }
           } catch (Exception e) {
               // ignore
           }
           if (playbackThread == threadToClear) {
               playbackThread = null;
           }
       }
    }
    public boolean getIsPaused(){
        return isPaused;
    }

    // Getters and setters
    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Thread getPlaybackThread() {
        return playbackThread;
    }

    public void setPlaybackThread(Thread playbackThread) {
        this.playbackThread = playbackThread;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public FileInputStream getFis() {
        return fis;
    }

    public void setFis(FileInputStream fis) {
        this.fis = fis;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }

    public Library getLibrary() {
        return library;
    }
}