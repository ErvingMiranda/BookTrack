package org.booktrack.multas;

import org.booktrack.interfaces.CalculadoraMulta;

public class CalculadoraMultaBasica implements CalculadoraMulta {

    @Override
    public double calcular(int diasRetraso) {
        if (diasRetraso <= 0) {
            return 0;
        }
        return diasRetraso * 1.0;
    }
}
