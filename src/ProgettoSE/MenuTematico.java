package ProgettoSE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class MenuTematico extends Menu implements Prenotabile {
    private float lavoro_menu;
    private ArrayList<LocalDate> disponibilità;

    public MenuTematico(ArrayList<Piatto> piatti_menu, float lavoro_menu, ArrayList<LocalDate> disponibilità) {
        super(piatti_menu);
        this.lavoro_menu = lavoro_menu;
        this.disponibilità = disponibilità;
    }
}
