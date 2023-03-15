package ProgettoSE.Utilità;

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Attori.Gestore;
import ProgettoSE.Costanti;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;
import ProgettoSE.Ricetta;
import ProgettoSE.mylib.InputDati;
import ProgettoSE.mylib.MyMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class Creazione {

    public static Alimento creaAlimento(String tipo) {
        System.out.printf("\nInserisci i dati dell'alimento di tipo: %s \n\n", tipo);
        String nome = InputDati.leggiStringaConSpazio("Inserisci il nome: ");
        float quantita = (float) InputDati.leggiDoubleConMinimo("Inserisci la quantità: ", 0);
        String unita_misura = InputDati.leggiStringaNonVuota("Inserisci l'unità di misura: ");
        switch (tipo) {
            case "ingrediente":
                return new Ingrediente(nome, quantita, unita_misura);
            case "extra":
                float consumo_procapite = (float) InputDati.leggiDoubleConMinimo("Inserisci il consumo procapite: ", 0);
                return new Extra(nome, quantita, unita_misura, consumo_procapite);
            case "bevanda":
                consumo_procapite = (float) InputDati.leggiDoubleConMinimo("Inserisci il consumo procapite: ", 0);
                return new Bevanda(nome, quantita, unita_misura, consumo_procapite);
            default:
                return null;
        }
    }

    private static Prenotabile creaPrenotabile(Gestore gestore, String tipologia) {
        System.out.printf("\nInserisci i dati del %s \n\n", tipologia);
        String nome = InputDati.leggiStringaConSpazio("Inserisci il nome: ");
        float lavoro = (float) InputDati.leggiDoubleConMinimo("Inserisci il lavoro: ", 0);
        ArrayList<LocalDate> disponibilita = new ArrayList<>();
        do {
            boolean data_errata = false;
            do {
                try {
                    disponibilita.add(LocalDate.parse(InputDati.leggiStringaNonVuota("Inserisci la data di inizio nel formato yyyy-mm-dd: ")));
                    disponibilita.add(LocalDate.parse(InputDati.leggiStringaNonVuota("Inserisci la data di fine nel formato yyyy-mm-dd: ")));
                    data_errata = false;
                } catch (DateTimeParseException e) {
                    System.out.println("Data in formato non valido");
                    data_errata = true;
                }
            } while (data_errata);
        } while (InputDati.yesOrNo("\nVuoi aggiungere un'altra disponibilità?"));
        if (tipologia.equals(Costanti.MENU_TEMATICO)) {
            return new MenuTematico(nome, new ArrayList<>(), lavoro, disponibilita);
        } else if (tipologia.equals(Costanti.PIATTO)) {
            return new Piatto(nome, disponibilita, lavoro, new Ricetta());
        }
        return null;
    }

    public static void creaMenuTematico(Gestore gestore) {
        MenuTematico menu_tematico = (MenuTematico) creaPrenotabile(gestore, Costanti.MENU_TEMATICO);
        ArrayList<Piatto> piatti = new ArrayList<>();
        do {
            Visualizzazione.mostraPiatti(gestore.getRistorante().getAddettoPrenotazione().getMenu());
            String nome_piatto = InputDati.leggiStringaConSpazio("\nInserisci il nome del piatto: ");
            boolean trovato = false;
            for (Prenotabile piatto : gestore.getRistorante().getAddettoPrenotazione().getMenu()) {
                if (piatto instanceof Piatto && piatto.getNome().equalsIgnoreCase(nome_piatto)) {
                    piatti.add((Piatto) piatto);
                    trovato = true;
                    break;
                }
            }
            if (!trovato)
                System.out.println("Piatto non trovato");
        } while (piatti.size() < 1 || InputDati.yesOrNo("Vuoi aggiungere un altro piatto al menu?"));
        menu_tematico.setPiatti_menu(piatti);
        gestore.getRistorante().getAddettoPrenotazione().getMenu().add(menu_tematico);
    }

    public static void creaPiatto(Gestore gestore) {
        Piatto piatto = (Piatto) creaPrenotabile(gestore, Costanti.PIATTO);
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
                if (ingrediente instanceof Ingrediente && ingrediente.getNome().equalsIgnoreCase(nome_ingrediente)) {

                    nuovo_ingrediente.setNome(ingrediente.getNome());
                    nuovo_ingrediente.setQta((float)InputDati.leggiDoubleConMinimo("Inserisci la quantità di " + nome_ingrediente + " in " + ingrediente.getMisura() + ": ", 0));
                    nuovo_ingrediente.setMisura(ingrediente.getMisura());

                    if(nuovo_ingrediente.getQta() != 0.0) ingredienti_nuovo_piatto.add(nuovo_ingrediente);
                    else System.out.println("Ingrediente non aggiunto perchè la quantità è 0");
                    trovato = true;
                    break;
                }
            }
            if (!trovato) System.out.println("Ingrediente non trovato");
            InputDati.leggiStringa("\nPremi invio per continuare");
        } while (ingredienti_nuovo_piatto.size() < 1 || InputDati.yesOrNo("\nVuoi aggiungere un altro ingrediente alla ricetta?"));
        Ricetta ricetta = new Ricetta(ingredienti_nuovo_piatto, n_porzioni, lavoro_porzione);
        piatto.setRicetta(ricetta);
        gestore.getRistorante().getAddettoPrenotazione().getMenu().add(piatto);
    }

    /**
     * <h3>Metodo per la creazione dei vari menu</h3>
     *
     * @param funzione corrispondente alla tipologia del menu da creare
     * @return menu in base alla funzione specificata come parametro
     */
    public static MyMenu creaMenu(String funzione) {

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
