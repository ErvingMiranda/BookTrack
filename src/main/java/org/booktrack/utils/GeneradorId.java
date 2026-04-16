package org.booktrack.utils;

import java.util.UUID;

public final class GeneradorId {

    private GeneradorId() {
        throw new IllegalStateException("Clase utilitaria");
    }

    public static String generar() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
