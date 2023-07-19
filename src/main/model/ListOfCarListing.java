package model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

// creates a new list of cars
public class ListOfCarListing {

    private List<CarListing> listings;

    public ListOfCarListing() {
        listings = new ArrayList<>();
    }

    public List<CarListing> getListings() {
        return this.listings;
    }

    public void addListingToList(CarListing carlisting) {
        listings.add(carlisting);
    }

    public int getSize() {
        return listings.size();
    }
}
