package ProgettoSE;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Piatto> piatti_menu;
    public Menu(ArrayList<Piatto> piatti_menu) {
        this.piatti_menu = piatti_menu;
    }
    public ArrayList<Piatto> getPiatti_menu() {
        return piatti_menu;
    }
    public void setPiatti_menu(ArrayList<Piatto> piatti_menu) {
        this.piatti_menu = piatti_menu;
    }
}
