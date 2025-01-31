package com.jabberpoint.controller;

import com.jabberpoint.model.Presentation;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <p>KeyController zorgt voor toetsenbordnavigatie binnen JabberPoint.</p>
 * @author Ian F. Darwin
 * @author Gert Florijn
 * @author Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class KeyController extends KeyAdapter {
	private final Presentation presentation; // Commands worden gestuurd naar de presentatie

	public KeyController(Presentation presentation) {
		this.presentation = presentation;
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, KeyEvent.VK_RIGHT, '+':
				presentation.nextSlide();
				break;

			case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, KeyEvent.VK_LEFT, '-':
				presentation.prevSlide();
				break;

			case KeyEvent.VK_Q: // 'Q' of 'q' om af te sluiten
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;

			case KeyEvent.VK_G: // 'G' om naar een specifieke slide te gaan
				if (keyEvent.isControlDown()) { // Alleen als Ctrl + G wordt ingedrukt
					goToSlide();
				}
				break;

			default:
				break;
		}
	}

	/**
	 * Opent een dialoogvenster waarin de gebruiker een slide-nummer kan invoeren.
	 */
	private void goToSlide() {
		String input = JOptionPane.showInputDialog(null, "Voer het slide nummer in:",
				"Ga naar slide", JOptionPane.QUESTION_MESSAGE);

		if (input != null) { // Als de gebruiker niet op "Annuleren" klikt
			try {
				int slideNumber = Integer.parseInt(input) - 1; // -1 omdat slides 0-gebaseerd zijn
				presentation.setSlideNumber(slideNumber);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						" Ongeldig nummer! Voer een geldig slide nummer in.",
						"Fout", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
