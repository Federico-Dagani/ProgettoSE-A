package ProgettoSE.Attori;

import ProgettoSE.*;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.ServiziFileXML.LetturaFileXML;

import java.time.LocalDate;
import java.util.ArrayList;

public class Gestore extends Persona {
    //ATTRIBUTI
    private Ristorante ristorante;
    //METODI
    /**
     * <h2>Costruttore</h2>
     * @param nome nome del gestore
     * @param ristorante ristorante gestito dal gestore
     */
    public Gestore(String nome, Ristorante ristorante) {
        super(nome);
        this.ristorante = ristorante;
    }
    /**
     * <h2>Getter</h2>
     * @return ristorante
     */
    public Ristorante getRistorante() {
        return ristorante;
    }
    /**
     * <h2>Invariante di classe</h2>
     * @return true se ristorante non è null, false altrimenti
     */
    private boolean risOk() {
        return ristorante != null;
    }
    /**
     * <h2>Metodo che inizializza il ristorante leggendo i dati dal file XML</h2>
     * Dopo la lettura invoca i metodi controllaMenu() e controllaRicette() per controllare che i menu tematici e le ricette siano valide<br><br>
     * <b>Precondizione:</b> il ristorante è null<br>
     * <b>Postcondizione:</b> il ristorante non è null
     * @return stringa che contiene i messaggi di errore (se ci sono) del menu tematico e delle ricette rifiutati
     * @throws IllegalArgumentException se il ristorante è già stato inizializzato
     */
    public String inizializzaRistorante() {
        //precondizione: il ristorante è null
        if(ristorante != null) throw new IllegalArgumentException("Il ristorante è già stato inizializzato");
        //leggo il ristorante dal file xml e lo inizializzo
        LetturaFileXML letturaFileXML = new LetturaFileXML();
        ristorante = letturaFileXML.leggiRistorante(Costanti.FILE_RISTORANTE);
        //controllo che i menu tematici e le ricette siano valide
        String messaggio = controllaMenu();
        messaggio += controllaRicette();
        //postcondizione: il ristorante non è null
        assert risOk();
        return messaggio;
    }

