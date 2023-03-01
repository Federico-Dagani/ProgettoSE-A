package ProgettoSE;

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Attori.Cliente;
import ProgettoSE.Attori.Gestore;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.mylib.MyMenu;
import ProgettoSE.mylib.InputDati;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class Main {

    public static void main(String[] args) throws IOException {

        benvenuto();

        Gestore gestore = new Gestore("CAPO", null);

        Tempo data_attuale = new Tempo(LocalDate.now());

        inizializzazione(gestore);

        MyMenu menu_attori = nuovoMenu(Costanti.ATTORI);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int scelta_attore = menu_attori.scegliConUscita();
        while (scelta_attore != 0){
            switch (scelta_attore){

                case 1:
                    MyMenu menu_gestore = nuovoMenu(Costanti.GESTORE);

                    int scelta_funz_gestore = menu_gestore.scegliConUscita();
                    while (scelta_funz_gestore != 0){
                        scegliFunzionalitaGestore(scelta_funz_gestore, gestore);
                        System.out.println("Premere un tasto per continuare ... ");
                        br.readLine();
                        scelta_funz_gestore = menu_gestore.scegliConUscita();
                    }
                    System.out.println("\n" + Costanti.USCITA_MENU + Costanti.GESTORE.toUpperCase(Locale.ROOT));
                    break;

                case 2:
                    inserisciPrenotazione(gestore, data_attuale.getData_corrente());
                    break;
            }
            System.out.println("\n" + "Premere un tasto per continuare ... ");
            br.readLine();
            scelta_attore = menu_attori.scegliConUscita();
        }

        System.out.println("\n" + Costanti.USCITA_MENU + Costanti.ATTORI.toUpperCase(Locale.ROOT));
        System.out.println("\n" + Costanti.END);

        stampaMenuDelGiorno(gestore, data_attuale.getData_corrente());

    }

    private static void benvenuto(){
        System.out.println(Costanti.CORNICE_SUP);
        System.out.printf("|\t"+Costanti.BENVENUTO+"\t|\n");
        System.out.println(Costanti.CORNICE_INF);
        System.out.println();
    }

    private static void scegliFunzionalitaGestore(int scelta, Gestore gestore){
        switch (scelta){
            case 1: System.out.println("\nIl carico di lavoro per persona è: " + gestore.getRistorante().getLavoro_persona());
                break;
            case 2: System.out.println("\nIl numero dei posti disponibili nel ristorante è: " + gestore.getRistorante().getN_posti());
                break;
            case 3: mostraAlimento(gestore.getRistorante().getMagazziniere().getMagazzino().getBevande());
                break;
            case 4: mostraAlimento(gestore.getRistorante().getMagazziniere().getMagazzino().getExtras());
                break;
            case 5: mostraConsumoProcapite(gestore.getRistorante().getMagazziniere().getMagazzino().getBevande());
                break;
            case 6: mostraConsumoProcapite(gestore.getRistorante().getMagazziniere().getMagazzino().getExtras());
                break;
            case 7: mostraMenuTematici(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;
            case 8: mostraPiatti(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;
            case 9: mostraRicette(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;

        }
    }

    private static void inserisciPrenotazione(Gestore gestore, LocalDate data_attuale){
        //NOME
        String nome_cliente = InputDati.leggiStringaNonVuota("Nome cliente: ");

        //DATA
        String stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
        int msg = gestore.getRistorante().getAddettoPrenotazione().controlloDataPrenotazione(data_attuale,stringa_data_prenotazione, gestore.getRistorante().getN_posti());
        while (msg != 0){
            switch (msg) {
                case 1:
                    System.out.println("Formato data non valido.");
                    break;
                case 2:
                    System.out.println("La data inserita deve essere sucessiva alla data attuale (" + data_attuale + ")");
                    break;
                case 3:
                    System.out.println("Il ristorante non ha posti disponibili in questa data (" + stringa_data_prenotazione + ")");
            }
            stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
            msg = gestore.getRistorante().getAddettoPrenotazione().controlloDataPrenotazione(data_attuale, stringa_data_prenotazione, gestore.getRistorante().getN_posti());
        }
        LocalDate data_prenotazione = LocalDate.parse(stringa_data_prenotazione);

        //POSTI
        System.out.printf("Attenzione: abbiamo stimato che rimangono %d posti prenotabili, assumendo che ogni persona prenoti in media 2 piatti, rispettala ca**o!!", gestore.getRistorante().getAddettoPrenotazione().stimaPostiRimanenti(data_prenotazione, gestore.getRistorante().getLavoro_persona(), gestore.getRistorante().getN_posti()));

        int max = gestore.getRistorante().getN_posti() - gestore.getRistorante().getAddettoPrenotazione().calcolaPostiOccupati(data_prenotazione);
        int n_persone = InputDati.leggiInteroConMinimoMassimo("Numero persone: ", 1 , max );
        int n_coperti = n_persone;

        //SCELTE
        HashMap<Prenotabile, Integer> scelte = new HashMap<>();
        do {
            stampaMenuDelGiorno(gestore, data_prenotazione);

            Boolean validita = false;
            Prenotabile portata = null;
            Integer quantità = 0;
            do {
                if(quantità != 0)
                    System.out.println("Persone rimanenti senza una portata assegnata: " + n_persone + "\n");
                String scelta = InputDati.leggiStringaNonVuota("Inserisca il nome della portata da ordinare: ");
                for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data_prenotazione)) {
                    if (prenotabile.getNome().equalsIgnoreCase(scelta)) {
                        portata = prenotabile;
                        validita = true;
                    }else System.out.println("IL nome della portata non è corretto o non presente in menu.");
                }
                }while(!validita);
            quantità = InputDati.leggiInteroConMinimo("Inserisca le porzioni desiderate di " + portata.getNome().toLowerCase(Locale.ROOT) + ": ",1);
            scelte.put(portata, quantità);


            n_persone -= quantità;
        }while(n_persone > 0 || InputDati.yesOrNo("Vuoi ordinare altre portate?"));


        //CALCOLO CONSUMO BEVANDE E GENERI EXTRA
        HashMap<Alimento,Float> cons_bevande = gestore.getRistorante().getMagazziniere().calcolaConsumoAlimento(n_coperti, Costanti.BEVANDA);
        HashMap<Alimento,Float> cons_extra = gestore.getRistorante().getMagazziniere().calcolaConsumoAlimento(n_coperti, Costanti.EXTRA);

        //Costruzione Prenotazione
        Cliente cliente = new Cliente(nome_cliente);
        Prenotazione prenotazione = new Prenotazione(cliente,n_coperti, data_prenotazione, scelte, cons_bevande, cons_extra);

        //Controllo
        //variabili di supporto
        int lavoro_persona = gestore.getRistorante().getLavoro_persona();
        int n_posti = gestore.getRistorante().getN_posti();

        if(gestore.getRistorante().getAddettoPrenotazione().validaCaricoLavoro(data_prenotazione, lavoro_persona, n_posti, prenotazione)){

            gestore.getRistorante().getAddettoPrenotazione().getPrenotazioni().add(prenotazione);
            System.out.println("Prenotazione Registrata.\n");

        }else System.out.println("Prenotazione Annullata, hai superato il carico di lavoro massimo del ristorante.");

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
                azioni_gestore[6] = "menu tematici presenti nel menu";
                azioni_gestore[7] = "piatti presenti nel menu";
                azioni_gestore[8] = "il ricettario";
                MyMenu menu_gestore = new MyMenu( Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + Costanti.GESTORE.toUpperCase(Locale.ROOT) , azioni_gestore);
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
            System.out.println("\nIl menù disponibile per il giorno " + data + " offre queste specialità:");
            System.out.println("(puoi scegliere sia i piatti all'interno del menù alla carta che i menù tematici presenti) \n");
            for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data)) {
                if (prenotabile instanceof Piatto) {
                    Piatto piatto = (Piatto) prenotabile;
                    System.out.printf("- " + piatto.getNome().toUpperCase());
                    System.out.printf(" con ingredienti: ");
                    ArrayList<Alimento> ingredienti = piatto.getRicetta().getIngredienti();
                    for (Alimento ingrediente : ingredienti) {
                        if(ingrediente.equals(piatto.getRicetta().getIngredienti().get(ingredienti.toArray().length-1)))
                            System.out.printf(ingrediente.getNome() + ".");
                        else
                            System.out.printf(ingrediente.getNome() + ", ");
                    }
                    System.out.printf("\n\n");

                } else if (prenotabile instanceof MenuTematico) {
                    MenuTematico menu_tematico = (MenuTematico) prenotabile;
                    System.out.printf("- Menù " + menu_tematico.getNome().toUpperCase());
                    System.out.printf(" con i seguenti piatti: ");
                    ArrayList<Piatto> piatti = menu_tematico.getPiatti_menu();
                    for (Piatto piatto : menu_tematico.getPiatti_menu()) {
                        if(piatto.equals(piatti.get(piatti.toArray().length-1)))
                            System.out.printf("" + piatto.getNome() + ".");
                        else
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
