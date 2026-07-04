package ar.edu.unahur.obj2.cloud.observadores;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.cloud.ClusterDeComputo;

public class AlarmaSaturacionCritica implements ObservadorCluster {
    private List<String> alertasDisparadas = new ArrayList<>();

    @Override
    public void notificarCambio(ClusterDeComputo clusterAfectado, String tipoDeMovimiento, Double cantidadDeVcpus) {
        if (clusterAfectado.obtenerVcpusDisponibles() <= 0.0) {
            String advertenciaDeSaturacion = "ADVERTENCIA: Cluster " + clusterAfectado.obtenerIdentificador()
                    + " operando en zona de sobre-asignacion de recursos";
            this.alertasDisparadas.add(advertenciaDeSaturacion);
        }
    }

    public List<String> obtenerAlertasDisparadas() {
        return this.alertasDisparadas;
    }
}
