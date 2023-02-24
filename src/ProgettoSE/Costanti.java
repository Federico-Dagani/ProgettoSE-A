package ProgettoSE;

public class Costanti {

    //GESTIONE FILE XML
    //nomi file
    public static final String FILE_RISTORANTE = "src/ProgettoSE/Ristorante.xml" ;

    //tag
    public static final String RISTORANTE = "ristorante";
    public static final String N_POSTI = "n_posti";
    public static final String LAVORO_PERSONE = "lavoro_persone";
    public static final String MAGAZZINO = "magazzino";
    public static final String BEVANDA = "bevanda";
    public static final String NOME = "nome";
    public static final String QTA = "qta";
    public static final String MISURA = "misura";
    public static final String CONS_PROCAPITE = "cons_procapite";
    public static final String INGREDIENTE = "ingrediente";
    public static final String EXTRA = "extra";
    public static final String RICETTA = "ricetta";
    public static final String PIATTO = "piatto";
    public static final String INIZIO = "inizio";
    public static final String FINE = "fine";
    public static final String MENU_TEMATICO = "menu_tematico";
    public static final String LAVORO_MENU = "lavoro_menu";
    public static final String PORTATA = "portata";
    public static final String N_PORZIONI = "n_porzioni";
    public static final String LAVORO_PORZIONE = "lavoro_porzione";
    public static final String DISPONIBILITA = "disponibilita";
    public static final String DISPONIBILITA_MENU = "disponibilita_menu";

    //comunicazioni di inzio/fine lettura/scrittura file
    public static final String INIZIO_FILE = "\nInizio a %s il file: %s ...\n";
    public static final String FINE_FILE = "\nFine della %s del file :)\n";
    public static final String LETTURA = "lettura";
    public static final String SCRITTURA = "scrittura";
    //errori vari nei servizi xml
    public static final String ERRORE_INIZIALIZZAZIONE_READER = "Errore nell'inizializzazione del reader: ";
    public static final String ERRORE_LETTURA_FILE = "Errore nella lettura del file: %s, per ulteriori info: %s\n";
    public static final String ERRORE_STRADA = "Errore strada non trovata :(";
    public static final String ERRORE_TIPOLOGIA = "Tipologia non esistente";
    public static final String ERRORE_INIZIALIZZAZIONE_WRITER = "Errore nell'inizializzazione del writer: %s\n";
    public static final String ERRORE_SCRITTURA_FILE = "Errore nella scrittura del file: %s\n";
    //altro
    public static final String ENCODING = "utf-8";
    public static final String VERSION = "1.0";

    //GESTIONE MAIN
    //errori vari nel main
    public static final String ERR_ID_NON_TROVATO = "id non trovato:(";
    public static final String ERRORE_NUM_CITTA = "Il numero per le città inserito non è corretto";
    //benvenuto
    public static final String CORNICE_SUP = "┌———————————————————————————————————————————————————————————————————————————————————————┐";
    public static final String BENVENUTO = "Benvenut* nel programma RovinePerdute del gruppo Programmazione di fondamenti :)";
    public static final  String CORNICE_INF = "└———————————————————————————————————————————————————————————————————————————————————————┘";
    //fase iniziale
    public static final String CINQUE = "5 citta";
    public static final String DODICI = "12 citta";
    public static final String CINQUANTA = "50 citta";
    public static final String DUECENTO = "200 citta";
    public static final String DUEMILA = "2000 citta";
    public static final String DIECIMILA = "10000 citta";
    //altro
    public static final String NUM_CITTA = "numeri città";
    public static final String INSERIMENTO_NUM_CITTA = "Inserisci il numero delle città per la generazione del grafo: ";
    public static final String TONATHIU = "tonathiu";
    public static final String METZTLI = "metztli";
    public static final String CALCOLO_IN_CORSO = "\nSto calcolando il percorso del veicolo %s, potrebbe volerci del tempo :)\n";

}

