package org.booktrack.repository;

import java.util.ArrayList;
import java.util.List;
import org.booktrack.interfaces.RepositorioUsuarios;
import org.booktrack.models.Usuario;

public class RepositorioUsuariosMemoria implements RepositorioUsuarios {

    private final List<Usuario> usuarios;

    public RepositorioUsuariosMemoria() {
        this.usuarios = new ArrayList<>();
    }

    @Override
    public void guardar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        usuarios.add(usuario);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String id) {
        if (id == null || id.isBlank()) {
            return;
        }
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }
}
