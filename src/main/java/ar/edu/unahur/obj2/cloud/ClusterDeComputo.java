package ar.edu.unahur.obj2.cloud;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.cloud.observadores.ObservadorCluster;

public class ClusterDeComputo {
    private String identificador;
    private Double vcpusDisponibles;
    private List<ObservadorCluster> listaDeObservadoresInteresados = new ArrayList<>();

    public ClusterDeComputo(String identificadorInicial, Double capacidadInicialEnVcpus) {
        this.identificador = identificadorInicial;
        this.vcpusDisponibles = capacidadInicialEnVcpus;
    }

    public String obtenerIdentificador() {
        return this.identificador;
    }

    public Double obtenerVcpusDisponibles() {
        return this.vcpusDisponibles;
    }

    public void asignarCapacidad(Double cantidadDeUnidadesAAsignar) {
        this.vcpusDisponibles -= cantidadDeUnidadesAAsignar;
        this.notificarCambioDeCapacidadALosSistemasInteresados("Asignacion", cantidadDeUnidadesAAsignar);
    }

    public void liberarCapacidad(Double cantidadDeUnidadesALiberar) {
        this.vcpusDisponibles += cantidadDeUnidadesALiberar;
        this.notificarCambioDeCapacidadALosSistemasInteresados("Liberacion", cantidadDeUnidadesALiberar);
    }

    public void registrarObservador(ObservadorCluster nuevoSistemaInteresado) {
        this.listaDeObservadoresInteresados.add(nuevoSistemaInteresado);
    }

    public void eliminarObservador(ObservadorCluster sistemaInteresadoARemover) {
        this.listaDeObservadoresInteresados.remove(sistemaInteresadoARemover);
    }

    private void notificarCambioDeCapacidadALosSistemasInteresados(String tipoDeMovimiento,
            Double cantidadDeVcpusInvolucradas) {
        this.listaDeObservadoresInteresados.forEach(sistemaInteresadoActual -> sistemaInteresadoActual
                .notificarCambio(this, tipoDeMovimiento, cantidadDeVcpusInvolucradas));
    }
}
