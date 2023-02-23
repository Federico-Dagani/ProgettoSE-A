package ProgettoSE.Menu;

import ProgettoSE.Piatto;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Piatto> piatti_menu;

    public Menu(ArrayList<Piatto> piatti_menu) {
        this.piatti_menu = piatti_menu;
    }

    //getter and setter
    public ArrayList<Piatto> getPiatti_menu() {
        return piatti_menu;
    }
    public void setPiatti_menu(ArrayList<Piatto> piatti_menu) {
        this.piatti_menu = piatti_menu;
    }

    public void aggiungiPiatto(Piatto piatto){
        piatti_menu.add(piatto);
    }

    public Piatto getPiatto(String nome_piatto){
        for (Piatto piatto : piatti_menu){
            if(piatto.getNome().equals(nome_piatto))
                return piatto;
        }
        return null;
    }
}
