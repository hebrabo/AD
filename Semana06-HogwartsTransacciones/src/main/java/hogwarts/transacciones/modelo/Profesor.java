package hogwarts.transacciones.modelo;

import java.time.LocalDate;

public class Profesor {
    private int id_profesor; // solo se usa al leer desde la BD
    private String nombre;
    private String apellido;
    private LocalDate fecha_inicio;
    private Asignatura asignatura; // solo se usa al mostrar datos

    // Constructor para insertar (sin ID ni asignatura)
    public Profesor(String nombre, String apellido, LocalDate fecha_inicio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_inicio = fecha_inicio;
    }

    // Constructor para leer desde BD (con ID y asignatura)
    public Profesor(int id_profesor, String nombre, String apellido, LocalDate fecha_inicio, Asignatura asignatura) {
        this.id_profesor = id_profesor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_inicio = fecha_inicio;
        this.asignatura = asignatura;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id_profesor +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaInicio=" + fecha_inicio +
                ", asignatura=" + asignatura +
                '}';
    }
}