    /**
     * <h2>Metodo che controlla se i menu tematici del ristorante sono invalidi (e in tal caso li elimina) secondo 2 vincoli:</h2>
     * <ul><li>il lavoro di un menu tematico deve essere < 4/3 del lavoro totale del ristorante</li>
     * <li>il menu tematico deve contenere piatti disponibili nel periodo di disponibilità del menu</li></ul>
     * <b>Precondizione:</b> il ristorante non è null<br>
     * <b>Postcondizione:</b> la lunghezza menu_tematici da eliminare <= lunghezza menu_tematici iniziale
     * @return stringa che contiene i messaggi di errore (se ci sono) dei menu tematici rifiutati
     *
     */
    public String controllaMenu() {
        //precondizione: il ristorante non è null
        assert risOk();
        String messaggio = "";
        ArrayList<Prenotabile> menu_da_eliminare = new ArrayList<>();
        int lunghezza_menu_tematici_iniziale = ristorante.getAddettoPrenotazione().getMenu().size();
        //ciclo i menu_tematici del ristorante in modo da controllare che il lavoro sia < 4/3 del lavoro totale del ristorante (se non lo è lo elimino)
        for (Prenotabile menu_tematico : ristorante.getAddettoPrenotazione().getMenu()) {
            if (menu_tematico instanceof MenuTematico) {
                //controllo se il lavoro è < 4/3 del lavoro totale del ristorante (se non lo è lo elimino)
                if (((MenuTematico) menu_tematico).getLavoro_menu() > ristorante.getLavoro_persona() * 4 / 3) {
                    menu_da_eliminare.add(menu_tematico);
                    messaggio += "\nIl menu tematico " + menu_tematico.getNome() + " è stato scartato perchè il lavoro richiesto è maggiore del 4/3 del lavoro totale del ristorante";
                }

                //controllo se il menu contiene piatti che non sono disponibili nel periodo di disponibilità del menu
                if (!disponibilitaPiattiCorrette((MenuTematico) menu_tematico)) {
                    menu_da_eliminare.add(menu_tematico);
                    messaggio += "\nIl menu tematico " + menu_tematico.getNome() + " è stato scartato perchè contiene piatti non disponibili nelle date del menu";
                }
            }
        }
        //elimino i menu tematici invalidi selezionati e memorizzati in menu_da_eliminare per non avere problemi di concorrenza
        menu_da_eliminare.forEach(menu_tematico -> ristorante.getAddettoPrenotazione().getMenu().remove(menu_tematico));
        //postcondizione: la lunghezza menu_tematici da eliminare <= lunghezza menu_tematici iniziale
        assert menu_da_eliminare.size() <= lunghezza_menu_tematici_iniziale;
        return messaggio;
    }
    /**
     * <h2>Metodo di supporto che controlla se le disponibilità dei piatti di un menu sono valide per quel menu</h2>
     * <b>Precondizione:</b> il menu non è null<br>
     * @param menu_tematico menu tematico da controllare
     * @return true se le disponibilità dei piatti sono valide, false altrimenti
     */
    public boolean disponibilitaPiattiCorrette(MenuTematico menu_tematico) {
        //precondizione: il menu non è null
        assert menu_tematico != null;
        for (Piatto piatto : menu_tematico.getPiatti_menu()) {
            for (int i = 0; i < menu_tematico.getDisponibilità().size(); i += 2) {
                if (!piattoDisponibileInData(piatto, menu_tematico.getDisponibilità().get(i), menu_tematico.getDisponibilità().get(i + 1)))
                    return false;
            }
        }
        return true;
    }
    /**
     * <h2>Metodo di supporto che controlla se le disponibilità del piatto comprendono tutto l'intervallo delle date inizio fine</h2>
     * <b>Precondizioni:</b><br<li>il piatto non è null<li>le date non sono null<br></ul>
     * @param piatto piatto da controllare
     * @param inizio data inizio intervallo
     * @param fine data fine intervallo
     * @return true se il piatto è disponibile in tutto l'intervallo, false altrimenti
     * @throws IllegalArgumentException se il piatto è null o le date sono null
     */
    private boolean piattoDisponibileInData(Piatto piatto, LocalDate inizio, LocalDate fine) {
        //precondizione: il piatto non è null
        if (piatto == null) throw new IllegalArgumentException("Il piatto non può essere null");
        //precondizione: le date non sono null
        if (inizio == null || fine == null) throw new IllegalArgumentException("Le date non possono essere null");
        for (int i = 0; i < piatto.getDisponibilità().size(); i += 2) {
            //se trovo almeno una disponibilita del piatto che copre questo intervallo (ovvero una parte della disponibilità del menu tematico) aòòpra ritorno true
            if (Tempo.data1AnticipaData2(piatto.getDisponibilità().get(i), inizio) && Tempo.data1AnticipaData2(fine, piatto.getDisponibilità().get(i + 1)))
                return true;
        }
        return false;
    }
    /**
     * <h2>Metodo che controlla se le ricette dei piatti sono valide per il ristorante (e in caso gli elimina) secondo 2 vincoli</h2>
     * <ol><li>il lavoro di un piatto deve essere < del lavoro per persona</li>
     * <li>il menu tematico deve contenere piatti disponibili nel periodo di disponibilità del menu</li></ol>
     * <b>Precondizione:</b> il ristorante non è null<br>
     * <b>Postcondizione:</b> la lunghezza menu da eliminare <= lunghezza menu iniziale<br>
     * @return stringa che contiene i messaggi di errore (se ci sono) dei piatti rifiutati
     */
    public String controllaRicette() {
        //precondizione: il ristorante non è null
        assert risOk();
        String messaggio = "";
        ArrayList<Prenotabile> piatti_da_eliminare = new ArrayList<>();
        int lunghezza_menu_iniziale = ristorante.getAddettoPrenotazione().getMenu().size();
        //ciclo le ricette del ristorante in modo da controllare che
        for (Prenotabile piatto : ristorante.getAddettoPrenotazione().getMenu()) {
            if (piatto instanceof Piatto) {
                //controllo che il carico di lavoro del piatto sia una frazione del carico di lavoro per persona
                if (((Piatto) piatto).getRicetta().getLavoro_porzione() >= ristorante.getLavoro_persona()) {
                    piatti_da_eliminare.add(piatto);
                    messaggio += "\nIl piatto " + piatto.getNome() + " è stato scartato perchè il lavoro del piatto è maggiore del lavoro per persona del ristorante" ;
                }
                //controllo che le disponibilità del piatto siano coerenti: data di inizio precede data di fine disponibilità
                for (int i = 0; i < piatto.getDisponibilità().size(); i += 2) {
                    if (Tempo.data1AnticipaData2(piatto.getDisponibilità().get(i + 1), piatto.getDisponibilità().get(i))) {
                        piatti_da_eliminare.add(piatto);
                        messaggio += "\nIl piatto " + piatto.getNome() + " è stato scartato perchè la disponibilità non è valida";
                    }
                }
            }
        }
        piatti_da_eliminare.forEach(piatto -> ristorante.getAddettoPrenotazione().getMenu().remove(piatto));
        //postcondizione: la lunghezza piatti_da_eliminare <= lunghezza menu iniziale
        assert piatti_da_eliminare.size() <= lunghezza_menu_iniziale;
        return messaggio;
    }
    /**
     * <h2>Metodo che comunica al magazziniere la lista della spesa e al cuoco la lista dei piatti da cucinare per il giorno attuale</h2>
     * <b>Precondizione:</b> la data non è null<br>
     * <b>Postcondizione:</b> la lista della spesa è aggiornata<br>
     * @param data_attuale data attuale
     * @return String messaggio
     * @throws IllegalArgumentException se la data non è valida
     */
    public String comunica(LocalDate data_attuale) {
        //precondizione data_attuale non null
        if (data_attuale == null) throw new IllegalArgumentException("Data non valida");
        //se non ci sono prenotazioni per il giorno attuale non faccio nulla e ritorno un messaggio vuoto
        if (ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale).isEmpty()) return "";
        //altrimenti unisco le prenotazioni del giorno in un'unica grande prenotazione
        Prenotazione prenotazione_del_giorno = ristorante.getAddettoPrenotazione().unisciPrenotazioni(ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale));
        //faccio calcolare al magazziniere la lista della spesa basandosi sulla grande prenotazione
        ristorante.getMagazziniere().creaListaSpesa(prenotazione_del_giorno);
        //faccio aggiungere al magazziniere gli alimenti acquistati e mi faccio comunicare cosa sta aggiungendo
        String messaggio = ristorante.getMagazziniere().aggiungiSpesaInMagazzino();
        //faccio eliminare dal magazzino gli alimenti che sono stati consumati in cucina
        ristorante.getMagazziniere().portaInCucina(prenotazione_del_giorno);
        //faccio eliminare dal addetto prenotazione le prenotazioni vecchie comprese quelle in data odierna(che sono state già elaborate)
        ristorante.getAddettoPrenotazione().rimuoviPrenotazioniVecchie(data_attuale);
        //postcondizione messaggio non null
        assert messaggio != null;
        return messaggio;
    }
}