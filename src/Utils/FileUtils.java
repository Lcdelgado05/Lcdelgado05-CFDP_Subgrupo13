package Utils;

import java.io.File;
import java.util.Random;

public class FileUtils {

    public static final Random RANDOM = new Random();

    /**
     * Creates the specified folder if it does not exist.
     *
     * @param folder The folder to be created on the file system.
     */
    public static void createFolder(File folder) {
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Error creating folder: " + folder.getName());
        }
    }

    /**
     * Returns a random document type from the available options.
     *
     * @return A randomly selected document type as a String.
     */
    public static String getRandomDocumentType() {
        String[] documentTypes = {"CC", "CE", "TI"};
        return documentTypes[RANDOM.nextInt(documentTypes.length)];
    }

    /**
     * Generates a random document number consisting of 8 digits.
     *
     * @return A random 8-digit document number as a String.
     */
    public static String generateRandomDocumentNumber() {
        return String.format("%08d", RANDOM.nextInt(100000000));
    }

    /**
     * Returns a random first name from the predefined list of first names.
     *
     * @return A randomly selected first name.
     */
    public static String getRandomFirstName() {
        String[] firstNames = {"John", "Jane", "Alex", "Emily", "Michael"};
        return firstNames[RANDOM.nextInt(firstNames.length)];
    }

    /**
     * Returns a random last name from the predefined list of last names.
     *
     * @return A randomly selected last name.
     */
    public static String getRandomLastName() {
        String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Brown"};
        return lastNames[RANDOM.nextInt(lastNames.length)];
    }
}
