package ar.edu.unahur.obj2.cloud;

public class ClusterDeComputo { 
    private String identificador;
    private Double vcpusDisponibles;

    public ClusterDeComputo(String identificador, Double vcpusDisponibles) {
        this.identificador = identificador;
        this.vcpusDisponibles = vcpusDisponibles;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public Double getVcpusDisponibles() {
        return this.vcpusDisponibles;
    }

    public void asignarCapacidad(Double vcpus) {
        this.vcpusDisponibles -= vcpus;
    }

    public void liberarCapacidad(Double vcpus) {
        this.vcpusDisponibles += vcpus;
    }
}
