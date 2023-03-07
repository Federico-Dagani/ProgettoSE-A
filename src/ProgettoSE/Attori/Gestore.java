package ProgettoSE.Attori;

import ProgettoSE.Costanti;
import ProgettoSE.Prenotazione;
import ProgettoSE.Ristorante;
import ProgettoSE.ServiziFileXML.LetturaFileXML;

import java.time.LocalDate;

public class Gestore extends Persona {

    private Ristorante ristorante;

    public Gestore(String nome, Ristorante ristorante) {
        super(nome);
        this.ristorante = ristorante;
    }

    //get e set
    public Ristorante getRistorante() {
        return ristorante;
    }

    public void setRistorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    //METODI
    public void inizializzaRistorante() {
        LetturaFileXML letturaFileXML = new LetturaFileXML();
        ristorante = letturaFileXML.leggiRistorante(Costanti.FILE_RISTORANTE);
    }

    //
    public String comunica(LocalDate data_attuale) {
        Prenotazione prenotazione_del_giorno = ristorante.getAddettoPrenotazione().unisciPrenotazioni(ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale));
        ristorante.getMagazziniere().creaListaSpesa(prenotazione_del_giorno);
        String messaggio = ristorante.getMagazziniere().aggiungiSpesaInMagazzino();
        //prob
        ristorante.getMagazziniere().portaInCucina(prenotazione_del_giorno);
        ristorante.getAddettoPrenotazione().rimuoviPrenotazioniVecchie(data_attuale);
        return messaggio;
    }

}
