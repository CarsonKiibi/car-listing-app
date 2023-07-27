package TestPersistence;

import model.CarListing;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkListing(int id, String make, String model, int year, int mileage, String desc, CarListing carListing) {
        assertEquals(id, carListing.getId());
        assertEquals(make, carListing.getMake());
        assertEquals(model, carListing.getModel());
        assertEquals(year, carListing.getYear());
        assertEquals(mileage, carListing.getMileage());
        assertEquals(desc, carListing.getDesc());
    }
}
