package ProgettoSE;

import ProgettoSE.Attori.Gestore;
import ProgettoSE.mylib.MyMenu;
import ProgettoSE.Costanti;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        benvenuto();

        Gestore gestore = new Gestore("CAPO", null);

        inizializzazione(gestore);

        MyMenu menu_attori = nuovoMenu(Costanti.ATTORI);
        int scelta_utente = menu_attori.scegliConUscita();


    }

    private static void benvenuto(){
        System.out.println(Costanti.CORNICE_SUP);
        System.out.printf("|\t"+Costanti.BENVENUTO+"\t|\n");
        System.out.println(Costanti.CORNICE_INF);
        System.out.println();
    }

    /**
     * <h3>Metodo per la creazione dei vari menu</h3>
     * @param funzione corrispondente alla tipologia del menu da creare
     * @return menu in base alla funzione specificata come parametro
     */
    private static MyMenu nuovoMenu(String funzione){

        switch (funzione){

            case Costanti.ATTORI:

                String[] utenti = new String[2];
                utenti[0] = Costanti.GESTORE;
                utenti[1] = Costanti.UTENTE;
                MyMenu menu_attori = new MyMenu(Costanti.ATTORI.toUpperCase(Locale.ROOT), utenti);
                return menu_attori;
        }
        return null;
    }

    private static void inizializzazione(Gestore gestore){
        System.out.printf("Il gestore sta inizializzando il ristorante ...");
        gestore.inizializzaRistorante();
        System.out.println("Inizializzazione automatica del ristorante completata.");
        System.out.println();
    }
}
