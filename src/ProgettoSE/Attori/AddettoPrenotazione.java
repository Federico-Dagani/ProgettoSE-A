package ProgettoSE.Attori;

import ProgettoSE.Menu.*;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;
import ProgettoSE.Prenotazione;
import ProgettoSE.mylib.InputDati;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddettoPrenotazione extends Persona {

    //ATTRIBUTI
    public AddettoPrenotazione(String nome) {
        super(nome);
    }

    private ArrayList<Prenotazione> prenotazioni;
    private ArrayList<Prenotabile> menu;

    //METODI
    //costruttore
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
    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public void setMenu(ArrayList<Prenotabile> menu) {
        this.menu = menu;
    }

    /**
     * metodo, utilizzato nella lettura Xml, che aggiunge i piatti del menu alla carta nel menu complessivo (composto da piatti e menu tematici)
     *
     * @param menu_carta contenente i piatti del menu alla carta
     */
    public void aggiungiMenu_carta(Menu menu_carta) {
        for (Piatto piatto : menu_carta.getPiatti_menu()) {
            menu.add(piatto);
        }
    }

    /**
     * metodo, utilizzato nella lettura Xml, che aggiunge i menu tematici nel menu complessivo (composto da piatti e menu tematici)
     *
     * @param menu_tematico un singolo menu tematico
     */
    public void aggiungiMenu_tematico(MenuTematico menu_tematico) {
        menu.add(menu_tematico);
    }

    /**
     * aggiunge una prenotazione solamente se valida
     *
     * @param prenotazione  l'oggetto prenotazione da aggiungere
     * @param data_corrente la data del giorno in cui si riceve la prenotazione
     * @return true se la prenotazione è valida ed è stata aggiunta, false se non è valida (in tal caso viene rifiutata)
     */
    public boolean aggiungiPrenotazione(Prenotazione prenotazione, LocalDate data_corrente) {
        if (prenotazione.getN_coperti() >= 1 && prenotazione.getData().isAfter(data_corrente)) {
            prenotazioni.add(prenotazione);
            return true;
        } else return false;
    }

    /**
     * @param data_corrente data del giorno da controllare
     * @return true se è stata eliminata almeno 1 prenotazione dalla lista prenotazioni,
     * false se non è stata eliminata alcuna prenotazione
     */
    public boolean eliminaPrenotazioni(LocalDate data_corrente) {
        int l_iniziale = prenotazioni.toArray().length;
        for (Prenotazione prenotazione : prenotazioni) {
            if (prenotazione.getData().isEqual(data_corrente)) {
                prenotazioni.remove(prenotazione);
            }
        }
        if (l_iniziale != prenotazioni.toArray().length) return true;
        else return false;
    }

    public ArrayList<Prenotabile> calcolaMenuDelGiorno(LocalDate data_attuale) {
        ArrayList<Prenotabile> menu_del_giorno = new ArrayList<>();
        for (Prenotabile prenotabile : menu) {
            ArrayList<LocalDate> disponibilita = prenotabile.getDisponibilità();
            int inizio = 0;
            for (int i = 0; i < disponibilita.toArray().length / 2; i++) {
                if (data_attuale.isAfter(disponibilita.get(inizio)) && data_attuale.isBefore(disponibilita.get(inizio + 1)) && !menu_del_giorno.contains(prenotabile)) {
                    menu_del_giorno.add(prenotabile);
                }
                inizio += 2;
            }
        }
        return menu_del_giorno;
    }

    /**
     *
     * @param data_attuale
     * @param stringa_data_prenotazione
     * @return un intero che identifica il tipo di messaggio di errore
     * 1 se il "Formato data non valido."
     * 2 se "La data inserita deve essere sucessiva alla data attuale (" + data_attuale + ")"
     * 0 se è ok
     */
    public int controlloDataPrenotazione(LocalDate data_attuale, String stringa_data_prenotazione, int posti_ristorante) {
        LocalDate data_prenotazione = null;
        try {
            data_prenotazione = LocalDate.parse(stringa_data_prenotazione);
        } catch (DateTimeParseException e) {
            return 1;
        }
        if (data_prenotazione.isBefore(data_attuale) || data_prenotazione.isEqual(data_attuale))
            return 2;
        else if (posti_ristorante - calcolaPostiOccupati(data_prenotazione) == 0){
            return 3;
        }
        return 0;
    }

    public int calcolaPostiOccupati(LocalDate data_prenotazione){
        int posti_occupati = 0;
        for (Prenotazione prenotazione : prenotazioni){
            if(prenotazione.getData().isEqual(data_prenotazione)){
                posti_occupati += prenotazione.getN_coperti();
            }
        }
        return posti_occupati;
    }

    public int stimaPostiRimanenti(LocalDate data_prenotazione, int lavoro_persona, int n_posti){

        float lavoro_rimanente = n_posti*lavoro_persona - calcolaLavoro(filtraPrenotazioniPerData(this.prenotazioni, data_prenotazione));

        //è corretto?
        return Math.floorDiv((int) lavoro_rimanente, lavoro_persona);
    }

    public boolean validaCaricoLavoro(LocalDate data_prenotazione, int lavoro_persona, int n_posti, Prenotazione possibile_prenotazione){

        ArrayList<Prenotazione> possibili_prenotazioni = filtraPrenotazioniPerData(this.prenotazioni, data_prenotazione);
        possibili_prenotazioni.add(possibile_prenotazione);

       //ora calcolo il lavoro totale della somma delle prenotazioni(questo array che ho creato: possibili_prenotazioni)
        if(calcolaLavoro(possibili_prenotazioni) > lavoro_persona*n_posti + (20/100)*lavoro_persona*n_posti){
            return true;
        }else {
            return false;
        }
    }

    //devo creare una prenotazione complessiva, che corrisponderà ad una data precisa, poi invoherò il metodo calolaLavoroPrenotazione()
    public float calcolaLavoro(ArrayList<Prenotazione> prenotazioni_in_corso){
        HashMap<Prenotabile, Integer> scelte_complessive = new HashMap<>();

        for(Prenotazione prenotazione : prenotazioni_in_corso){

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
        return new Prenotazione(scelte_complessive).getLavoro_prenotazione();
    }


    public ArrayList<Prenotazione> filtraPrenotazioniPerData(ArrayList<Prenotazione> prenotazioni_da_filtrare, LocalDate data){
        ArrayList<Prenotazione> prenotazioni_filtrate = new ArrayList<>();
        for (Prenotazione prenotazione : prenotazioni_da_filtrare){
            if(prenotazione.getData().equals(data))
                prenotazioni_filtrate.add(prenotazione);
        }
        return prenotazioni_filtrate;
    }
}
