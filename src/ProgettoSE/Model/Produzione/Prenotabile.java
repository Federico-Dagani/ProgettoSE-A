package ProgettoSE.Model.Produzione;

import java.time.LocalDate;
import java.util.ArrayList;

public interface Prenotabile {
    /**
     * <h2>Metodo che restituisce il nome del prenotabile</h2>
     * @return nome del prenotabile
     */
    String getNome();
    /**
     * <h2>Metodo che restituisce le date di disponibilità del prenotabile</h2>
     * @return date di disponibilità del prenotabile
     */
    ArrayList<LocalDate> getDisponibilità();
}
