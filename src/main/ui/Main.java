package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

// Code related to Json and corresponding tests inspired or directly used from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


public class Main {
    public static void main(String[] args) throws IOException {
        /*try {
            new CarApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        } */
        new GUI();
    }
}
