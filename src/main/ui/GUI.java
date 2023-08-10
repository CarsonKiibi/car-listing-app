package ui;

import model.CarListing;
import model.Event;
import model.EventLog;
import model.ListOfCarListing;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements WindowListener {
    private ListOfCarListing locl;
    private CarListing cl;
    private int maxPrice;
    private int minPrice;
    private final int resetMaxPrice = 100000000;
    private final int resetMinPrice = 0;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JPanel mainMenu;
    // buttons
    private JButton viewListingsButton;
    private JButton addListingButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;
    private JButton removeButton;

    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JButton priceFilterButton;

    private JFrame currentListingFrame;

    private ArrayList<JButton> buttons;

    private JPanel addListing;

    private JPanel viewListings;

    private static final String JSON_STORE = "./data/carlistings.json";

    // EFFECTS: constructs a GUI with title, size, defaultCloseOperation
    // sets max and min price to include all reasonable numbers
    // adds button listeners, other menu components
    public GUI() {
        super("Car Listing App");
        setSize(new Dimension(800, 450));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        minPrice = 0;
        maxPrice = 100000000;

        JLabel addBanner = handleBanner("./data/carListingBanner.png", 780, 100);
        initializeMainMenu();

        mainMenu.add(addBanner);
        initializeMainMenuButtons(mainMenu);
        listingButtonListeners();
        persistenceButtonListeners();
        addWindowListener(this);

        setLocationRelativeTo(null);
    }

    // REQUIRES: valid image file, positive integer width and height
    // EFFECTS: creates properly scaled JLabel banner
    private JLabel handleBanner(String filename, int width, int height) {
        ImageIcon banner = new ImageIcon(filename);
        Image scaledBanner = banner.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        JLabel addBanner = new JLabel();
        addBanner.setIcon(new ImageIcon(scaledBanner));
        return addBanner;
    }

    // MODIFIES: this
    // EFFECTS: initializes main menu, JsonReader/Writer, ListOfCarListing
    public void initializeMainMenu() {
        mainMenu = new JPanel();
        add(mainMenu);

        locl = new ListOfCarListing("Default ListOfCarListings");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: panel
    // EFFECTS: initializes and adds menu buttons to panel
    public void initializeMainMenuButtons(JPanel panel) {
        buttons = new ArrayList<JButton>();
        viewListingsButton = new JButton("View Listings");
        buttons.add(viewListingsButton);
        addListingButton = new JButton("Add a Listing");
        buttons.add(addListingButton);
        saveButton = new JButton("Save to File");
        buttons.add(saveButton);
        loadButton = new JButton("Load from File");
        buttons.add(loadButton);
        exitButton = new JButton("Exit App");
        buttons.add(exitButton);

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(Color.darkGray);
            button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            button.setBackground(Color.GRAY);
            panel.add(button);
            setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up action listeners for viewListingsButton,
    // addListingButton
    private void listingButtonListeners() {
        viewListingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrentListings();
                locl.setMaxPrice(resetMaxPrice);
                locl.setMinPrice(resetMinPrice);
            }
        });
        addListingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddListingDialog();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up action listeners for saveButton,
    // loadButton, exitButton
    private void persistenceButtonListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveListings();
                JOptionPane.showMessageDialog(null, "Saved" + locl.getName() + " to " + JSON_STORE);
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadListings();
                JOptionPane.showMessageDialog(null, "Loaded " + locl.getName() + " from " + JSON_STORE);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logEvents();
                System.exit(0);
            }
        });
    }

    private void logEvents() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
            System.out.print("\n");
        }
    }

    // MODIFIES: currentListingFrame
    // EFFECTS: Displays current listings options,
    // handles price filtering
    private void showCurrentListings() {
        if (currentListingFrame != null) {
            currentListingFrame.setVisible(false);
        }

        JFrame listingsFrame = new JFrame("Current Listings");

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        handleFilter(filterPanel);

        JPanel listingsPanel = new JPanel();
        listingsPanel.setLayout(new GridLayout(0, 1, 20, 20));

        for (CarListing carListing : locl.getListings()) {
            if (carListing.getPrice() <= locl.getMaxPrice() && carListing.getPrice() >= locl.getMinPrice()) {
                JPanel listingPanel = createListingPanel(carListing);
                listingsPanel.add(listingPanel);
            }
        }
        locl.loopListOfCarListing(locl);

        handleListingFrame(listingsFrame, filterPanel, listingsPanel);
    }

    // MODIFIES: currentListingFrame
    // EFFECTS: handles display of listing frame with filterPanel, listingsPanel
    private void handleListingFrame(JFrame listingsFrame, JPanel filterPanel, JPanel listingsPanel) {
        JScrollPane scrollPane = new JScrollPane(listingsPanel);
        listingsFrame.add(scrollPane, BorderLayout.CENTER);
        currentListingFrame = listingsFrame;
        listingsFrame.setSize(400, 800);
        listingsFrame.add(filterPanel, BorderLayout.NORTH);
        listingsFrame.add(scrollPane, BorderLayout.CENTER);
        listingsFrame.setLocationRelativeTo(null);
        listingsFrame.setVisible(true);
    }

    // MODIFIES: locl
    // EFFECTS: Initializes filter components, adds pricing fields
    private void handleFilter(JPanel filterPanel) {
        minPriceField = new JTextField(8);
        maxPriceField = new JTextField(8);
        priceFilterButton = new JButton("Filter");

        priceFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPriceFields();
            }
        });

        filterPanel.add(new JLabel("Min Price: "));
        filterPanel.add(minPriceField);
        filterPanel.add(new JLabel("Max Price: "));
        filterPanel.add(maxPriceField);
        filterPanel.add(priceFilterButton);
    }

    // MODIFIES: locl
    // EFFECTS: sets max and min price fields and updates currentListings
    public void setPriceFields() {
        try {
            int max = Integer.parseInt(maxPriceField.getText());
            locl.setMaxPrice(max);
            int min = Integer.parseInt(minPriceField.getText());
            locl.setMinPrice(min);
            showCurrentListings();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter valid fields");
        }
    }


    // EFFECTS: create listing panel with listing info, adds image
    private JPanel createListingPanel(CarListing carListing) {
        JPanel listingPanel = new JPanel();
        listingPanel.setBorder(BorderFactory.createEtchedBorder());
        listingPanel.setLayout(new BoxLayout(listingPanel, BoxLayout.Y_AXIS));

        JLabel imageLabel = handleBanner("./data/3143257.jpg", 362, 180);

        JLabel makeLabel = new JLabel("Make: " + carListing.getMake());
        JLabel modelLabel = new JLabel("Model: " + carListing.getModel());
        JLabel yearLabel = new JLabel("Year: " + carListing.getYear());
        JLabel mileageLabel = new JLabel("Mileage: " + carListing.getMileage());
        JLabel priceLabel = new JLabel("Price: " + carListing.getPrice());
        JLabel descLabel = new JLabel("Description: " + carListing.getDesc());

        addLabelsToListing(listingPanel, imageLabel, makeLabel, modelLabel,
                yearLabel, mileageLabel, priceLabel, descLabel);

        handleRemoveButton(carListing, listingPanel);
        return listingPanel;
    }

    // MODIFIES: listingPanel
    // EFFECTS: creates remove listing button and handles its functionality
    private void handleRemoveButton(CarListing carListing, JPanel listingPanel) {
        removeButton = new JButton("Remove Listing");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeListing(carListing);
                showCurrentListings();
            }
        });

        listingPanel.add(removeButton);
    }

    // MODIFIES: listingPanel
    // EFFECTS: adds all car info to listingPanel by taking in required fields
    private void addLabelsToListing(JPanel listingPanel, JLabel imageLabel, JLabel makeLabel, JLabel modelLabel,
                                    JLabel yearLabel, JLabel mileageLabel, JLabel priceLabel, JLabel descLabel) {
        listingPanel.add(imageLabel);
        listingPanel.add(makeLabel);
        listingPanel.add(modelLabel);
        listingPanel.add(yearLabel);
        listingPanel.add(mileageLabel);
        listingPanel.add(priceLabel);
        listingPanel.add(descLabel);
    }

    // MODIFIES: locl
    // EFFECTS: removes specified car listing from locl
    private void removeListing(CarListing carListing) {
        locl.removeListingFromList(carListing);
    }

    // EFFECTS: displays info for adding new listing
    private void showAddListingDialog() {
        JFrame addListingFrame = createAddListingFrame();
        addListingFrame.setVisible(true);
    }

    // EFFECTS: creates listing frame and calls for fields to be added
    private JFrame createAddListingFrame() {
        JFrame addListingFrame = new JFrame("Add Listing");
        addListingFrame.setLayout(new GridLayout(7, 2, 25, 25)); //?

        handleAddListingFields(addListingFrame);

        addListingFrame.pack();
        addListingFrame.setLocationRelativeTo(this);
        return addListingFrame;
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds fields for entering car info
    private void handleAddListingFields(JFrame addListingFrame) {
        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField mileageField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descField = new JTextField();

        addListingFrame.add(new JLabel("Make:"));
        addListingFrame.add(makeField);
        addListingFrame.add(new JLabel("Model:"));
        addListingFrame.add(modelField);
        addListingFrame.add(new JLabel("Year:"));
        addListingFrame.add(yearField);
        addListingFrame.add(new JLabel("Mileage:"));
        addListingFrame.add(mileageField);
        addListingFrame.add(new JLabel("Price: "));
        addListingFrame.add(priceField);
        addListingFrame.add(new JLabel("Description:"));
        addListingFrame.add(descField);

        JButton addButton = new JButton("Add");
        addListingButtonAction(addListingFrame, makeField, modelField, yearField,
                mileageField, priceField, descField, addButton);
        addListingFrame.add(new JLabel());
        addListingFrame.add(addButton);
    }

    // MODIFIES: locl
    // EFFECTS: adds fields for new car listing if user input is valid, closes add panel
    private void addListingButtonAction(JFrame addListingFrame, JTextField makeField, JTextField modelField,
                                        JTextField yearField, JTextField mileageField, JTextField priceField,
                                        JTextField descField,
                                        JButton addButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int year = Integer.parseInt(yearField.getText());
                    int mileage = Integer.parseInt(mileageField.getText());
                    int price = Integer.parseInt(priceField.getText());
                    CarListing newListing = new CarListing(0, makeField.getText(), modelField.getText(),
                            year, mileage, price, descField.getText());

                    addCarToListing(newListing);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Please enter fields correctly");
                }
                addListingFrame.setVisible(false);
            }
        });
    }

    // MODIFIES: JSON_STORE
    // EFFECTS: Saves car listings to JSON file
    private void saveListings() {
        try {
            jsonWriter.open();
            jsonWriter.write(locl);
            jsonWriter.close();
            System.out.println("Saved " + locl.getName() + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: locl, JSON_STORE
    // EFFECTS: Loads car listings from JSON file
    private void loadListings() {
        try {
            locl = jsonReader.read();
            System.out.println("Loaded " + locl.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: locl
    // EFFECTS: Adds given car listing to list
    public void addCarToListing(CarListing carListing) {
        locl.addListingToList(carListing);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        logEvents();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
