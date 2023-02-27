package ProgettoSE;

import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Attori.Cliente;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Prenotazione {
    private Cliente nominativo;
    private int n_coperti;
    private LocalDate data;
    private HashMap<Prenotabile, Integer> scelte;
    //N.B. bisogna fare un controllo che la somma di tutti gli Integer sia >= n_coperti
    private HashMap<Bevanda,Float> cons_bevande;  //calcolo del cons_procapite delle bevande * n_coperti
    private HashMap<Extra,Float> cons_extra; //calcolo del cons_procapite degli extra * n_coperti

    //METODI

    //costruttore
    public Prenotazione(Cliente nominativo, int n_coperti, LocalDate data, HashMap<Prenotabile, Integer> scelte, HashMap<Bevanda, Float> cons_bevande, HashMap<Extra, Float> cons_extra) {
        this.nominativo = nominativo;
        this.n_coperti = n_coperti;
        this.data = data;
        this.scelte = scelte;
        this.cons_bevande = cons_bevande;
        this.cons_extra = cons_extra;
    }

    //get e set
    public Cliente getNominativo() {
        return nominativo;
    }

    public void setNominativo(Cliente nominativo) {
        this.nominativo = nominativo;
    }

    public int getN_coperti() {
        return n_coperti;
    }

    public void setN_coperti(int n_coperti) {
        this.n_coperti = n_coperti;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public HashMap<Prenotabile, Integer> getScelte() {
        return scelte;
    }

    public void setScelte(HashMap<Prenotabile, Integer> scelte) {
        this.scelte = scelte;
    }

    public HashMap<Bevanda, Float> getCons_bevande() {
        return cons_bevande;
    }

    public void setCons_bevande(HashMap<Bevanda, Float> cons_bevande) {
        this.cons_bevande = cons_bevande;
    }

    public HashMap<Extra, Float> getCons_extra() {
        return cons_extra;
    }

    public void setCons_extra(HashMap<Extra, Float> cons_extra) {
        this.cons_extra = cons_extra;
    }


}
