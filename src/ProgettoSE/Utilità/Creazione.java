package ProgettoSE.Utilità;

import ProgettoSE.*;
import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Alimentari.Ingrediente;

import ProgettoSE.Menu.MenuTematico;

import ProgettoSE.Attori.Gestore;

import ProgettoSE.mylib.InputDati;
import ProgettoSE.mylib.MyMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class Creazione {

    /**
     * <h2>Metodo che crea un alimento</h2><br>
     * <b>Precondizione:</b> il tipo dell'alimento non è null<br>
     * @param tipo tipo dell'alimento da creare (Extra, Bevanda o Ingrediente)
     * @throws IllegalArgumentException se il tipo dell'alimento non è valido
     * @return Alimento
     */
    public static Alimento creaAlimento(String tipo) {
        //precondizione: il tipo dell'alimento non è null
        if (tipo == null) throw new IllegalArgumentException("Tipo alimento non valido");

        System.out.printf("\nInserisci i dati dell'alimento di tipo: %s \n\n", tipo);
        String nome = InputDati.leggiStringaConSpazio(Costanti.INS_NOME);
        float quantita = (float) InputDati.leggiDoubleConMinimo(Costanti.INS_QTA, 0);
        String unita_misura = InputDati.leggiStringaNonVuota(Costanti.INS_MISURA);
        //dentro lo switch andrò a settare il consumo procapite solo se il tipo è Extra o Bevanda
        switch (tipo) {
            case Costanti.INGREDIENTE:
                return new Ingrediente(nome, quantita, unita_misura);
            case Costanti.EXTRA:
                float consumo_procapite = (float) InputDati.leggiDoubleConMinimo(Costanti.INS_CONS_PROCAPITE, 0);
                return new Extra(nome, quantita, unita_misura, consumo_procapite);
            case Costanti.BEVANDA:
                consumo_procapite = (float) InputDati.leggiDoubleConMinimo(Costanti.INS_CONS_PROCAPITE, 0);
                return new Bevanda(nome, quantita, unita_misura, consumo_procapite);
            default:
                return null;
        }
    }

    /**
     * <h2>Metodo che crea un prenotabile</h2><br>
     * <b>Precondizione:</b> la tipologia non è null<br>
     * @param tipologia tipologia del prenotabile da creare (MenuTematico o Piatto)
     * @throws IllegalArgumentException se il gestore è null
     * @return Prenotabile
     */
    private static Prenotabile creaPrenotabile(String tipologia) {
        //precondizione: la tipologia non è null
        if (tipologia == null) throw new IllegalArgumentException("Tipologia non valida");

        System.out.printf("\nInserisci i dati del %s \n\n", tipologia);
        String nome = InputDati.leggiStringaConSpazio(Costanti.INS_NOME);
        float lavoro = (float) InputDati.leggiDoubleConMinimo(Costanti.INS_LAVORO, 0);
        ArrayList<LocalDate> disponibilita = new ArrayList<>();

        do {
            boolean data_errata; //variabile per permettere di reinserire immediamente delle nuvo disponibilità in caso quelle inserite siano scorrette
            do {
                String data_inizio_da_parsare = InputDati.leggiStringaNonVuota(Costanti.INS_DATA_INIZIO);
                String data_fine_da_parsare = InputDati.leggiStringaNonVuota(Costanti.INS_DATA_FINE);

                LocalDate data_inizio_parsata = Tempo.parsaData(data_inizio_da_parsare);
                LocalDate data_fine_parsata = Tempo.parsaData(data_fine_da_parsare);

                if(data_inizio_parsata == null || data_fine_parsata == null){
                    System.out.println(Costanti.DATA_NON_VALIDA);
                    data_errata = true;
                } else data_errata = false;

            } while (data_errata);
        } while (InputDati.yesOrNo("\nVuoi aggiungere un'altra disponibilità?"));
        //in base alla tipologia creo oggetti diversi
        if (tipologia.equals(Costanti.MENU_TEMATICO)) {
            return new MenuTematico(nome, new ArrayList<>(), lavoro, disponibilita);
        } else if (tipologia.equals(Costanti.PIATTO)) {
            return new Piatto(nome, disponibilita, lavoro, new Ricetta());
        }
        return null;
    }

    /**
     * <h2>Metodo che crea un menu tematico</h2><br>
     * <b>Precondizione:</b> il gestore non è null<br>
     * <b>Postcondizione:</b> il menu tematico è creato e aggiunto al menu del ristorante<br>
     * @param gestore gestore che crea il menu tematico
     * @throws IllegalArgumentException se il gestore è null
     */
    public static void creaMenuTematico(Gestore gestore) {
        //precondizione: il gestore non è null
        if (gestore == null) throw new IllegalArgumentException(Costanti.GESTORE_NON_NULLO);

        MenuTematico menu_tematico = (MenuTematico) creaPrenotabile(Costanti.MENU_TEMATICO);
        ArrayList<Piatto> piatti = new ArrayList<>();
        ArrayList<Prenotabile> menu_ristorante = gestore.getRistorante().getAddettoPrenotazione().getMenu();

        do {
            //prima mostro i piatti presenti nel ristorante
            Visualizzazione.mostraPiatti(menu_ristorante);

            String nome_piatto = InputDati.leggiStringaConSpazio("\n" + Costanti.INS_NOME);
            //flag per salvarmi se il piatto è stato trovato o meno
            boolean trovato = false;
            for (Prenotabile piatto : menu_ristorante) {
                //controllo che il piatto sia un piatto, che il nome sia uguale a quello inserito e che non sia già stato inserito nell'ArrayList piatti (ovvero quei piatti che saranno inseriti nel menù, solo sucessivamente)
                if (piatto instanceof Piatto && piatto.getNome().equalsIgnoreCase(nome_piatto) && !piatti.contains(piatto)) {
                    piatti.add((Piatto) piatto);
                    trovato = true;
                    break;
                }
            }
            Visualizzazione.ripulisciConsole();

            if (!trovato) System.out.println("Piatto non trovato o già inserito nel menu");
            else System.out.println("Piatto aggiunto al menu");

            InputDati.premerePerContinuare();
            //tramite il cortocircuito evito di chiedere se si vuole aggiungere un altro piatto se il piatto non è stato trovato
        } while (piatti.size() < Costanti.MINIMO_PIATTI_PER_MENU || InputDati.yesOrNo("Vuoi aggiungere un altro piatto al menu?"));
        menu_tematico.setPiatti_menu(piatti);
        menu_ristorante.add(menu_tematico);
        //postcondizione: il menu tematico è creato e aggiunto al menu del ristorante
        assert menu_ristorante.contains(menu_tematico);
    }

    /**
     * <h2>Metodo che crea un piatto</h2><br>
     * <b>Precondizione:</b> il gestore non è null<br>
     * <b>Postcondizione:</b> il piatto è creato e aggiunto al menu del ristorante<br>
     * @param gestore gestore che crea il piatto
     * @throws IllegalArgumentException se il gestore è null
     */
    public static void creaPiatto(Gestore gestore) {
        //precondizione: il gestore non è null
        if (gestore == null) throw new IllegalArgumentException(Costanti.GESTORE_NON_NULLO);

        Piatto piatto = (Piatto) creaPrenotabile(Costanti.PIATTO);

        int n_porzioni = InputDati.leggiInteroConMinimo("\nInserisci il numero di porzioni delle ricetta per cucinare il piatto: ", 1);
        float lavoro_porzione = piatto.getLavoro_piatto();

        //aggiunta degli ingredienti alla ricetta
        ArrayList<Alimento> ingredienti_nuovo_piatto = new ArrayList<>();
        do {
            Visualizzazione.mostraAlimenti(gestore.getRistorante().getMagazziniere().getMagazzino().getIngredienti());

            String nome_ingrediente = InputDati.leggiStringaConSpazio("\nInserisci il nome dell'ingrediente: ");
            Ingrediente nuovo_ingrediente = new Ingrediente();
            boolean trovato = false;
            for (Alimento ingrediente : gestore.getRistorante().getMagazziniere().getMagazzino().getIngredienti()) {
                //controllo se ho già inserito l'ingrediente, in tal caso non lo aggiungo
                boolean gia_presente = ingredienti_nuovo_piatto.stream().anyMatch(ingrediente_da_valutare -> ingrediente_da_valutare.getNome().equalsIgnoreCase(nome_ingrediente));
                //controllo che l'ingrediente sia un ingrediente, che il nome sia uguale a quello inserito e che non sia già stato inserito nell'ArrayList ingredienti_nuovo_piatto (ovvero quegli ingredienti che saranno inseriti nella ricetta, solo sucessivamente)
                if (ingrediente instanceof Ingrediente && ingrediente.getNome().equalsIgnoreCase(nome_ingrediente) && !gia_presente) {
                    nuovo_ingrediente.setNome(ingrediente.getNome());
                    nuovo_ingrediente.setQta((float)InputDati.leggiDoubleConMinimo("Inserisci la quantità di " + nome_ingrediente + " in " + ingrediente.getMisura() + ": ", 0));
                    nuovo_ingrediente.setMisura(ingrediente.getMisura());
                    //controllo che la nuova qta sia diversa da 0, in tal caso aggiungo l'ingrediente alla ricetta
                    if(nuovo_ingrediente.getQta() != 0.0) ingredienti_nuovo_piatto.add(nuovo_ingrediente);
                    trovato = true;
                    break; //esco dal for
                }
            }

            Visualizzazione.ripulisciConsole();
            //informo l'utente dell'esito dell'operazione di aggiunta dell'ingrediente alla ricetta
            if (!trovato) System.out.println("Ingrediente non trovato o già inserito nella ricetta");
            else if(nuovo_ingrediente.getQta() == 0.0) System.out.println("Quantità non valida");
            else System.out.println("Ingrediente aggiunto alla ricetta");

            InputDati.premerePerContinuare();
            //tramite il cortocircuito evito di chiedere se si vuole aggiungere un altro ingrediente se l'ingrediente non è stato trovato
        } while (ingredienti_nuovo_piatto.size() < Costanti.MINIMO_INGRED_PER_RICETTA || InputDati.yesOrNo("\nVuoi aggiungere un altro ingrediente alla ricetta?"));

        Ricetta ricetta = new Ricetta(ingredienti_nuovo_piatto, n_porzioni, lavoro_porzione);
        piatto.setRicetta(ricetta);
        gestore.getRistorante().getAddettoPrenotazione().getMenu().add(piatto);
        //postcondizione: il piatto è creato e aggiunto al menu del ristorante
        assert gestore.getRistorante().getAddettoPrenotazione().getMenu().contains(piatto);
    }

    /**
     * <h2>Metodo che crea un menu</h2><br>
     * <b>Precondizione:</b> la funzione non è null<br>
     * @param funzione funzionalità che dovrà aver il menu
     * @return il menu creato
     * @throws IllegalArgumentException se la funzione è null
     */
    public static MyMenu creaMenu(String funzione) {
        //precondizione: la funzione non è null
        if(funzione == null) throw new IllegalArgumentException("La funzione non può essere null");

        switch (funzione) {

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
                return new MyMenu(Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + Costanti.GESTORE.toUpperCase(Locale.ROOT), azioni_gestore);

            case Costanti.TEMPO:

                String[] azioni_tempo = new String[2];
                azioni_tempo[0] = "Incrementa di un giorno";
                azioni_tempo[1] = "Scegli una data";
                return new MyMenu(Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + Costanti.TEMPO.toUpperCase(Locale.ROOT), azioni_tempo);

            case Costanti.INIZIALIZZAZIONE:

                String[] azioni_inizializzazione = new String[7];
                azioni_inizializzazione[0] = "Modifica il numero di posti";
                azioni_inizializzazione[1] = "Modifica il lavoro persone";
                azioni_inizializzazione[2] = "Aggiungi ingrediente";
                azioni_inizializzazione[3] = "Aggiungi extra";
                azioni_inizializzazione[4] = "Aggiungi bevanda";
                azioni_inizializzazione[5] = "Aggiungi menu";
                azioni_inizializzazione[6] = "Aggiungi piatto";
                return new MyMenu(Costanti.FUNZIONALITA.toUpperCase(Locale.ROOT) + "di " + Costanti.INIZIALIZZAZIONE.toUpperCase(Locale.ROOT), azioni_inizializzazione);
        }
        return null;
    }
}
