package persistence;

import model.CarListing;
import model.ListOfCarListing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Code and corresponding tests inspired or directly used from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfCarListing from file and returns it
    // throws IOException if an error occurs reading data from file
    public ListOfCarListing read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfCarListing(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses listOfCarListing from JSON object and returns it
    private ListOfCarListing parseListOfCarListing(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ListOfCarListing locl = new ListOfCarListing(name);
        addListings(locl, jsonObject);
        return locl;
    }

    // MODIFIES: locl
    // EFFECTS: parses Listings from JSON objet and adds them to ListOfCarListings
    private void addListings(ListOfCarListing locl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("carListings");
        for (Object json : jsonArray) {
            JSONObject nextCarListing = (JSONObject) json;
            addListing(locl, nextCarListing);
        }
    }

    // MODIFIES: locl
    // EFFECTS: parses carListing from JSON object and adds it to workroom
    private void addListing(ListOfCarListing locl, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String make = jsonObject.getString("make");
        String model = jsonObject.getString("model");
        int year = jsonObject.getInt("year");
        int mileage = jsonObject.getInt("mileage");
        int price = jsonObject.getInt("price");
        String desc = jsonObject.getString("desc");
        CarListing carListing = new CarListing(id, make, model, year, mileage, price, desc);
        locl.addListingToList(carListing);
    }
}
