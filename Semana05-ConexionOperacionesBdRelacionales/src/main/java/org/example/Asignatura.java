package org.example;

public class Asignatura {
    private int id_asignatura;
    private String nombre_asignatura;
    private String aula;
    private boolean obligatoria;

    public Asignatura(int id_asignatura, String nombre_asignatura, String aula, boolean obligatoria) {
        this.id_asignatura = id_asignatura;
        this.nombre_asignatura = nombre_asignatura;
        this.aula = aula;
        this.obligatoria = obligatoria;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public String getNombre_asignatura() {
        return nombre_asignatura;
    }

    public String getAula() {
        return aula;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setId_asignatura(int id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public void setNombre_asignatura(String nombre_asignatura) {
        this.nombre_asignatura = nombre_asignatura;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "id_asignatura=" + id_asignatura +
                ", nombre_asignatura='" + nombre_asignatura + '\'' +
                ", aula='" + aula + '\'' +
                ", obligatoria=" + obligatoria +
                '}';
    }
}
