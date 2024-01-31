import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
    private String showTitle; // The title of the presentation
    private ArrayList<Slide> showList = new ArrayList<>(); 
    private int currentSlideNumber = 0;
    private SlideViewerComponent slideViewComponent = null;

    public Presentation() {
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent) {
        this.slideViewComponent = slideViewerComponent;
        clear();
    }

	public int getSize() {
		return this.showList.size();
	}

	public String getTitle() {
		return this.showTitle;
	}

	public void setTitle(String nt) {
		this.showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	//Returns the number of the current slide
	public int getSlideNumber() {
		return this.currentSlideNumber;
	}

	//Change the current slide number and report it the window
	public void setSlideNumber(int number) {
		this.currentSlideNumber = number;
		if (this.slideViewComponent != null) {
			this.slideViewComponent.update(this, getCurrentSlide());
		}
	}

	//Navigate to the previous slide unless we are at the first slide
	public void prevSlide() {
		if (this.currentSlideNumber > 0) {
			this.setSlideNumber(this.currentSlideNumber - 1);
	    }
	}

	//Navigate to the next slide unless we are at the last slide
	public void nextSlide() {
		if (this.currentSlideNumber < (this.showList.size()-1)) {
			this.setSlideNumber(this.currentSlideNumber + 1);
		}
	}

	//Remove the presentation
 	void clear() {
        showList.clear();
        setSlideNumber(-1);
    }

	//Add a slide to the presentation
	public void append(Slide slide) {
		this.showList.add(slide);
	}

	//Return a slide with a specific number
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return (Slide)this.showList.get(number);
	}

	//Return the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
