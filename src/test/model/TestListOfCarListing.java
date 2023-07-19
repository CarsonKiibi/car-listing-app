package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestListOfCarListing {
    private CarListing testListing0;
    private CarListing testListing1;
    private CarListing testListing1Clone;
    private ListOfCarListing testListings;

    @BeforeEach
    void runBefore() {
        testListing0 = new CarListing(0, "Ford", "Raptor", 2019, 1000);
        testListing1 = new CarListing(1, "Toyota", "Camry", 2020, 5000);
        testListing1Clone = new CarListing(1, "Toyota", "Camry", 2020, 5000);
        testListings = new ListOfCarListing();
    }

    @Test
    void testAddListingToListOne() {
        assertEquals(0, testListings.getSize());
        testListings.addListingToList(testListing0);
        assertEquals(1, testListings.getSize());
    }

    @Test
    void testAddListingToListTwoDiff() {
        assertEquals(0, testListings.getSize());
        testListings.addListingToList(testListing0);
        testListings.addListingToList(testListing1);
        assertEquals(2, testListings.getSize());
        assertNotEquals(testListing0.getModel(), testListing1.getModel());
    }

    @Test
    void testAddListingToListTwoSame() {
        testListings.addListingToList(testListing1);
        testListings.addListingToList(testListing1Clone);
        assertEquals(2, testListings.getSize());
        assertEquals(testListing1.getMake(), testListing1Clone.getMake());
        assertNotEquals(testListing1.getId(), testListing1Clone.getId());
    }

}
