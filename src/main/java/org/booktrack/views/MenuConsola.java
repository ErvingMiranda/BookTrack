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

    private final ServicioUsuarios servicioUsuarios;
    private final ServicioLibros servicioLibros;
    private final ServicioPrestamos servicioPrestamos;
    private final Scanner scanner;

    public MenuConsola(
            ServicioUsuarios servicioUsuarios,
            ServicioLibros servicioLibros,
            ServicioPrestamos servicioPrestamos) {
        if (servicioUsuarios == null || servicioLibros == null || servicioPrestamos == null) {
            throw new IllegalArgumentException("Los servicios son obligatorios");
        }
        this.servicioUsuarios = servicioUsuarios;
        this.servicioLibros = servicioLibros;
        this.servicioPrestamos = servicioPrestamos;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== BookTrack - Menu Principal ===");
            System.out.println("1. Administrar usuarios");
            System.out.println("2. Administrar libros");
            System.out.println("3. Administrar prestamos");
            System.out.println("4. Ver reportes");
            System.out.println("5. Salir");

            int opcion = leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1:
                    menuUsuarios();
                    break;
                case 2:
                    menuLibros();
                    break;
                case 3:
                    menuPrestamos();
                    break;
                case 4:
                    verReportes();
                    break;
                case 5:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private void menuUsuarios() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Administrar Usuarios ---");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Ver usuarios");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Volver");

            int opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        registrarUsuario();
                        break;
                    case 2:
                        verUsuarios();
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        eliminarUsuario();
                        break;
                    case 5:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void menuLibros() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Administrar Libros ---");
            System.out.println("1. Registrar libro");
            System.out.println("2. Ver libros");
            System.out.println("3. Editar libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Volver");

            int opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        registrarLibro();
                        break;
                    case 2:
                        verLibros();
                        break;
                    case 3:
                        editarLibro();
                        break;
                    case 4:
                        eliminarLibro();
                        break;
                    case 5:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void menuPrestamos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Administrar Prestamos ---");
            System.out.println("1. Crear prestamo");
            System.out.println("2. Ver prestamos");
            System.out.println("3. Eliminar prestamo");
            System.out.println("4. Registrar devolucion");
            System.out.println("5. Volver");

            int opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        crearPrestamo();
                        break;
                    case 2:
                        verPrestamos();
                        break;
                    case 3:
                        eliminarPrestamo();
                        break;
                    case 4:
                        registrarDevolucion();
                        break;
                    case 5:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registrarUsuario() {
        String nombre = leerTextoNoVacio("Nombre: ");
        String telefono = leerTextoNoVacio("Telefono: ");
        String correo = leerTextoNoVacio("Correo: ");

        servicioUsuarios.registrarUsuario(new Usuario(nombre, telefono, correo));
        System.out.println("Usuario registrado.");
    }

    private void verUsuarios() {
        List<Usuario> usuarios = servicioUsuarios.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("\nUsuarios registrados:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void editarUsuario() {
        String id = leerTextoNoVacio("ID de usuario a editar: ");
        Usuario usuario = servicioUsuarios.buscarUsuario(id);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        String nombre = leerTextoNoVacio("Nuevo nombre: ");
        String telefono = leerTextoNoVacio("Nuevo telefono: ");
        String correo = leerTextoNoVacio("Nuevo correo: ");
        servicioUsuarios.editarUsuario(id, nombre, telefono, correo);
        System.out.println("Usuario actualizado.");
    }

    private void eliminarUsuario() {
        String id = leerTextoNoVacio("ID de usuario a eliminar: ");
        if (servicioUsuarios.buscarUsuario(id) == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        servicioUsuarios.eliminarUsuario(id);
        System.out.println("Usuario eliminado.");
    }

    private void registrarLibro() {
        String titulo = leerTextoNoVacio("Titulo: ");
        String autor = leerTextoNoVacio("Autor: ");
        String categoria = leerTextoNoVacio("Categoria: ");
        boolean disponible = leerBooleano("Disponible (s/n): ");

        servicioLibros.registrarLibro(new Libro(titulo, autor, categoria, disponible));
        System.out.println("Libro registrado.");
    }

    private void verLibros() {
        List<Libro> libros = servicioLibros.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\nLibros registrados:");
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private void editarLibro() {
        String id = leerTextoNoVacio("ID de libro a editar: ");
        Libro libro = servicioLibros.buscarLibro(id);
        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        String titulo = leerTextoNoVacio("Nuevo titulo: ");
        String autor = leerTextoNoVacio("Nuevo autor: ");
        String categoria = leerTextoNoVacio("Nueva categoria: ");
        boolean disponible = leerBooleano("Disponible (s/n): ");
        servicioLibros.editarLibro(id, titulo, autor, categoria, disponible);
        System.out.println("Libro actualizado.");
    }

    private void eliminarLibro() {
        String id = leerTextoNoVacio("ID de libro a eliminar: ");
        if (servicioLibros.buscarLibro(id) == null) {
            System.out.println("Libro no encontrado.");
            return;
        }
        servicioLibros.eliminarLibro(id);
        System.out.println("Libro eliminado.");
    }

    private void crearPrestamo() {
        if (servicioUsuarios.listarUsuarios().isEmpty()) {
            System.out.println("No hay usuarios registrados. Registre usuarios antes de crear prestamos.");
            return;
        }
        if (servicioLibros.listarLibros().isEmpty()) {
            System.out.println("No hay libros registrados. Registre libros antes de crear prestamos.");
            return;
        }

        verUsuarios();
        String idUsuario = leerTextoNoVacio("ID del usuario para el prestamo: ");
        Usuario usuario = servicioUsuarios.buscarUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        Prestamo prestamo = new Prestamo(usuario);
        boolean finalizar = false;

        while (!finalizar) {
            System.out.println("\n--- Edicion de prestamo en curso ---");
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Ver items del prestamo");
            System.out.println("4. Finalizar prestamo");
            System.out.println("5. Cancelar prestamo");

            int opcion = leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1:
                    verLibros();
                    agregarLibroAPrestamo(prestamo);
                    break;
                case 2:
                    eliminarLibroDePrestamo(prestamo);
                    break;
                case 3:
                    mostrarItemsPrestamo(prestamo);
                    break;
                case 4:
                    if (prestamo.getItems().isEmpty()) {
                        System.out.println("El prestamo debe tener al menos un libro.");
                    } else {
                        servicioPrestamos.registrarPrestamo(prestamo);
                        System.out.println("Prestamo registrado con ID: " + prestamo.getId());
                        finalizar = true;
                    }
                    break;
                case 5:
                    cancelarPrestamo(prestamo);
                    System.out.println("Prestamo cancelado.");
                    finalizar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private void agregarLibroAPrestamo(Prestamo prestamo) {
        String idLibro = leerTextoNoVacio("ID del libro a agregar: ");
        Libro libro = servicioLibros.buscarLibro(idLibro);
        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        int cantidad = leerEntero("Cantidad: ");
        servicioPrestamos.agregarLibroAPrestamo(prestamo, libro, cantidad);
        System.out.println("Libro agregado al prestamo.");
    }

    private void eliminarLibroDePrestamo(Prestamo prestamo) {
        if (prestamo.getItems().isEmpty()) {
            System.out.println("No hay libros en el prestamo.");
            return;
        }

        mostrarItemsPrestamo(prestamo);
        String idLibro = leerTextoNoVacio("ID del libro a eliminar del prestamo: ");
        boolean existe = false;
        for (ItemPrestamo item : prestamo.getItems()) {
            if (item.getLibro().getId().equals(idLibro)) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            System.out.println("El libro no esta en el prestamo.");
            return;
        }

        servicioPrestamos.eliminarLibroDePrestamo(prestamo, idLibro);
        System.out.println("Libro eliminado del prestamo.");
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

    private void mostrarItemsPrestamo(Prestamo prestamo) {
        if (prestamo.getItems().isEmpty()) {
            System.out.println("No hay items en el prestamo.");
            return;
        }

        System.out.println("Items del prestamo:");
        for (ItemPrestamo item : prestamo.getItems()) {
            System.out.println(item);
        }
    }

    private void verPrestamos() {
        List<Prestamo> prestamos = servicioPrestamos.listarPrestamos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay prestamos registrados.");
            return;
        }

        System.out.println("\nPrestamos registrados:");
        for (Prestamo prestamo : prestamos) {
            System.out.println(prestamo);
        }
    }

    private void eliminarPrestamo() {
        String id = leerTextoNoVacio("ID de prestamo a eliminar: ");
        if (servicioPrestamos.buscarPrestamo(id) == null) {
            System.out.println("Prestamo no encontrado.");
            return;
        }
        servicioPrestamos.eliminarPrestamo(id);
        System.out.println("Prestamo eliminado.");
    }

    private void registrarDevolucion() {
        String idPrestamo = leerTextoNoVacio("ID del prestamo: ");
        int diasRetraso = leerEntero("Dias de retraso: ");
        double multa = servicioPrestamos.registrarDevolucion(idPrestamo, diasRetraso);
        System.out.printf("Multa calculada: US$%.2f%n", multa);
    }

    private void verReportes() {
        int cantidadPrestamos = servicioPrestamos.contarPrestamos();
        int librosDisponibles = servicioLibros.contarLibrosDisponibles();
        double totalMultas = servicioPrestamos.calcularTotalMultas();

        System.out.println("\n--- Reportes ---");
        System.out.println("Cantidad de prestamos realizados: " + cantidadPrestamos);
        System.out.println("Cantidad de libros disponibles: " + librosDisponibles);
        System.out.printf("Total de multas generadas: US$%.2f%n", totalMultas);
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.");
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
            System.out.println("El valor no puede estar vacio.");
        }
    }

    private boolean leerBooleano(String mensaje) {
        while (true) {
            String respuesta = leerTextoNoVacio(mensaje).toLowerCase();
            if ("s".equals(respuesta) || "si".equals(respuesta)) {
                return true;
            }
            if ("n".equals(respuesta) || "no".equals(respuesta)) {
                return false;
            }
            System.out.println("Respuesta invalida. Use 's'/'si' o 'n'/'no'.");
        }
    }
}
