package ProgettoSE;

import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Attori.Gestore;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.mylib.MyMenu;

import java.time.LocalDate;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        benvenuto();

        Gestore gestore = new Gestore("CAPO", null);

        LocalDate data_attuale = LocalDate.parse("2023-03-02");

        inizializzazione(gestore);

        MyMenu menu_attori = nuovoMenu(Costanti.ATTORI);
        switch (menu_attori.scegliConUscita()){

            case 1:
                MyMenu menu_gestore = nuovoMenu(Costanti.GESTORE);
                menu_gestore.scegliConUscita();
                break;
        }

        stampaMenuDelGiorno(gestore, data_attuale);

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

            case Costanti.GESTORE:

                String[] azioni_gestore = new String[9];
                azioni_gestore[0] = "carico di lavoro per persona";
                azioni_gestore[1] = "numero di posti a sedere disponibili";
                azioni_gestore[2] = "insieme delle bevande";
                azioni_gestore[3] = "insieme dei generi extra";
                azioni_gestore[4] = "consumo pro-capite di bevande";
                azioni_gestore[5] = "consumo pro-capite di generi extra";
                azioni_gestore[6] = "informazioni di ciascun piatto";
                azioni_gestore[7] = "ricette presenti";
                azioni_gestore[8] = "menu tematici presenti";
                MyMenu menu_gestore = new MyMenu(Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + Costanti.GESTORE.toUpperCase(Locale.ROOT), azioni_gestore);
                return menu_gestore;

            case Costanti.UTENTE:

                String[] azioni_utente = new String[0];

                MyMenu menu_utente = new MyMenu(Costanti.UTENTE.toUpperCase(Locale.ROOT), azioni_utente);
                return menu_utente;
        }
        return null;
    }

    private static void inizializzazione(Gestore gestore){
        System.out.printf("Il gestore sta inizializzando il ristorante ...");
        gestore.inizializzaRistorante();
        System.out.println("Inizializzazione automatica del ristorante completata.");
        System.out.println();
    }

    private static void stampaMenuDelGiorno(Gestore gestore, LocalDate data){
        if (gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data).isEmpty())
            System.out.println("Non ci sono piatti disponibili per il giorno " + data);
        else {
            System.out.println("Il menù disponibile per il giorno " + data + " offre queste specialità:\n");
            for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data)) {
                if (prenotabile instanceof Piatto) {
                    Piatto piatto = (Piatto) prenotabile;
                    System.out.println(piatto.getNome().toUpperCase());
                    System.out.printf("(Ingredienti: ");
                    for (Ingrediente ingrediente : piatto.getRicetta().getIngredienti()) {
                        System.out.printf("" + ingrediente.getNome() + ", ");
                    }
                    System.out.printf(")\n\n");

                } else if (prenotabile instanceof MenuTematico) {
                    MenuTematico menu_tematico = (MenuTematico) prenotabile;
                    System.out.println("MENU' " + menu_tematico.getNome().toUpperCase());
                    System.out.printf("Piatti: ");
                    for (Piatto piatto : menu_tematico.getPiatti_menu()) {
                        System.out.printf("" + piatto.getNome() + ", ");
                    }
                    System.out.printf("\n\n");

                }
            }
        }
    }
}
