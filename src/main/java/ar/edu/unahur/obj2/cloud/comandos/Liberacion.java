package ar.edu.unahur.obj2.cloud.comandos;

import ar.edu.unahur.obj2.cloud.ClusterDeComputo;
import ar.edu.unahur.obj2.cloud.Operacion;
import ar.edu.unahur.obj2.cloud.excepciones.ValorInvalidoException;
import ar.edu.unahur.obj2.cloud.excepciones.LimiteSobreAsignacionException;

public class Liberacion implements Operacion {
    private ClusterDeComputo clusterObjetivo;
    private Double unidadesDeComputoALiberar;

    public Liberacion(ClusterDeComputo clusterDestino, Double cantidadDeVcpus) {
        if (cantidadDeVcpus <= 0) {
            throw new ValorInvalidoException("La cantidad de vCPUs debe ser mayor a 0");
        }
        this.clusterObjetivo = clusterDestino;
        this.unidadesDeComputoALiberar = cantidadDeVcpus;
    }

    @Override
    public void ejecutar() throws LimiteSobreAsignacionException {
        this.clusterObjetivo.liberarCapacidad(this.unidadesDeComputoALiberar);
    }

    @Override
    public void deshacer() {
        this.clusterObjetivo.asignarCapacidad(this.unidadesDeComputoALiberar);
    }
}
