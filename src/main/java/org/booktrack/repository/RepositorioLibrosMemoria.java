package org.booktrack.repository;

import java.util.ArrayList;
import java.util.List;
import org.booktrack.interfaces.RepositorioLibros;
import org.booktrack.models.Libro;

public class RepositorioLibrosMemoria implements RepositorioLibros {

    private final List<Libro> libros;

    public RepositorioLibrosMemoria() {
        this.libros = new ArrayList<>();
    }

    @Override
    public void guardar(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }
        libros.add(libro);
    }

    @Override
    public List<Libro> obtenerTodos() {
        return new ArrayList<>(libros);
    }

    @Override
    public Libro buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        for (Libro libro : libros) {
            if (libro.getId().equals(id)) {
                return libro;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String id) {
        if (id == null || id.isBlank()) {
            return;
        }
        libros.removeIf(libro -> libro.getId().equals(id));
    }
}
