package ar.edu.unahur.obj2.cloud;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.unahur.obj2.cloud.comandos.Asignacion;
import ar.edu.unahur.obj2.cloud.comandos.Liberacion;
import ar.edu.unahur.obj2.cloud.comandos.PlanificadorDeDespliegues;
import ar.edu.unahur.obj2.cloud.excepciones.ValorInvalidoException;
import ar.edu.unahur.obj2.cloud.excepciones.LimiteSobreAsignacionException;

public class OperacionesCloudTest {

    @Test
    public void testEjecutarYDeshacerAsignacionIndividual() throws Exception {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);
        Asignacion asignacion = new Asignacion(clusterPrueba, 100.0);

        asignacion.ejecutar();
        assertEquals(400.0, clusterPrueba.obtenerVcpusDisponibles());

        asignacion.deshacer();
        assertEquals(500.0, clusterPrueba.obtenerVcpusDisponibles());
    }

    @Test
    public void testEjecutarYDeshacerLiberacionIndividual() throws Exception {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);
        Liberacion liberacion = new Liberacion(clusterPrueba, 100.0);

        liberacion.ejecutar();
        assertEquals(600.0, clusterPrueba.obtenerVcpusDisponibles());

        liberacion.deshacer();
        assertEquals(500.0, clusterPrueba.obtenerVcpusDisponibles());
    }

    @Test
    public void testInstanciarOperacionesConValoresMenoresOIgualesACeroLanzaValorInvalidoException() {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);

        assertThrows(ValorInvalidoException.class, () -> new Asignacion(clusterPrueba, 0.0));
        assertThrows(ValorInvalidoException.class, () -> new Liberacion(clusterPrueba, -50.0));
    }

    @Test
    public void testPlanificadorEjecutaLoteDePendientesYVaciaLaColaAlFinal() throws Exception {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);
        PlanificadorDeDespliegues planificador = new PlanificadorDeDespliegues();

        planificador.registrarEnPlan(new Asignacion(clusterPrueba, 200.0));
        planificador.registrarEnPlan(new Liberacion(clusterPrueba, 100.0));

        planificador.ejecutarPlanPendiente();
        assertEquals(400.0, clusterPrueba.obtenerVcpusDisponibles());

        planificador.ejecutarPlanPendiente();
        assertEquals(400.0, clusterPrueba.obtenerVcpusDisponibles());
    }

    @Test
    public void testPlanificadorConFallaCheckedEnListaDesenuelveLaExcepcionCorrectamente() {
        PlanificadorDeDespliegues planificador = new PlanificadorDeDespliegues();
        planificador.registrarEnPlan(new Operacion() {
            @Override
            public void ejecutar() throws LimiteSobreAsignacionException {
                throw new LimiteSobreAsignacionException("Falla controlada");
            }

            @Override
            public void deshacer() {
            }
        });

        assertThrows(LimiteSobreAsignacionException.class, () -> planificador.ejecutarPlanPendiente());
    }
}
