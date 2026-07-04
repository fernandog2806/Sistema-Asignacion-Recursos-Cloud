package ar.edu.unahur.obj2.cloud.comandos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ar.edu.unahur.obj2.cloud.Operacion;
import ar.edu.unahur.obj2.cloud.excepciones.LimiteSobreAsignacionException;

public class PlanDeDespliegue implements Operacion {
    private List<Operacion> operacionesInternas = new ArrayList<>();

    public void agregarAlPlan(Operacion nuevaOperacion) {
        this.operacionesInternas.add(nuevaOperacion);
    }

    @Override
    public void ejecutar() throws LimiteSobreAsignacionException {
        List<Operacion> operacionesEjecutadasConExito = new ArrayList<>();
        try {
            this.operacionesInternas.forEach(operacionActual -> {
                try {
                    operacionActual.ejecutar();
                    operacionesEjecutadasConExito.add(operacionActual);
                } catch (LimiteSobreAsignacionException errorDeSobreAsignacion) {
                    throw new RuntimeException(errorDeSobreAsignacion);
                }
            });
        } catch (RuntimeException errorInesperado) {
            Collections.reverse(operacionesEjecutadasConExito);
            operacionesEjecutadasConExito.forEach(operacionARevertir -> operacionARevertir.deshacer());

            if (errorInesperado.getCause() instanceof LimiteSobreAsignacionException) {
                throw (LimiteSobreAsignacionException) errorInesperado.getCause();
            }
            throw errorInesperado;
        }
    }

    @Override
    public void deshacer() {
        Collections.reverse(this.operacionesInternas);
        this.operacionesInternas.forEach(operacionADeshacer -> operacionADeshacer.deshacer());
        Collections.reverse(this.operacionesInternas);
    }
}
