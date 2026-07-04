package ar.edu.unahur.obj2.cloud.observadores;

import ar.edu.unahur.obj2.cloud.ClusterDeComputo;

public interface ObservadorCluster {
    void notificarCambio(ClusterDeComputo clusterAfectado, String tipoDeMovimiento, Double cantidadDeVcpus);
}
