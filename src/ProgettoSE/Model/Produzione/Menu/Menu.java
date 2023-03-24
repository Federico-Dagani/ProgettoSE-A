package ProgettoSE.Model.Produzione.Menu;

import ProgettoSE.Model.Produzione.Piatto;

import java.util.ArrayList;

abstract public class Menu {
    //ATTRIBUTI
    private ArrayList<Piatto> piatti_menu;
    //METODI
    /**
     * <h2>Costruttore di Menu</h2>
     * @param piatti_menu lista dei piatti del menu
     */
    public Menu(ArrayList<Piatto> piatti_menu) {
        this.piatti_menu = piatti_menu;
    }

    //GET E SET
    public ArrayList<Piatto> getPiatti_menu() {
        return piatti_menu;
    }
    public void setPiatti_menu(ArrayList<Piatto> piatti_menu) {
        this.piatti_menu = piatti_menu;
    }

    public void aggiungiPiatto(Piatto piatto){
        piatti_menu.add(piatto);
    }

    /**
     * <h2>Metodo per ottenere un piatto dal nome</h2>
     * @param nome_piatto nome del piatto da cercare
     * @return il piatto cercato
     */
    public Piatto getPiatto(String nome_piatto){
        for (Piatto piatto : piatti_menu){
            if(piatto.getNome().equals(nome_piatto))
                return piatto;
        }
        return null;
    }

}
