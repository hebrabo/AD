package hogwarts.transacciones.modelo;

public class Asignatura {
    private int id_asignatura; // solo se usa cuando leemos desde la BD
    private String nombreAsignatura;
    private String aula;
    private boolean obligatoria;

    // Constructor para insertar (sin ID)
    public Asignatura(String nombreAsignatura, String aula, boolean obligatoria) {
        this.nombreAsignatura = nombreAsignatura;
        this.aula = aula;
        this.obligatoria = obligatoria;
    }

    // Constructor para leer desde BD (con ID)
    public Asignatura(int id_asignatura, String nombreAsignatura, String aula, boolean obligatoria) {
        this.id_asignatura = id_asignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.aula = aula;
        this.obligatoria = obligatoria;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getAula() {
        return aula;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "id=" + id_asignatura +
                ", nombre='" + nombreAsignatura + '\'' +
                ", aula='" + aula + '\'' +
                ", obligatoria=" + obligatoria +
                '}';
    }
}


