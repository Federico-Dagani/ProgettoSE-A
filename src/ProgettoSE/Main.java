package ProgettoSE;

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Attori.Gestore;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.mylib.MyMenu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        benvenuto();

        Gestore gestore = new Gestore("CAPO", null);

        LocalDate data_attuale = LocalDate.parse("2023-03-02");

        inizializzazione(gestore);

        MyMenu menu_attori = nuovoMenu(Costanti.ATTORI);
        switch (menu_attori.scegliConUscita()){

            case 0:
                System.out.println(Costanti.USCITA_MENU + Costanti.ATTORI.toUpperCase(Locale.ROOT));
                System.out.println(Costanti.END);
                break;

            case 1:
                MyMenu menu_gestore = nuovoMenu(Costanti.GESTORE);
                scegliFunzionalitaGestore(menu_gestore.scegliConUscita(), gestore);
                break;

            case 2:
                break;
        }

        //stampaMenuDelGiorno(gestore, data_attuale);

    }

    private static void benvenuto(){
        System.out.println(Costanti.CORNICE_SUP);
        System.out.printf("|\t"+Costanti.BENVENUTO+"\t|\n");
        System.out.println(Costanti.CORNICE_INF);
        System.out.println();
    }

    private static void scegliFunzionalitaGestore(int scelta, Gestore gestore){
        switch (scelta){
            case 0:
                break;
            case 1:
                System.out.println("\n Il carico di lavoro per persona è: " + gestore.getRistorante().getLavoro_persona());
                break;
            case 2:
                System.out.println("\n Il numero dei posti disponibili nel ristorante è: " + gestore.getRistorante().getN_posti());
                break;
            case 3:
                mostraAlimento(gestore.getRistorante().getMagazziniere().getMagazzino().getBevande());
                break;
            case 4:
                mostraAlimento(gestore.getRistorante().getMagazziniere().getMagazzino().getExtras());
                break;
            case 5: mostraConsumoProcapite(gestore.getRistorante().getMagazziniere().getMagazzino().getBevande());
                break;
            case 6: mostraConsumoProcapite(gestore.getRistorante().getMagazziniere().getMagazzino().getExtras());
                break;
            case 7:mostraPiatti(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;
            case 8:mostraRicette(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;
            case 9:mostraMenuTematici(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;

        }

    }


    private static void mostraMenuTematici(ArrayList<Prenotabile> menu){
        System.out.println("\n\nI menu tematici del menù alla carta sono i seguenti: ");
        for( Prenotabile prenotabile : menu){
            if (prenotabile instanceof  MenuTematico){
                MenuTematico menuTematico = (MenuTematico) prenotabile;
                System.out.println("\nNome " + menuTematico.getNome());
                System.out.println("Periodi disponibilità: ");
                int inizio=0;
                for(int i=0; i < menuTematico.getDisponibilità().toArray().length/2; i++){
                    System.out.println("Inizio: " + menuTematico.getDisponibilità().get(inizio) + "\tFine: " + menuTematico.getDisponibilità().get(inizio+1));
                    inizio += 2;
                }
            }
        }
    }
    private static void mostraPiatti(ArrayList<Prenotabile> menu){
        System.out.println("\n\nI piatti del menù alla carta sono i seguenti: ");
        for( Prenotabile prenotabile : menu){
         if (prenotabile instanceof  Piatto){
             Piatto piatto = (Piatto) prenotabile;
             System.out.println("\nNome " + piatto.getNome());
             System.out.println("Periodi disponibilità: ");
             int inizio=0;
             for(int i=0; i < piatto.getDisponibilità().toArray().length/2; i++){
                 System.out.println("Inizio: " + piatto.getDisponibilità().get(inizio) + "\tFine: " + piatto.getDisponibilità().get(inizio+1));
                 inizio += 2;
             }
         }
      }
    }
    private static void mostraRicette(ArrayList<Prenotabile> menu){
        System.out.println("\n\nLe ricette del menù alla carta sono le seguenti: ");
        for( Prenotabile prenotabile : menu){
            if (prenotabile instanceof  Piatto){
                Piatto piatto = (Piatto) prenotabile;
                System.out.println("\n" + piatto.getNome().toUpperCase());
                System.out.println("Ricetta: ");
                for(Alimento ingrediente : piatto.getRicetta().getIngredienti()){
                    System.out.println("  °\t"  + ingrediente.getQta() + " " + ingrediente.getMisura() + " di " + ingrediente.getNome());
                }
            }
        }
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
                    for (Alimento ingrediente : piatto.getRicetta().getIngredienti()) {
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

    private static void mostraAlimento(ArrayList<Alimento> alimenti){
        if(alimenti.get(0) instanceof Bevanda){
            System.out.println("\nLista delle bevande presenti nel ristorante: ");
        }else if(alimenti.get(0) instanceof Extra){
            System.out.println("\nLista degli extra presenti nel ristorante:");
        }
        for (Alimento alimento : alimenti){
            System.out.printf("- " + alimento.getNome() + "\n");
            //System.out.printf("quantità: " + alimento.getQta() + " " + alimento.getMisura() + "\n");
        }
    }

    private static void mostraConsumoProcapite(ArrayList<Alimento> alimenti){
        if(alimenti.get(0) instanceof Bevanda){
            System.out.println("\nLista delle bevande con i relativi consumi procapite: ");
        }else if(alimenti.get(0) instanceof Extra){
            System.out.println("\nLista degli extra con i relativi consumi procapite: ");
        }
        for(Alimento alimento : alimenti){
            if(alimento instanceof Bevanda){
                System.out.printf("- " + alimento.getNome() + ", ");
                System.out.printf("consumo procapite: " + ((Bevanda) alimento).getCons_procapite() + "\n");
            }else if(alimento instanceof Extra){
                System.out.printf("- " + alimento.getNome() + ", ");
                System.out.printf("consumo procapite: " + ((Extra)alimento).getCons_procapite() + "\n");
            }
        }

    }
}
