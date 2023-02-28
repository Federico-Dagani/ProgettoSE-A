package ProgettoSE;

import java.time.LocalDate;
import java.util.ArrayList;

public interface Prenotabile {
    String getNome();

    ArrayList<LocalDate> getDisponibilità();
}
