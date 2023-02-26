package ProgettoSE;

import ProgettoSE.Attori.Gestore;

public class Main {
    public static void main(String[] args) {

        Gestore gestore = new Gestore("CAPO", null);

        gestore.inizializzaRistorante();

        System.out.println("ciao");

    }
}
