package org.booktrack.repository;

import org.booktrack.interfaces.CalculadoraMulta;

public class CalculadoraMultaBasica implements CalculadoraMulta {

    @Override
    public double calcular(int diasRetraso) {
        if (diasRetraso <= 0) {
            return 0;
        }
        return diasRetraso * 5.0;
    }
}
