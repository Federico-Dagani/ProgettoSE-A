package ProgettoSE.Attori;
//import classi progetto
import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Menu.*;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;
import ProgettoSE.Prenotazione;
import ProgettoSE.Tempo;
//import gestione tempo
import java.time.LocalDate;
//import strutture dati
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddettoPrenotazione extends Persona {

    //ATTRIBUTI
    private ArrayList<Prenotazione> prenotazioni;
    private ArrayList<Prenotabile> menu;

    //MANCA INVARIANTE DI CLASSE

    //METODI
    /**
     * <h2>Meotodo costruttore della classe AdettoPrenotazione</h2>
     * @param nome il nome dell'addetto alle prenotazioni
     * @param prenotazioni le prenotazioni effettuate
     * @param menu il menu complessivo (composto da piatti e menu tematici
     */
    public AddettoPrenotazione(String nome, ArrayList<Prenotazione> prenotazioni, ArrayList<Prenotabile> menu) {
        super(nome);
        this.prenotazioni = prenotazioni;
        this.menu = menu;
    }
    //getters
    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
    public ArrayList<Prenotabile> getMenu() {
        return menu;
    }
    //setters
    public void setMenu(ArrayList<Prenotabile> menu) {
        this.menu = menu;
    }

    /**
     * <h2>Metodo che aggiunge un menu alla carta al menu complessivo</h2>
     * <b>Precondizione: </b>il menu alla carta deve essere composto da piatti <br
     * <b>Postcondizione: </b>il menu alla carta è stato effettivamente aggiunto al menu complessivo
     * @param menu_carta il menu alla carta (ovvero un insieme di piatti) da aggiungere
     * @throws IllegalArgumentException se il menu alla carta non è composto da piatti
     * @return void
     */
    public void aggiungiMenu_carta(MenuCarta menu_carta) {
        //precondizione: il menu alla carta è composto da piatti
        if (menu_carta.getPiatti_menu().isEmpty()) throw new IllegalArgumentException("Il menu alla carta non è composto da piatti");
        //scorro i piatti e li aggiungo
        for (Piatto piatto : menu_carta.getPiatti_menu()) {
            menu.add(piatto);
        }
        //postcondizione: il menu alla carta è stato aggiunto al menu complessivo
        assert menu.contains(menu_carta);
    }

    /**
     * <h2>Metodo che aggiunge il menu tematico nel menu complessivo (composto da piatti e menu tematici)</h2>
     * <b>Precondizione: </b>il menu tematico deve essere composto da piatti <br>
     * <b>Postcondizione: </b>il menu tematico è stato effettivamente aggiunto al menu complessivo
     * @param menu_tematico un singolo menu tematico
     * @throws IllegalArgumentException se il menu tematico non è composto da piatti
     * @return void
     */
    public void aggiungiMenu_tematico(MenuTematico menu_tematico) {
        //precondizinoe: il menu tematico è composto da piatti
        if (menu_tematico.getPiatti_menu().isEmpty()) throw new IllegalArgumentException("Il menu tematico non è composto da piatti");
        menu.add(menu_tematico);
        //postcondizione: il menu tematico è stato aggiunto al menu complessivo
        assert menu.contains(menu_tematico);
    }

    /**
     * <h2>Metodo che rimuove le prenotazioni antecedenti alla data attuale</h2>
     * <b>Precondizione: </b>la data attuale è valida <br>
     * <b>Postcondizione: </b>le prenotazioni antecedenti alla data attuale sono state eliminate
     * @param data_attuale la data attuale
     * @throws IllegalArgumentException se la data attuale non è valida
     * @return void
     */
    public void rimuoviPrenotazioniVecchie(LocalDate data_attuale) {
        //precondizione: la data attuale è valida
        if (data_attuale == null) throw new IllegalArgumentException("La data attuale non è valida");
        ArrayList<Prenotazione> prenotazioni_da_eliminare = new ArrayList<>();
        //scorro le prenotazioni e le aggiungo all'arraylist delle prenotazioni da eliminare controllando la data
        //se dovessi fare prenotazioni.remove(prenotazione) all'interno del ciclo for, mi darebbe un errore di concorrenza
        prenotazioni.forEach(prenotazione -> {
            if (prenotazione.getData().isBefore(data_attuale) || prenotazione.getData().isEqual(data_attuale)){
                prenotazioni_da_eliminare.add(prenotazione);
            }
        });
        //eliminio le prenotazioni
        prenotazioni_da_eliminare.forEach(prenotazione_da_eliminare -> prenotazioni.remove(prenotazione_da_eliminare));
        //postcondizione: le prenotazioni antecedenti alla data attuale sono state eliminate
        assert !prenotazioni.containsAll(prenotazioni_da_eliminare);
    }

    /**
     * <h2>Metodo che calcola il menu del giorno relativo ad una data inserita</h2>
     * <b>Precondizione: </b>la data attuale è valida <br>
     * <b>Postcondizione: </b>il menu del giorno è stato calcolato
     * @param data_attuale la data attuale
     * @throws IllegalArgumentException se la data attuale non è valida
     * @return ArrayList<Prenotabile> il menu disponibile in data inserita
     */
    public ArrayList<Prenotabile> calcolaMenuDelGiorno(LocalDate data_attuale) {
        //precondizione: la data attuale è valida
        if (data_attuale == null) throw new IllegalArgumentException("La data attuale non è valida");
        ArrayList<Prenotabile> menu_del_giorno = new ArrayList<>();
        for (Prenotabile prenotabile : menu) {
            //prendo le disponibilità del piatto/menu tematico
            ArrayList<LocalDate> disponibilita = prenotabile.getDisponibilità();
            int inizio = 0;
            //per ogni disponibilità, controllo se la data attuale è compresa tra la data di inizio e di fine disponibilità
            for (int i = 0; i < disponibilita.toArray().length / 2; i++) {
                if( data_attuale.getDayOfYear() >= disponibilita.get(inizio).getDayOfYear() && data_attuale.getDayOfYear() <= disponibilita.get(inizio+1).getDayOfYear() && !menu_del_giorno.contains(prenotabile)){
                    menu_del_giorno.add(prenotabile);
                }
                inizio += 2;
            }
        }
        //postcondizione: il menu del giorno è stato calcolato
        assert !menu_del_giorno.isEmpty();

        return menu_del_giorno;
    }

    /**
     * <h2>Metodo che esegue diversi controlli sulla data</h2>
     * <b>Precondizione: </b>il numero dei posti del ristorante è positivo <br>
     * <b>Postcondizione: </b>la data inserita è corretta
     * @param data_attuale la data attuale
     * @param stringa_data_prenotazione data della prenotazione del cliente (di tipo String)
     * @throws IllegalArgumentException il numero dei posti è negativo
     * @return un intero rappresentate un eventuale messaggio d'errore oppure la data della prenotazione (di tipo LocalDate) in caso non ci siano errori <br>
     * <b>Legenda: </b> <br>
     * <b>1</b> se il formato della data non è valido <br>
     * <b>2</b> se la data inserita corrisponde o precede la data attuale <br>
     * <b>3</b> se il ristorante ha esaurito i posti disponibili in quella data <br>
     * <b>4</b> se la data inserita è di sabato o di domenica <br>
     * <b>0</b> se la data inserita è corretta <br>
     */
    public int controlloDataPrenotazione(LocalDate data_attuale, String stringa_data_prenotazione, int posti_ristorante) {

        //precondizioni: il numero dei posti del ristorante è maggiore di 0
        if (posti_ristorante <= 0) throw new IllegalArgumentException("Il numero dei posti del ristorante non è valido");

        LocalDate data_prenotazione = Tempo.parsaData(stringa_data_prenotazione);
        if(data_prenotazione == null)
            return 1;
        if (data_prenotazione.isBefore(data_attuale) || data_prenotazione.isEqual(data_attuale))
            return 2;
        if (posti_ristorante - calcolaPostiOccupati(data_prenotazione) == 0)
            return 3;
        if(data_prenotazione.getDayOfWeek().getValue() == 7 || data_prenotazione.getDayOfWeek().getValue() == 6)
            return 4;
        //postcondizione: la data inserita è corretta
        assert data_prenotazione.isAfter(data_attuale);
        return 0;
    }

    /**
     * <h2>Metodo che calcola il numero di posti occupati in una data</h2>
     * <b>Precondizione: </b>la data della prenotazione è valida <br>
     * <b>Postcondizione: </b>il numero di posti occupati è stato calcolato
     * @param data_prenotazione la data della prenotazione
     * @throws IllegalArgumentException se la data della prenotazione non è valida
     * @return int il numero di posti occupati in quella data
     */
    public int calcolaPostiOccupati(LocalDate data_prenotazione){
        //precondizione: la data della prenotazione è valida
        if (data_prenotazione == null) throw new IllegalArgumentException("La data della prenotazione non è valida");
        int posti_occupati = 0;
        for (Prenotazione prenotazione : prenotazioni){
            if(prenotazione.getData().isEqual(data_prenotazione)){
                //se sono alla data corretta, aggiungo il numero di posti occupati
                posti_occupati += prenotazione.getN_coperti();
            }
        }
        //postcondizione: il numero di posti occupati è stato calcolato
        assert posti_occupati >= 0;
        return posti_occupati;
    }

    /**
     * <h2>Metodo che calcola il numero di posti rimanenti in una data</h2>
     * <b>Precondizione: </b>la data della prenotazione è valida <br>
     * <b>Postcondizione: </b>il numero di posti rimanenti è stato calcolato
     * @param data_prenotazione la data della prenotazione
     * @param lavoro_persona il lavoro per persona
     * @param n_posti il numero di posti del ristorante
     * @throws IllegalArgumentException se la data della prenotazione non è valida
     * @return int il numero di posti rimanenti in quella data
     */
    public int stimaPostiRimanenti(LocalDate data_prenotazione, int lavoro_persona, int n_posti){

        //precondizione: la data della prenotazione è valida
        if (data_prenotazione == null) throw new IllegalArgumentException("La data della prenotazione non è valida");

        float lavoro_rimanente = n_posti*lavoro_persona - unisciPrenotazioni(filtraPrenotazioniPerData(data_prenotazione)).getLavoro_prenotazione();

        //postcondizione: il numero di posti rimanenti è stato calcolato
        assert lavoro_rimanente >= 0;

        return (int) Math.ceil(lavoro_rimanente/lavoro_persona);

    }

    /**
     * <h2>Metodo che controlla se il lavoro complessivo di una prenotazione può essere soddisfatto dal personale del ristorante</h2>
     * <b>Precondizione: </b>la data della prenotazione è valida <br>
     * <b>Postcondizione: </b>il lavoro complessivo di una prenotazione può essere soddisfatto dal personale del ristorante
     * @param data_prenotazione la data della prenotazione
     * @param lavoro_persona il lavoro per persona
     * @param n_posti il numero di posti del ristorante
     * @param possibile_prenotazione la prenotazione che si vuole aggiungere
     * @throws IllegalArgumentException se la data della prenotazione non è valida
     * @return boolean true se il lavoro complessivo di una prenotazione può essere soddisfatto dal personale del ristorante, false altrimenti
     */
    public boolean validaCaricoLavoro(LocalDate data_prenotazione, int lavoro_persona, int n_posti, Prenotazione possibile_prenotazione){

        //precondizione: la data della prenotazione è valida
        if (data_prenotazione == null) throw new IllegalArgumentException("La data della prenotazione non è valida");

        ArrayList<Prenotazione> possibili_prenotazioni = filtraPrenotazioniPerData(data_prenotazione);
        //aggiungo la prenotazione possibile a quelle già presenti in tal data
        possibili_prenotazioni.add(possibile_prenotazione);

        //postcondizione: il lavoro complessivo di una prenotazione può essere soddisfatto dal personale del ristorante
        assert unisciPrenotazioni(possibili_prenotazioni).getLavoro_prenotazione() <= lavoro_persona*n_posti + (20/100)*lavoro_persona*n_posti;

       //ora calcolo il lavoro totale della somma delle prenotazioni(questo array che ho creato: possibili_prenotazioni)
        if(unisciPrenotazioni(possibili_prenotazioni).getLavoro_prenotazione() > lavoro_persona*n_posti + (20/100)*lavoro_persona*n_posti){
            return false;
        }else {
            return true;
        }
    }

    /**
     * <h2>Metodo che unisce le prenotazioni di un giorno in una sola prenotazione</h2>
     * <b>Precondizione: </b>la lista delle prenotazioni è nulla <br>
     * <b>Postcondizione: </b>le prenotazioni sono state unite
     * @param prenotazioni_in_corso le prenotazioni di un giorno
     * @throws IllegalArgumentException se la lista delle prenotazioni è vuota
     * @return Prenotazione la prenotazione complessiva
     */
    public Prenotazione unisciPrenotazioni(ArrayList<Prenotazione> prenotazioni_in_corso){

        //precondizione: la lista delle prenotazioni è nulla
        if (prenotazioni_in_corso == null) throw new IllegalArgumentException("La lista delle prenotazioni è nulla");

        HashMap<Prenotabile, Integer> scelte_complessive = new HashMap<>();
        HashMap<Alimento, Float> cons_bevande_complessivo = new HashMap<>();
        HashMap<Alimento, Float> cons_extra_complessivo = new HashMap<>();

        for(Prenotazione prenotazione : prenotazioni_in_corso){
            //ciclo sui consumi delle bevande (es: <Coca Cola, 4>)
            for(Map.Entry<Alimento, Float> cons_bevanda : prenotazione.getCons_bevande().entrySet()) {
                //se non è presente allora aggiungo il consumo di quella bevanda
                if (!cons_bevande_complessivo.containsKey(cons_bevanda.getKey())) {
                    cons_bevande_complessivo.put(cons_bevanda.getKey(), cons_bevanda.getValue());
                } else {
                    //altrimenti aggiorno il consumo di quella bevanda, sommandolo alla precedente
                    float nuovo_cons = cons_bevande_complessivo.get(cons_bevanda.getKey()) + cons_bevanda.getValue();
                    cons_bevande_complessivo.put(cons_bevanda.getKey(), nuovo_cons);
                }
            }
            //con gli extra eseguo la stessa cosa come con le bevande
            for (Map.Entry<Alimento, Float> cons_extra : prenotazione.getCons_extra().entrySet()) {
                if (!cons_extra_complessivo.containsKey(cons_extra.getKey())) {
                    cons_extra_complessivo.put(cons_extra.getKey(), cons_extra.getValue());
                } else {
                    float nuovo_cons = cons_extra_complessivo.get(cons_extra.getKey()) + cons_extra.getValue();
                    cons_extra_complessivo.put(cons_extra.getKey(), nuovo_cons);
                }
            }

            for(Map.Entry<Prenotabile, Integer> scelta_prenotazione : prenotazione.getScelte().entrySet()) {
                Prenotabile prenotabile_scelto = scelta_prenotazione.getKey();
                if (scelte_complessive.containsKey(prenotabile_scelto)) {
                    //il prenotabile è già presente nell'hash map complessivo, dunque devo solo incrementare il numero di porzioni che voglio di quello
                    int n_porzioni = scelte_complessive.get(prenotabile_scelto) + scelta_prenotazione.getValue();
                    scelte_complessive.put(prenotabile_scelto, n_porzioni);
                } else {
                    //il prenotabile non è presente, lo agguiungo
                    scelte_complessive.put(scelta_prenotazione.getKey(), scelta_prenotazione.getValue());
                }
            }
        }
        //postcondizione: le prenotazioni sono state unite
        assert !scelte_complessive.isEmpty();
        return new Prenotazione(scelte_complessive, cons_bevande_complessivo, cons_extra_complessivo);
    }

    /**
     * <h2>Metodo che filtra le prenotazioni in base alla data</h2>
     * <b>Precondizione: </b>la data della prenotazione non è valida <br>
     * <b>Postcondizione: </b>le prenotazioni sono state filtrate
     * @param data la data della prenotazione
     * @throws IllegalArgumentException se la data della prenotazione non è valida
     * @return ArrayList<Prenotazione> le prenotazioni filtrate
     */
    public ArrayList<Prenotazione> filtraPrenotazioniPerData(LocalDate data){
        //precondizione: la data della prenotazione è valida
        if (data == null) throw new IllegalArgumentException("La data della prenotazione non è valida");
        ArrayList<Prenotazione> prenotazioni_filtrate = new ArrayList<>();
        for (Prenotazione prenotazione : this.prenotazioni){
            if(prenotazione.getData().equals(data))
                prenotazioni_filtrate.add(prenotazione);
        }
        //postcondizione: le prenotazioni sono state filtrate
        assert prenotazioni_filtrate.size() >= 0;
        return prenotazioni_filtrate;
    }

    /**
     * <h2>Metodo che controlla se le ricette dei piatti sono valide per il ristorante (e in caso gli elimina) secondo 2 vincoli</h2>
     * <ol><li>il lavoro di un piatto deve essere < del lavoro per persona</li>
     * <li>il menu tematico deve contenere piatti disponibili nel periodo di disponibilità del menu</li></ol>
     * <b>Postcondizione:</b> la lunghezza menu da eliminare <= lunghezza menu iniziale<br>
     * @param lavoro_persona il lavoro per persona
     * @return stringa che contiene i messaggi di errore (se ci sono) dei piatti rifiutati
     */
    public String controllaRicette(int lavoro_persona) {
        String messaggio = "";
        ArrayList<Prenotabile> piatti_da_eliminare = new ArrayList<>();
        int lunghezza_menu_iniziale = menu.size();
        //ciclo le ricette del ristorante in modo da controllare che
        for (Prenotabile piatto : menu) {
            if (piatto instanceof Piatto) {
                //controllo che il carico di lavoro del piatto sia una frazione del carico di lavoro per persona
                if (((Piatto) piatto).getRicetta().getLavoro_porzione() >= lavoro_persona) {
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
        piatti_da_eliminare.forEach(piatto -> menu.remove(piatto));
        //postcondizione: la lunghezza piatti_da_eliminare <= lunghezza menu iniziale
        assert piatti_da_eliminare.size() <= lunghezza_menu_iniziale;
        return messaggio;
    }

    /**
     * <h2>Metodo che controlla se i menu tematici del ristorante sono invalidi (e in tal caso li elimina) secondo 2 vincoli:</h2>
     * <ul><li>il lavoro di un menu tematico deve essere < 4/3 del lavoro totale del ristorante</li>
     * <li>il menu tematico deve contenere piatti disponibili nel periodo di disponibilità del menu</li></ul>
     * <b>Precondizione:</b> lavoro_persona >= 0 <br>
     * <b>Postcondizione:</b> la lunghezza menu_tematici da eliminare <= lunghezza menu_tematici iniziale
     * @param lavoro_persona il lavoro per persona del ristorante
     * @return stringa che contiene i messaggi di errore (se ci sono) dei menu tematici rifiutati
     */
    public String controllaMenu(int lavoro_persona) {
        //precondizione: lavoro_persona >= 0
        if (lavoro_persona < 0) throw new IllegalArgumentException("Il lavoro per persona non può essere negativo");
        String messaggio = "";
        ArrayList<Prenotabile> menu_da_eliminare = new ArrayList<>();
        int lunghezza_menu_tematici_iniziale = menu.size();
        //ciclo i menu_tematici del ristorante in modo da controllare che il lavoro sia < 4/3 del lavoro totale del ristorante (se non lo è lo elimino)
        for (Prenotabile menu_tematico : menu) {
            if (menu_tematico instanceof MenuTematico) {
                //controllo se il lavoro è < 4/3 del lavoro totale del ristorante (se non lo è lo elimino)
                if (((MenuTematico) menu_tematico).getLavoro_menu() > lavoro_persona * 4 / 3) {
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
        menu_da_eliminare.forEach(menu_tematico -> menu.remove(menu_tematico));
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
    private boolean disponibilitaPiattiCorrette(MenuTematico menu_tematico) {
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
}
