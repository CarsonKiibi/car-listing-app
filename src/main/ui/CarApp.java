package ui;

import model.CarListing;
import model.ListOfCarListing;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// REFERENCE //
// this project uses reference to "TellerApp" provided on edx.
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// CarApp(), runCarApp(), displayMainMenu(), and processMenuCommand() are
// either inspired by or almost directly implement parts of TellerApp.

// Code related to Json and corresponding tests inspired or directly used from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Car Listing Application
public class CarApp extends JFrame {
    private Scanner input;

    private String addMake = null;
    private String addModel = null;
    private int addYear = 0;
    private int addMileage = 0;
    private int addPrice = 0;
    private String addDesc = null;
    //private ImageIcon addImage = null;

    private CarListing listing;
    private ListOfCarListing listOfCarListing;

    private static final String JSON_STORE = "./data/carlistings.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: initializes new CarApp and creates ListOfCarListing
    public CarApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        listOfCarListing = new ListOfCarListing("Default ListOfCarListings");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCarApp();
    }

    // MODIFIES: this
    // EFFECTS: starts the car app and processes user input
    private void runCarApp() {
        boolean doContinue = true;
        String command = null;
        input = new Scanner(System.in);

        while (doContinue) {
            displayMainMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                doContinue = false;
            } else {
                processMenuCommand(command);
            }
        }

        System.out.println("Quitting Application");
    }

    // EFFECTS: displays menu of options to user
    private void displayMainMenu() {
        System.out.println("\nWould you like to:");
        System.out.println("\tPress (v) View Current Listings");
        System.out.println("\tPress (a) Add Listing");
        System.out.println("\tPress (s) Save Listings to File");
        System.out.println("\tPress (l) Load Listings from File");
        System.out.println("\tPress (q) Quit Application");
    }

    // MODIFIES: this
    // EFFECTS: processes user's menu command
    public void processMenuCommand(String command) {
        command.toLowerCase();
        if (command.equals("v")) {
            displayViewListings();
        } else if (command.equals("a")) {
            addListingSequence();
        } else if (command.equals("s")) {
            saveListings();
        } else if (command.equals("l")) {
            loadListings();
        } else {
            System.out.println("Invalid Selection");
        }
    }

    // EFFECTS: displays all currently added listings to ListOfCarListing
    public void displayViewListings() {
        System.out.println("\tCurrently Posted Listings:");
        List<CarListing> carListings = listOfCarListing.getListings();
        ListOfCarListing.loopListOfCarListing(listOfCarListing);
    }

    // EFFECTS: outputs message in case of no listings
    public static void noListings() {
        System.out.println("There are currently no listings");
    }

    // EFFECTS: displays details of a car listing
    public static void displayListing(CarListing carlisting) {
        System.out.print("\t Make: " + carlisting.getMake());
        System.out.print("\t Model: " + carlisting.getModel());
        System.out.print("\t Year: " + carlisting.getYear());
        System.out.print("\t Mileage: " + carlisting.getMileage());
        System.out.print("\t ID: " + carlisting.getId());
        System.out.print("\t Price: " + carlisting.getPrice());
        System.out.print("\t Extra information: " + carlisting.getDesc());
        System.out.println("\n");
    }

    // MODIFIES: this
    // EFFECTS: sequence of prompts to get listing details, creates new listing
    // based of those details, adds it to list of car listings
    public void addListingSequence() {
        System.out.println("Enter a make: ");
        addMake = input.nextLine();
//        input.nextLine();
        System.out.println("Enter a model: ");
        addModel = input.nextLine();
        System.out.println("Enter model year: ");
        addYear = Integer.parseInt(input.nextLine());
        System.out.println("Enter mileage: ");
        addMileage = Integer.parseInt(input.nextLine());
        System.out.println("Add a price: ");
        addPrice = Integer.parseInt(input.nextLine());
        System.out.println("Enter any other information about your vehicle: ");
        addDesc = input.nextLine();
//        input.nextLine();
        listing = new CarListing(0, addMake, addModel, addYear, addMileage, addPrice, addDesc); // addImage?
        addCarToListing(listing);
        System.out.println("Adding car listing!");
    }

    // MODIFIES: this
    // EFFECTS: adds given car listing to ListOfCarListing
    public void addCarToListing(CarListing carListing) {
        listOfCarListing.addListingToList(carListing);
    }

    // EFFECTS: saves listings to file
    private void saveListings() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfCarListing);
            jsonWriter.close();
            System.out.println("Saved" + listOfCarListing.getName() + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads listings from file
    private void loadListings() {
        try {
            listOfCarListing = jsonReader.read();
            System.out.print("Loaded " + listOfCarListing.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
