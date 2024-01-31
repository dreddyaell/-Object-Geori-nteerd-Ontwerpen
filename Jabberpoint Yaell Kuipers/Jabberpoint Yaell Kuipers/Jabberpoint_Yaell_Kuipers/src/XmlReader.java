import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlReader implements InterfaceReader {
    private static final String DEFAULT_API_TO_USE = "dom";
    private static final String SHOWTITLE = "showtitle";
    private static final String SLIDETITLE = "title";
    private static final String SLIDE = "slide";
    private static final String ITEM = "item";
    private static final String LEVEL = "level";
    private static final String KIND = "kind";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";

    private static final String PCE = "Parser Configuration Exception";
    private static final String UNKNOWNTYPE = "Unknown Element type";
    private static final String NFE = "Number Format Exception";

    private static final String DEMO_NAME = "Demo";
    private static final String DEFAULT_EXTENSION = ".xml";

    @Override
    public void loadFile(Presentation p, String fileName) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            Element rootElement = document.getDocumentElement();
            p.setTitle(getTitle(rootElement, SHOWTITLE));

            NodeList slideNodes = rootElement.getElementsByTagName(SLIDE);
            int maxSlides = slideNodes.getLength();
            for (int slideIndex = 0; slideIndex < maxSlides; slideIndex++) {
                Element slideElement = (Element) slideNodes.item(slideIndex);
                Slide slide = new Slide();
                slide.setTitle(getTitle(slideElement, SLIDETITLE));
                p.append(slide);

                NodeList itemNodes = slideElement.getElementsByTagName(ITEM);
                int maxItems = itemNodes.getLength();
                for (int itemIndex = 0; itemIndex < maxItems; itemIndex++) {
                    Element itemElement = (Element) itemNodes.item(itemIndex);
                    loadSlideItem(slide, itemElement);
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            System.err.println(PCE);
            e.printStackTrace();
        }
    }

 @Override
    public void saveFile(Presentation p, String demo) {
        throw new UnsupportedOperationException("XML saving not implemented.");
    }

   private String getTitle(Element element, String tagName) {
        NodeList titleNodes = element.getElementsByTagName(tagName);
        return titleNodes.item(0).getTextContent();
    }

    private void loadSlideItem(Slide slide, Element itemElement) {
        int level = 1;
        NamedNodeMap attributes = itemElement.getAttributes();
        Node levelNode = attributes.getNamedItem(LEVEL);
        if (levelNode != null) {
            String levelText = levelNode.getTextContent();
            try {
                level = Integer.parseInt(levelText);
            } catch (NumberFormatException e) {
                System.err.println(NFE);
                e.printStackTrace();
            }
        }
        String type = attributes.getNamedItem(KIND).getTextContent();
        if (TEXT.equals(type)) {
            slide.append(new TextItem(level, itemElement.getTextContent()));
        } else if (IMAGE.equals(type)) {
            slide.append(new BitmapItem(level, itemElement.getTextContent()));
        } else {
            System.err.println(UNKNOWNTYPE);
        }
    }
}
