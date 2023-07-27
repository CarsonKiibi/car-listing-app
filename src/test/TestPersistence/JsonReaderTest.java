package TestPersistence;

import model.CarListing;
import model.ListOfCarListing;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("/data/doesNotExist.json");
        try {
            ListOfCarListing locl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoListings() {
        JsonReader reader = new JsonReader("./data/testReaderNoListings.json");
        try {
            ListOfCarListing locl = reader.read();
            assertEquals("Test Car Listings", locl.getName());
            assertEquals(0, locl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderBasicListings() {
        JsonReader reader = new JsonReader("./data/testReaderBasicListings.json");
        try {
            ListOfCarListing locl = reader.read();
            assertEquals("Test Car Listings", locl.getName());
            List<CarListing> listOfCarListings = locl.getListings();
            assertEquals(2, listOfCarListings.size());
            checkListing(1, "Ford", "Explorer", 2000, 123123, "Great!", listOfCarListings.get(0));
            checkListing(2, "Toyota", "Camry", 2005, 234234, "Okay.", listOfCarListings.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
