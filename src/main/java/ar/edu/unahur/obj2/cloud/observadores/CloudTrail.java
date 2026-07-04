package ar.edu.unahur.obj2.cloud.observadores;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.cloud.ClusterDeComputo;

public class CloudTrail implements ObservadorCluster {
    private List<String> registroUnificado = new ArrayList<>();

    @Override
    public void notificarCambio(ClusterDeComputo clusterAfectado, String tipoDeMovimiento, Double cantidadDeVcpus) {
        String lineaDeLog = "LOG: " + tipoDeMovimiento + " de " + cantidadDeVcpus + " vCPUs en "
                + clusterAfectado.obtenerIdentificador();
        this.registroUnificado.add(lineaDeLog);
    }

    public List<String> obtenerRegistroUnificado() {
        return this.registroUnificado;
    }
}
