package com.jabberpoint.io;

import com.jabberpoint.model.BitmapItem;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;

/**
 * XMLReader leest een presentatie vanuit een XML-bestand.
 */
public class XMLReader implements PresentationReader {


    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            File xmlFile = new File(filename);
            BitmapItem.basePath = new File(filename).getParent(); // Zet het pad VOORDAT objecten worden aangemaakt
            System.out.println("Afbeeldingsmap correct ingesteld op: " + BitmapItem.basePath);


            if (!xmlFile.exists()) {
                throw new IOException("Bestand niet gevonden: " + filename);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // Root element
            Element root = doc.getDocumentElement();
            presentation.setTitle(root.getElementsByTagName("showtitle").item(0).getTextContent());

            NodeList slides = root.getElementsByTagName("slide");

            for (int i = 0; i < slides.getLength(); i++) {
                Element slideElement = (Element) slides.item(i);
                Slide slide = new Slide();
                slide.setTitle(slideElement.getElementsByTagName("title").item(0).getTextContent());

                NodeList items = slideElement.getElementsByTagName("item");
                for (int j = 0; j < items.getLength(); j++) {
                    Element item = (Element) items.item(j);
                    String kind = item.getAttribute("kind");
                    int level = Integer.parseInt(item.getAttribute("level"));
                    String content = item.getTextContent();

                    if ("text".equals(kind)) {
                        slide.append(level, content);
                    } else if ("image".equals(kind)) {
                        slide.appendImage(level, content);
                    }
                }

                presentation.append(slide);
            }
            System.out.println("geen fouten!");
        } catch (Exception e) {
            throw new IOException("Fout bij laden van XML-bestand: " + e.getMessage());
        }


    }

}
