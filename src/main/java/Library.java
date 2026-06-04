import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;


public class Library {
    private final DbManager dbManager;
    private ArrayList<Track> activeTracks;

    public Library() {
        this.dbManager = new DbManager();
        this.activeTracks = new ArrayList<>();
    }
  public static void main(String[] args) {

    
    
  }

  public Path searchPath() {
        String userHome = System.getProperty("user.home");
        Path musicPath = Paths.get(userHome, "Music");
        return musicPath;
    }

  public boolean isSupportedFormat(Path path) {
    return path.toString().endsWith(".mp3");
  }

    public void scanFolder(){
     Path path = searchPath();
     dbManager.SetupDatabase();
     HashSet<String> cachedPaths = dbManager.getAllStoredPaths();
     HashSet<String> visitedPaths = new HashSet<>();

     try { 
       Files.walkFileTree(path, new SimpleFileVisitor<Path>()   {
         @Override
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
           if (isSupportedFormat(file)) {
             if(cachedPaths.contains(file.toString())) {
              visitedPaths.add(file.toString());
              return FileVisitResult.CONTINUE;
             }else {
               Track track = new Track();
               track.setFilePath(file.toString());
               track.loadMetadata();
               dbManager.insertTrack(track);
             }
             
           
           } return FileVisitResult.CONTINUE;

         }

       });
        for (String cachedPath : cachedPaths) {
          if (!visitedPaths.contains(cachedPath)) {
            dbManager.deleteTrack(cachedPath);
          }
        }
       loadLibrary();
     } 
     catch (IOException e) {
       e.printStackTrace();
     }

 }

 public void loadLibrary() {
   this.activeTracks = dbManager.getAllTracks();
  
}
    public ArrayList<Track> getActiveTracks() {
        return activeTracks;
    }
}