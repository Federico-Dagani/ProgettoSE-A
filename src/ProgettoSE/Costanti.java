package ProgettoSE;

public class Costanti {

    //path
    public static final String FILE_RISTORANTE = "src/ProgettoSE/Ristorante.xml";
    //tempo
    public static final String TEMPO = "Tempo";
    //attori
    public static final String ATTORI = "attori";
    public static final String UTENTE = "Cliente";
    public static final String GESTORE = "Gestore";
    public static final String ADDETTO_PRENOTAZIONE = "addetto_prenotazione";
    public static final String MAGAZZINIERE = "magazziniere";
    //luoghi
    public static final String RISTORANTE = "ristorante";
    public static final String MAGAZZINO = "magazzino";
    //alimenti
    public static final String INGREDIENTE = "ingrediente";
    public static final String EXTRA = "extra";
    public static final String BEVANDA = "bevanda";
    //produzione
    public static final String RICETTA = "ricetta";
    public static final String PIATTO = "piatto";
    public static final String MENU_TEMATICO = "menu_tematico";
    public static final String PORTATA = "portata";
    //attributi alimento
    public static final String NOME = "nome";
    public static final String QTA = "qta";
    public static final String MISURA = "misura";
    //attributi produzione
    public static final String LAVORO_PERSONE = "lavoro_persone";
    public static final String CONS_PROCAPITE = "cons_procapite";
    public static final String INIZIO = "inizio";
    public static final String FINE = "fine";
    public static final String LAVORO_MENU = "lavoro_menu";
    public static final String N_PORZIONI = "n_porzioni";
    public static final String LAVORO_PORZIONE = "lavoro_porzione";
    public static final String DISPONIBILITA = "disponibilita";
    public static final String DISPONIBILITA_MENU = "disponibilita_menu";
    //attributi luoghi
    public static final String N_POSTI = "n_posti";
    //massaggi di inserimento
    public static final String INS_LAVORO_PERSONA = "Inserisci il nuovo numero di lavoro per persona: ";
    public static final String INS_CONS_PROCAPITE = "Inserisci il consumo procapite: ";
    public static final String INS_DATA_INIZIO = "Inserisci la data di inzio nel formato yyyy-mm-dd: ";
    public static final String INS_DATA_FINE = "Inserisci la data di fine nel formato yyyy-mm-dd: ";
    public static final String INS_DATA = "Inserisci una data valida (yyyy-mm-dd): ";
    public static final String INS_NOME = "Inserisci il nome: ";
    public static final String INS_QTA = "Inserisci la quantità: ";
    public static final String INS_MISURA = "Inserisci l'unità di misura: ";
    public static final String INS_LAVORO = "Inserisci il lavoro: ";
    //messaggi x menu
    public static final String FUNZIONALITA = "funzionalità ";
    public static final String USCITA_MENU = "... uscita dal menù ";
    public static final String END = "PROGRAMMA TERMINATO.";
    public static final String INIZIALIZZAZIONE = "Inizializzazione";
    //messaggi di errori
    public static final String UGUALE_ATTUALE = "Il numero di %s inserito è uguale a quello attuale";
    public static final String DATA_NON_VALIDA = "Data in formato non valido";
    public static final String GESTORE_NON_NULLO = "Il gestore non può essere nullo.";
    //valori ristorante
    public static final float IMPREVISTI_CUCINA = (float) (10.0/100);
    public static final float ALIMENTI_SCARTATI = (float) (5.0/100); //rappresenta scarti scaduti o deteriorati, a fine giornata
    public static final int MINIMO_INGRED_PER_RICETTA = 2 ;
    public static final int MINIMO_PIATTI_PER_MENU = 2;

    //comunicazioni di inzio/fine lettura/scrittura file
    public static final String INIZIO_FILE = "\n%s file: %s ...\n";
    public static final String FINE_FILE = "\nFine della %s del file :)\n";
    public static final String LETTURA = "Lettura";
    public static final String SCRITTURA = "scrittura";
    //errori vari nei servizi xml
    public static final String ERRORE_INIZIALIZZAZIONE_READER = "Errore nell'inizializzazione del reader: ";
    public static final String ERRORE_LETTURA_FILE = "Errore nella lettura del file: %s, per ulteriori info: %s\n";
    //benvenuto
    public static final String CORNICE_SUP = "┌———————————————————————————————————————————————————————┐";
    public static final String BENVENUTO = "Progetto di Ingegneria del Software A.A. 2022-2023";
    public static final  String CORNICE_INF = "└———————————————————————————————————————————————————————┘";
}