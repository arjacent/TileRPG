import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Creature {
  
  // Animations  
  private Animation animDown, animUp, animLeft, animRight;
  // Attack timer
  private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;

  // Inventory
  private Inventory inventory;
  
  public Player(Handler handler, float x, float y) {
    super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
    
    bounds.x = 16;
    bounds.y = 32;
    bounds.width = 32;
    bounds.height = 32;
    
    // animations
    animDown = new Animation(85, Assets.link_down);
    animUp = new Animation(85, Assets.link_up);
    animLeft = new Animation(85, Assets.link_left);
    animRight = new Animation(85, Assets.link_right);
    
    inventory = new Inventory(handler);
    
  }  

  public Inventory getInventory() {
    return inventory;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  @Override
  public void update() {
    // animations
    animDown.update();
    animUp.update();
    animLeft.update();
    animRight.update();
    // movement
    getInput();
    move();
    handler.getGameCamera().centerOnEntity(this);
    // attack
    checkAttacks();
    // inventory
    inventory.update();
  }
  
  private void checkAttacks() {
    attackTimer += System.currentTimeMillis() - lastAttackTimer;
    lastAttackTimer = System.currentTimeMillis();
    if(attackTimer < attackCooldown)
        return;
    
    // in invetory, leave
    if (inventory.isActive()) return;
    
    Rectangle cb = getCollisionBounds(0, 0);
    Rectangle ar = new Rectangle();
    int arSize = 20;
    ar.width = arSize;
    ar.height = arSize;
    
    if(handler.getKeyManager().aUp){
        ar.x = cb.x + cb.width / 2 - arSize / 2;
        ar.y = cb.y - arSize;
    }else if(handler.getKeyManager().aDown){
        ar.x = cb.x + cb.width / 2 - arSize / 2;
        ar.y = cb.y + cb.height;
    }else if(handler.getKeyManager().aLeft){
        ar.x = cb.x - arSize;
        ar.y = cb.y + cb.height / 2 - arSize / 2;
    }else if(handler.getKeyManager().aRight){
        ar.x = cb.x + cb.width;
        ar.y = cb.y + cb.height / 2 - arSize / 2;
    }else{
        return;
    }
    
    attackTimer = 0;
    
    for(Entity e : handler.getWorld().getEntityManager().getEntities()){
        if(!e.equals(this) && e.getCollisionBounds(0, 0).intersects(ar)){
            e.hurt(1);
            return;
        }
    }
  }
  
  // handles anything input (keys to move player ex)
  private void getInput() {
   if (inventory.isActive()) return;
    
   xMove = 0;
   yMove = 0;
   
   if (handler.getKeyManager().up)
     yMove = -speed;
   if (handler.getKeyManager().down)
     yMove = speed;
   if (handler.getKeyManager().left)
     xMove = -speed;
   if (handler.getKeyManager().right)
     xMove = speed;
  }

  @Override
  public void render(Graphics g) {
    g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), 
        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    
    // draws out collision box:
    
//    g.setColor(Color.red);
//    g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//        (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
//        bounds.width, bounds.height);
      
  }
  
  public void postRender(Graphics g) {
    inventory.render(g);
  }
  
  @Override
  public void die() {
    System.out.println("You lose");
  }
  
  private BufferedImage getCurrentAnimationFrame() {
    if (xMove < 0) {
      return animLeft.getCurrentFrame();
    } else if (xMove > 0) {
      return animRight.getCurrentFrame();
    } else if (yMove < 0) {
      return animUp.getCurrentFrame();
    } else {
      return animDown.getCurrentFrame();
    }
  }

}
