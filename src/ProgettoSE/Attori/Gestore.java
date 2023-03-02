package ProgettoSE.Attori;

import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Costanti;
import ProgettoSE.Magazzino;
import ProgettoSE.Prenotazione;
import ProgettoSE.Ristorante;
import ProgettoSE.ServiziFileXML.LetturaFileXML;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public boolean comunica(LocalDate data_attuale) {
      ristorante.getMagazziniere().calcolaListaSpesa(ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale));
      return ristorante.getAddettoPrenotazione().eliminaPrenotazioni(data_attuale);
    }

}
