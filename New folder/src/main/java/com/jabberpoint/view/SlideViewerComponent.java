package com.jabberpoint.view;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serial;
import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {

	private Slide slide; //The current slide
	private Font labelFont = null; //The font for labels
	private Presentation presentation = null; //The presentation
	private JFrame frame = null;

	@Serial
	private static final long serialVersionUID = 227L;

	private static final Color BGCOLOR = Color.white;
	private static final Color COLOR = Color.black;
	private static final String FONTNAME = "Dialog";
	private static final int FONTSTYLE = Font.BOLD;
	private static final int FONTHEIGHT = 10;
	private static final int XPOS = 1100;
	private static final int YPOS = 20;

	public SlideViewerComponent(Presentation pres, JFrame frame) {
		setBackground(BGCOLOR);
		presentation = pres;
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
		this.frame = frame;
	}

	public Dimension getPreferredSize() {
		return new Dimension(Slide.WIDTH, Slide.HEIGHT);
	}

	public void update(Presentation presentation, Slide data) {
		System.out.println("Update called with slide: " + (data != null ? "Valid slide" : "No slide"));
		if (data == null) {
			repaint();
			return;
		}
		this.presentation = presentation;
		this.slide = data;
		repaint();
		frame.setTitle(presentation.getTitle());
	}

	public void paintComponent(Graphics g) {
		System.out.println("paintComponent called");  // Debugging statement
		g.setColor(BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (presentation.getSlideNumber() < 0 || slide == null) {
			System.out.println("No slide to display or invalid slide number.");  // Debugging statement
			return;
		}
		System.out.println("Drawing slide...");  // Debugging statement
		g.setFont(labelFont);
		g.setColor(COLOR);
		g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
				presentation.getSize(), XPOS, YPOS);
		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		slide.draw(g, area, this);
	}




}
