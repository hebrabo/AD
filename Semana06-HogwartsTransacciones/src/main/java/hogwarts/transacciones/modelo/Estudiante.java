package hogwarts.transacciones.modelo;

import java.time.LocalDate;

public class Estudiante {
    private String nombre;
    private String apellido;
    private int anyo_curso;
    private LocalDate fecha_nacimiento;

    public Estudiante(String nombre, String apellido, int anyo_curso, LocalDate fecha_nacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.anyo_curso = anyo_curso;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getAnyo_curso() {
        return anyo_curso;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", anyo_curso=" + anyo_curso +
                ", fecha_nacimiento=" + fecha_nacimiento +
                '}';
    }
}
