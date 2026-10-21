package areabusiness;

import java.io.*;
import java.util.ArrayList;

public class DataManager {

    private static final String FILE_NAME = "business_data.dat";

    // Save businesses to file
    public static void saveData(ArrayList<Business> businesses) {

        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME));

            output.writeObject(businesses);
            output.close();

        } catch (IOException e) {

            System.out.println("Error saving data: "
                    + e.getMessage());
        }
    }

    // Load businesses from file
    public static ArrayList<Business> loadData() {

        try {
            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME));

            ArrayList<Business> businesses =
                    (ArrayList<Business>) input.readObject();

            input.close();

            return businesses;

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }
}