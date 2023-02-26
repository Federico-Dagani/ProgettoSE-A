package ProgettoSE.Attori;

import ProgettoSE.Menu.*;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;
import ProgettoSE.Prenotazione;

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

    public void aggiungiMenu_carta(Menu menu_carta){
        for(Piatto piatto : menu_carta.getPiatti_menu()){
            menu.add(piatto);
        }
    }

    public void aggiungiMenu_tematico(MenuTematico menu_tematico){
        menu.add(menu_tematico);
    }

    public void aggiungiPrenotazione(Prenotazione prenotazione){
        prenotazioni.add(prenotazione);
    }
}
