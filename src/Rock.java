import java.awt.Graphics;

public class Rock extends StaticEntity {
  
  public Rock(Handler handler, float x, float y) {
    super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
    
    bounds.x = 3;
    bounds.y = (int) (height / 2f);
    bounds.width = width - 6;
    bounds.height = (int) (height - height / 2f);
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(Graphics g) {
    g.drawImage(Assets.rock, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);    
  }
  
  @Override
  public void die() {
    handler.getWorld().getItemManager().addItem(Item.rockItem.createNew((int) x, (int) y));

  }

}
