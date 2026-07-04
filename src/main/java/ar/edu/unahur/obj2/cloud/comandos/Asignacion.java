package ar.edu.unahur.obj2.cloud.comandos;

import ar.edu.unahur.obj2.cloud.ClusterDeComputo;
import ar.edu.unahur.obj2.cloud.Operacion;
import ar.edu.unahur.obj2.cloud.excepciones.ValorInvalidoException;
import ar.edu.unahur.obj2.cloud.excepciones.LimiteSobreAsignacionException;

public class Asignacion implements Operacion {
    private ClusterDeComputo clusterObjetivo;
    private Double unidadesDeComputoAAsignar;

    public Asignacion(ClusterDeComputo clusterDestino, Double cantidadDeVcpus) {
        if (cantidadDeVcpus <= 0) {
            throw new ValorInvalidoException("La cantidad de vCPUs debe ser mayor a 0");
        }
        this.clusterObjetivo = clusterDestino;
        this.unidadesDeComputoAAsignar = cantidadDeVcpus;
    }

    @Override
    public void ejecutar() throws LimiteSobreAsignacionException {
        // 🔌 Conectamos con el nuevo nombre: obtenerVcpusDisponibles()
        Double capacidadFuturaDisponible = this.clusterObjetivo.obtenerVcpusDisponibles()
                - this.unidadesDeComputoAAsignar;

        if (capacidadFuturaDisponible < -200.0) {
            throw new LimiteSobreAsignacionException("Error: Se supera el limite de sobre-asignacion de -200 vCPUs");
        }
        this.clusterObjetivo.asignarCapacidad(this.unidadesDeComputoAAsignar);
    }

    @Override
    public void deshacer() {
        this.clusterObjetivo.liberarCapacidad(this.unidadesDeComputoAAsignar);
    }
}
