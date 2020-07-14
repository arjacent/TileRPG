import java.awt.Graphics;

public abstract class State {
  
  public static State currentState = null;
  
  protected Handler handler;
  
  public State(Handler handler) {
    this.handler = handler;
  }
  
  public static void setState(State state) {
    currentState = state;
  }
  
  public static State getState() {
    return currentState;
  }
  
  public abstract void update();
  public abstract void render(Graphics g);

}
