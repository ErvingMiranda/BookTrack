package org.booktrack.interfaces;

import java.util.List;
import org.booktrack.models.Usuario;

public interface RepositorioUsuarios {

    void guardar(Usuario usuario);

    List<Usuario> obtenerTodos();

    Usuario buscarPorId(String id);

    void eliminar(String id);
}
