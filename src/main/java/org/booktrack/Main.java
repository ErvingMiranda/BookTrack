package org.booktrack;

import org.booktrack.interfaces.CalculadoraMulta;
import org.booktrack.interfaces.RepositorioLibros;
import org.booktrack.interfaces.RepositorioPrestamos;
import org.booktrack.interfaces.RepositorioUsuarios;
import org.booktrack.multas.CalculadoraMultaBasica;
import org.booktrack.repository.RepositorioLibrosMemoria;
import org.booktrack.repository.RepositorioPrestamosMemoria;
import org.booktrack.repository.RepositorioUsuariosMemoria;
import org.booktrack.services.ServicioLibros;
import org.booktrack.services.ServicioPrestamos;
import org.booktrack.services.ServicioUsuarios;
import org.booktrack.views.MenuConsola;

public class Main {

    public static void main(String[] args) {
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuariosMemoria();
        RepositorioLibros repositorioLibros = new RepositorioLibrosMemoria();
        RepositorioPrestamos repositorioPrestamos = new RepositorioPrestamosMemoria();
        CalculadoraMulta calculadoraMulta = new CalculadoraMultaBasica();

        ServicioUsuarios servicioUsuarios = new ServicioUsuarios(repositorioUsuarios);
        ServicioLibros servicioLibros = new ServicioLibros(repositorioLibros);
        ServicioPrestamos servicioPrestamos = new ServicioPrestamos(
                repositorioPrestamos,
                repositorioLibros,
                calculadoraMulta);

        MenuConsola menuConsola = new MenuConsola(
                servicioUsuarios,
                servicioLibros,
                servicioPrestamos);

        menuConsola.iniciar();
    }
}
