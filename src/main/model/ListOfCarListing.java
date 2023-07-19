package model;

import ui.CarApp;

import java.util.ArrayList;
import java.util.List;

// creates a new list of cars
public class ListOfCarListing {

    private List<CarListing> listings;

    // MODIFIES: this
    // EFFECTS: initializes new empty list of car listings
    public ListOfCarListing() {
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


}
