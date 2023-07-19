package ui;

import model.CarListing;
import model.ListOfCarListing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCarApp {
    private CarListing testListing0;
    private CarListing testListing1;
    private CarListing testListing1Clone;
    private ListOfCarListing testListings;
    private CarApp testCarApp;

    @BeforeEach
    void runBefore() {
        testListing0 = new CarListing(0, "Ford", "Raptor", 2019, 1000);
        testListing1 = new CarListing(1, "Toyota", "Camry", 2020, 5000);
        testListing1Clone = new CarListing(1, "Toyota", "Camry", 2020, 5000);
        testListings = new ListOfCarListing();
        testCarApp = new CarApp();
    }

    @Test
    void testDisplayViewListings() {
        String command = "v";
        testCarApp.processMenuCommand(command);
        assertFalse(testCarApp.displayViewListings());
        testListings.addListingToList(testListing0);
        assertTrue(testCarApp.displayViewListings());
    }
}
