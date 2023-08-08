package ui;

import model.CarListing;
import model.ListOfCarListing;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CarGUI extends JFrame {
    // TODO: refactor, add images???
    private ListOfCarListing listOfCarListing;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel mainMenu;

    private JButton viewButton;
    private JButton addButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;

    private static final String JSON_STORE = "./data/carlistings.json";

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public CarGUI() throws IOException {
        //  SwingUtilities.invokeLater(() -> {
        //       try {
        initializeUI();
//            } catch (IOException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Error initalizing UI" + e.getMessage(),
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
    }

    private void initializeUI() throws IOException {
        setTitle("Car Listing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 450));

        setupMain();
        JLabel mainLabel = new JLabel("Car Listing App");
        mainMenu.add(mainLabel);
        initializeButtons();
        addButtons(mainMenu);

        listingButtonListeners();
        persistenceButtonListeners();

        setLocationRelativeTo(null);
        mainMenu.setVisible(true);
    }

    private void setupMain() throws IOException {
        mainMenu = new JPanel();
        add(mainMenu);
        mainMenu.setLayout(null);

        //BufferedImage titleImage = ImageIO.read(new File("./data/3143257.jpg"));
        //JLabel picLabel = new JLabel(new ImageIcon(titleImage));

        listOfCarListing = new ListOfCarListing("Default ListOfCarListings");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void initializeButtons() {
        viewButton = new JButton("View Listings");
        addButton = new JButton("Add Listing");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");
    }

    private void listingButtonListeners() {
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrentListings();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddListingDialog();
            }
        });
    }

    private void persistenceButtonListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveListings();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadListings();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void addButtons(JPanel panel) {
        //add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(viewButton);
        //add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addButton);
        //add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(saveButton);
        //add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loadButton);
        //add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(exitButton);
    }

    private void showCurrentListings() {
        JFrame listingsFrame = new JFrame("Current Listings");
        listingsFrame.setLayout(new BorderLayout());

        JTextArea listingsTextArea = new JTextArea();
        listingsTextArea.setEditable(false);

        for (CarListing carListing : listOfCarListing.getListings()) {
            listingsTextArea.append(carListing.toString() + "\n");
        }

        listingsFrame.add(new JScrollPane(listingsTextArea), BorderLayout.CENTER);
        listingsFrame.pack();
        listingsFrame.setLocationRelativeTo(this);
        listingsFrame.setVisible(true);
    }

    private JFrame createAddListingFrame() {
        JFrame addListingFrame = new JFrame("Add Listing");
        addListingFrame.setLayout(new GridLayout(7, 2, 25, 25));

        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField mileageField = new JTextField();
        JTextField descField = new JTextField();

        addListingFrame.add(new JLabel("Make:"));
        addListingFrame.add(makeField);
        addListingFrame.add(new JLabel("Model:"));
        addListingFrame.add(modelField);
        addListingFrame.add(new JLabel("Year:"));
        addListingFrame.add(yearField);
        addListingFrame.add(new JLabel("Mileage:"));
        addListingFrame.add(mileageField);
        addListingFrame.add(new JLabel("Description:"));
        addListingFrame.add(descField);

        JButton addButton = new JButton("Add");
        addListingButtonAction(addListingFrame, makeField, modelField, yearField, mileageField, descField, addButton);
        addListingFrame.add(new JLabel());
        addListingFrame.add(addButton);

        addListingFrame.pack();
        addListingFrame.setLocationRelativeTo(this);
        return addListingFrame;
    }

    private void showAddListingDialog() {
        JFrame addListingFrame = createAddListingFrame();
        addListingFrame.setVisible(true);
    }

    private void addListingButtonAction(JFrame addListingFrame, JTextField makeField, JTextField modelField,
                                        JTextField yearField, JTextField mileageField, JTextField descField,
                                        JButton addButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.parseInt(yearField.getText());
                int mileage = Integer.parseInt(mileageField.getText());
                CarListing newListing = new CarListing(0, makeField.getText(), modelField.getText(),
                        year, mileage, descField.getText());

                addCarToListing(newListing);
                addListingFrame.dispose();
            }
        });
    }

    private void saveListings() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfCarListing);
            jsonWriter.close();
            System.out.println("Saved " + listOfCarListing.getName() + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadListings() {
        try {
            listOfCarListing = jsonReader.read();
            System.out.println("Loaded " + listOfCarListing.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void addCarToListing(CarListing carListing) {
        listOfCarListing.addListingToList(carListing);
    }
}
