package ProgettoSE.Attori;

import ProgettoSE.Costanti;
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
    public String comunica(LocalDate data_precedente, LocalDate data_attuale) {
        ristorante.getMagazziniere().creaListaSpesa2(ristorante.getAddettoPrenotazione().unisciPrenotazioni(ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale)));
        ristorante.getAddettoPrenotazione().aggiornaPrenotazioni(data_precedente, data_attuale);
        return ristorante.getMagazziniere().aggiornaMagazzino();
    }

}
