import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
public class App {
    public static void main(String[] args) {
         Library library = new Library();
         library.scanFolder();
         PlaybackEngine player = new MusicController(library);
         try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Funky");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1280, 1024);
            frame.setMaximumSize(new Dimension(1280, 1024));
            frame.setLayout(new BorderLayout());


            JList<String> trackList = new JList<>(library.getActiveTracks().stream().map(Track::getTitle).toArray(String[]::new));
            trackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            trackList.setBackground(new Color(0x2F2A26));
            trackList.setForeground(new Color(0xC85A1C));
            trackList.setSelectionBackground(new Color(0x8A3E1A));
            trackList.setSelectionForeground(new Color(0xF2D2B6));

            trackList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int index = trackList.getSelectedIndex();
                    if (index >= 0) {
                        Track selectedTrack = library.getActiveTracks().get(index);
                        player.play(selectedTrack, 0);
                    }
                }
            });

            JPanel trackPanel = new JPanel();
            //trackPanel.setBackground(Color.BLACK);
            trackPanel.setPreferredSize(new Dimension(800, 0));
            trackPanel.setLayout(new BorderLayout());
            trackPanel.add(trackList, BorderLayout.CENTER);
            frame.add(trackPanel, BorderLayout.WEST);



            JPanel controlPanel = new JPanel();
            controlPanel.setBackground(Color.DARK_GRAY);
            controlPanel.setPreferredSize(new Dimension(0, 200));
            frame.add(controlPanel, BorderLayout.CENTER);

            

            frame.setVisible(true);
        });
    }
    
}
