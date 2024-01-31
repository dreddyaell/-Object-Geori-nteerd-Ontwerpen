import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
    private BufferedImage bufferedImage;
    private final String imageName;

    public BitmapItem(int level, String name) {
        super(level);
        this.imageName = name;
        loadImage();
    }

    public BitmapItem() {
        this(0, null);
    }

    private void loadImage() {
        if (imageName != null) {
            try {
                bufferedImage = ImageIO.read(new File(imageName));
            } catch (IOException e) {
                System.err.println("File " + imageName + " not found");
            }
        }
    }

    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        if (bufferedImage == null) return new Rectangle();

        int width = (int) (bufferedImage.getWidth(observer) * scale);
        int leading = (int) (myStyle.getLeading() * scale);
        int height = (int) (bufferedImage.getHeight(observer) * scale);

        return new Rectangle(leading, 0, width, height);
    }

	//Draws the image
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
        if (bufferedImage == null) return;
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getLeading() * scale);
		int imageBufferWidth = (int) (this.bufferedImage.getWidth(observer)*scale);
		int imageBufferHeight = (int) (this.bufferedImage.getHeight(observer)*scale);
		g.drawImage(this.bufferedImage, width, height, imageBufferWidth, imageBufferHeight, observer);
	}

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + this.imageName + "]";
	}
}
