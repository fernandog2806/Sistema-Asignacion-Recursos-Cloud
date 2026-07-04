package ar.edu.unahur.obj2.cloud;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.unahur.obj2.cloud.comandos.PlanDeDespliegue;
import ar.edu.unahur.obj2.cloud.comandos.Asignacion;
import ar.edu.unahur.obj2.cloud.comandos.Liberacion;
import ar.edu.unahur.obj2.cloud.excepciones.LimiteSobreAsignacionException;

public class PlanDespliegueTest {

    @Test
    public void testPlanDeDespliegueConFallaPorSobreAsignacionInterrumpeYHaceRollbackAlEstadoOriginal() {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 100.0);
        PlanDeDespliegue plan = new PlanDeDespliegue();

        plan.agregarAlPlan(new Asignacion(clusterPrueba, 150.0));
        plan.agregarAlPlan(new Asignacion(clusterPrueba, 300.0));

        assertThrows(LimiteSobreAsignacionException.class, () -> plan.ejecutar());
        assertEquals(100.0, clusterPrueba.obtenerVcpusDisponibles());
    }

    @Test
    public void testDeshacerUnPlanCompletoRevierteTodasLasOperacionesInternas() throws Exception {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);
        PlanDeDespliegue plan = new PlanDeDespliegue();
        plan.agregarAlPlan(new Asignacion(clusterPrueba, 100.0));
        plan.agregarAlPlan(new Liberacion(clusterPrueba, 200.0));

        plan.ejecutar();
        plan.deshacer();
        assertEquals(500.0, clusterPrueba.obtenerVcpusDisponibles());
    }

    @Test
    public void testPlanConFallaInesperadaLanzaRuntimeExceptionYTambienAplicaRollback() {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);
        PlanDeDespliegue plan = new PlanDeDespliegue();
        plan.agregarAlPlan(new Asignacion(clusterPrueba, 100.0));
        plan.agregarAlPlan(new Operacion() {
            @Override
            public void ejecutar() throws LimiteSobreAsignacionException {
                throw new NullPointerException("Error electrico inesperado");
            }

            @Override
            public void deshacer() {
            }
        });

        assertThrows(RuntimeException.class, () -> plan.ejecutar());
        assertEquals(500.0, clusterPrueba.obtenerVcpusDisponibles());
    }
}
