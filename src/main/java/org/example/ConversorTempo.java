package org.example;

public class ConversorTempo {

    private ConversorTempo(){}

    public static String formatoMinutos (int minutos){
        if  (minutos < 0){
            throw new IllegalArgumentException("minutos deve ser positivo");
        }
        int horas = minutos/60;
        int mins = minutos % 60;

        return String.format("%02d:%02d", horas, mins);
    }
}