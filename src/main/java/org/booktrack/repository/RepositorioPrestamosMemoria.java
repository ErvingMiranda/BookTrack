package org.booktrack.repository;

import java.util.ArrayList;
import java.util.List;
import org.booktrack.interfaces.RepositorioPrestamos;
import org.booktrack.models.Prestamo;

public class RepositorioPrestamosMemoria implements RepositorioPrestamos {

    private final List<Prestamo> prestamos;

    public RepositorioPrestamosMemoria() {
        this.prestamos = new ArrayList<>();
    }

    @Override
    public void guardar(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("El prestamo no puede ser null");
        }
        prestamos.add(prestamo);
    }

    @Override
    public List<Prestamo> obtenerTodos() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public Prestamo buscarPorId(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId().equals(id)) {
                return prestamo;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String id) {
        if (id == null || id.isBlank()) {
            return;
        }
        prestamos.removeIf(prestamo -> prestamo.getId().equals(id));
    }
}
