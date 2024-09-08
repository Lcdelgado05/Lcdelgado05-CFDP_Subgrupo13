package Entidades;

/**
 * Representa un producto en el sistema de ventas.
 * <p>
 * La clase <code>Producto</code> almacena información sobre un producto específico, incluyendo su identificador,
 * nombre y precio por unidad. Proporciona métodos para acceder y modificar estos datos.
 * </p>
 */
public class Producto {
    private String idProducto;
    private String nombreProducto;
    private double precioPorUnidad;

    public Producto(String idProducto, String nombreProducto, double precioPorUnidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioPorUnidad = precioPorUnidad;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioPorUnidad() {
        return precioPorUnidad;
    }

    public void setPrecioPorUnidad(double precioPorUnidad) {
        this.precioPorUnidad = precioPorUnidad;
    }

    public String toString() {
        return idProducto + ";" + nombreProducto + ";" + precioPorUnidad;
    }
}