package ProgettoSE;

import java.util.ArrayList;

public class Ristorante {
    private int n_posti;
    private float lavoro_persona;
    private ArrayList<Prenotazione> prenotazioni;

    public Ristorante(int n_posti, float lavoro_persona, ArrayList<Prenotazione> prenotazioni) {
        this.n_posti = n_posti;
        this.lavoro_persona = lavoro_persona;
        this.prenotazioni = prenotazioni;
    }

    public int getN_posti() {
        return n_posti;
    }

    public void setN_posti(int n_posti) {
        this.n_posti = n_posti;
    }

    public float getLavoro_persona() {
        return lavoro_persona;
    }

    public void setLavoro_persona(float lavoro_persona) {
        this.lavoro_persona = lavoro_persona;
    }

    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public void addPrenotazione(Prenotazione prenotazione) {
        this.prenotazioni.add(prenotazione);
    }



}
