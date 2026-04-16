package org.booktrack.interfaces;

import java.util.List;
import org.booktrack.models.Prestamo;

public interface RepositorioPrestamos {

    void guardar(Prestamo prestamo);

    List<Prestamo> obtenerTodos();

    Prestamo buscarPorId(String id);

    void eliminar(String id);
}
