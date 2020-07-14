import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {
  
  private Handler handler;
  private boolean active = false;
  private ArrayList<Item> inventoryItems;
  
  // hard-coded inventory menu values
  private int invX = 64, invY = 48,
      invWidth = 512, invHeight = 384,
      invListCenterX = invX + 171,  // center bar in inventory sprite sheet
      invListCenterY = invY + invHeight / 2 + 5,
      invListSpacing = 30;
  
  private int invImageX = 452, invImageY = 82,
      invImageWidth = 64, invImageHeight = 64;
  
  private int invCountX = 484, invCountY = 172;
  
  private int selectedItem = 0;  // index of selected item
  
  public Inventory(Handler handler) {
    this.handler = handler;
    inventoryItems = new ArrayList<>();
    
    // for testing, adding items to inv by default
    addItem(Item.rockItem.createNew(5));
    addItem(Item.woodItem.createNew(5));
  }
  
  public void update() {
    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
      active = !active;
    if (!active)
      return;
    // scrolling thru inventory using W/S
    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
      selectedItem--;
    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
      selectedItem++;
    
    // wrapping items around (reached end)
    if (selectedItem < 0)
      selectedItem = inventoryItems.size() - 1;
    else if (selectedItem >= inventoryItems.size())
      selectedItem = 0;
    
    
  }
  
  public void render(Graphics g) {
    if (!active)
      return;
    g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);
    
    int len = inventoryItems.size();  // number of items in inventory
    if (len == 0) return;
    
    for (int i = -5; i < 6; i++) {
      if (selectedItem + i < 0 || selectedItem + i >= len)
        continue;
      if (i == 0) {
        Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", 
            invListCenterX, invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);
      } else {
        Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), 
            invListCenterX, invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
      }
    }
      // selected item pic and count
    Item item = inventoryItems.get(selectedItem);
    g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
    Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);

  }
  
  // inventory methods
  public void addItem(Item item) {
    for (Item i : inventoryItems) {
      if (i.getId() == item.getId()) {
        i.setCount(i.getCount() + item.getCount());
        return;
      }
    }
    inventoryItems.add(item);
  }
  
  public Handler getHandler() {
    return handler;
  }
  
  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public boolean isActive() {
    return active;
  }  

}
