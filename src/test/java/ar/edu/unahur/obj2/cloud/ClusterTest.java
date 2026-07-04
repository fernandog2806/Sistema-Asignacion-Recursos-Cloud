package ar.edu.unahur.obj2.cloud;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClusterTest {

    @Test
    public void testAsignarCapacidadReduceRecursosYLiberarCapacidadLosIncrementa() {
        ClusterDeComputo clusterPrueba = new ClusterDeComputo("CLUSTER-01", 500.0);

        clusterPrueba.asignarCapacidad(200.0);
        assertEquals(300.0, clusterPrueba.obtenerVcpusDisponibles());

        clusterPrueba.liberarCapacidad(100.0);
        assertEquals(400.0, clusterPrueba.obtenerVcpusDisponibles());
    }
}
