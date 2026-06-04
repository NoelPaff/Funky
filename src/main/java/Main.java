import java.util.Scanner;
    public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.scanFolder();

        if (!library.getActiveTracks().isEmpty()) {
            Track track = library.getActiveTracks().get(7); 
            PlaybackEngine player = new MusicController(library);
            player.play(track, 0); 
            Scanner scan = new Scanner(System.in);
            while (true) {
            if (scan.nextLine().equals("pause")) {
                player.pause();
            }
             if (scan.nextLine().equals("resume")) {
                player.resume();
            }
            if (scan.nextLine().equals("next")) {
                player.next();
            }
            }
        }
    }
}
   
