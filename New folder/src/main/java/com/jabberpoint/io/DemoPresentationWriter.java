package com.jabberpoint.io;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import com.jabberpoint.model.SlideItem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DemoPresentationWriter implements Writer {

    @Override
    public void saveFile(Presentation presentation, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write the presentation title
            writer.println("Presentation Title: " + presentation.getTitle());
            writer.println();

            // Write each slide and its content
            for (int i = 0; i < presentation.getSize(); i++) {
                Slide slide = presentation.getSlide(i);
                writer.println("Slide " + (i + 1) + ": " + slide.getTitle());

                for (SlideItem item : slide.getSlideItems()) {
                    writer.println(" - " + item.toString());
                }
                writer.println();
            }

            System.out.println("Presentation saved successfully to " + filename);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save presentation to " + filename, e);
        }
    }
}
