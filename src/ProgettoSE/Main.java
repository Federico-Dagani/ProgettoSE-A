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
import ProgettoSE.Menu.MenuTematico;
//importa classi utilità
import ProgettoSE.mylib.MyMenu;
import ProgettoSE.mylib.InputDati;
//importa classi per gestione input da tastiera
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//importa classi per utilizzo costrutti
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) throws IOException, DateTimeParseException, ParseException {

        benvenuto();

        Gestore gestore = new Gestore(nominaGestore() , null);

        Tempo data_attuale = new Tempo(LocalDate.now());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        inizializzazione(gestore);

        if(InputDati.yesOrNo("Vuoi modificare ulteriormente i dati di inizializzazione?")) {
            modificaDatiIniziali(gestore);
        }


        MyMenu menu_attori = nuovoMenu(Costanti.ATTORI);
        int scelta_attore = menu_attori.scegliConUscita();
        while (scelta_attore != 0){
            switch (scelta_attore){

                case 1:
                    MyMenu menu_gestore = nuovoMenu(Costanti.GESTORE);

                    int scelta_funz_gestore = menu_gestore.scegliConUscita();
                    while (scelta_funz_gestore != 0){
                        scegliFunzionalitaGestore(scelta_funz_gestore, gestore);
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                        scelta_funz_gestore = menu_gestore.scegliConUscita();
                    }
                    System.out.println("\n" + Costanti.USCITA_MENU + Costanti.GESTORE.toUpperCase(Locale.ROOT));
                    break;

                case 2:
                    inserisciPrenotazione(gestore, data_attuale.getData_corrente());
                    break;

                case 3:
                    MyMenu menu_tempo = nuovoMenu(Costanti.TEMPO);
                    int scelta_funz_tempo = menu_tempo.scegliConUscita();
                    while (scelta_funz_tempo != 0){
                        scegliFunzionalitaTemporali(scelta_funz_tempo, data_attuale, gestore);
                        System.out.println("\nPremere un tasto per continuare ... ");
                        br.readLine();
                        scelta_funz_tempo = menu_tempo.scegliConUscita();
                    }
                    System.out.println("\n" + Costanti.USCITA_MENU + Costanti.TEMPO.toUpperCase(Locale.ROOT));
                    break;
            }
            System.out.println("\nPremere un tasto per continuare ... ");
            br.readLine();
            scelta_attore = menu_attori.scegliConUscita();
        }

        System.out.println("\n" + Costanti.USCITA_MENU + Costanti.ATTORI.toUpperCase(Locale.ROOT));
        System.out.println("\n" + Costanti.END);

    }

    private static void benvenuto(){
        System.out.println(Costanti.CORNICE_SUP);
        System.out.println("|\t"+Costanti.BENVENUTO+"\t|");
        System.out.println(Costanti.CORNICE_INF);
        System.out.println();
    }

    private static String nominaGestore(){
        return InputDati.leggiStringaConSpazio("Benvenuto, inserisca il nome del gestore del ristorante: ");
    }

    private static void modificaDatiIniziali(Gestore gestore) throws DateTimeParseException {
        MyMenu menu_inizializza = nuovoMenu(Costanti.INIZIALIZZAZIONE);
        int scelta_inizializza = menu_inizializza.scegliConUscita();
        while (scelta_inizializza != 0){
            switch(scelta_inizializza) {
                case 1: //modifica n_posti ristorante
                    gestore.getRistorante().setN_posti(InputDati.leggiInteroConMinimo("Inserisci il nuovo numero di posti del ristorante: ", 1));
                    break;
                case 2: //modifica lavoro_persone ristorante
                    gestore.getRistorante().setLavoro_persona(InputDati.leggiInteroConMinimo("Inserisci il nuovo numero di lavoro per persona : ", 1));
                    System.out.println(gestore.controllaMenu());
                    System.out.println(gestore.controllaRicette());
                    break;
                case 3://aggiungi ingrediente in magazzino
                    Ingrediente nuovo_ingrediente = new Ingrediente(InputDati.leggiStringaConSpazio("Inserisci il nome dell'ingrediente: "), (float) InputDati.leggiDoubleConMinimo("Inserisci la quantità di ingrediente: ", 0), InputDati.leggiStringaNonVuota("Inserisci l'unità di misura: "));
                    if (controllaDuplicato(nuovo_ingrediente, gestore.getRistorante().getMagazziniere().getMagazzino())){
                        gestore.getRistorante().getMagazziniere().getMagazzino().getIngredienti().add(nuovo_ingrediente);
                        System.out.println("Ingrediente aggiunto al magazzino");
                    }else
                        System.out.println("Ingrediente già presente nel magazzino");
                    break;
                case 4://aggiungi extra in magazzino
                    Extra nuovo_extra = new Extra(InputDati.leggiStringaConSpazio("Inserisci il nome dell'extra: "), (float) InputDati.leggiDoubleConMinimo("Inserisci la quantità di extra: ", 0), InputDati.leggiStringaNonVuota("Inserisci l'unità di misura: "), (float) InputDati.leggiDoubleConMinimo("Inserisci il consumo procapite dell'extra: ", 0));
                    if(controllaDuplicato(nuovo_extra, gestore.getRistorante().getMagazziniere().getMagazzino())) {
                        gestore.getRistorante().getMagazziniere().getMagazzino().getExtras().add(nuovo_extra);
                        System.out.println("Extra aggiunto al magazzino");
                    }else
                        System.out.println("Extra già presente nel magazzino");
                    break;
                case 5://aggiungi bevanda in magazzino
                    Bevanda nuova_bevanda = new Bevanda(InputDati.leggiStringaConSpazio("Inserisci il nome della bevanda: "), (float) InputDati.leggiDoubleConMinimo("Inserisci la quantità di bevanda: ", 0), InputDati.leggiStringaNonVuota("Inserisci l'unità di misura: "), (float) InputDati.leggiDoubleConMinimo("Inserisci il consumo procapite della bevanda: ", 0));
                    if(controllaDuplicato(nuova_bevanda, gestore.getRistorante().getMagazziniere().getMagazzino())){
                        gestore.getRistorante().getMagazziniere().getMagazzino().getBevande().add(nuova_bevanda);
                        System.out.println("Bevanda aggiunta al magazzino");
                    } else
                        System.out.println("Bevanda già presente nel magazzino");
                    break;
                case 6://aggiungi menu tematico
                    creaMenuTematico(gestore);
                    String messaggio = gestore.controllaMenu();
                    if (messaggio.equals(""))
                        System.out.println("Menu tematico creato");
                    else
                        System.out.println(messaggio);
                    break;
                case 7://aggiungi piatto
                    creaPiatto(gestore);
                     messaggio = gestore.controllaRicette();
                    if (messaggio.equals(""))
                        System.out.println("Piatto creato");
                    else
                        System.out.println(messaggio);
                    break;
            }
            scelta_inizializza = menu_inizializza.scegliConUscita();
        }
    }

    private static void creaMenuTematico(Gestore gestore) throws DateTimeParseException {
        String nome = InputDati.leggiStringaConSpazio("Inserisci il nome del menu tematico: ");
        float lavoro_menu = (float) InputDati.leggiDoubleConMinimo("Inserisci il lavoro per il menu: ", 0);
        ArrayList<LocalDate> disponibilita = new ArrayList<>();

        do{
            boolean data_errata = false;
            do{
                try{
                    disponibilita.add(LocalDate.parse(InputDati.leggiStringaNonVuota("Inserisci la data di inizio nel formato yyyy-mm-dd: ")));
                    disponibilita.add(LocalDate.parse(InputDati.leggiStringaNonVuota("Inserisci la data di fine nel formato yyyy-mm-dd: ")));
                }catch (DateTimeParseException e){
                    System.out.println("Data in formato non valido");
                    data_errata = true;
                }
            }while (data_errata);
        }while (InputDati.yesOrNo("Vuoi aggiungere un'altra disponibilità al menu?"));
        ArrayList<Piatto> piatti = new ArrayList<>();
        do{
            mostraPiatti(gestore.getRistorante().getAddettoPrenotazione().getMenu());
            String nome_piatto = InputDati.leggiStringaConSpazio("Inserisci il nome del piatto: ");
            boolean trovato = false;
            for(Prenotabile piatto : gestore.getRistorante().getAddettoPrenotazione().getMenu()){
                if (piatto instanceof Piatto && piatto.getNome().equalsIgnoreCase(nome_piatto)){
                    piatti.add((Piatto) piatto);
                    trovato = true;
                    break;
                }
            }
            if (!trovato)
                System.out.println("Piatto non trovato");
        }while (piatti.size() < 1 || InputDati.yesOrNo("Vuoi aggiungere un altro piatto al menu?"));
        gestore.getRistorante().getAddettoPrenotazione().getMenu().add(new MenuTematico(nome, piatti, lavoro_menu, disponibilita));
    }

    private static void creaPiatto(Gestore gestore) throws DateTimeParseException{
        String nome = InputDati.leggiStringaConSpazio("Inserisci il nome del piatto: ");
        float lavoro_piatto = (float) InputDati.leggiDoubleConMinimo("Inserisci il lavoro per il piatto: ", 0);
        ArrayList<LocalDate> disponibilita = new ArrayList<>();
        do{
            boolean data_errate = false;
            do{
                data_errate = false;
                try {
                    disponibilita.add(LocalDate.parse(InputDati.leggiStringaNonVuota("Inserisci la data di inizio nel formato yyyy-mm-dd: ")));
                    disponibilita.add(LocalDate.parse(InputDati.leggiStringaNonVuota("Inserisci la data di fine nel formato yyyy-mm-dd: ")));
                }catch (DateTimeParseException e) {
                    System.out.println("Formato data non valido");
                    data_errate = true;
                }
            }while(data_errate);
        }while (InputDati.yesOrNo("Vuoi aggiungere un'altra disponibilità al piatto?"));
        int n_porzioni = InputDati.leggiInteroConMinimo("Inserisci il numero di porzioni: ", 1);
        float lavoro_porzione = lavoro_piatto;
        ArrayList<Alimento> ingredienti = new ArrayList<>();
        do{
            mostraAlimento(gestore.getRistorante().getMagazziniere().getMagazzino().getIngredienti());
            String nome_piatto = InputDati.leggiStringaConSpazio("Inserisci il nome del piatto: ");
            Ingrediente nuovo_ingrediente = new Ingrediente("", 0, "");
            boolean trovato = false;
            for(Alimento ingrediente : gestore.getRistorante().getMagazziniere().getMagazzino().getIngredienti()){
                if (ingrediente instanceof Ingrediente && ingrediente.getNome().equalsIgnoreCase(nome_piatto)){
                    nuovo_ingrediente.setNome(ingrediente.getNome());
                    nuovo_ingrediente.setQta((float)InputDati.leggiDoubleConMinimo("Inserisci la quantità di " + nome_piatto + ": ", 0));
                    nuovo_ingrediente.setMisura(InputDati.leggiStringaNonVuota("Inserisci l'unità di misura: "));
                    ingredienti.add(nuovo_ingrediente);
                    trovato = true;
                    break;
                }
            }
            if (!trovato)
                System.out.println("Ingrediente non trovato");
        }while (ingredienti.size() < 1 || InputDati.yesOrNo("Vuoi aggiungere un altro ingrediente alla ricetta?"));
        Ricetta ricetta = new Ricetta(ingredienti, n_porzioni, lavoro_porzione);
        gestore.getRistorante().getAddettoPrenotazione().getMenu().add(new Piatto(nome, disponibilita, lavoro_piatto , ricetta));
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

    private static void scegliFunzionalitaTemporali(int scelta, Tempo data_attuale, Gestore gestore){
        LocalDate data_precedente = data_attuale.getData_corrente();
        switch (scelta){
            case 1:
                data_attuale.scorriGiorno();
                break;
            case 2:
                data_attuale.setData_corrente(gestisciData(gestore, data_precedente));
                break;
        }
        System.out.println("\nLa data attuale è stata incrementata, ora è: " + data_attuale.getData_corrente() + ".\nLa lista spesa è stata aggiornata.");
        //dopo aer modificato il giorno, il gestore comunica al magazziniere di aggiornare la lista spesa e rifornire il magazzino, all'addeetto prenotazione di aggiornare le prenotazioni
        System.out.println(gestore.comunica(data_attuale.getData_corrente()));
    }

    private static LocalDate gestisciData(Gestore gestore, LocalDate data_attuale){
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
            System.out.printf("\nI posti liberi nel ristorante sono %d.", p_effettivi_rimanenti);
            System.out.printf("\nAbbiamo stimato di poter cucinare %d portate (solitamente 2 portate a testa).", p_stima_lav_rimanenti*2);
        }else{
            System.out.println("\nCi scusiamo ma la stima del carico di lavoro non ci permette di accettare altre prenotazioni in questa data");
            return;
        }
        int n_coperti = InputDati.leggiInteroConMinimoMassimo("\nNumero persone : ", 1 , Math.min(p_effettivi_rimanenti, p_stima_lav_rimanenti*2));
        //variabili di supporto
        int lavoro_persona = gestore.getRistorante().getLavoro_persona();
        int n_posti = gestore.getRistorante().getN_posti();

        //CALCOLO CONSUMO BEVANDE E GENERI EXTRA
        HashMap<Alimento,Float> cons_bevande = gestore.getRistorante().getMagazziniere().calcolaConsumoBevande(n_coperti);
        HashMap<Alimento,Float> cons_extra = gestore.getRistorante().getMagazziniere().calcolaConsumoExtras(n_coperti);

        //SCELTE
        HashMap<Prenotabile, Integer> scelte = new HashMap<>();
        Integer n_portate = 0;
        do {
            menu_vuoto = stampaMenuDelGiorno(gestore, data_prenotazione);
            if(!menu_vuoto){
                stampaScelte(scelte);
                if(n_portate < n_coperti)
                    System.out.printf("\nDeve scelgliere almeno altre %d portate per convalidare la prenotazione.", n_coperti-n_portate);


                boolean validita = false;
                Prenotabile portata = null;
                Integer quantità;


                do {
                    String scelta = InputDati.leggiStringa("\n\nInserisca il nome della portata da ordinare: \n");

                    for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data_prenotazione)) {
                        if (prenotabile.getNome().equalsIgnoreCase(scelta)) {
                            portata = prenotabile;
                            validita = true;
                        }
                    }
                    if(!validita)
                        System.out.println("Portata non presente nel menu del giorno.");
                }while(!validita);
                quantità = InputDati.leggiInteroConMinimo("Inserisca le porzioni desiderate di " + portata.getNome().toLowerCase(Locale.ROOT) + ": \n",0);

                //devo leggere il value precedente e sommarlo alla nuova quantità aggiunta, dopodichè rimetto la value nuova nella Map
                Integer quantita_precedente = scelte.get(portata);
                if(quantita_precedente == null)
                    quantita_precedente = 0;
                if(quantita_precedente+quantità !=0)
                    scelte.put(portata, quantità+quantita_precedente);

                Prenotazione prenotazione = new Prenotazione(null, n_coperti, data_prenotazione, scelte, cons_bevande, cons_extra);
                boolean lavoro_validato = gestore.getRistorante().getAddettoPrenotazione().validaCaricoLavoro(data_prenotazione, lavoro_persona, n_posti, prenotazione);

                if(lavoro_validato && quantità > 0){
                    System.out.println("Portata aggiunta all'ordine.");
                }else if(!lavoro_validato){
                    System.out.println("Il carico di lavoro non ci permette di accettare un così alto numero di portate. Rimosso dalla lista: " + portata.getNome() + " x" + quantità);
                    //tolgo la portata inserita che mi fa sballare il carico di lavoro totale e reinserisco
                    scelte.remove(portata);
                    if(quantita_precedente != 0)
                        scelte.put(portata, quantita_precedente);
                }else if(quantità == 0){
                    System.out.println("Portata non aggiunta all'ordine.");
                }

                System.out.println("\n" + "Premere un tasto per continuare ... ");
                br.readLine();

                n_portate = 0;
                for(Integer value : scelte.values())
                    n_portate +=value;
            }
        //cortocircuito
        }while(!menu_vuoto && (n_portate < n_coperti || InputDati.yesOrNo("Ogni commensale ha ordinato almeno una portata ciascuno, vuole ordinare altre portate?")));

        if(!menu_vuoto){
            //Costruzione Prenotazione
            Prenotazione prenotazione = new Prenotazione(cliente, n_coperti, data_prenotazione, scelte, cons_bevande, cons_extra);
            gestore.getRistorante().getAddettoPrenotazione().getPrenotazioni().add(prenotazione);
            System.out.println("\nPrenotazione Registrata.\n");
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

                String[] utenti = new String[3];
                utenti[0] = Costanti.GESTORE;
                utenti[1] = Costanti.UTENTE;
                utenti[2] = Costanti.TEMPO;
                return new MyMenu(Costanti.ATTORI.toUpperCase(Locale.ROOT), utenti);

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
                return new MyMenu( Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + Costanti.GESTORE.toUpperCase(Locale.ROOT) , azioni_gestore);

            case Costanti.TEMPO:

                String[] azioni_tempo = new String[2];
                azioni_tempo[0] = "Incrementa di un giorno";
                azioni_tempo[1] = "Scegli una data";
                return new MyMenu(Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + Costanti.TEMPO.toUpperCase(Locale.ROOT), azioni_tempo);

            case Costanti.INIZIALIZZAZIONE:

                String[] azioni_inizializzazione = new String[7];
                azioni_inizializzazione[0] = "Modidica il numero di posti";
                azioni_inizializzazione[1] = "Modifica il lavolo persone";
                azioni_inizializzazione[2] = "Aggiungi ingrediente";
                azioni_inizializzazione[3] = "Aggiungi extra";
                azioni_inizializzazione[4] = "Aggiungi bevanda";
                azioni_inizializzazione[5] = "Aggiungi menu";
                azioni_inizializzazione[6] = "Aggiungi piatto";
                return new MyMenu(Costanti.INIZIALIZZAZIONE.toUpperCase(Locale.ROOT), azioni_inizializzazione);
        }
        return null;
    }

    private static void inizializzazione(Gestore gestore){
        System.out.printf("Il gestore sta inizializzando il ristorante ...");
        System.out.println(gestore.inizializzaRistorante());
        System.out.println("Inizializzazione automatica del ristorante completata.");
        System.out.println();
    }

    private static boolean stampaMenuDelGiorno(Gestore gestore, LocalDate data){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data).isEmpty()){
            System.out.println("Non ci sono piatti disponibili per il giorno " + data);
            return true;
        } else {
            System.out.println("\nIl menù disponibile per il giorno " + data + " offre queste specialità:");
            System.out.println("(può scegliere sia i piatti all'interno del menù alla carta che i menù tematici presenti) \n");
            for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data)) {
                if (prenotabile instanceof Piatto) {
                    Piatto piatto = (Piatto) prenotabile;
                    System.out.printf("- " + piatto.getNome().toUpperCase());
                   // System.out.printf(" con ingredienti: (");
                    System.out.printf(": (");
                    ArrayList<Alimento> ingredienti = piatto.getRicetta().getIngredienti();
                    for (Alimento ingrediente : ingredienti) {
                        if(ingrediente.equals(piatto.getRicetta().getIngredienti().get(ingredienti.toArray().length-1)))
                            System.out.printf(ingrediente.getNome() + ".)");
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
            return false;
        }
    }

    private static void mostraAlimento(ArrayList<Alimento> alimenti){
        if(alimenti.get(0) instanceof Bevanda){
            System.out.println("\nLista delle bevande presenti nel ristorante: ");
        }else if(alimenti.get(0) instanceof Extra){
            System.out.println("\nLista degli extra presenti nel ristorante:");
        }else if(alimenti.get(0) instanceof Ingrediente){
            System.out.println("\nLista degli ingredienti presenti nel ristorante:");
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

    private static void stampaScelte(HashMap<Prenotabile, Integer> scelte){
        if(scelte.isEmpty()){
            return;
        }
        System.out.println("Le scelte effettuate finora sono le seguenti: ");
        for (Prenotabile prenotabile : scelte.keySet()){
            if(prenotabile instanceof Piatto){
                System.out.printf("- " + prenotabile.getNome() + ", ");
                System.out.printf("quantità: " + scelte.get(prenotabile) + "\n");
            }else if(prenotabile instanceof MenuTematico){
                System.out.printf("- Menù " + prenotabile.getNome() + ", ");
                System.out.printf("quantità: " + scelte.get(prenotabile) + "\n");
            }
        }
    }


    /**
     * metodo che controlla se un alimento è già presente nel magazzino (per evitare duplicati)
     * @param alimento_nuovo alimento da controllare
     * @param magazzino magazzino in cui cercare
     * @return true se l'alimento non è presente nel magazzino, false altrimenti
     */
    private static boolean controllaDuplicato(Alimento alimento_nuovo, Magazzino magazzino){
        for(Alimento alimento_magazzino : magazzino.getBevande()){
            if(alimento_magazzino.getNome().equalsIgnoreCase(alimento_nuovo.getNome())){
                return false;
            }
        }
        for(Alimento alimento_magazzino : magazzino.getExtras()){
            if(alimento_magazzino.getNome().equalsIgnoreCase(alimento_nuovo.getNome())){
                return false;
            }
        }
        for(Alimento alimento_magazzino : magazzino.getIngredienti()){
            if(alimento_magazzino.getNome().equalsIgnoreCase(alimento_nuovo.getNome())){
                return false;
            }
        }
        return true;
    }
}