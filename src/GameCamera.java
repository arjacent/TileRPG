// view port
public class GameCamera {
  
  private Handler handler;
  // adds offset to original positions
  private float xOffset, yOffset;
  
  public GameCamera(Handler handler, float xOffset, float yOffset) {
    this.handler = handler;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }
  
  public void checkBlankSpace() {
    if (xOffset < 0) 
      xOffset = 0;
    else if (xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth())
      xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth();
    if (yOffset < 0) 
      yOffset = 0;
    else if (yOffset > handler.getWorld().getWidth() * Tile.TILEHEIGHT - handler.getHeight())
      yOffset = handler.getWorld().getWidth() * Tile.TILEHEIGHT - handler.getHeight();
  }
  
  public void centerOnEntity(Entity e) {
    xOffset = e.getX() + (e.getWidth() - handler.getWidth()) / 2;
    yOffset = e.getY() + (e.getHeight() - handler.getHeight()) / 2;
    checkBlankSpace();
  }
  
  public void move(float xAmt, float yAmt) {
    xOffset += xAmt;
    yOffset += yAmt;
    checkBlankSpace();
  }

  public float getxOffset() {
    return xOffset;
  }

  public void setxOffset(float xOffset) {
    this.xOffset = xOffset;
  }

  public float getyOffset() {
    return yOffset;
  }

  public void setyOffset(float yOffset) {
    this.yOffset = yOffset;
  }
}
