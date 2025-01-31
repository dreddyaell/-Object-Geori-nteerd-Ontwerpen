package com.jabberpoint.view;

import com.jabberpoint.controller.KeyController;
import com.jabberpoint.controller.MenuController;
import com.jabberpoint.model.Presentation;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import javax.swing.JFrame;

/**
 * Het applicatievenster voor de SlideViewerComponent
 * @author Ian F. Darwin, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class SlideViewerFrame extends JFrame {
	@Serial
	private static final long serialVersionUID = 3227L;
	private static final String JABTITLE = "JabberPoint 1.6 - OU";
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;

	/**
	 * Constructor voor SlideViewerFrame.
	 * @param title Titel van het venster
	 * @param presentation De presentatie die getoond wordt
	 * @param keyController Controller voor toetsenbordbediening
	 * @param menuController Controller voor menu-bediening
	 */
	public SlideViewerFrame(String title, Presentation presentation, KeyController keyController, MenuController menuController) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);

		setupWindow(slideViewerComponent, keyController, menuController);
	}

	/**
	 * Stelt het venster in en voegt controllers toe.
	 */
	private void setupWindow(SlideViewerComponent slideViewerComponent, KeyController keyController, MenuController menuController) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().add(slideViewerComponent);
		addKeyListener(keyController);
		setMenuBar(menuController);
		setSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
	}
}
