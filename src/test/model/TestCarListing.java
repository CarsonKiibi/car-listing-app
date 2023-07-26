package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCarListing {
    private CarListing testListing0;
    private CarListing testListing1;

    @BeforeEach
    void runBefore() {
        testListing0 = new CarListing(0, "Ford", "Raptor", 2019, 1000, "missing steering wheel");
        testListing1 = new CarListing(1, "Toyota", "Camry", 2020, 5000, "burnt out headlights");
    }

    @Test
    void constructorTest() {
        assertEquals(1, testListing0.getId());
        assertEquals("Ford", testListing0.getMake());
        assertEquals("Raptor", testListing0.getModel());
        assertEquals(2019, testListing0.getYear());
        assertEquals(1000, testListing0.getMileage());
    }

}
