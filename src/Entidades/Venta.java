package Entidades;

/**
 * Representa una venta de un producto.
 * <p>
 * La clase <code>Venta</code> almacena información sobre una venta específica, incluyendo el identificador del producto
 * y la cantidad vendida. Proporciona métodos para acceder y modificar estos datos.
 * </p>
 */
public class Venta {
    private String idProducto;
    private int cantidad;

    public Venta(String idProducto, int cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String toString() {
        return "ID Producto: " + idProducto + ", Cantidad: " + cantidad;
    }
}