package InfoVentas;

import java.util.List;

public class Vendedor {
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private List<Venta> ventas;

    public Vendedor(String tipoDocumento, String numeroDocumento, String nombre, String apellido) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }


    public String toString() {
        return String.format("Vendedor{tipoDocumento='%s', numeroDocumento='%s', nombre='%s', apellido='%s'}",
                tipoDocumento, numeroDocumento, nombre, apellido);
    }
}
