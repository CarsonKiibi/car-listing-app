package ui;

import model.CarListing;
import model.ListOfCarListing;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame {
    private ListOfCarListing locl;
    private CarListing cl;
    private int maxPrice;
    private int minPrice;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JPanel mainMenu;
    // buttons
    private JButton viewListingsButton;
    private JButton addListingButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;
    private JButton deleteButton;

    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JButton priceFilterButton;

    private JFrame currentListingFrame;

    private ArrayList<JButton> buttons;

    private JPanel addListing;

    private JPanel viewListings;

    private static final String JSON_STORE = "./data/carlistings.json";

    public GUI() {
        super("Car Listing App");
        setSize(new Dimension(800, 450));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel mainHeader = new JLabel("Auction App", JLabel.CENTER);
        minPrice = 0;
        maxPrice = 100000000;

        initializeMainMenu();
        mainMenu.add(mainHeader);
        initializeMainMenuButtons(mainMenu);
        listingButtonListeners();
        persistenceButtonListeners();
        
        setLocationRelativeTo(null);
    }

    public void initializeMainMenu() {
        mainMenu = new JPanel();
        add(mainMenu);

        locl = new ListOfCarListing("Default ListOfCarListings");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

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

    private void listingButtonListeners() {
        viewListingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrentListings();
            }
        });
        addListingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddListingDialog();
            }
        });
//        addListingButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                removeListing();
//            }
//        });
    }

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
                System.exit(0);
            }
        });
    }

    // Modify the showCurrentListings method
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

        handleListingFrame(listingsFrame, filterPanel, listingsPanel);
    }

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


    // Helper method to create a JPanel for a listing
    private JPanel createListingPanel(CarListing carListing) {
        JPanel listingPanel = new JPanel();
        listingPanel.setBorder(BorderFactory.createEtchedBorder());
        listingPanel.setLayout(new BoxLayout(listingPanel, BoxLayout.Y_AXIS));

        ImageIcon icon = new ImageIcon("./data/3143257.jpg");
        Image scaleImage = icon.getImage().getScaledInstance(362, 180, Image.SCALE_DEFAULT); // 362 180
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(scaleImage));

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

    private void handleRemoveButton(CarListing carListing, JPanel listingPanel) {
        JButton removeButton = new JButton("Remove Listing");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeListing(carListing);
                showCurrentListings();
            }
        });

        listingPanel.add(removeButton);
    }

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

    private void removeListing(CarListing carListing) {
        locl.removeListingFromList(carListing);
    }

    private void showAddListingDialog() {
        JFrame addListingFrame = createAddListingFrame();
        addListingFrame.setVisible(true);
    }

    private JFrame createAddListingFrame() {
        JFrame addListingFrame = new JFrame("Add Listing");
        addListingFrame.setLayout(new GridLayout(7, 2, 25, 25)); //?

        handleAddListingFields(addListingFrame);

        addListingFrame.pack();
        addListingFrame.setLocationRelativeTo(this);
        return addListingFrame;
    }

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
            }
        });
    }


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

    private void loadListings() {
        try {
            locl = jsonReader.read();
            System.out.println("Loaded " + locl.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void addCarToListing(CarListing carListing) {
        locl.addListingToList(carListing);
    }


}
