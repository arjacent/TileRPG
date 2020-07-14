import java.awt.Canvas;
import java.awt.Dimension;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Display {
  private JFrame frame;
  private Canvas canvas;
  
  private String title;
  private int width, height;
  
  public Display(String title, int width, int height) {
    this.title = title;
    this.width = width;
    this.height = height;
    createDisplay();
  }
  
  private void createDisplay() {
    frame = new JFrame(title);
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    canvas = new Canvas();
    canvas.setPreferredSize((new Dimension(width, height)));
    canvas.setMaximumSize((new Dimension(width, height)));
    canvas.setMinimumSize((new Dimension(width, height)));
    canvas.setFocusable(false);  // frame only thing with focus, for keyboard
    
    frame.add(canvas);
    frame.pack();  // resizes window to fully show canvas
    
    // audio 
    try {
      AudioInputStream audioInputStream =
          AudioSystem.getAudioInputStream(
              new File("./res/audio/elf.mid"));
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
      clip.loop(0);
    } catch(Exception ex) {
      ex.printStackTrace();
      System.out.println("Unable to load audio...");
    }
  }
  
  public Canvas getCanvas() {
    return canvas;
  }
  
  public JFrame getFrame() {
    return frame;
  }

}
