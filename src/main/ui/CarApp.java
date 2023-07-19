package ui;

import model.CarListing;
import model.ListOfCarListing;

import java.util.List;
import java.util.Scanner;

public class CarApp {
    private Scanner input;
    private String addMake = null;
    private String addModel = null;
    private int addYear = 0;
    private int addMileage = 0;
    private CarListing listing;
    private ListOfCarListing listOfCarListing;


    // EFFECTS: runs car app, creates new listofcarlisting
    public CarApp() {
        listOfCarListing = new ListOfCarListing();
        runCarApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCarApp() {
        boolean doContinue = true;
        String command = null;
        input = new Scanner(System.in);

        while (doContinue) {
            displayMainMenu();
            command = input.next();
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
        System.out.println("\tPress (q) Quit Application");
    }
    // MODIFIES: this
    // EFFECTS: processes user command

    public void processMenuCommand(String command) {
        if (command.equals("v")) {
            displayViewListings();
        } else if (command.equals("a")) {
            addListingSequence();
        }
    }

    // EFFECTS: displays all currently added listings to ListOfCarListing
    public boolean displayViewListings() {
        System.out.println("\tCurrently Posted Listings:");
        List<CarListing> carListings = listOfCarListing.getListings();
        if (carListings.size() == 0) {
            System.out.println("There are currently no listings");
            return false;
        } else {
            for (CarListing carlisting : carListings) {
                System.out.print("\t Make: " + carlisting.getMake());
                System.out.print("\t Model: " + carlisting.getModel());
                System.out.print("\t Year: " + carlisting.getYear());
                System.out.print("\t Mileage:" + carlisting.getMileage());
                System.out.print("\t ID: " + carlisting.getId());
                System.out.println();
            }
            return true;
        }
    }

    public void addListingSequence() {
        System.out.println("Enter a make: ");
        addMake = input.next();
        System.out.println("Enter a model: ");
        addModel = input.next();
        System.out.println("Enter model year: ");
        addYear = Integer.parseInt(input.next());
        System.out.println("Enter mileage: ");
        addMileage = Integer.parseInt(input.next());
        listing = new CarListing(0, addMake, addModel, addYear, addMileage);
        addCarToListing(listing);
        System.out.println("Adding car listing!");
    }

    public void addCarToListing(CarListing carListing) {
        listOfCarListing.addListingToList(carListing);
    }
}
