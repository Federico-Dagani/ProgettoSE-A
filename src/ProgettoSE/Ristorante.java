package ProgettoSE;

import ProgettoSE.Attori.AddettoPrenotazione;
import ProgettoSE.Attori.Magazziniere;

import java.util.ArrayList;

public class Ristorante {
    private int n_posti;
    private int lavoro_persona;
    private AddettoPrenotazione addettoPrenotazione;
    private Magazziniere magazziniere;

    public Ristorante() { }

    public Ristorante(int n_posti, int lavoro_persona, AddettoPrenotazione addettoPrenotazione, Magazziniere magazziniere) {
        this.n_posti = n_posti;
        this.lavoro_persona = lavoro_persona;
        this.addettoPrenotazione = addettoPrenotazione;
        this.magazziniere = magazziniere;
    }

    public int getN_posti() {
        return n_posti;
    }

    public void setN_posti(int n_posti) {
        this.n_posti = n_posti;
    }

    public int getLavoro_persona() {
        return lavoro_persona;
    }

    public void setLavoro_persona(int lavoro_persona) {
        this.lavoro_persona = lavoro_persona;
    }

    public AddettoPrenotazione getAddettoPrenotazione() {
        return addettoPrenotazione;
    }

    public void setAddettoPrenotazione(AddettoPrenotazione addettoPrenotazione) {
        this.addettoPrenotazione = addettoPrenotazione;
    }

    public Magazziniere getMagazziniere() {
        return magazziniere;
    }

    public void setMagazziniere(Magazziniere magazziniere) {
        this.magazziniere = magazziniere;
    }
}
