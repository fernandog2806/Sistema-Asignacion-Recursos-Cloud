package ar.edu.unahur.obj2.cloud.observadores;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.cloud.ClusterDeComputo;

public class NotificacionSRE implements ObservadorCluster {
    private List<String> historialDeNotificaciones = new ArrayList<>();

    @Override
    public void notificarCambio(ClusterDeComputo clusterAfectado, String tipoDeMovimiento, Double cantidadDeVcpus) {
        String mensajeDeAlerta = "";
        if (tipoDeMovimiento.equals("Liberacion")) {
            mensajeDeAlerta = "Se han liberado " + cantidadDeVcpus + " vCPUs en el cluster "
                    + clusterAfectado.obtenerIdentificador();
        } else if (tipoDeMovimiento.equals("Asignacion")) {
            mensajeDeAlerta = "Se han asignado " + cantidadDeVcpus + " vCPUs en el cluster "
                    + clusterAfectado.obtenerIdentificador();
        }
        this.historialDeNotificaciones.add(mensajeDeAlerta);
    }

    public List<String> obtenerHistorialDeNotificaciones() {
        return this.historialDeNotificaciones;
    }
}
