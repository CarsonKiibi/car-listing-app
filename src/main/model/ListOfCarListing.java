package model;

import org.json.JSONArray;
import org.json.JSONObject;
import ui.CarApp;

import java.util.ArrayList;
import java.util.List;

import persistence.Writable;

// Code related to Json and corresponding tests inspired or directly used from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// creates a new list of cars
public class ListOfCarListing implements Writable {
    private String name;
    private List<CarListing> listings;

    // MODIFIES: this
    // EFFECTS: initializes new empty list of car listings
    public ListOfCarListing(String name) {
        this.name = name;
        listings = new ArrayList<>();
    }

    public List<CarListing> getListings() {
        return this.listings;
    }

    // MODIFIES: this
    // EFFECTS: adds a given carListing to the current list of car listings
    public void addListingToList(CarListing carlisting) {
        listings.add(carlisting);
    }

    // EFFECTS: returns size of list of car listings
    public int getSize() {
        return listings.size();
    }

    public String getName() {
        return this.name;
    }

    // REQUIRES: carListings != null
    // EFFECTS: if list of car listings is empty, displays noListings() message and returns false,
    // otherwise displays each car listing and returns true
    public static boolean loopListOfCarListing(ListOfCarListing carListings) {
        if (carListings.getSize() == 0) {
            CarApp.noListings();
            return false;
        } else {
            for (CarListing carlisting : carListings.getListings()) {
                CarApp.displayListing(carlisting);
            }
            return true;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("carListings", listingsToJson());
        return json;
    }

    // EFFECTS: returns carListings in this listOfCarListings as a JSON array
    private JSONArray listingsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CarListing cl : listings) {
            jsonArray.put(cl.toJson());
        }

        return jsonArray;
    }


}
