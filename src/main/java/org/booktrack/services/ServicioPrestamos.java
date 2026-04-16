package org.booktrack.services;

import java.util.List;
import org.booktrack.interfaces.CalculadoraMulta;
import org.booktrack.interfaces.RepositorioLibros;
import org.booktrack.interfaces.RepositorioPrestamos;
import org.booktrack.models.ItemPrestamo;
import org.booktrack.models.Libro;
import org.booktrack.models.Prestamo;

public class ServicioPrestamos {

    private final RepositorioPrestamos repositorioPrestamos;
    private final RepositorioLibros repositorioLibros;
    private final CalculadoraMulta calculadoraMulta;
    private double totalMultas;

    public ServicioPrestamos(
            RepositorioPrestamos repositorioPrestamos,
            RepositorioLibros repositorioLibros,
            CalculadoraMulta calculadoraMulta) {

        if (repositorioPrestamos == null) {
            throw new IllegalArgumentException("El repositorio de prestamos es obligatorio");
        }
        if (repositorioLibros == null) {
            throw new IllegalArgumentException("El repositorio de libros es obligatorio");
        }
        if (calculadoraMulta == null) {
            throw new IllegalArgumentException("La calculadora de multa es obligatoria");
        }

        this.repositorioPrestamos = repositorioPrestamos;
        this.repositorioLibros = repositorioLibros;
        this.calculadoraMulta = calculadoraMulta;
        this.totalMultas = 0;
    }

    public void registrarPrestamo(Prestamo prestamo) {
        repositorioPrestamos.guardar(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        return repositorioPrestamos.obtenerTodos();
    }

    public Prestamo buscarPrestamo(String id) {
        return repositorioPrestamos.buscarPorId(id);
    }

    public void eliminarPrestamo(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id del prestamo es obligatorio");
        }

        Prestamo prestamo = repositorioPrestamos.buscarPorId(id);
        if (prestamo == null) {
            throw new IllegalArgumentException("Prestamo no encontrado");
        }

        if (!prestamo.isDevuelto()) {
            for (ItemPrestamo item : prestamo.getItems()) {
                item.getLibro().aumentarStock(item.getCantidad());

                Libro libroRepositorio = repositorioLibros.buscarPorId(item.getLibro().getId());
                if (libroRepositorio != null && libroRepositorio != item.getLibro()) {
                    libroRepositorio.aumentarStock(item.getCantidad());
                }
            }
        }

        repositorioPrestamos.eliminar(id);
    }

    public void agregarLibroAPrestamo(Prestamo prestamo, Libro libro, int cantidad) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El prestamo es obligatorio");
        }
        if (libro == null) {
            throw new IllegalArgumentException("El libro es obligatorio");
        }
        if (prestamo.isDevuelto()) {
            throw new IllegalStateException("No se puede modificar un prestamo devuelto");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (cantidad > libro.getStockDisponible()) {
            throw new IllegalStateException("No hay suficientes ejemplares disponibles");
        }

        prestamo.agregarItem(new ItemPrestamo(libro, cantidad));
        libro.reducirStock(cantidad);
    }

    public void eliminarLibroDePrestamo(Prestamo prestamo, String idLibro) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El prestamo es obligatorio");
        }
        if (idLibro == null || idLibro.isBlank()) {
            throw new IllegalArgumentException("El id del libro es obligatorio");
        }
        if (prestamo.isDevuelto()) {
            throw new IllegalStateException("No se puede modificar un prestamo devuelto");
        }

        ItemPrestamo itemEncontrado = null;

        for (ItemPrestamo item : prestamo.getItems()) {
            if (item.getLibro().getId().equals(idLibro)) {
                itemEncontrado = item;
                break;
            }
        }

        if (itemEncontrado == null) {
            throw new IllegalArgumentException("El libro no esta en el prestamo");
        }

        itemEncontrado.getLibro().aumentarStock(itemEncontrado.getCantidad());
        prestamo.eliminarItem(idLibro);
    }

    public void actualizarCantidadLibroEnPrestamo(Prestamo prestamo, String idLibro, int nuevaCantidad) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El prestamo es obligatorio");
        }
        if (idLibro == null || idLibro.isBlank()) {
            throw new IllegalArgumentException("El id del libro es obligatorio");
        }
        if (nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (prestamo.isDevuelto()) {
            throw new IllegalStateException("No se puede modificar un prestamo devuelto");
        }

        ItemPrestamo itemEncontrado = null;
        for (ItemPrestamo item : prestamo.getItems()) {
            if (item.getLibro().getId().equals(idLibro)) {
                itemEncontrado = item;
                break;
            }
        }

        if (itemEncontrado == null) {
            throw new IllegalArgumentException("El libro no esta en el prestamo");
        }

        int cantidadActual = itemEncontrado.getCantidad();
        if (nuevaCantidad == cantidadActual) {
            return;
        }

        Libro libro = itemEncontrado.getLibro();
        if (nuevaCantidad > cantidadActual) {
            int extra = nuevaCantidad - cantidadActual;
            if (extra > libro.getStockDisponible()) {
                throw new IllegalStateException("No hay suficientes ejemplares disponibles");
            }
            libro.reducirStock(extra);
        } else {
            int aDevolver = cantidadActual - nuevaCantidad;
            libro.aumentarStock(aDevolver);
        }

        itemEncontrado.setCantidad(nuevaCantidad);
    }

    public double registrarDevolucion(String idPrestamo, int diasRetraso) {
        Prestamo prestamo = repositorioPrestamos.buscarPorId(idPrestamo);
        if (prestamo == null) {
            throw new IllegalArgumentException("Prestamo no encontrado");
        }
        if (prestamo.isDevuelto()) {
            return 0;
        }

        prestamo.marcarComoDevuelto();

        for (ItemPrestamo item : prestamo.getItems()) {
            item.getLibro().aumentarStock(item.getCantidad());

            Libro libroRepositorio = repositorioLibros.buscarPorId(item.getLibro().getId());
            if (libroRepositorio != null && libroRepositorio != item.getLibro()) {
                libroRepositorio.aumentarStock(item.getCantidad());
            }
        }

        double multa = calculadoraMulta.calcular(diasRetraso);
        totalMultas += multa;
        return multa;
    }

    public int contarPrestamos() {
        return repositorioPrestamos.obtenerTodos().size();
    }

    public double calcularTotalMultas() {
        return totalMultas;
    }
}
