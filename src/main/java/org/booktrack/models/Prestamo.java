package org.booktrack.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.booktrack.utils.GeneradorId;

public class Prestamo {

    private final String id;
    private Usuario usuario;
    private final List<ItemPrestamo> items;
    private boolean devuelto;

    public Prestamo(Usuario usuario) {
        this.id = GeneradorId.generar();
        setUsuario(usuario);
        this.items = new ArrayList<>();
        this.devuelto = false;
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        this.usuario = usuario;
    }

    public void agregarItem(ItemPrestamo item) {
        if (item == null) {
            throw new IllegalArgumentException("El item es obligatorio");
        }
        items.add(item);
    }

    public void eliminarItem(String idLibro) {
        if (idLibro == null || idLibro.isBlank()) {
            throw new IllegalArgumentException("El id del libro es obligatorio");
        }
        items.removeIf(item -> item.getLibro().getId().equals(idLibro));
    }

    public List<ItemPrestamo> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void marcarComoDevuelto() {
        this.devuelto = true;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id='" + id + '\'' +
                ", usuarioId='" + usuario.getId() + '\'' +
                ", items=" + items +
                ", devuelto=" + devuelto +
                '}';
    }
}
