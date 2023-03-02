package ProgettoSE;

import ProgettoSE.mylib.InputDati;

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

    /**
     * metodo che aumenta la data di 1 singolo giorno
     */
    public void scorriGiorno(){
        data_corrente = data_corrente.plusDays(1);
    }

}
