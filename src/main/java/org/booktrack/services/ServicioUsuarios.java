package org.booktrack.services;

import java.util.List;
import org.booktrack.interfaces.RepositorioUsuarios;
import org.booktrack.models.Usuario;

public class ServicioUsuarios {

    private final RepositorioUsuarios repositorioUsuarios;

    public ServicioUsuarios(RepositorioUsuarios repositorioUsuarios) {
        if (repositorioUsuarios == null) {
            throw new IllegalArgumentException("El repositorio de usuarios es obligatorio");
        }
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void registrarUsuario(Usuario usuario) {
        repositorioUsuarios.guardar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repositorioUsuarios.obtenerTodos();
    }

    public Usuario buscarUsuario(String id) {
        return repositorioUsuarios.buscarPorId(id);
    }

    public void editarUsuario(String id, String nombre, String telefono, String correo) {
        Usuario usuario = repositorioUsuarios.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
    }

    public void eliminarUsuario(String id) {
        repositorioUsuarios.eliminar(id);
    }
}
