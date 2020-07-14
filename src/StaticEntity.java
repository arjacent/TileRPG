// static entities are those that don't move (ie tree or rock)
public abstract class StaticEntity extends Entity{
  
  public StaticEntity(Handler handler, float x, float y, int width, int height) {
    super(handler, x, y, width, height);
  }

}
