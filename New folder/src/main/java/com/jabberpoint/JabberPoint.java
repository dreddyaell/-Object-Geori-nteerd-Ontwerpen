package com.jabberpoint;

import com.jabberpoint.controller.KeyController;
import com.jabberpoint.controller.MenuController;
import com.jabberpoint.io.AccessorFactory;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.util.Style;
import com.jabberpoint.view.SlideViewerFrame;

import javax.swing.*;
import java.io.IOException;

/**
 * JabberPoint Main Program
 */
public class JabberPoint {
	private final Presentation presentation;

    /**
	 * Constructor.
	 */
	public JabberPoint(Presentation presentation, KeyController keyController, MenuController menuController, SlideViewerFrame frame) {
		this.presentation = presentation;
    }

	/**
	 * Start de applicatie.
	 */
	public void run(String[] args) {
		Style.createStyles();
		try {
			if (args.length == 0) { // Laad demo presentatie
				AccessorFactory.getFactory("demo").CreateReader().loadFile(presentation, "");
			}
			presentation.setSlideNumber(0);
		} catch (IOException ex) {
			showErrorDialog("IO Error: " + ex);
		}
	}

	/**
	 * Toont een foutmelding in een dialoogvenster.
	 */
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Main methode die de applicatie start.
	 */
	public static void main(String[] args) {
		Presentation presentation = new Presentation();
		KeyController keyController = new KeyController(presentation);

		SlideViewerFrame frame = new SlideViewerFrame("JabberPoint 1.6 - OU", presentation, keyController, null);
		MenuController menuController = new MenuController(presentation, frame);
		frame.setMenuBar(menuController);
		JabberPoint app = new JabberPoint(presentation, keyController, menuController, frame);
		app.run(args);
	}

}
