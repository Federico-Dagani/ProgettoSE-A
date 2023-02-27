package ProgettoSE;

import java.time.LocalDate;

public class Tempo {

    private LocalDate data_corrente;

    //METODI

    //costruttore
    public Tempo(LocalDate data_corrente) {
        this.data_corrente = data_corrente;
    }

    //get e set
    public LocalDate getData_corrente() {
        return data_corrente;
    }

    public void setData_corrente(LocalDate data_corrente) {
        this.data_corrente = data_corrente;
    }
}
