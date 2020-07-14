import java.awt.Font;
import java.awt.image.BufferedImage;

// preloads all image and sound files
public class Assets {
  
  public static Font font28;
  
  public static final int w1 = 961/10, h1 = 832/8;
  private static final int width = 32, height = 32;
  
  public static BufferedImage player, dirt, grass, stone, tree, rock, wood;
  public static BufferedImage[] link_down, link_up, link_left, link_right;
  public static BufferedImage[] btn_start;
  public static BufferedImage inventoryScreen;
  
  public static void init() {
    font28 = FontLoader.loadFont("./res/fonts/slkscr.ttf", 28f);
    // Inventory menu
    inventoryScreen = ImageLoader.loadImage("./textures/inventoryScreen.png");
    // The player (Link - 10 frames)
    SpriteSheet link_sheet = new SpriteSheet(ImageLoader.loadImage("./textures/link_sprite.png"));
    link_down = new BufferedImage[10];
    link_up = new BufferedImage[10];
    link_left = new BufferedImage[10];
    link_right = new BufferedImage[10];
    for (int i = 0; i < 10; i++) {
      link_down[i] = link_sheet.crop(i * w1,  4 * h1 + 1, w1, h1);
      link_up[i] = link_sheet.crop(i * w1,  6 * h1, w1, h1);
      link_left[i] = link_sheet.crop(i * w1,  5 * h1, w1, h1);
      link_right[i] = link_sheet.crop(i * w1,  7 * h1, w1, h1);
    }  
    SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("./textures/sheet2.png"));
    btn_start = new BufferedImage[2];
    btn_start[0] = sheet2.crop(width * 6, height * 4, width * 2, height);
    btn_start[1] = sheet2.crop(width * 6, height * 5, width * 2, height);
    
    SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("./textures/sheet.png"));

    
    player = sheet.crop(0, 0, width, height);
    dirt = sheet.crop(width, 0, width, height);
    grass = sheet.crop(width * 2, 0, width, height);
    stone = sheet.crop(width * 3, 0, width, height);
    tree = sheet.crop(0, height, width, height);
    
    rock = sheet2.crop(0, height * 2, width, height);
    wood = sheet2.crop(width, height, width, height);
    
  }

}
