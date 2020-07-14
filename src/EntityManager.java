import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

// renders and updates all entities, including player
public class EntityManager {
  
  private Handler handler;
  private Player player;
  private ArrayList<Entity> entities;
  private Comparator<Entity> renderSorter = new Comparator<Entity>() {
    @Override
    public int compare(Entity a, Entity b) {
      if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
        return -1;
      else return 1;
    }
  };
  
  public EntityManager(Handler handler, Player player) {
    this.handler = handler;
    this.player = player;
    entities = new ArrayList<>();
    entities.add(player);
  }
  
  public void update() {
    Iterator<Entity> iter = entities.iterator();
    Entity e;
    while (iter.hasNext()) {
      e = iter.next();
      e.update();
      if (!e.isActive())
        iter.remove();
    }
    entities.sort(renderSorter);
  }
  
  public void render(Graphics g) {
    for (Entity e: entities) {
      e.render(g);
    }
    player.postRender(g);  // render inventory last, so it covers
  }
  
  public void addEntity(Entity e) {
    entities.add(e);
  }

  public Handler getHandler() {
    return handler;
  }

  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public ArrayList<Entity> getEntities() {
    return entities;
  }

  public void setEntities(ArrayList<Entity> entities) {
    this.entities = entities;
  }
  
}
