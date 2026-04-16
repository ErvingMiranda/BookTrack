package org.booktrack.services;

import java.util.List;
import org.booktrack.interfaces.RepositorioLibros;
import org.booktrack.models.Libro;

public class ServicioLibros {

    private final RepositorioLibros repositorioLibros;

    public ServicioLibros(RepositorioLibros repositorioLibros) {
        if (repositorioLibros == null) {
            throw new IllegalArgumentException("El repositorio de libros es obligatorio");
        }
        this.repositorioLibros = repositorioLibros;
    }

    public void registrarLibro(Libro libro) {
        repositorioLibros.guardar(libro);
    }

    public List<Libro> listarLibros() {
        return repositorioLibros.obtenerTodos();
    }

    public Libro buscarLibro(String id) {
        return repositorioLibros.buscarPorId(id);
    }

    public void editarLibro(String id, String titulo, String autor, String categoria, int stockTotal) {
        Libro libro = repositorioLibros.buscarPorId(id);
        if (libro == null) {
            throw new IllegalArgumentException("Libro no encontrado");
        }

        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.actualizarStock(stockTotal);
    }

    public void eliminarLibro(String id) {
        repositorioLibros.eliminar(id);
    }

    public int contarTitulosDisponibles() {
        int disponibles = 0;
        for (Libro libro : repositorioLibros.obtenerTodos()) {
            if (libro.isDisponible()) {
                disponibles++;
            }
        }
        return disponibles;
    }

    public int contarEjemplaresDisponibles() {
        int total = 0;
        for (Libro libro : repositorioLibros.obtenerTodos()) {
            total += libro.getStockDisponible();
        }
        return total;
    }
}