package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarListing {
    private static int nextPostId = 1;
    private int id;
    private String make;
    private String model;
    private int year;
    private int mileage;

    /*List<String> possibleCars = Arrays.asList("toyota","ford","chevrolet","honda",
            "nissan","jeep","hyundai","kia","ram","subaru","gmc","volkswagen", "bmw",
            "mazda","mercedes-benz","lexus","tesla","dodge","audi","buick","acura",
            "volvo","cadillac","chrysler","mitsubishi","land rover","lincoln","porsche",
            "infiniti","genesis","mini","maserati","alfa romeo","jaguar","bentley",
            "ferrari","lamborghini","aston martin","polestar","fiat","rolls-royce",
            "mclaren","lucid","bugatti","lotus","pontiac","rimac"); */

    // creates a car listing with a post id, make, model, year, and mileage
    public CarListing(int id, String make, String model, int year, int mileage) {
        this.id = nextPostId;
        nextPostId++;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public int getId() {
        return this.id;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public int getYear() {
        return this.year;
    }

    public int getMileage() {
        return this.mileage;
    }
}
