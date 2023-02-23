package ProgettoSE;

import ProgettoSE.Attori.Gestore;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Gestore gestore = new Gestore("CAPO", null);

        gestore.inizializzaRistorante();

        System.out.println("ciao");

    }
}
