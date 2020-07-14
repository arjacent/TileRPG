import java.awt.Graphics;

public class MenuState extends State {
  
  private UIManager uiManager;

  public MenuState(Handler handler) {
    super(handler);
    uiManager = new UIManager(handler);
    handler.getMouseManager().setUIManager(uiManager);
    
    uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.btn_start, 
        new ClickListener() {
          @Override
          public void onClick() {
            handler.getMouseManager().setUIManager(null);
            State.setState(handler.getGame().gameState);            
          }
        }));
  }

  @Override
  public void update() {
    uiManager.update();
  }

  @Override
  public void render(Graphics g) {
    uiManager.render(g);
    
    // TESTING: add red square to arrow
//    g.setColor(Color.RED);
//    g.fillRect(handler.getMouseManager().getMouseX(),
//        handler.getMouseManager().getMouseY(),
//        8, 8);
    
  }

}
