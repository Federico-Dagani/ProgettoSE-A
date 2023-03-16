package ProgettoSE;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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

    /**
     * <h2>Metodo che permette di avanzare di un mese la data corrente</h2><br>
     * @param data1 data che dovrà precedere la data2
     * @param data2 data che dovrà seguire la data1
     * @return void
     */
    public static boolean data1AnticipaData2(LocalDate data1, LocalDate data2) {

        if(data1.getMonthValue() < data2.getMonthValue())
            return true;
        else
            return data1.getMonthValue() == data2.getMonthValue() && data1.getDayOfMonth() <= data2.getDayOfMonth();
    }

    /**
     * <h2>Metodo che permette di parsare una stringa in una data</h2>
     * <b>Precondizione:</b> data_da_parsare non è null
     * @param data_da_parsare stringa da parsare
     * @return LocalDate che rappresenta la data parsata, in caso sia nel formato sbagliato ritorna null
     */
    public static LocalDate parsaData(String data_da_parsare){
        //precondizione: data_da_parsare non è nullù
        if(data_da_parsare == null) throw new IllegalArgumentException("data_da_parsare non può essere null");
        LocalDate data_parsata;
        try{
            data_parsata = LocalDate.parse(data_da_parsare);
        }catch(DateTimeParseException e){
            data_parsata = null;
        }
        return data_parsata;
    }
}
