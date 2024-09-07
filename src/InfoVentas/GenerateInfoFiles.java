package InfoVentas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenerateInfoFiles {

    private static final String[] TIPOS_DOCUMENTO = {"CC", "CE", "TI"};
    private static final String[] NOMBRES = {"John", "Jane", "Alex", "Emily", "Michael"};
    private static final String[] APELLIDOS = {"Doe", "Smith", "Johnson", "Williams", "Brown"};

    public static void main(String[] args) {
        File carpetaPrincipal = new File("ProductosVendidos");
        File carpetaVentas = new File(carpetaPrincipal, "Ventas");
        File carpetaVendedores = new File(carpetaPrincipal, "Vendedores");
        File carpetaProductos = new File(carpetaPrincipal, "Productos");

        // Crear las carpetas si no existen
        if (!carpetaVentas.exists() && !carpetaVentas.mkdirs()) {
            System.err.println("Error al crear la carpeta de Ventas.");
            return;
        }
        if (!carpetaVendedores.exists() && !carpetaVendedores.mkdirs()) {
            System.err.println("Error al crear la carpeta de Vendedores.");
            return;
        }
        if (!carpetaProductos.exists() && !carpetaProductos.mkdirs()) {
            System.err.println("Error al crear la carpeta de Productos.");
            return;
        }

        // Crear un mapa para almacenar los números de documento
        Map<String, String> numerosDocumentos = new HashMap<>();

        // Generar los archivos
        generarArchivoVendedores(new File(carpetaVendedores, "informacion_vendedores.txt"), numerosDocumentos);
        generarArchivosVentas(carpetaVentas, numerosDocumentos);
        generarArchivoProductos(new File(carpetaProductos, "informacion_productos.txt"));
    }

    private static void generarArchivoVendedores(File archivo, Map<String, String> numerosDocumentos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 0; i < 5; i++) {
                String tipoDocumento = TIPOS_DOCUMENTO[new Random().nextInt(TIPOS_DOCUMENTO.length)];
                String numeroDocumento = String.format("%08d", new Random().nextInt(100000000));
                String nombre = NOMBRES[new Random().nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[new Random().nextInt(APELLIDOS.length)];
                bw.write(String.format("%s;%s;%s;%s%n", tipoDocumento, numeroDocumento, nombre, apellido));

                // Guardar el número de documento para usarlo en archivos de ventas
                numerosDocumentos.put(numeroDocumento, "Vendedor_" + i);
            }
            System.out.println("Archivo de vendedores generado exitosamente en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al generar el archivo de vendedores: " + archivo.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private static void generarArchivosVentas(File carpeta, Map<String, String> numerosDocumentos) {
        Random random = new Random();
        for (String numeroDocumento : numerosDocumentos.keySet()) {
            File archivoVentas = new File(carpeta, "ventas_" + numeroDocumento + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoVentas))) {
                bw.write(String.format("DNI;%s%n", numeroDocumento));
                for (int j = 0; j < 3; j++) {
                    String idProducto = "P" + String.format("%03d", j + 1);
                    int cantidad = random.nextInt(20) + 1;
                    bw.write(String.format("%s;%d;%n", idProducto, cantidad));
                }
                System.out.println("Archivo de ventas generado exitosamente para el vendedor: " + numeroDocumento);
            } catch (IOException e) {
                System.err.println("Error al generar el archivo de ventas para el vendedor: " + numeroDocumento);
                e.printStackTrace();
            }
        }
    }

    private static void generarArchivoProductos(File archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 0; i < 5; i++) {
                String idProducto = "P" + String.format("%03d", i + 1);
                String nombreProducto = "Producto_" + i;
                double precio = new Random().nextDouble() * 100;
                bw.write(String.format("%s;%s;%.2f%n", idProducto, nombreProducto, precio));
            }
            System.out.println("Archivo de productos generado exitosamente en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al generar el archivo de productos: " + archivo.getAbsolutePath());
            e.printStackTrace();
        }
    }
}