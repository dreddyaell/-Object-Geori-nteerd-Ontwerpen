import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.io.IOException;

import javax.swing.JOptionPane;




/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	private final Frame parent; //The frame, only used as parent for the Dialogs
	private final Presentation presentation; //Commands are given to the presentation

	private static final long serialVersionUID = 227L;

	private MenuItem menuItem;

	public MenuController(Frame frame, Presentation pres) {
		this.parent = frame;
		this.presentation = pres;
		this.addFileMenu();
		this.addViewMenu();
		this.addHelp();
	}

 	 private MenuItem mkMenuItem(String name, char shortcut) {
        return new MenuItem(name, new MenuShortcut(shortcut));
    }

	private void showAboutBox() {
		JOptionPane.showMessageDialog(this.parent, "JabberPoint is a primitive slide-show program in Java(tm). It\nis freely copyable as long as you keep this notice and\nthe splash screen intact.\nCopyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\nAdapted by Gert Florijn (version 1.1) and Sylvia Stuurman (version 1.2 and higher) for the OpenUniversity of the Netherlands, 2002 -- now.\nAuthor's version available from http://www.darwinsys.com/", "About JabberPoint", 1);
	}

	private void addFileMenu() {
		Menu fileMenu = new Menu("File");
		this.addOpen(fileMenu);
		this.addNew(fileMenu);
		this.addSave(fileMenu);
		fileMenu.addSeparator();
		this.addExit(fileMenu);
		this.add(fileMenu);
	}

	private void addOpen(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("Open"));
		this.menuItem.addActionListener((actionEvent) -> {
			this.presentation.clear();
			try {
				Factory.getFactory("demo").CreateReader().loadFile(this.presentation, "");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			this.presentation.setSlideNumber(0);
			this.parent.repaint();
		});
	}

	private void addNew(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("New"));
		this.menuItem.addActionListener((actionEvent) -> {
			this.presentation.clear();
			this.parent.repaint();
		});
	}

	private void addSave(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("Save"));
		this.menuItem.addActionListener((e) -> {
			Factory.getFactory("Demo").CreateReader().saveFile(this.presentation, "Demo");
		});
	}
	private void addNext(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("Next"));
		this.menuItem.addActionListener((actionEvent) -> {
			this.presentation.nextSlide();
		});
	}

	private void addExit(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("Exit"));
		this.menuItem.addActionListener((actionEvent) -> {
			System.exit(0);
		});
	}

	private void addViewMenu() {
		Menu viewMenu = new Menu("View");
		this.addNext(viewMenu);
		this.addPrev(viewMenu);
		this.addGoto(viewMenu);
		this.add(viewMenu);
	}
	private void addHelp() {
		Menu helpMenu = new Menu("Help");
		this.addAbout(helpMenu);
		this.setHelpMenu(helpMenu);
	}

	private void addPrev(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("Prev"));
		this.menuItem.addActionListener((actionEvent) -> {
			this.presentation.prevSlide();
		});
	}

 private void addGoto(Menu menu) {
        menu.add(this.menuItem = this.mkMenuItem("Go to", 'G'));
        this.menuItem.addActionListener((actionEvent) -> {
            try {
                String pageNumberStr = JOptionPane.showInputDialog("Page number?");
                int pageNumber = Integer.parseInt(pageNumberStr);
                if (pageNumber > 0 && pageNumber <= this.presentation.getSize()) {
                    this.presentation.setSlideNumber(pageNumber - 1);
                } else {
                   
                }
            } catch (NumberFormatException e) {
              
            }
        });
    }

	private void addAbout(Menu menu) {
		menu.add(this.menuItem = this.mkMenuItem("About"));
		this.menuItem.addActionListener((actionEvent) -> {
			this.showAboutBox();
		});
	}
}

