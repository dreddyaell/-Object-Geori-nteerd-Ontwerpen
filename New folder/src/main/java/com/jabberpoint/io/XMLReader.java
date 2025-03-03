package com.jabberpoint.io;

import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 * XMLReader leest een presentatie vanuit een XML-bestand.
 */
public class XMLReader implements PresentationReader {

    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // Root element
            Element root = doc.getDocumentElement();
            presentation.setTitle(root.getElementsByTagName("title").item(0).getTextContent());

            NodeList slides = root.getElementsByTagName("slide");

            for (int i = 0; i < slides.getLength(); i++) {
                Element slideElement = (Element) slides.item(i);
                Slide slide = new Slide();
                slide.setTitle(slideElement.getElementsByTagName("title").item(0).getTextContent());

                NodeList texts = slideElement.getElementsByTagName("text");
                for (int j = 0; j < texts.getLength(); j++) {
                    slide.appendItem(texts.item(j).getTextContent());
                }

                presentation.append(slide);
            }
        } catch (Exception e) {
            throw new IOException("Fout bij laden van XML-bestand: " + e.getMessage());
        }
    }
}
