import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;
import java.awt.*;
import java.util.ArrayList;

/** <p>A slide. This class has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
    public final static int WIDTH = 1200; 
    public final static int HEIGHT = 800;
    private String title; 
    private ArrayList<SlideItem> items = new ArrayList<>(); 


	public Slide(){

	}

	//Add a SlideItem
	public void append(SlideItem anItem) {
		this.items.addElement(anItem);
	}

	//Return the title of a slide
	public String getTitle() {
		return this.title;
	}

	//Change the title of a slide
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public SlideItem getSlideItem(int number) {
		return (SlideItem)this.items.elementAt(number);
	}

	//Create a TextItem out of a String and add the TextItem
	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	//Returns the SlideItem


	//Return all the SlideItems in a vector
	public Vector<SlideItem> getSlideItems() {
		return this.items;
	}

	//Returns the size of a slide
	public int getSize() {
		return this.items.size();
	}

	//Draws the slide
   public int drawItem(SlideItem slideItem, int y, Graphics g, Rectangle area, ImageObserver view) {
        Style style = Styles.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, this.getScale(area), g, style, view);
        return slideItem.getBoundingBox(g, view, this.getScale(area), style).height;
    }

	  public void draw(Graphics g, Rectangle area, ImageObserver view) {
        int y = area.y;
        y += this.drawItem(new TextItem(0, this.getTitle()), y, g, area, view);

        for (SlideItem currentItem : this.items) {
            y += this.drawItem(currentItem, y, g, area, view);
        }
    }

	//Returns the scale to draw a slide
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}
}
