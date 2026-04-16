# BookTrack - Sistema de Gestión de Biblioteca Estudiantil

## Objetivo

Desarrollar una aplicación orientada a objetos que modele un sistema de gestión de biblioteca estudiantil, en respuesta a una necesidad real de mejora en la administración de recursos bibliográficos, aplicando principios SOLID y buenas prácticas de diseño.

---

## Contexto del problema

Se ha recibido una solicitud por parte de una biblioteca estudiantil perteneciente a una institución educativa, la cual requiere mejorar la forma en que administra sus libros, usuarios y préstamos.

Actualmente, el control se realiza de manera manual o mediante registros poco organizados, lo que genera diversas dificultades en la operación diaria.

Entre los principales problemas se encuentran:

- dificultad para conocer qué libros están disponibles;
- pérdida de tiempo en el registro de préstamos y devoluciones;
- errores al calcular multas por retraso;
- poca flexibilidad para adaptar el sistema a nuevas políticas.

Debido a estas limitaciones, se requiere desarrollar una solución tecnológica que sea escalable, mantenible y fácil de extender, permitiendo gestionar usuarios, libros, préstamos y reportes de manera clara y estructurada.

---

## Requerimientos funcionales

### 1. Usuarios
- Registrar usuarios con nombre, teléfono y correo
- Consultar usuarios registrados
- Editar usuarios
- Eliminar usuarios

### 2. Libros
- Registrar libros con título, autor, categoría y disponibilidad
- Consultar libros registrados
- Editar libros
- Eliminar libros
- Solo se pueden prestar libros disponibles

### 3. Préstamos
- Registrar préstamos de libros a usuarios
- Un préstamo puede contener uno o más libros
- Permitir agregar y eliminar libros dentro de un préstamo antes de finalizarlo
- Registrar devoluciones
- Un usuario puede tener múltiples préstamos
- Cambiar automáticamente la disponibilidad de cada libro al prestarlo o devolverlo

### 4. Multas
- Calcular multa si uno o más libros se devuelven con retraso
- Permitir cambiar la forma de cálculo sin modificar el sistema
- En el futuro se podrán agregar nuevas políticas de multa

### 5. Reportes
- Cantidad de préstamos realizados
- Cantidad de libros disponibles
- Total de multas generadas

---

## Requerimientos técnicos

El sistema debe cumplir con:

- Uso de clases bien definidas  
- Uso de encapsulación  
- Uso de abstracción  
- Uso de interfaces  
- Uso de clases estáticas para utilidades  
- Organización clara del código  
- Aplicación de principios SOLID  

---

## Estructura esperada del proyecto

```text
src/main/java/org/booktrack/
├── Main.java
├── models/
├── interfaces/
├── repository/
├── services/
├── views/
└── utils/
```

## Decisiones de diseño

- Uso de identificadores automáticos mediante `GeneradorId`
- Almacenamiento en memoria (sin base de datos)
- Interfaz de usuario mediante consola
- Separación clara de responsabilidades en capas
- Uso de interfaces para desacoplar el sistema
- Cálculo de multas desacoplado mediante interfaz
- Uso de `ItemPrestamo` para modelar la relación entre préstamos y libros (composición)

## Reglas de implementación

- No incluir lógica de negocio en las clases modelo
- Los servicios manejan la lógica del sistema
- Los repositorios gestionan almacenamiento
- El menú interactúa únicamente con los servicios
- El cálculo de multas se implementa mediante una interfaz
- La disponibilidad de los libros debe actualizarse automáticamente

---

## Aplicación de SOLID

### Single Responsibility Principle
Cada clase tiene una única responsabilidad:
- `Usuario`, `Libro`, `Prestamo`, `ItemPrestamo` representan entidades del dominio
- `ServicioPrestamos` gestiona la lógica del sistema
- `CalculadoraMulta` gestiona el cálculo de multas

### Open/Closed Principle
El sistema permite agregar nuevas formas de cálculo de multas sin modificar la lógica existente.

### Liskov Substitution Principle
Cualquier implementación de `CalculadoraMulta` puede sustituir a otra sin afectar el comportamiento del sistema.

### Interface Segregation Principle
Las interfaces están separadas según su responsabilidad:
- repositorios
- cálculo de multas

### Dependency Inversion Principle
Los servicios dependen de abstracciones y no de implementaciones concretas.

---

## Diagrama UML

```mermaid
classDiagram

class Usuario {
    -id
    -nombre
    -telefono
    -correo
}

class Libro {
    -id
    -titulo
    -autor
    -categoria
    -disponible
}

class Prestamo {
    -id
    -devuelto
    +calcularMulta()
}

class ItemPrestamo {
    -cantidad
}

class CalculadoraMulta {
    <<interface>>
    +calcular(diasRetraso)
}

class CalculadoraMultaBasica {
    +calcular(diasRetraso)
}

class RepositorioUsuarios {
    <<interface>>
}

class RepositorioLibros {
    <<interface>>
}

class RepositorioPrestamos {
    <<interface>>
}

class ServicioUsuarios
class ServicioLibros
class ServicioPrestamos

Prestamo "1" *-- "N" ItemPrestamo : contiene
ItemPrestamo "N" --> "1" Libro : referencia
Prestamo "N" --> "1" Usuario : pertenece a

CalculadoraMulta <|.. CalculadoraMultaBasica : implementa

ServicioPrestamos ..> CalculadoraMulta : usa
ServicioUsuarios ..> RepositorioUsuarios
ServicioLibros ..> RepositorioLibros
ServicioPrestamos ..> RepositorioPrestamos
