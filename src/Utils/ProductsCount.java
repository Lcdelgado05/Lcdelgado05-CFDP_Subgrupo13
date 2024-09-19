package Utils;

import java.util.Random;

/**
 * Provides random product-related data.
 */
public class ProductsCount {

    private static final Random RANDOM = new Random();

    // Array of product names
    private static final String[] PRODUCT_NAMES = {
        "libra_lentejas", "bolsa_yogurt", "pan_baguette", "botella_aceite",
        "cereal_desayuno", "paquete_arroz", "tarro_mermelada", "bote_miel",
        "docena_huevos", "paquete_pasta", "lata_tomate", "botella_vinagre",
        "papa_kg", "zanahoria_kg", "cebolla_kg", "aceite_soja", "azucar_kg",
        "sal_kg", "harina_kg", "cacao_en_polvo"
    };

    // Array of product prices
    private static final int[] PRODUCT_PRICES = {
        28000, 32500, 58000, 42000, 39000, 15000, 18000, 22000,
        13000, 20000, 25000, 30000, 17000, 22000, 20000, 19000,
        24000, 14000, 21000, 23000
    };

    /**
     * Returns a random product name from the predefined list.
     *
     * @return A randomly selected product name.
     */
    public static String getRandomProductName() {
        return PRODUCT_NAMES[RANDOM.nextInt(PRODUCT_NAMES.length)];
    }

    /**
     * Returns a random product price from the predefined list.
     *
     * @return A randomly selected product price.
     */
    public static int getRandomProductPrice() {
        return PRODUCT_PRICES[RANDOM.nextInt(PRODUCT_PRICES.length)];
    }
}





