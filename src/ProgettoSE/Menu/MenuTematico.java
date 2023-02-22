package ProgettoSE.Menu;

import ProgettoSE.Menu.Menu;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuTematico extends Menu implements Prenotabile {

    private String nome;
    private float lavoro_menu;
    private ArrayList<LocalDate> disponibilità;

    public MenuTematico(ArrayList<Piatto> piatti_menu, float lavoro_menu, ArrayList<LocalDate> disponibilità) {
        super(piatti_menu);
        this.lavoro_menu = lavoro_menu;
        this.disponibilità = disponibilità;
    }

    @Override
    public String getNome() {
        return nome;
    }
}
