package ProgettoSE;
//importa classi alimenti

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
//importa classi attori
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Attori.Cliente;
import ProgettoSE.Attori.Gestore;
//importa classe MenuTematico
//importa classi utilità
import ProgettoSE.Utilità.Creazione;
import ProgettoSE.Utilità.Visualizzazione;
import ProgettoSE.mylib.MyMenu;
import ProgettoSE.mylib.InputDati;
//importa classi per gestione input da tastiera
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//importa classi per utilizzo costrutti
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws IOException, DateTimeParseException {

        benvenuto();

        Gestore gestore = new Gestore(nominaGestore(), null);

        Tempo data_attuale = new Tempo(LocalDate.now());

        inizializzazione(gestore);

        if (InputDati.yesOrNo("Vuoi modificare ulteriormente i dati di inizializzazione?")){
            Visualizzazione.ripulisciConsole();
            modificaDatiIniziali(gestore);
        }
        Visualizzazione.ripulisciConsole();

        MyMenu menu_attori = Creazione.creaMenu(Costanti.ATTORI);
        int scelta_attore = menu_attori.scegliConUscita();
        Visualizzazione.ripulisciConsole();
        while (scelta_attore != 0) {
            switch (scelta_attore) {

                case 1:
                    MyMenu menu_gestore = Creazione.creaMenu(Costanti.GESTORE);
                    int scelta_funz_gestore = menu_gestore.scegliConUscita();
                    Visualizzazione.ripulisciConsole();
                    while (scelta_funz_gestore != 0) {
                        scegliFunzionalitaGestore(scelta_funz_gestore, gestore);
                        InputDati.premerePerContinuare();
                        Visualizzazione.ripulisciConsole();
                        scelta_funz_gestore = menu_gestore.scegliConUscita();
                        Visualizzazione.ripulisciConsole();
                    }
                    System.out.println("\n" + Costanti.USCITA_MENU + Costanti.GESTORE.toUpperCase(Locale.ROOT));
                    break;

                case 2:
                    inserisciPrenotazione(gestore, data_attuale.getData_corrente());
                    break;

                case 3:
                    MyMenu menu_tempo = Creazione.creaMenu(Costanti.TEMPO);
                    int scelta_funz_tempo = menu_tempo.scegliConUscita();
                    Visualizzazione.ripulisciConsole();
                    while (scelta_funz_tempo != 0) {
                        scegliFunzionalitaTemporali(scelta_funz_tempo, data_attuale, gestore);
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                        scelta_funz_tempo = menu_tempo.scegliConUscita();
                        Visualizzazione.ripulisciConsole();
                    }
                    System.out.println("\n" + Costanti.USCITA_MENU + Costanti.TEMPO.toUpperCase(Locale.ROOT));
                    break;
            }
            InputDati.premerePerContinuare();
            Visualizzazione.ripulisciConsole();
            scelta_attore = menu_attori.scegliConUscita();
            Visualizzazione.ripulisciConsole();
        }

        System.out.println("\n" + Costanti.USCITA_MENU + Costanti.ATTORI.toUpperCase(Locale.ROOT));
        System.out.println("\n" + Costanti.END);

    }

    private static void benvenuto() {
        System.out.println(Costanti.CORNICE_SUP);
        System.out.println("|\t" + Costanti.BENVENUTO + "\t|");
        System.out.println(Costanti.CORNICE_INF);
        System.out.println();
    }

    private static String nominaGestore() {
        return InputDati.leggiStringaConSpazio("Benvenuto, inserisca il nome del gestore del ristorante: ");
    }

    private static void modificaDatiIniziali(Gestore gestore) throws IOException {

        MyMenu menu_inizializza = Creazione.creaMenu(Costanti.INIZIALIZZAZIONE);
        int scelta_inizializza = menu_inizializza.scegliConUscita();
        Visualizzazione.ripulisciConsole();

        while (scelta_inizializza != 0) {

            switch (scelta_inizializza) {

                case 1: //modifica n_posti ristorante
                    int n_posti = InputDati.leggiInteroConMinimo("\nInserisci il nuovo numero di posti del ristorante: ", 1);
                    if(n_posti == gestore.getRistorante().getN_posti())
                        System.out.printf("\n" + Costanti.UGUALE_ATTUALE + "\n", "numero di posti");
                    else
                        gestore.getRistorante().setN_posti(n_posti);
                    InputDati.premerePerContinuare();
                    break;

                case 2: //modifica lavoro_persona
                    int lavoro_persona = InputDati.leggiInteroConMinimo("\nInserisci il nuovo numero di lavoro per persona: ", 1);
                    gestore.getRistorante().setLavoro_persona(lavoro_persona);
                    if(lavoro_persona == gestore.getRistorante().getLavoro_persona())
                        System.out.printf("\n" + Costanti.UGUALE_ATTUALE + "\n", "numero di lavoro per persona");
                    else
                        gestore.getRistorante().setLavoro_persona(lavoro_persona);
                    String messaggio = gestore.controllaMenu() + "\n" + gestore.controllaRicette();
                    if(messaggio.equals("\n")){
                        InputDati.premerePerContinuare();
                    }else{
                        System.out.println(messaggio);
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                    }
                    break;

                case 3://aggiungi ingrediente in magazzino
                    Alimento nuovo_ingrediente = Creazione.creaAlimento(Costanti.INGREDIENTE);
                    controllaPresenza(nuovo_ingrediente, gestore);
                    System.out.println("\nPremere un tasto per continuare ... ");
                    br.readLine();
                    break;

                case 4://aggiungi extra in magazzino
                    Alimento nuovo_extra = Creazione.creaAlimento(Costanti.EXTRA);
                    controllaPresenza(nuovo_extra, gestore);
                    System.out.println("\nPremere un tasto per continuare ... ");
                    br.readLine();
                    break;

                case 5://aggiungi bevanda in magazzino
                    Alimento nuova_bevanda = Creazione.creaAlimento(Costanti.BEVANDA);
                    controllaPresenza(nuova_bevanda, gestore);
                    System.out.println("\nPremere un tasto per continuare ... ");
                    br.readLine();
                    break;

                case 6://aggiungi menu tematico
                    Creazione.creaMenuTematico(gestore);
                    String mex = gestore.controllaMenu();
                    Visualizzazione.ripulisciConsole();
                    if (mex.equals("")){
                        System.out.println("\nMenu tematico creato");
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                    } else{
                        System.out.println(mex);
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                    }
                    break;

                case 7://aggiungi piatto
                    Creazione.creaPiatto(gestore);
                    messaggio = gestore.controllaRicette();
                    Visualizzazione.ripulisciConsole();
                    if (messaggio.equals("")){
                        System.out.println("\nPiatto creato");
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                    } else{
                        System.out.println(messaggio);
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                    }
                    break;
            }
            Visualizzazione.ripulisciConsole();
            scelta_inizializza = menu_inizializza.scegliConUscita();
            Visualizzazione.ripulisciConsole();
        }
    }

    private static void controllaPresenza(Alimento alimento, Gestore gestore) {
        if (controllaDuplicato(alimento, gestore.getRistorante().getMagazziniere().getMagazzino())) {
            if (alimento instanceof Ingrediente) {
                gestore.getRistorante().getMagazziniere().getMagazzino().getIngredienti().add(alimento);
                System.out.println("\nIngrediente aggiunto al magazzino");
            } else if (alimento instanceof Extra) {
                gestore.getRistorante().getMagazziniere().getMagazzino().getExtras().add(alimento);
                System.out.println("\nExtra aggiunto al magazzino");
            } else if (alimento instanceof Bevanda) {
                gestore.getRistorante().getMagazziniere().getMagazzino().getBevande().add(alimento);
                System.out.println("\nBevanda aggiunta al magazzino");
            }
        } else
            System.out.println(alimento.getNome() + " già presente nel magazzino");
    }

    private static void scegliFunzionalitaGestore(int scelta, Gestore gestore) {
        switch (scelta) {
            case 1:
                Visualizzazione.mostraCaricoLavoroPersona(gestore);
                break;
            case 2:
                Visualizzazione.mostraPostiDisponibili(gestore);
                break;
            case 3:
                Visualizzazione.mostraAlimenti(gestore.getRistorante().getMagazziniere().getMagazzino().getBevande());
                break;
            case 4:
                Visualizzazione.mostraAlimenti(gestore.getRistorante().getMagazziniere().getMagazzino().getExtras());
                break;
            case 5:
                Visualizzazione.mostraConsumoProcapite(gestore.getRistorante().getMagazziniere().getMagazzino().getBevande());
                break;
            case 6:
                Visualizzazione.mostraConsumoProcapite(gestore.getRistorante().getMagazziniere().getMagazzino().getExtras());
                break;
            case 7:
                Visualizzazione.mostraMenuTematici(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;
            case 8:
                Visualizzazione.mostraPiatti(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;
            case 9:
                Visualizzazione.mostraRicette(gestore.getRistorante().getAddettoPrenotazione().getMenu());
                break;

        }
    }

    private static void scegliFunzionalitaTemporali(int scelta, Tempo data_attuale, Gestore gestore) {
        switch (scelta) {
            case 1:
                data_attuale.scorriGiorno();
                break;
            case 2:
                //String stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
                boolean data_errata = false;
                do{
                    try {
                        String stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
                        data_attuale.setData_corrente(LocalDate.parse(stringa_data_prenotazione));
                        data_errata = false;
                        if(data_attuale.getData_corrente().isBefore(LocalDate.now())) {
                            System.out.println("La data inserita è precedente alla data attuale (" + data_attuale + ")");
                            data_errata = true;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Data non valida");
                        //stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
                        data_errata = true;
                    }
                }while (data_errata);
                break;
        }
        System.out.println("\nLa data attuale è stata incrementata, ora è: " + data_attuale.getData_corrente() + ".\n\nLa lista spesa è stata aggiornata.");
        //dopo aer modificato il giorno, il gestore comunica al magazziniere di aggiornare la lista spesa e rifornire il magazzino, all'addeetto prenotazione di aggiornare le prenotazioni
        System.out.println(gestore.comunica(data_attuale.getData_corrente()));
    }

    private static LocalDate gestisciData(Gestore gestore, LocalDate data_attuale) {
        String stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
        int msg = gestore.getRistorante().getAddettoPrenotazione().controlloDataPrenotazione(data_attuale, stringa_data_prenotazione, gestore.getRistorante().getN_posti());
        while (msg != 0) {
            switch (msg) {
                case 1:
                    System.out.println("Formato data non valido.");
                    break;
                case 2:
                    System.out.println("La data inserita deve essere sucessiva alla data attuale (" + data_attuale + ")");
                    break;
                case 3:
                    System.out.println("Il ristorante non ha posti disponibili in questa data (" + stringa_data_prenotazione + ")");
                    break;
                case 4:
                    System.out.println("La data inserita corrisponde ad un giorno festivo, sono ammessi solo giorni feriali.");
                    break;
            }
            stringa_data_prenotazione = InputDati.leggiStringa("Inserisci una data valida (yyyy-mm-dd) :");
            msg = gestore.getRistorante().getAddettoPrenotazione().controlloDataPrenotazione(data_attuale, stringa_data_prenotazione, gestore.getRistorante().getN_posti());
        }
        return LocalDate.parse(stringa_data_prenotazione);
    }

    private static void inserisciPrenotazione(Gestore gestore, LocalDate data_attuale) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Inserimento dati NUOVA PRENOTAZIONE\n");

        //NOME
        String nome_cliente = InputDati.leggiStringaNonVuota("Nome cliente: ");
        Cliente cliente = new Cliente(nome_cliente);

        //MENU VUOTO
        boolean menu_vuoto;

        //DATA
        LocalDate data_prenotazione = gestisciData(gestore, data_attuale);

        //POSTI
        int p_stima_lav_rimanenti = gestore.getRistorante().getAddettoPrenotazione().stimaPostiRimanenti(data_prenotazione, gestore.getRistorante().getLavoro_persona(), gestore.getRistorante().getN_posti());
        int p_effettivi_rimanenti = gestore.getRistorante().getN_posti() - gestore.getRistorante().getAddettoPrenotazione().calcolaPostiOccupati(data_prenotazione);
        if (p_stima_lav_rimanenti > 0) {
            System.out.printf("\nI posti liberi nel ristorante sono %d.\n", p_effettivi_rimanenti);
            System.out.printf("\nAbbiamo stimato di poter cucinare %d portate (solitamente 2 portate a testa).\n", p_stima_lav_rimanenti * 2);
        } else {
            System.out.printf("\nCi scusiamo ma la stima del carico di lavoro non ci permette di accettare altre prenotazioni in questa data\n");
            return;
        }
        int n_coperti = InputDati.leggiInteroConMinimoMassimo("\nNumero persone: ", 1, Math.min(p_effettivi_rimanenti, p_stima_lav_rimanenti * 2));
        //variabili di supporto
        int lavoro_persona = gestore.getRistorante().getLavoro_persona();
        int n_posti = gestore.getRistorante().getN_posti();

        //CALCOLO CONSUMO BEVANDE E GENERI EXTRA
        HashMap<Alimento, Float> cons_bevande = gestore.getRistorante().getMagazziniere().calcolaConsumoBevande(n_coperti);
        HashMap<Alimento, Float> cons_extra = gestore.getRistorante().getMagazziniere().calcolaConsumoExtras(n_coperti);

        //SCELTE
        HashMap<Prenotabile, Integer> scelte = new HashMap<>();
        Integer n_portate = 0;
        do {
            menu_vuoto = Visualizzazione.stampaMenuDelGiorno(gestore, data_prenotazione);
            if (!menu_vuoto) {
                Visualizzazione.stampaScelte(scelte);
                if (n_portate < n_coperti)
                    System.out.printf("\nDeve scelgliere almeno altre %d portate per convalidare la prenotazione.\n", n_coperti - n_portate);


                boolean validita = false;
                Prenotabile portata = null;
                Integer quantità;


                do {
                    String scelta = InputDati.leggiStringa("\n\nInserisca il nome della portata da ordinare: ");

                    for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data_prenotazione)) {
                        if (prenotabile.getNome().equalsIgnoreCase(scelta)) {
                            portata = prenotabile;
                            validita = true;
                        }
                    }
                    if (!validita)
                        System.out.println("Portata non presente nel menu del giorno.");
                } while (!validita);
                quantità = InputDati.leggiInteroConMinimo("Inserisca le porzioni desiderate di " + portata.getNome().toLowerCase(Locale.ROOT) + ": ", 0);

                //devo leggere il value precedente e sommarlo alla nuova quantità aggiunta, dopodichè rimetto la value nuova nella Map
                Integer quantita_precedente = scelte.get(portata);
                if (quantita_precedente == null)
                    quantita_precedente = 0;
                if (quantita_precedente + quantità != 0)
                    scelte.put(portata, quantità + quantita_precedente);

                Prenotazione prenotazione = new Prenotazione(null, n_coperti, data_prenotazione, scelte, cons_bevande, cons_extra);
                boolean lavoro_validato = gestore.getRistorante().getAddettoPrenotazione().validaCaricoLavoro(data_prenotazione, lavoro_persona, n_posti, prenotazione);

                Visualizzazione.ripulisciConsole();

                if (lavoro_validato && quantità > 0) {
                    System.out.println("Portata aggiunta all'ordine.");
                } else if (!lavoro_validato) {
                    System.out.println("Il carico di lavoro non ci permette di accettare un così alto numero di portate. Rimosso dalla lista: " + portata.getNome() + " x" + quantità);
                    //tolgo la portata inserita che mi fa sballare il carico di lavoro totale e reinserisco
                    scelte.remove(portata);
                    if (quantita_precedente != 0)
                        scelte.put(portata, quantita_precedente);
                } else if (quantità == 0) {
                    System.out.println("Portata non aggiunta all'ordine.");
                }

                System.out.println("\n" + "Premere un tasto per continuare ... ");
                br.readLine();

                Visualizzazione.ripulisciConsole();

                n_portate = 0;
                for (Integer value : scelte.values())
                    n_portate += value;
            }
            //cortocircuito
        } while (!menu_vuoto && (n_portate < n_coperti || InputDati.yesOrNo("Ogni commensale ha ordinato almeno una portata ciascuno, vuole ordinare altre portate?")));

        Visualizzazione.ripulisciConsole();

        if (!menu_vuoto) {
            //Costruzione Prenotazione
            Prenotazione prenotazione = new Prenotazione(cliente, n_coperti, data_prenotazione, scelte, cons_bevande, cons_extra);
            gestore.getRistorante().getAddettoPrenotazione().getPrenotazioni().add(prenotazione);
            System.out.printf("\nPrenotazione Registrata.\n");
        }
    }

    private static void inizializzazione(Gestore gestore) {
        System.out.printf("\nIl gestore sta inizializzando il ristorante ...");
        System.out.println(gestore.inizializzaRistorante());
        System.out.println("\nInizializzazione automatica del ristorante completata.\n");
    }

    /**
     * metodo che controlla se un alimento è già presente nel magazzino (per evitare duplicati)
     *
     * @param alimento_nuovo alimento da controllare
     * @param magazzino      magazzino in cui cercare
     * @return true se l'alimento non è presente nel magazzino, false altrimenti
     */
    private static boolean controllaDuplicato(Alimento alimento_nuovo, Magazzino magazzino) {
        for (Alimento alimento_magazzino : magazzino.getBevande()) {
            if (alimento_magazzino.getNome().equalsIgnoreCase(alimento_nuovo.getNome())) {
                return false;
            }
        }
        for (Alimento alimento_magazzino : magazzino.getExtras()) {
            if (alimento_magazzino.getNome().equalsIgnoreCase(alimento_nuovo.getNome())) {
                return false;
            }
        }
        for (Alimento alimento_magazzino : magazzino.getIngredienti()) {
            if (alimento_magazzino.getNome().equalsIgnoreCase(alimento_nuovo.getNome())) {
                return false;
            }
        }
        return true;
    }
}