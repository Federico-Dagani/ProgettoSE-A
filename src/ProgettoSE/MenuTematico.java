package ProgettoSE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class MenuTematico extends Menu{
    private float lavoro_menu;
    private ArrayList<LocalDate> disponibilità;
    private final UUID id;

    public MenuTematico(ArrayList<Piatto> piatti_menu, float lavoro_menu, ArrayList<LocalDate> disponibilità, UUID id) {
        super(piatti_menu);
        this.lavoro_menu = lavoro_menu;
        this.disponibilità = disponibilità;
        this.id = id;
    }
}
