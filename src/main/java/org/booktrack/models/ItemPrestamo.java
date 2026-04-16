package org.booktrack.models;

public class ItemPrestamo {

    private Libro libro;
    private int cantidad;

    public ItemPrestamo(Libro libro, int cantidad) {
        setLibro(libro);
        setCantidad(cantidad);
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro es obligatorio");
        }
        this.libro = libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ItemPrestamo{" +
                "libroId='" + libro.getId() + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
