package ui;

import model.CarListing;
import model.ListOfCarListing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private ListOfCarListing locl;
    private CarListing cl;

    private JPanel mainMenu;
    // buttons
    private JButton viewListingsButton;
    private JButton addListingButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;

    private ArrayList<JButton> buttons;

    private JPanel addListing;

    private JPanel viewListings;

    public GUI() {
        super("Car Listing App");
        setSize(new Dimension(800, 450));
        JLabel mainHeader = new JLabel("Auction App", JLabel.CENTER);
        initializeMainMenu();
        mainMenu.add(mainHeader);
        initializeMainMenuButtons(mainMenu);
    }

    public void initializeMainMenu() {
        mainMenu = new JPanel();
        add(mainMenu);
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
            button.setFont(new Font("Helvetica", Font.BOLD, 14));
            button.setBackground(Color.darkGray);
            button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            button.setBackground(Color.GRAY);
            panel.add(button);
            setVisible(true);
        }
    }


}
