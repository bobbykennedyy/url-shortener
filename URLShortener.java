import java.io.*;
import java.util.*;
import java.net.URL;

/*
 *  Author: Bobby Kennedy
 *  Description: 
 *  The URL shortner is a java based project that takes long URL's and generates short URL's for the user.
 *  It supports shortening, redirection, and preseistent storage. This makes it easy to share and track links.
 *  It also allows the user to create their own custom short URL's. Its designed to be fast and easy to integrate. 
 *  GitHub: https://github.com/bobbykennedyy/url-shortener.git

 */
/*
 * Class for the URL shortner methods
 */
public class URLShortener {
    public static final String BASE_URL = "http://short.ly/"; // Base URL for the short URL
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789!@#$%^&*()"; // Charachters
                                                                                                                       // for
                                                                                                                       // the
                                                                                                                       // shortURL
    public static final int URL_LENGTH = 5; // Standard short URL length
    public static final String FILE_NAME_FORWARD = "Hashmap_forward.ser"; // Forward Map file name
    public static final String FILE_NAME_REVERSE = "Hashmap_reverse.ser"; // Reverse Map file name
    public static HashMap<String, String> forwardMap = openHashMap(FILE_NAME_FORWARD); // Universal Forward map
    public static HashMap<String, String> reverseMap = openHashMap(FILE_NAME_REVERSE); // Universal Reverse Map

    /*
     * Takes a URL as input and saves it to a variable to be returned
     */
    public static URL URLInput() {
        Scanner input = new Scanner(System.in); // Creates a scanner for input
        try {
            System.out.print("Please enter a URL: ");
            String sample = input.nextLine();
            return new URL(sample); // Returns the URL
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Converts a Long URL to a short one and stores it in a hash table
     */
    public static void LongToShort() {
        System.out.println("Type in Exit to terminate");
        while (true) {
            try {
                String shortURL = shortURLGenerator(); // Generates a new short URL
                String originalURL = URLInput().toString(); // Generates a longer URL
                if (shortURL == "Exit") { // Breaks if the user inputs Exit
                    break;
                }
                if (reverseMap.containsKey(originalURL)) // Continues if its already been added
                {
                    System.out.println("URL already added");
                    continue;
                }
                while (forwardMap.containsKey(shortURL)) // Makes sure the short URL is unique
                {
                    shortURL = shortURLGenerator();
                }
                // Stores the values in the maps
                forwardMap.put(shortURL, originalURL);
                reverseMap.put(originalURL, shortURL);
            } catch (Exception e) {
                break; // Breaks if there is an invalid URL
            }
        }
        // Saves the maps into the files
        saveHashMap(forwardMap, FILE_NAME_FORWARD);
        saveHashMap(reverseMap, FILE_NAME_REVERSE);

    }

    /*
     * Returns the long URL from a short URL
     */
    public static String longURLLookup(String ShortURL) {
        if (ShortURL == null)
            return "Invalid URL";
        else {
            HashMap<String, String> reverse = openHashMap(FILE_NAME_REVERSE); // Looks in the reverse map to get the
                                                                              // original URL
            String URL = reverse.get(ShortURL);
            return URL;
        }
    }

    /*
     * Gets the ShortURL from a Long URL
     */
    public static String shortURLLookup(String LongURL) {
        if (LongURL == null)
            return "Invalid URL";
        else {
            HashMap<String, String> forward = openHashMap(FILE_NAME_FORWARD); // Looks in the forward map for the
                                                                              // original URL
            String URL = forward.get(LongURL); // Returns the short URL
            return URL;
        }

    }

    /*
     * Generates a short URL
     */
    private static String shortURLGenerator() {
        StringBuilder shortURL = new StringBuilder(); // Creates a new String builder
        shortURL.append(BASE_URL); // Adds the base URL
        for (int i = 0; i < URL_LENGTH; i++) {
            shortURL.append(CHARACTERS.charAt((int) (Math.random() * CHARACTERS.length()))); // Adds in random
                                                                                             // characters to the end of
                                                                                             // the base URL
        }
        return shortURL.toString(); // Returns the new URL
    }

    /*
     * Saves the hashmap to be used later
     */
    public static void saveHashMap(HashMap<String, String> map, String filename) {
        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(filename)); // Creates a new object to
                                                                                             // store the hashmap
            obj.writeObject(map); // Writes the map to the file
            obj.close();
        } catch (Exception e) {
            System.out.println("Exception thrown: " + e);
        }
    }

    /*
     * Opens the saved hashmap
     */
    public static HashMap<String, String> openHashMap(String filename) {

        try {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(filename)); // Creates a new object to
                                                                                          // open the file
            HashMap<String, String> map = (HashMap<String, String>) obj.readObject(); // Stores the file in a hashmap
            obj.close();
            return map;
        } catch (Exception e) {
            System.out.println("Exception Thrown: " + e); // Returns the exception and null
            return null;
        }
    }

    /*
     * Class for running the project
     */
    public static class Test {
        public static void main(String[] args) {
            while (true) {
                System.out.println("\nWelcome to the URL Shortener Program");
                System.out.println("Please enter the number corresponding to the aciton");
                System.out.println("1. Generate a short URL");
                System.out.println("2. Store a URL");
                System.out.println("3. Print out the hashmap");
                System.out.println("4. Get an original URL from a short URL");
                System.out.println("5. Get a short URL from an original URL");
                System.out.println("6. Clear the hashmap");
                System.out.println("7. Exit the program");
                Scanner scan = new Scanner(System.in);

                int choice = scan.nextInt();
                scan.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("\nHere is an example short URL: " + shortURLGenerator());
                        break;
                    case 2:
                        LongToShort();
                        break;
                    case 3:
                        HashMap<String, String> map = openHashMap(FILE_NAME_FORWARD);
                        System.out.println("Loaded HashMap:" + map);
                        break;
                    case 4:
                        System.out.println("Please provide the short URL");
                        String shortURL = scan.nextLine();
                        System.out.println("Original URL: " + shortURLLookup(shortURL));

                        break;
                    case 5:
                        System.out.println("Please provide the original URL");
                        String originalURL = scan.nextLine();
                        System.out.println("Short URL: " + longURLLookup(originalURL));
                        break;
                    case 6:
                        HashMap<String, String> map1 = openHashMap(FILE_NAME_FORWARD);
                        HashMap<String, String> map2 = openHashMap(FILE_NAME_REVERSE);
                        map1.clear();
                        map2.clear();
                        saveHashMap(map1, FILE_NAME_FORWARD);
                        saveHashMap(map2, FILE_NAME_REVERSE);
                        System.out.println("Hashmap cleared");
                        break;
                    case 7:
                        System.out.println("Exiting program"); {
                        scan.close();
                        return;
                    }
                    default:
                        System.out.println("Not a valid input, please try again");
                }

            }
        }
    }
}