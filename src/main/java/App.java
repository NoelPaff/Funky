import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
public class App {
    private static PlaybackEngine player;
    
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
            frame.setResizable(false);
            frame.setLayout(new BorderLayout());


            JList<String> trackList = new JList<>(library.getActiveTracks().stream().map(Track::getTitle).toArray(String[]::new));
            trackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            trackList.setBackground(new Color(0x2F2A26));
            trackList.setForeground(new Color(0xC85A1C));
            trackList.setSelectionBackground(new Color(0x8A3E1A));
            trackList.setSelectionForeground(new Color(0xF2D2B6));


            JPanel trackPanel = new JPanel();
            //trackPanel.setBackground(Color.BLACK);
            trackPanel.setPreferredSize(new Dimension(800, 0));
            trackPanel.setLayout(new BorderLayout());
            trackPanel.add(trackList, BorderLayout.CENTER);
            frame.add(trackPanel, BorderLayout.WEST);



            JPanel controlPanel = new JPanel();
            controlPanel.setBackground(Color.DARK_GRAY);
            controlPanel.setPreferredSize(new Dimension(0, 200));
            MigLayout mig = new MigLayout(" ay 0%, btt");// due to buttomtotop gap/padding for top/bottom reversed
            controlPanel.setLayout(mig);
            ImageIcon iconPause = new ImageIcon(App.class.getResource("/images/pause.png"));
            ImageIcon iconPlay = new ImageIcon(App.class.getResource("/images/play.png"));
            ImageIcon iconNext = new ImageIcon(App.class.getResource("/images/next.png"));
            ImageIcon iconPrev = new ImageIcon(App.class.getResource("/images/prev.png"));
            JButton pausePlay = new JButton(iconPlay);
            JButton playNext = new JButton(iconNext);
            JButton playPrev = new JButton(iconPrev);
            pausePlay.setContentAreaFilled(false);
            playNext.setContentAreaFilled(false);
            playPrev.setContentAreaFilled(false);

            

           playNext.addActionListener(e -> {
                player.next();
                if (trackList.getSelectedIndex() < library.getActiveTracks().size() - 1) {
                    trackList.setSelectedIndex(trackList.getSelectedIndex() + 1);
                }else
                trackList.setSelectedIndex(0);
                

            });
        

            playPrev.addActionListener(e -> {
                player.previous();
                if (trackList.getSelectedIndex() == 0) {
                    trackList.setSelectedIndex(library.getActiveTracks().size()- 1);
                }else trackList.setSelectedIndex(trackList.getSelectedIndex() - 1);

            });


            pausePlay.addActionListener(e -> {
                if (!player.getIsPaused()) {
                    player.pause();
                    pausePlay.setIcon(iconPlay);
                }
                else if (player.getIsPaused()) {
                    player.resume();
                    pausePlay.setIcon(iconPause);
                    
                }             
                
            });

            trackList.addListSelectionListener(e -> {
                if (e.getValueIsAdjusting()) {
                    int index = trackList.getSelectedIndex();
                    if (index >= 0 ) {
                        Track selectedTrack = library.getActiveTracks().get(index);
                        player.play(selectedTrack, 0);
                        pausePlay.setIcon(iconPause);

                        
                    }
                }
            });
           
           
            
            
            
            controlPanel.add(playPrev, "w 35!, h 35!, align left, gap 50 10 35 0");   //left right top bottom
            controlPanel.add(pausePlay, "w 45!, h 45!, align center, gap 0 0 35 0");
            controlPanel.add(playNext, "w 35!, h 35!, align right, gap 10 50 35 0");
            
            frame.add(controlPanel, BorderLayout.CENTER);

            

            frame.setVisible(true);
        });
    }
    

    
}
