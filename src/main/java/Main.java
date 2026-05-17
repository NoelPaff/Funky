
    public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.scanFolder();

        if (!library.getActiveTracks().isEmpty()) {
            Track track = library.getActiveTracks().get(1); 
            PlaybackEngine player = new MusicController();
            player.play(track); 
        }
    }
}
   
