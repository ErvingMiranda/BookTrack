package org.booktrack.models;

import org.booktrack.utils.GeneradorId;

public class Libro {

    private final String id;
    private String titulo;
    private String autor;
    private String categoria;
    private int stockTotal;
    private int stockDisponible;

    public Libro(String titulo, String autor, String categoria, int stockTotal) {
        this.id = GeneradorId.generar();
        setTitulo(titulo);
        setAutor(autor);
        setCategoria(categoria);
        setStockTotal(stockTotal);
        this.stockDisponible = stockTotal;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo es obligatorio");
        }
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor es obligatorio");
        }
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("La categoria es obligatoria");
        }
        this.categoria = categoria;
    }

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        if (stockTotal <= 0) {
            throw new IllegalArgumentException("El stock total debe ser mayor que cero");
        }

        if (this.stockDisponible > stockTotal) {
            throw new IllegalArgumentException("El stock total no puede ser menor al stock disponible actual");
        }

        this.stockTotal = stockTotal;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public boolean isDisponible() {
        return stockDisponible > 0;
    }

    public void reducirStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (cantidad > stockDisponible) {
            throw new IllegalArgumentException("No hay suficientes ejemplares disponibles");
        }

        stockDisponible -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        stockDisponible += cantidad;

        if (stockDisponible > stockTotal) {
            stockDisponible = stockTotal;
        }
    }

    public void actualizarStock(int nuevoStockTotal) {
        if (nuevoStockTotal <= 0) {
            throw new IllegalArgumentException("El stock total debe ser mayor que cero");
        }

        int prestados = stockTotal - stockDisponible;

        if (nuevoStockTotal < prestados) {
            throw new IllegalArgumentException(
                    "El nuevo stock total no puede ser menor que la cantidad actualmente prestada"
            );
        }

        this.stockTotal = nuevoStockTotal;
        this.stockDisponible = nuevoStockTotal - prestados;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", categoria='" + categoria + '\'' +
                ", stockTotal=" + stockTotal +
                ", stockDisponible=" + stockDisponible +
                '}';
    }
}