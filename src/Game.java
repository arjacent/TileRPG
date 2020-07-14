import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {
  private Display display;
  private int width;
  private int height;
  private String title;
  
  private boolean isRunning;
  private Thread thread;
  private BufferStrategy bs;
  private Graphics g;
  
  // States
  protected State gameState;
  protected State menuState;
  
  // Input 
  KeyManager keyManager;
  MouseManager mouseManager;
  
  // Camera
  private GameCamera gameCamera;
  
  // Handler
  private Handler handler;
  
  public Game(String title, int width, int height) {
    this.width = width;
    this.height = height;
    this.title = title;
    keyManager = new KeyManager();
    mouseManager = new MouseManager();
  }
  
  private void init() {
    display = new Display(title, width, height);
    display.getFrame().addKeyListener(keyManager);
    display.getFrame().addMouseListener(mouseManager);
    display.getFrame().addMouseMotionListener(mouseManager);
    display.getCanvas().addMouseListener(mouseManager);  // both fire events, depending on which is active
    display.getCanvas().addMouseMotionListener(mouseManager);
    
    Assets.init();    
    
    handler = new Handler(this);
    gameCamera = new GameCamera(handler, 0, 0);
    
    gameState = new GameState(handler);
    menuState = new MenuState(handler);
    State.setState(menuState);
  }
  
  // aka tick()
  private void update() {
    keyManager.update();
    
    if (State.getState() != null)
      State.getState().update();
  }

  // draw to screen
  private void render() {
    bs = display.getCanvas().getBufferStrategy();
    if (bs == null) {
      display.getCanvas().createBufferStrategy(3);
      return;
    }
    g = bs.getDrawGraphics();
    g.clearRect(0, 0, width, height);
    // ////////////////////
    
    g.setColor(Color.red);
    g.fillRect(0, 0, width/4, height/4);
    g.setColor(Color.blue);
    g.fillRect(-10, 0, 30, 800);
    
    if (State.getState() != null)
      State.getState().render(g);

    //g.drawImage(Assets.link02, 400, 400, null);
    
    // ////////////////////
    bs.show();
    g.dispose();
    
  }

  /* Main game loop */
  @Override
  public void run() {
    init();
    
    int fps = 60;
    double timePerTick = 1e9 / fps;
    double delta = 0;
    long now;
    long lastTime = System.nanoTime();
    long timer = 0;
    long ticks = 0;
    
    while (isRunning) {
      now = System.nanoTime();
      delta += (now - lastTime) / timePerTick;
      timer += now - lastTime;
      lastTime = now;
      
      if (delta >= 1) {
        update();
        render();
        ticks++;
        delta--;
      }
      // counts frame rate
//      if (timer > 1e9) {
//        System.out.println("Frames: " + ticks);
//        ticks = 0;
//        timer = 0;
//      }
    }
    
    stop();    
  }
  
  public synchronized void start() {
    if (isRunning) return;
    isRunning = true;
    thread = new Thread(this);
    thread.start();
  }
  
  public synchronized void stop() {
    if (!isRunning) return;
    isRunning = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public KeyManager getKeyManager() {
    return keyManager;
  }
  
  public MouseManager getMouseManager() {
    return mouseManager;
  }
  
  public GameCamera getGameCamera() {
    return gameCamera;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  

}
