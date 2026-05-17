
    public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // 1. Check the hard drive for any new files and update the DB file
        library.scanFolder();

        // 2. RESURRECTION: Read the DB file and recreate the Java objects in RAM!

        // 3. Now your objects EXIST again! You can safely use them:
        if (!library.getActiveTracks().isEmpty()) {
            // Grab the first resurrected track object
            Track firstTrack = library.getActiveTracks().get(0); 
            
            // This will work perfectly now, because the object has its filepath populated from the DB!
            PlaybackEngine player = new MusicController();
            player.play(firstTrack); 
        }
    }
}
   
