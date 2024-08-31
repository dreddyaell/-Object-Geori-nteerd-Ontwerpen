import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MenuController extends MenuBar {

    private Frame parent; //The frame, only used as parent for the Dialogs
    private Presentation presentation; //Commands are given to the presentation

    private static final long serialVersionUID = 227L;

    protected static final String ABOUT = "About";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String HELP = "Help";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PAGENR = "Page number?";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";
    protected static final String VIEW = "View";

    protected static final String TESTFILE = "testPresentation.xml";
    protected static final String SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);

        // Open Menu Item
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Presentation");
                int result = fileChooser.showOpenDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    presentation.clear();

                    try {
                        // Use the factory to get an appropriate reader
                        Reader xmlReader = AccessorFactory.getFactory("xml").CreateReader();
                        xmlReader.loadFile(presentation, selectedFile.getAbsolutePath());
                        presentation.setSlideNumber(0);
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(parent, IOEX + exc,
                                LOADERR, JOptionPane.ERROR_MESSAGE);
                    }
                    parent.repaint();
                }
            }
        });

        // New Menu Item
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.clear();
                // Add an initial blank slide
                Slide newSlide = new Slide();
                newSlide.setTitle("New Slide");
                presentation.append(newSlide);
                presentation.setSlideNumber(0);
                parent.repaint();
            }
        });


        // Save Menu Item
        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Writer xmlWriter = AccessorFactory.getFactory("xml").CreateWriter();
                    xmlWriter.saveFile(presentation, SAVEFILE);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc,
                            SAVEERR, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Exit Menu Item
        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.exit(0);
            }
        });
        add(fileMenu);

        // View Menu
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.nextSlide();
            }
        });
        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                presentation.prevSlide();
            }
        });
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            }
        });
        add(viewMenu);

        // Help Menu
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(parent,
                        "JabberPoint\nVersion 1.6\nDeveloped by [Yaell Kuipers]",
                        "About JabberPoint",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        setHelpMenu(helpMenu);
    }

    // Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
