package ar.edu.unahur.obj2.cloud;

import ar.edu.unahur.obj2.cloud.excepciones.LimiteSobreAsignacionException;

public interface Operacion {
    void ejecutar() throws LimiteSobreAsignacionException;

    void deshacer();
}
