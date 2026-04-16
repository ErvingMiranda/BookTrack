package org.booktrack.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.booktrack.models.ItemPrestamo;
import org.booktrack.models.Libro;
import org.booktrack.models.Prestamo;
import org.booktrack.models.Usuario;
import org.booktrack.services.ServicioLibros;
import org.booktrack.services.ServicioPrestamos;
import org.booktrack.services.ServicioUsuarios;

public class MenuConsola {

    private static final int ANCHO = 70;

    private final ServicioUsuarios servicioUsuarios;
    private final ServicioLibros servicioLibros;
    private final ServicioPrestamos servicioPrestamos;
    private final Scanner scanner;

    public MenuConsola(
            ServicioUsuarios servicioUsuarios,
            ServicioLibros servicioLibros,
            ServicioPrestamos servicioPrestamos) {

        if (servicioUsuarios == null || servicioLibros == null || servicioPrestamos == null) {
            throw new IllegalArgumentException("Los servicios son obligatorios.");
        }

        this.servicioUsuarios = servicioUsuarios;
        this.servicioLibros = servicioLibros;
        this.servicioPrestamos = servicioPrestamos;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            limpiarPantalla();
            imprimirTitulo("BOOKTRACK - MENU PRINCIPAL");
            System.out.println("1. Administrar usuarios");
            System.out.println("2. Administrar libros");
            System.out.println("3. Administrar prestamos");
            System.out.println("4. Ver reportes");
            System.out.println("5. Salir");
            imprimirLinea();

            int opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> menuUsuarios();
                    case 2 -> menuLibros();
                    case 3 -> menuPrestamos();
                    case 4 -> verReportes();
                    case 5 -> {
                        salir = true;
                        mostrarExito("Saliendo del sistema...");
                    }
                    default -> mostrarError("Opcion invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                mostrarError(e.getMessage());
                pausar();
            }
        }
    }

    private void menuUsuarios() {
        boolean volver = false;

        while (!volver) {
            limpiarPantalla();
            imprimirTitulo("ADMINISTRAR USUARIOS");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Ver usuarios");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Volver");
            imprimirLinea();

            int opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> registrarUsuario();
                    case 2 -> verUsuarios();
                    case 3 -> editarUsuario();
                    case 4 -> eliminarUsuario();
                    case 5 -> volver = true;
                    default -> {
                        mostrarError("Opcion invalida.");
                        pausar();
                    }
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                mostrarError(e.getMessage());
                pausar();
            }
        }
    }

    private void menuLibros() {
        boolean volver = false;

        while (!volver) {
            limpiarPantalla();
            imprimirTitulo("ADMINISTRAR LIBROS");
            System.out.println("1. Registrar libro");
            System.out.println("2. Ver libros");
            System.out.println("3. Editar libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Volver");
            imprimirLinea();

            int opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> registrarLibro();
                    case 2 -> verLibros();
                    case 3 -> editarLibro();
                    case 4 -> eliminarLibro();
                    case 5 -> volver = true;
                    default -> {
                        mostrarError("Opcion invalida.");
                        pausar();
                    }
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                mostrarError(e.getMessage());
                pausar();
            }
        }
    }

    private void menuPrestamos() {
        boolean volver = false;

        while (!volver) {
            limpiarPantalla();
            imprimirTitulo("ADMINISTRAR PRESTAMOS");
            System.out.println("1. Crear prestamo");
            System.out.println("2. Ver prestamos");
            System.out.println("3. Eliminar prestamo");
            System.out.println("4. Registrar devolucion");
            System.out.println("5. Volver");
            imprimirLinea();

            int opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> crearPrestamo();
                    case 2 -> verPrestamos();
                    case 3 -> eliminarPrestamo();
                    case 4 -> registrarDevolucion();
                    case 5 -> volver = true;
                    default -> {
                        mostrarError("Opcion invalida.");
                        pausar();
                    }
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                mostrarError(e.getMessage());
                pausar();
            }
        }
    }

    private void registrarUsuario() {
        limpiarPantalla();
        imprimirTitulo("REGISTRAR USUARIO");

        String nombre = leerTextoNoVacio("Nombre: ");
        String telefono = leerTextoNoVacio("Telefono: ");
        String correo = leerTextoNoVacio("Correo: ");

        servicioUsuarios.registrarUsuario(new Usuario(nombre, telefono, correo));
        mostrarExito("Usuario registrado correctamente.");
        pausar();
    }

    private void verUsuarios() {
        limpiarPantalla();
        imprimirTitulo("USUARIOS REGISTRADOS");

        List<Usuario> usuarios = servicioUsuarios.listarUsuarios();
        if (usuarios.isEmpty()) {
            mostrarAviso("No hay usuarios registrados.");
            pausar();
            return;
        }

        int contador = 1;
        for (Usuario usuario : usuarios) {
            System.out.println("Usuario #" + contador);
            imprimirLinea();
            System.out.println(formatearCampo("ID", usuario.getId()));
            System.out.println(formatearCampo("Nombre", usuario.getNombre()));
            System.out.println(formatearCampo("Telefono", usuario.getTelefono()));
            System.out.println(formatearCampo("Correo", usuario.getCorreo()));
            imprimirLinea();
            contador++;
        }

        System.out.println("Total de usuarios: " + usuarios.size());
        pausar();
    }

    private void editarUsuario() {
        limpiarPantalla();
        imprimirTitulo("EDITAR USUARIO");

        String id = leerTextoNoVacio("ID del usuario a editar: ");
        Usuario usuario = servicioUsuarios.buscarUsuario(id);

        if (usuario == null) {
            mostrarError("Usuario no encontrado.");
            pausar();
            return;
        }

        System.out.println("Deje vacio y presione Enter para conservar el valor actual.");
        imprimirLinea();

        String nombre = leerTextoEditable("Nuevo nombre: ", usuario.getNombre());
        String telefono = leerTextoEditable("Nuevo telefono: ", usuario.getTelefono());
        String correo = leerTextoEditable("Nuevo correo: ", usuario.getCorreo());

        servicioUsuarios.editarUsuario(id, nombre, telefono, correo);
        mostrarExito("Usuario actualizado correctamente.");
        pausar();
    }

    private void eliminarUsuario() {
        limpiarPantalla();
        imprimirTitulo("ELIMINAR USUARIO");

        String id = leerTextoNoVacio("ID del usuario a eliminar: ");
        Usuario usuario = servicioUsuarios.buscarUsuario(id);

        if (usuario == null) {
            mostrarError("Usuario no encontrado.");
            pausar();
            return;
        }

        System.out.println("Se eliminara el siguiente usuario:");
        imprimirLinea();
        System.out.println(formatearCampo("ID", usuario.getId()));
        System.out.println(formatearCampo("Nombre", usuario.getNombre()));
        System.out.println(formatearCampo("Telefono", usuario.getTelefono()));
        System.out.println(formatearCampo("Correo", usuario.getCorreo()));
        imprimirLinea();

        if (leerBooleano("¿Desea continuar? (s/n): ")) {
            servicioUsuarios.eliminarUsuario(id);
            mostrarExito("Usuario eliminado correctamente.");
        } else {
            mostrarAviso("Operacion cancelada.");
        }

        pausar();
    }

    private void registrarLibro() {
        limpiarPantalla();
        imprimirTitulo("REGISTRAR LIBRO");

        String titulo = leerTextoNoVacio("Titulo: ");
        String autor = leerTextoNoVacio("Autor: ");
        String categoria = leerTextoNoVacio("Categoria: ");
        int stock = leerEnteroPositivo("Stock total: ");

        servicioLibros.registrarLibro(new Libro(titulo, autor, categoria, stock));
        mostrarExito("Libro registrado correctamente.");
        pausar();
    }

    private void verLibros() {
        limpiarPantalla();
        imprimirTitulo("LIBROS REGISTRADOS");

        List<Libro> libros = servicioLibros.listarLibros();
        if (libros.isEmpty()) {
            mostrarAviso("No hay libros registrados.");
            pausar();
            return;
        }

        int contador = 1;
        for (Libro libro : libros) {
            System.out.println("Libro #" + contador);
            imprimirLinea();
            System.out.println(formatearCampo("ID", libro.getId()));
            System.out.println(formatearCampo("Titulo", libro.getTitulo()));
            System.out.println(formatearCampo("Autor", libro.getAutor()));
            System.out.println(formatearCampo("Categoria", libro.getCategoria()));
            System.out.println(formatearCampo("Stock total", String.valueOf(libro.getStockTotal())));
            System.out.println(formatearCampo("Disponibles", String.valueOf(libro.getStockDisponible())));
            System.out.println(formatearCampo("Estado", libro.isDisponible() ? "Disponible" : "Sin existencias"));
            imprimirLinea();
            contador++;
        }

        System.out.println("Total de libros: " + libros.size());
        pausar();
    }

    private void editarLibro() {
        limpiarPantalla();
        imprimirTitulo("EDITAR LIBRO");

        String id = leerTextoNoVacio("ID del libro a editar: ");
        Libro libro = servicioLibros.buscarLibro(id);

        if (libro == null) {
            mostrarError("Libro no encontrado.");
            pausar();
            return;
        }

        System.out.println("Deje vacio y presione Enter para conservar el valor actual.");
        imprimirLinea();

        String titulo = leerTextoEditable("Nuevo titulo: ", libro.getTitulo());
        String autor = leerTextoEditable("Nuevo autor: ", libro.getAutor());
        String categoria = leerTextoEditable("Nueva categoria: ", libro.getCategoria());
        int stockTotal = leerEnteroEditable("Nuevo stock total: ", libro.getStockTotal());

        servicioLibros.editarLibro(id, titulo, autor, categoria, stockTotal);
        mostrarExito("Libro actualizado correctamente.");
        pausar();
    }

    private void eliminarLibro() {
        limpiarPantalla();
        imprimirTitulo("ELIMINAR LIBRO");

        String id = leerTextoNoVacio("ID del libro a eliminar: ");
        Libro libro = servicioLibros.buscarLibro(id);

        if (libro == null) {
            mostrarError("Libro no encontrado.");
            pausar();
            return;
        }

        System.out.println("Se eliminara el siguiente libro:");
        imprimirLinea();
        System.out.println(formatearCampo("ID", libro.getId()));
        System.out.println(formatearCampo("Titulo", libro.getTitulo()));
        System.out.println(formatearCampo("Autor", libro.getAutor()));
        System.out.println(formatearCampo("Categoria", libro.getCategoria()));
        System.out.println(formatearCampo("Stock total", String.valueOf(libro.getStockTotal())));
        System.out.println(formatearCampo("Disponibles", String.valueOf(libro.getStockDisponible())));
        imprimirLinea();

        if (leerBooleano("¿Desea continuar? (s/n): ")) {
            servicioLibros.eliminarLibro(id);
            mostrarExito("Libro eliminado correctamente.");
        } else {
            mostrarAviso("Operacion cancelada.");
        }

        pausar();
    }

    private void crearPrestamo() {
        limpiarPantalla();
        imprimirTitulo("CREAR PRESTAMO");

        if (servicioUsuarios.listarUsuarios().isEmpty()) {
            mostrarAviso("No hay usuarios registrados. Registre usuarios antes de crear prestamos.");
            pausar();
            return;
        }

        if (servicioLibros.listarLibros().isEmpty()) {
            mostrarAviso("No hay libros registrados. Registre libros antes de crear prestamos.");
            pausar();
            return;
        }

        String idUsuario = leerIdCancelable("ID del usuario para el prestamo");
        if (idUsuario == null) {
            mostrarAviso("Solicitud cancelada.");
            pausar();
            return;
        }
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsuario);

        if (usuario == null) {
            mostrarError("Usuario no encontrado.");
            pausar();
            return;
        }

        Prestamo prestamo = new Prestamo(usuario);
        boolean finalizar = false;

        while (!finalizar) {
            limpiarPantalla();
            imprimirTitulo("EDICION DE PRESTAMO");
            System.out.println(formatearCampo("Prestamo ID", prestamo.getId()));
            System.out.println(formatearCampo("Usuario", usuario.getNombre()));
            System.out.println(formatearCampo("Usuario ID", usuario.getId()));
            imprimirLinea();

            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Ver items del prestamo");
            System.out.println("4. Finalizar prestamo");
            System.out.println("5. Cancelar prestamo");
            imprimirLinea();

            int opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> agregarLibroAPrestamo(prestamo);
                case 2 -> eliminarLibroDePrestamo(prestamo);
                case 3 -> {
                    mostrarItemsPrestamo(prestamo);
                    pausar();
                }
                case 4 -> {
                    if (prestamo.getItems().isEmpty()) {
                        mostrarError("El prestamo debe tener al menos un libro.");
                        pausar();
                    } else {
                        servicioPrestamos.registrarPrestamo(prestamo);
                        mostrarExito("Prestamo registrado con ID: " + prestamo.getId());
                        pausar();
                        finalizar = true;
                    }
                }
                case 5 -> {
                    cancelarPrestamo(prestamo);
                    mostrarAviso("Prestamo cancelado.");
                    pausar();
                    finalizar = true;
                }
                default -> {
                    mostrarError("Opcion invalida.");
                    pausar();
                }
            }
        }
    }

    private void agregarLibroAPrestamo(Prestamo prestamo) {
        limpiarPantalla();
        imprimirTitulo("AGREGAR LIBRO AL PRESTAMO");

        mostrarLibrosSinPausa();
        String idLibro = leerIdCancelable("ID del libro a agregar");
        if (idLibro == null) {
            mostrarAviso("Solicitud cancelada.");
            pausar();
            return;
        }
        Libro libro = servicioLibros.buscarLibro(idLibro);

        if (libro == null) {
            mostrarError("Libro no encontrado.");
            pausar();
            return;
        }

        ItemPrestamo itemExistente = buscarItemEnPrestamo(prestamo, idLibro);
        if (itemExistente != null) {
            mostrarAviso("Este libro ya esta agregado en el prestamo.");
            if (!leerBooleano("Desea editar la cantidad pedida? (s/n): ")) {
                mostrarAviso("Operacion cancelada.");
                pausar();
                return;
            }

            int maximoPermitido = itemExistente.getCantidad() + libro.getStockDisponible();
            System.out.println("Cantidad actual en el prestamo: " + itemExistente.getCantidad());
            System.out.println("Cantidad maxima permitida para este libro: " + maximoPermitido);
            int nuevaCantidad = leerEnteroEnRango(
                    "Nueva cantidad a prestar (1-" + maximoPermitido + "): ",
                    1,
                    maximoPermitido);

            servicioPrestamos.actualizarCantidadLibroEnPrestamo(prestamo, idLibro, nuevaCantidad);
            mostrarExito("Cantidad actualizada en el prestamo.");
            pausar();
            return;
        }

        System.out.println("Ejemplares disponibles de este libro: " + libro.getStockDisponible());
        int cantidad = leerEnteroPositivo("Cantidad a prestar: ");

        servicioPrestamos.agregarLibroAPrestamo(prestamo, libro, cantidad);

        mostrarExito("Libro agregado al prestamo.");
        pausar();
    }

    private void eliminarLibroDePrestamo(Prestamo prestamo) {
        limpiarPantalla();
        imprimirTitulo("ELIMINAR LIBRO DEL PRESTAMO");

        if (prestamo.getItems().isEmpty()) {
            mostrarAviso("No hay libros en el prestamo.");
            pausar();
            return;
        }

        mostrarItemsPrestamo(prestamo);
        String idLibro = leerIdCancelable("ID del libro a eliminar del prestamo");
        if (idLibro == null) {
            mostrarAviso("Solicitud cancelada.");
            pausar();
            return;
        }

        boolean existe = false;
        for (ItemPrestamo item : prestamo.getItems()) {
            if (item.getLibro().getId().equals(idLibro)) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            mostrarError("El libro no esta en el prestamo.");
            pausar();
            return;
        }

        servicioPrestamos.eliminarLibroDePrestamo(prestamo, idLibro);
        mostrarExito("Libro eliminado del prestamo.");
        pausar();
    }

    private void cancelarPrestamo(Prestamo prestamo) {
        List<String> idsLibros = new ArrayList<>();

        for (ItemPrestamo item : prestamo.getItems()) {
            idsLibros.add(item.getLibro().getId());
        }

        for (String idLibro : idsLibros) {
            servicioPrestamos.eliminarLibroDePrestamo(prestamo, idLibro);
        }
    }

    private ItemPrestamo buscarItemEnPrestamo(Prestamo prestamo, String idLibro) {
        for (ItemPrestamo item : prestamo.getItems()) {
            if (item.getLibro().getId().equals(idLibro)) {
                return item;
            }
        }
        return null;
    }

    private void mostrarItemsPrestamo(Prestamo prestamo) {
        System.out.println("Items del prestamo:");
        imprimirLinea();

        if (prestamo.getItems().isEmpty()) {
            System.out.println("No hay items en el prestamo.");
            imprimirLinea();
            return;
        }

        int contador = 1;
        for (ItemPrestamo item : prestamo.getItems()) {
            System.out.println("Item #" + contador);
            System.out.println(formatearCampo("Libro ID", item.getLibro().getId()));
            System.out.println(formatearCampo("Titulo", item.getLibro().getTitulo()));
            System.out.println(formatearCampo("Cantidad", String.valueOf(item.getCantidad())));
            imprimirLinea();
            contador++;
        }
    }

    private void verPrestamos() {
        limpiarPantalla();
        imprimirTitulo("PRESTAMOS REGISTRADOS");

        List<Prestamo> prestamos = servicioPrestamos.listarPrestamos();
        if (prestamos.isEmpty()) {
            mostrarAviso("No hay prestamos registrados.");
            pausar();
            return;
        }

        int contador = 1;
        for (Prestamo prestamo : prestamos) {
            System.out.println("Prestamo #" + contador);
            imprimirLinea();
            System.out.println(formatearCampo("ID", prestamo.getId()));
            System.out.println(formatearCampo("Usuario ID", prestamo.getUsuario().getId()));
            System.out.println(formatearCampo("Usuario", prestamo.getUsuario().getNombre()));
            System.out.println(formatearCampo("Cantidad items", String.valueOf(prestamo.getItems().size())));
            System.out.println(formatearCampo("Devuelto", prestamo.isDevuelto() ? "Si" : "No"));
            imprimirLinea();

            if (!prestamo.getItems().isEmpty()) {
                for (ItemPrestamo item : prestamo.getItems()) {
                    System.out.println("  - " + item.getLibro().getTitulo() + " | Cantidad: " + item.getCantidad());
                }
                imprimirLinea();
            }

            contador++;
        }

        System.out.println("Total de prestamos: " + prestamos.size());
        pausar();
    }

    private void eliminarPrestamo() {
        limpiarPantalla();
        imprimirTitulo("ELIMINAR PRESTAMO");

        String id = leerTextoNoVacio("ID de prestamo a eliminar: ");
        Prestamo prestamo = servicioPrestamos.buscarPrestamo(id);

        if (prestamo == null) {
            mostrarError("Prestamo no encontrado.");
            pausar();
            return;
        }

        System.out.println("Se eliminara el siguiente prestamo:");
        imprimirLinea();
        System.out.println(formatearCampo("ID", prestamo.getId()));
        System.out.println(formatearCampo("Usuario", prestamo.getUsuario().getNombre()));
        System.out.println(formatearCampo("Devuelto", prestamo.isDevuelto() ? "Si" : "No"));
        System.out.println(formatearCampo("Cantidad items", String.valueOf(prestamo.getItems().size())));
        imprimirLinea();

        if (leerBooleano("¿Desea continuar? (s/n): ")) {
            servicioPrestamos.eliminarPrestamo(id);
            mostrarExito("Prestamo eliminado correctamente.");
        } else {
            mostrarAviso("Operacion cancelada.");
        }

        pausar();
    }

    private void registrarDevolucion() {
        limpiarPantalla();
        imprimirTitulo("REGISTRAR DEVOLUCION");

        String idPrestamo = leerTextoNoVacio("ID del prestamo: ");
        int diasRetraso = leerEntero("Dias de retraso: ");

        double multa = servicioPrestamos.registrarDevolucion(idPrestamo, diasRetraso);
        mostrarExito(String.format("Devolucion registrada. Multa calculada: US$%.2f", multa));
        pausar();
    }

    private void verReportes() {
        limpiarPantalla();
        imprimirTitulo("REPORTES");

        int cantidadPrestamos = servicioPrestamos.contarPrestamos();
        int titulosDisponibles = servicioLibros.contarTitulosDisponibles();
        int ejemplaresDisponibles = servicioLibros.contarEjemplaresDisponibles();
        double totalMultas = servicioPrestamos.calcularTotalMultas();

        System.out.println(formatearCampo("Prestamos realizados", String.valueOf(cantidadPrestamos)));
        System.out.println(formatearCampo("Titulos disponibles", String.valueOf(titulosDisponibles)));
        System.out.println(formatearCampo("Ejemplares disponibles", String.valueOf(ejemplaresDisponibles)));
        System.out.println(formatearCampo("Total de multas", String.format("US$%.2f", totalMultas)));
        imprimirLinea();

        pausar();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                mostrarError("Debe ingresar un numero entero.");
            }
        }
    }

    private int leerEnteroPositivo(String mensaje) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero > 0) {
                return numero;
            }
            mostrarError("Debe ingresar un numero mayor que cero.");
        }
    }

    private int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero >= minimo && numero <= maximo) {
                return numero;
            }
            mostrarError("Debe ingresar un numero entre " + minimo + " y " + maximo + ".");
        }
    }

    private int leerEnteroEditable(String mensaje, int valorActual) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                return valorActual;
            }

            try {
                int numero = Integer.parseInt(entrada);
                if (numero > 0) {
                    return numero;
                }
                mostrarError("Debe ingresar un numero mayor que cero.");
            } catch (NumberFormatException e) {
                mostrarError("Debe ingresar un numero entero valido.");
            }
        }
    }

    private String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            mostrarError("El valor no puede estar vacio.");
        }
    }

    private String leerIdCancelable(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (escriba - para cancelar solicitud): ");
            String texto = scanner.nextLine().trim();

            if (texto.equals("-")) {
                return null;
            }

            if (!texto.isEmpty()) {
                return texto;
            }

            mostrarError("El valor no puede estar vacio.");
        }
    }

    private String leerTextoEditable(String mensaje, String valorActual) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();

        if (texto.isEmpty()) {
            return valorActual;
        }

        return texto;
    }

    private boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("s") || respuesta.equals("si")) {
                return true;
            }

            if (respuesta.equals("n") || respuesta.equals("no")) {
                return false;
            }

            mostrarError("Respuesta invalida. Use s/si o n/no.");
        }
    }

    private void mostrarLibrosSinPausa() {
        List<Libro> libros = servicioLibros.listarLibros();

        System.out.println("Libros disponibles:");
        imprimirLinea();

        for (Libro libro : libros) {
            System.out.println(formatearCampo("ID", libro.getId()));
            System.out.println(formatearCampo("Titulo", libro.getTitulo()));
            System.out.println(formatearCampo("Autor", libro.getAutor()));
            System.out.println(formatearCampo("Categoria", libro.getCategoria()));
            System.out.println(formatearCampo("Stock total", String.valueOf(libro.getStockTotal())));
            System.out.println(formatearCampo("Disponibles", String.valueOf(libro.getStockDisponible())));
            imprimirLinea();
        }
    }

    private void imprimirTitulo(String titulo) {
        System.out.println("=".repeat(ANCHO));
        System.out.println(centrarTexto(titulo));
        System.out.println("=".repeat(ANCHO));
    }

    private void imprimirLinea() {
        System.out.println("-".repeat(ANCHO));
    }

    private String centrarTexto(String texto) {
        if (texto.length() >= ANCHO) {
            return texto;
        }

        int espacios = (ANCHO - texto.length()) / 2;
        return " ".repeat(espacios) + texto;
    }

    private String formatearCampo(String etiqueta, String valor) {
        return String.format("%-16s: %s", etiqueta, valor);
    }

    private void mostrarExito(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }

    private void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    private void mostrarAviso(String mensaje) {
        System.out.println("[INFO] " + mensaje);
    }

    private void pausar() {
        System.out.println();
        System.out.print("Presione Enter para continuar...");
        scanner.nextLine();
    }

    private void limpiarPantalla() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}
