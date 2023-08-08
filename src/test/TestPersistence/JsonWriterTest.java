package TestPersistence;

import model.CarListing;
import model.ListOfCarListing;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterIllegalFile() {
        try {
            ListOfCarListing locl = new ListOfCarListing("Test Car Listings");
            JsonWriter writer = new JsonWriter("./data/illegal\0file.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoListings() {
        try {
            ListOfCarListing locl = new ListOfCarListing("Test Car Listings");
            JsonWriter writer = new JsonWriter("./data/testWriterNoListings.json");
            writer.open();
            writer.write(locl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoListings.json");
            locl = reader.read();
            assertEquals("Test Car Listings", locl.getName());
            assertEquals(0, locl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterBasicListings() {
        try {
            ListOfCarListing locl = new ListOfCarListing("Test Car Listings");
            locl.addListingToList(new CarListing(1, "Tesla", "Model X", 2019, 5000, 100000, "Fast!"));
            locl.addListingToList(new CarListing(2, "Pagani", "Huayra", 2018, 2500, 1000000, "Pretty!"));
            JsonWriter writer = new JsonWriter("./data/testWriterBasicListings");
            writer.open();
            writer.write(locl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBasicListings");
            locl = reader.read();
            assertEquals("Test Car Listings", locl.getName());
            List<CarListing> listings = locl.getListings();
            assertEquals(2, listings.size());
            checkListing(3, "Tesla", "Model X", 2019, 5000, "Fast!", listings.get(0));
            checkListing(4, "Pagani", "Huayra", 2018, 2500, "Pretty!", listings.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
