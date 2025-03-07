package com.jabberpoint.model;

import com.jabberpoint.util.Style;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class BitmapItem extends SlideItem {
	private BufferedImage bufferedImage;
	private String imageName;

	protected static final String FILE = "Bestand niet gevonden: ";
	protected static final String NOTFOUND = " - Bestand bestaat niet of is onleesbaar.";


	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;

		// Controleer of het basispad correct is ingesteld
		if (BitmapItem.basePath == null || BitmapItem.basePath.isEmpty()) {
			System.err.println("Waarschuwing: basePath is leeg! Beelden worden mogelijk niet correct geladen.");
		}

		// Bepaal het juiste pad voor de afbeelding
		File imageFile;
		if (BitmapItem.basePath != null && !BitmapItem.basePath.isEmpty()) {
			imageFile = new File(BitmapItem.basePath, imageName); // Zoek in dezelfde map als de XML
		} else {
			imageFile = new File(imageName);
		}

		// Debug output om te zien waar de afbeelding wordt gezocht
		System.out.println("Probeert afbeelding te laden vanaf: " + imageFile.getAbsolutePath());

		try {
			bufferedImage = ImageIO.read(imageFile);
			if (bufferedImage == null) {
				System.err.println("Kan afbeelding niet laden (mogelijk ongeldig bestand): " + imageFile.getAbsolutePath());
			} else {
				System.out.println("Afbeelding geladen: " + imageFile.getAbsolutePath());
			}
		} catch (IOException e) {
			System.err.println("Fout bij laden afbeelding: " + imageFile.getAbsolutePath() + " → " + e.getMessage());
		}
	}

	private void loadImage() {
		if (imageName == null || imageName.isEmpty()) {
			System.err.println("Ongeldige bestandsnaam voor afbeelding.");
			return;
		}

		File imageFile = new File(BitmapItem.basePath, imageName);
		System.out.println("Probeert afbeelding te laden: " + imageFile.getAbsolutePath());

		if (!imageFile.exists()) {
			System.err.println("Afbeelding bestaat niet: " + imageFile.getAbsolutePath());
		} else {
			System.out.println("Afbeelding gevonden: " + imageFile.getAbsolutePath());
		}


		try {
			bufferedImage = ImageIO.read(imageFile);
			if (bufferedImage == null) {
				System.err.println("Kan afbeelding niet laden (mogelijk ongeldig bestand): " + imageFile.getAbsolutePath());
			}
		} catch (IOException e) {
			System.err.println("Fout bij laden afbeelding: " + imageFile.getAbsolutePath() + " → " + e.getMessage());
		}
	}

	public static String basePath = "";


	public String getName() {
		return imageName;
	}

	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		if (bufferedImage == null) {
			return new Rectangle(0, 0, 0, 0);
		}
		return new Rectangle((int) (myStyle.indent * scale), 0,
				(int) (bufferedImage.getWidth(observer) * scale),
				((int) (myStyle.leading * scale)) +
						(int) (bufferedImage.getHeight(observer) * scale));
	}

	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		if (bufferedImage == null) {
			System.err.println("Kan afbeelding niet tekenen: " + imageName);
			System.err.println("Probeert afbeelding te laden vanaf: " + new File(BitmapItem.basePath, imageName).getAbsolutePath());
			return;
		}

		int width = x + (int) (myStyle.indent * scale);
		int height = y + (int) (myStyle.leading * scale);
		g.drawImage(bufferedImage, width, height,
				(int) (bufferedImage.getWidth(observer) * scale),
				(int) (bufferedImage.getHeight(observer) * scale), observer);
	}


	@Override
	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}
