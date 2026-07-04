package ar.edu.unahur.obj2.cloud.comandos;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.cloud.Operacion;

public class PlanificadorDeDespliegues {
    private List<Operacion> operacionesPendientes = new ArrayList<>();

    public void ejecutarIndividual(Operacion operacionAVisualizar) throws Exception {
        operacionAVisualizar.ejecutar();
    }

    public void registrarEnPlan(Operacion nuevaOperacion) {
        this.operacionesPendientes.add(nuevaOperacion);
    }

    public void ejecutarPlanPendiente() throws Exception {
        try {
            this.operacionesPendientes.forEach(operacionActual -> {
                try {
                    operacionActual.ejecutar();
                } catch (Exception errorDeNegocio) {
                    throw new RuntimeException(errorDeNegocio);
                }
            });
        } catch (RuntimeException errorInesperado) {
            if (errorInesperado.getCause() instanceof Exception) {
                throw (Exception) errorInesperado.getCause();
            }
            throw errorInesperado;
        } finally {

            this.operacionesPendientes.clear();
        }
    }
}