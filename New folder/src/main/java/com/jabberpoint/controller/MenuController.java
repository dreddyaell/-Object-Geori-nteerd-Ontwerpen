package com.jabberpoint.controller;

import com.jabberpoint.io.AccessorFactory;
import com.jabberpoint.io.PresentationReader;
import com.jabberpoint.io.Writer;
import com.jabberpoint.model.Presentation;
import com.jabberpoint.model.Slide;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.swing.*;

/**
 * MenuController beheert de menu-opties en functionaliteit van JabberPoint.
 */
public class MenuController extends MenuBar {

    private final Presentation presentation;
    private Frame parent;  // De presentatie die wordt weergegeven

    @Serial
    private static final long serialVersionUID = 227L;

    // Menu-opties
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

    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";

    /**
     * Constructor voor MenuController.
     *
     * @param pres De presentatie die wordt beheerd.
     */
    public MenuController(Presentation pres, Frame frame) {
        this.presentation = pres;
        this.parent = frame;
        createMenu();
    }

    /**
     * Stelt het frame in (nodig voor bepaalde functionaliteiten).
     * @param frame Het nieuwe frame.
     */
    public void setFrame(Frame frame) {
        this.parent = frame;
    }

    /**
     * Creëert het menu en voegt event handlers toe.
     */
    private void createMenu() {
        MenuItem menuItem;
        Menu fileMenu = new Menu(FILE);

        // Open Menu Item
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener((ActionEvent actionEvent) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Presentation");
            int result = fileChooser.showOpenDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                openFile(selectedFile); // Roep de correcte methode aan
            }
        });

        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem("Load Default Slides"));
        menuItem.addActionListener(e -> loadDefaultSlides());


        // New Menu Item
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(e -> {
            Slide newSlide = new Slide();
            newSlide.setTitle("New Slide");
            presentation.append(newSlide);
            presentation.setSlideNumber(0);
            if (parent != null) parent.repaint();
        });

        // Save Menu Item (met bestandskeuze)
        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(e -> saveFile());

        // Exit Menu Item
        fileMenu.addSeparator();
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(e -> presentation.exit(0));

        add(fileMenu);

        // View Menu
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(e -> presentation.nextSlide());
        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(e -> presentation.prevSlide());
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(e -> {
            String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
            try {
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            } catch (NumberFormatException ex) {
                showErrorDialog("Ongeldig paginanummer!");
            }
        });
        add(viewMenu);

        // Help Menu
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(e -> JOptionPane.showMessageDialog(parent,
                "JabberPoint\nVersion 1.6\nDeveloped by [Yaell Kuipers]",
                "About JabberPoint",
                JOptionPane.INFORMATION_MESSAGE));
        setHelpMenu(helpMenu);
    }

    /**
     * Opent een bestand en laadt de presentatie.
     * @param file Het geselecteerde bestand.
     */
    public void openFile(File file) {
        try {
            String fileType = file.getName().toLowerCase().endsWith(".xml") ? "xml" : "demo";

            presentation.clear(); // Oude slides verwijderen

            PresentationReader reader = AccessorFactory.getFactory(fileType).CreateReader();
            reader.loadFile(presentation, file.getAbsolutePath());
            presentation.setSlideNumber(0);

            if (parent != null) parent.repaint();
        } catch (IOException e) {
            showErrorDialog("Kan bestand niet openen: " + file.getName());
        }
    }




    /**
     * Laat de gebruiker een locatie en bestandsnaam kiezen om de presentatie op te slaan.
     */
    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Presentation");

        // Standaard extensie instellen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML Files", "xml"));

        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Zorg ervoor dat het bestand de juiste extensie heeft
            if (!filePath.toLowerCase().endsWith(".xml")) {
                filePath += ".xml";
            }

            try {
                Writer xmlWriter = AccessorFactory.getFactory("xml").CreateWriter();
                xmlWriter.saveFile(presentation, filePath);
                JOptionPane.showMessageDialog(parent, "Presentatie opgeslagen als: " + filePath, "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException exc) {
                showErrorDialog("Fout bij opslaan: " + exc.getMessage());
            }
        }
    }

    /**
     * Laadt de standaard demo-presentatie.
     */
    public void loadDefaultSlides() {
        if (presentation == null) {
            showErrorDialog("Kan standaard slides niet laden, presentatie is null!");
            return;
        }
        presentation.clear(); //Oude slides verwijderen
        try {
            AccessorFactory.getFactory("demo").CreateReader().loadFile(presentation, "");
            presentation.setSlideNumber(0); //
            if (parent != null) parent.repaint();
        } catch (IOException e) {
            showErrorDialog("Kan standaard slides niet laden.");
        }
    }

    public void newSlide() {
        Slide newSlide = new Slide();
        newSlide.setTitle("New Slide");
        presentation.append(newSlide);
        presentation.setSlideNumber(presentation.getSize() - 1); // ✅ Ga naar de nieuwe slide
        if (parent != null) parent.repaint();
    }



    /**
     * Toont een foutmelding in een dialoogvenster.
     * @param message De foutmelding die wordt weergegeven.
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(parent, message, "Fout", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Creëert een menu-item met een sneltoets.
     * @param name Naam van het menu-item.
     * @return Het menu-item.
     */
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
