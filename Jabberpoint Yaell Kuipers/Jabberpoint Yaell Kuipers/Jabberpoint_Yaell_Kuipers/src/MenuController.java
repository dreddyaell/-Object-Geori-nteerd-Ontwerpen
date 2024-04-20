import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
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

    private MenuItem menuItem;
    private Menu fileMenu;
    private Menu viewMenu;
    private Menu helpMenu;

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        this.fileMenu = new Menu(FILE);
        this.viewMenu = new Menu(VIEW);
        this.helpMenu = new Menu(HELP);
        add(fileMenu);
        add(viewMenu);
        add(viewMenu);
        addOpen();
        addSave();
        addNew();
        fileMenu.addSeparator();
        addExit();
        addNext();
        addPrev();
        addGoto();
        addAboutBox();
    }

    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }

    public MenuItem addOpen() {
        this.fileMenu.add(menuItem = mkMenuItem(OPEN));

        this.menuItem.addActionListener(e -> {
            presentation.clear();
            //XMLReader read = new XMLReader();
            try {
                //read.loadFile(presentation, TESTFILE);
                presentation.setSlideNumber(0);
                presentation.clear();
                AccessorFactory.getFactory("").CreateReader().loadFile(presentation,"");
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(parent, IOEX + exc,
                        LOADERR, JOptionPane.ERROR_MESSAGE);
            }
            parent.repaint();
        });
        return menuItem;
    }

    public MenuItem addSave() {
        this.fileMenu.add(menuItem = mkMenuItem(SAVE));

        menuItem.addActionListener(e -> {
            //Accessor xmlAccessor = new XMLAccessor();
            //XMLWriter save = new XMLWriter();
            try {
               // xmlAccessor.saveFile(presentation, SAVEFILE);
                //save.saveFile(presentation, SAVEFILE);
                AccessorFactory.getFactory("demo").CreateWriter().saveFile(presentation,"demo");

            } catch (IOException exc) {
                JOptionPane.showMessageDialog(parent, IOEX + exc,
                        SAVEERR, JOptionPane.ERROR_MESSAGE);
            }
        });
        return menuItem;
    }

    public MenuItem addNew() {
        this.fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(e -> {
            presentation.clear();
            parent.repaint();
        });
        return menuItem;
    }

    public MenuItem addExit() {
        this.fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(e -> {
            presentation.exit(0);
        });
        return menuItem;
    }

    public MenuItem addNext() {
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(e -> {
            presentation.nextSlide();
        });
        return menuItem;
    }

    public MenuItem addPrev() {
        this.viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(e -> {
            presentation.prevSlide();
        });
        return menuItem;
    }

    public MenuItem addGoto() {
        this.viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(e -> {
            String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);
        });
        return menuItem;
    }

    public MenuItem addAboutBox() {
        this.helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(parent,
                    "JabberPoint is a primitive slide-show program in Java(tm). It\n" +
                            "is freely copyable as long as you keep this notice and\n" +
                            "the splash screen intact.\n" +
                            "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
                            "Adapted by Gert Florijn (version 1.1) and " +
                            "Sylvia Stuurman (version 1.2 and higher) for the Open" +
                            "University of the Netherlands, 2002 -- now.\n" +
                            "Author's version available from http://www.darwinsys.com/",
                    "About JabberPoint",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        setHelpMenu(helpMenu);        //Needed for portability (Motif, etc.).
        return  menuItem;
    }
}