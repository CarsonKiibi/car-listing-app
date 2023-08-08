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
        testListing0 = new CarListing(0, "Ford", "Raptor", 2019, 1000, 5000, "perfect!");
        testListing1 = new CarListing(1, "Toyota", "Camry", 2020, 5000, 10000, "no engine included");
        testListing1Clone = new CarListing(1, "Toyota", "Camry", 2020, 5000, 10000, "terrible car");
        testListings = new ListOfCarListing("Listing1");
        testListings.setMinPrice(5000);
        testListings.setMaxPrice(9999);

    }

    @Test
    void addListingToListOneTest() {
        assertEquals(0, testListings.getSize());
        testListings.addListingToList(testListing0);
        assertEquals(1, testListings.getSize());
        List<CarListing> testList2 = new ArrayList<>();
        testList2.add(testListing0);
        assertEquals(testList2, testListings.getListings());
    }

    @Test
    void addListingToListTwoDiffTest() {
        assertEquals(0, testListings.getSize());
        testListings.addListingToList(testListing0);
        testListings.addListingToList(testListing1);
        assertEquals(2, testListings.getSize());
        assertNotEquals(testListing0.getModel(), testListing1.getModel());
        assertNotEquals(testListing0.getMileage(), testListing1.getMileage());
        assertNotEquals(testListing0.getYear(), testListing1.getYear());
    }

    @Test
    void addListingToListTwoSameTest() {
        testListings.addListingToList(testListing1);
        testListings.addListingToList(testListing1Clone);
        assertEquals(2, testListings.getSize());
        assertEquals(testListing1.getMake(), testListing1Clone.getMake());
        assertNotEquals(testListing1.getId(), testListing1Clone.getId());
    }

    @Test
    void testLoopOverMax() {
        assertFalse(ListOfCarListing.loopListOfCarListing(testListings));
        testListings.addListingToList(testListing0);
        assertTrue(ListOfCarListing.loopListOfCarListing(testListings));
        testListings.addListingToList(testListing1);
        assertFalse(ListOfCarListing.loopListOfCarListing(testListings));
    }

    @Test
    void testLoopUnderMin() {
        testListings.setMinPrice(9999);
        testListings.setMaxPrice(10001);
        testListings.addListingToList(testListing0);
        assertFalse(ListOfCarListing.loopListOfCarListing(testListings));
    }

    @Test
    void testRemoveListingFirst() {
        testListings.addListingToList(testListing0);
        testListings.addListingToList(testListing1);
        assertEquals(2, testListings.getSize());
        testListings.removeListingFromList(testListing0);
        assertTrue(testListings.getListings().contains(testListing1));
        assertFalse(testListings.getListings().contains(testListing0));
    }

    @Test
    void testCantRemove() {
        testListings.addListingToList(testListing1);
        testListings.removeListingFromList(testListing0);
        assertEquals(1, testListings.getSize());
    }

}
