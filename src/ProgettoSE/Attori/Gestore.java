package ProgettoSE.Attori;

import ProgettoSE.Costanti;
import ProgettoSE.Prenotazione;
import ProgettoSE.Ristorante;
import ProgettoSE.ServiziFileXML.LetturaFileXML;

import java.time.LocalDate;

public class Gestore extends Persona {
    //ATTRIBUTI
    private Ristorante ristorante;

    //METODI
    public Gestore(String nome, Ristorante ristorante) {
        super(nome);
        this.ristorante = ristorante;
    }
    public Ristorante getRistorante() {
        return ristorante;
    }
    public void setRistorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }
    /**
     * <h2>Metodo che inizializza il ristorante leggendo i dati dal file XML
     * @throws
     * @return void
     *
     */
    public void inizializzaRistorante() {
        LetturaFileXML letturaFileXML = new LetturaFileXML();
        ristorante = letturaFileXML.leggiRistorante(Costanti.FILE_RISTORANTE);
    }

    public void modificaRistorante(){
        //TODO
    }

    /**
     * <h2>Metodo che comunica al magazziniere la lista della spesa e al cuoco la lista dei piatti da cucinare
     * @param data_attuale
     * @throws IllegalArgumentException se la data non è valida
     * @return String messaggio
     */
    public String comunica(LocalDate data_attuale) {
        //precondizione data_attuale non null
        if(data_attuale == null) throw new IllegalArgumentException("Data non valida");
        Prenotazione prenotazione_del_giorno = ristorante.getAddettoPrenotazione().unisciPrenotazioni(ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale));
        ristorante.getMagazziniere().creaListaSpesa(prenotazione_del_giorno);
        String messaggio = ristorante.getMagazziniere().aggiungiSpesaInMagazzino();
        //prob
        ristorante.getMagazziniere().portaInCucina(prenotazione_del_giorno);
        ristorante.getAddettoPrenotazione().rimuoviPrenotazioniVecchie(data_attuale);
        //postcondizione messaggio non null
        assert messaggio != null;
        return messaggio;
    }

}
