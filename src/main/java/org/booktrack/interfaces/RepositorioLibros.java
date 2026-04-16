package org.booktrack.interfaces;

import java.util.List;
import org.booktrack.models.Libro;

public interface RepositorioLibros {

    void guardar(Libro libro);

    List<Libro> obtenerTodos();

    Libro buscarPorId(String id);

    void eliminar(String id);
}
