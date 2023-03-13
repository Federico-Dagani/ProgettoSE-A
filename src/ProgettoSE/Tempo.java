package ProgettoSE;

import java.time.LocalDate;

public class Tempo {

    //ATTRIBUTI
    private static LocalDate data_corrente;

    //METODI
    /**
     * <h2>Metodo costruttore della classe Tempo</h2>
     * @param data_corrente data corrente
     */
    public Tempo(LocalDate data_corrente) {
        this.data_corrente = data_corrente;
    }

    /**
     * <h2>Metodo che permette di ricevere la data corrente</h2>
     * @return LocalDate data corrente
     */
    public LocalDate getData_corrente() {
        return data_corrente;
    }

    /**
     * <h2>Metodo che permette di settare la data corrente</h2>
     * @param data_corrente  data corrente
     */
    public void setData_corrente(LocalDate data_corrente) {
        this.data_corrente = data_corrente;
    }

    /**
     * <h2>Metodo che permette di avanzare di un giorno la data corrente</h2>
     * @return void
     */
    public void scorriGiorno(){
        data_corrente = data_corrente.plusDays(1);
    }

    public static boolean data1AnticipaData2(LocalDate data1, LocalDate data2) {
        if(data1.getMonthValue() < data2.getMonthValue())
            return true;
        else if(data1.getMonthValue() == data2.getMonthValue() && data1.getDayOfMonth() <= data2.getDayOfMonth())
            return true;
        else
            return false;
    }
}
