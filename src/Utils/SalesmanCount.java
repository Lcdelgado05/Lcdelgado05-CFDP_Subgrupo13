package Utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Provides utility methods for generating random salesman data.
 */
public class SalesmanCount {

    public static final Random RANDOM = new Random();

    private static final String[] DOCUMENT_TYPES = { "CC", "CE", "TI" };
    private static final String[] FIRST_NAMES = { "John", "Jane", "Alex", "Emily", "Michael", "Sophia", "James", "Olivia", "Liam", "Isabella" };
    private static final String[] LAST_NAMES = { "Doe", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez" };

    private static Set<String> usedNames = new HashSet<>();

    public static String getRandomDocumentType() {
        return DOCUMENT_TYPES[RANDOM.nextInt(DOCUMENT_TYPES.length)];
    }

    public static String generateRandomDocumentNumber() {
        return String.format("%08d", RANDOM.nextInt(100000000));
    }

    public static String getRandomFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    public static String getRandomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    /**
     * Generates a unique first and last name combination.
     *
     * @return An array with the first and last name.
     */
    public static String[] generateUniqueName() {
        String firstName, lastName;
        String nameCombination;
        do {
            firstName = getRandomFirstName();
            lastName = getRandomLastName();
            nameCombination = firstName + " " + lastName;
        } while (usedNames.contains(nameCombination));
        usedNames.add(nameCombination);
        return new String[] { firstName, lastName };
    }
}



