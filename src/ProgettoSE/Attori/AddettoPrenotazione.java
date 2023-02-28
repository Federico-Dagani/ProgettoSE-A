package ProgettoSE.Attori;

import ProgettoSE.Menu.*;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;
import ProgettoSE.Prenotazione;

import java.time.LocalDate;
import java.util.ArrayList;

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
}
