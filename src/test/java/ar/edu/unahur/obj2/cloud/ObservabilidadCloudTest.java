package ar.edu.unahur.obj2.cloud;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.unahur.obj2.cloud.observadores.CloudTrail;
import ar.edu.unahur.obj2.cloud.observadores.NotificacionSRE;
import ar.edu.unahur.obj2.cloud.observadores.AlarmaSaturacionCritica;

public class ObservabilidadCloudTest {

    @Test
    public void testMovimientosDeRecursosNotificanALosSistemasSuscritosDinamicos() {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("US-EAST-1", 100.0);
        CloudTrail auditoria = new CloudTrail();
        NotificacionSRE notificadorSre = new NotificacionSRE();
        AlarmaSaturacionCritica alarma = new AlarmaSaturacionCritica();

        clusterPrueba.registrarObservador(auditoria);
        clusterPrueba.registrarObservador(notificadorSre);
        clusterPrueba.registrarObservador(alarma);

        clusterPrueba.liberarCapacidad(50.0);
        assertEquals("LOG: Liberacion de 50.0 vCPUs en US-EAST-1", auditoria.obtenerRegistroUnificado().get(0));
        assertEquals("Se han liberado 50.0 vCPUs en el cluster US-EAST-1",
                notificadorSre.obtenerHistorialDeNotificaciones().get(0));
        assertEquals(0, alarma.obtenerAlertasDisparadas().size());

        clusterPrueba.asignarCapacidad(200.0);
        assertEquals("ADVERTENCIA: Cluster US-EAST-1 operando en zona de sobre-asignacion de recursos",
                alarma.obtenerAlertasDisparadas().get(0));
    }

    @Test
    public void testEliminarUnObservadorHaceQueDejeDeRecibirVariaciones() {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("US-EAST-1", 100.0);
        CloudTrail auditoria = new CloudTrail();

        clusterPrueba.registrarObservador(auditoria);
        clusterPrueba.eliminarObservador(auditoria);

        clusterPrueba.liberarCapacidad(50.0);
        assertEquals(0, auditoria.obtenerRegistroUnificado().size());
    }
}
