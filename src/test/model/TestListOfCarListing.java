package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestListOfCarListing {
    private CarListing testListing0;
    private CarListing testListing1;
    private CarListing testListing1Clone;
    private List<CarListing> listings;

    @BeforeEach
    void runBefore() {
        testListing0 = new CarListing(0, "Ford", "Raptor", 2019, 1000);
        testListing1 = new CarListing(1, "Toyota", "Camry", 2020, 5000);
        testListing1Clone = new CarListing(1, "Toyota", "Camry", 2020, 5000);
        listings = new ArrayList<>();
    }

    @Test
    void testAddListingToListOne() {
        assertEquals(0, listings.size());
        listings.add(testListing0);
        assertEquals(1, listings.size());
    }

    @Test
    void testAddListingToListTwoDiff() {
        assertEquals(0, listings.size());
        listings.add(testListing0);
        listings.add(testListing1);
        assertEquals(2, listings.size());
        assertNotEquals(testListing0.getModel(), testListing1.getModel());
    }

    @Test
    void testAddListingToListTwoSame() {
        listings.add(testListing1);
        listings.add(testListing1Clone);
        assertEquals(2, listings.size());
        assertEquals(testListing1.getMake(), testListing1Clone.getMake());
        assertNotEquals(testListing1.getId(), testListing1Clone.getId());
    }

}
