package ProgettoSE;

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Attori.Cliente;
import ProgettoSE.Menu.MenuTematico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class Prenotazione {
    private Cliente nominativo;
    private int n_coperti;
    private LocalDate data;
    private HashMap<Prenotabile, Integer> scelte;
    //N.B. bisogna fare un controllo che la somma di tutti gli Integer sia >= n_coperti
    //ci starebbe usare Map.Entry<> che crea solo una coppia e non un lista di coppie
    private HashMap<Alimento,Float> cons_bevande;  //calcolo del cons_procapite delle bevande * n_coperti
    private HashMap<Alimento,Float> cons_extra; //calcolo del cons_procapite degli extra * n_coperti

    //METODI

    //costruttore
    public Prenotazione(Cliente nominativo, int n_coperti, LocalDate data, HashMap<Prenotabile, Integer> scelte, HashMap<Alimento, Float> cons_bevande, HashMap<Alimento, Float> cons_extra) {
        this.nominativo = nominativo;
        this.n_coperti = n_coperti;
        this.data = data;
        this.scelte = scelte;
        this.cons_bevande = cons_bevande;
        this.cons_extra = cons_extra;
    }

    //metodo cotruttore fittizio perchè serve in AddettoPrenotazioni, nel metodo calcolaLavoro()
    public Prenotazione(HashMap<Prenotabile, Integer> scelte){
        this.nominativo = null;
        this.n_coperti = 0;
        this.data = null;
        this.scelte = scelte;
        this.cons_bevande = null;
        this.cons_extra = null;

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

    public HashMap<Alimento, Float> getCons_bevande() {
        return cons_bevande;
    }

    public void setCons_bevande(HashMap<Alimento, Float> cons_bevande) {
        this.cons_bevande = cons_bevande;
    }

    public HashMap<Alimento, Float> getCons_extra() {
        return cons_extra;
    }

    public void setCons_extra(HashMap<Alimento, Float> cons_extra) {
        this.cons_extra = cons_extra;
    }

    public float getLavoro_prenotazione(){
        float lavoro_tot = 0;
        //devo avere i prenotabili per poter risalire ai loro lavori
        Set<Prenotabile> prenotabili_presenti = scelte.keySet();
        //scorro i prenotabili e sommo i loro lavori moltiplicati per il  numero di presone che hanno scelto quel prenotabile
        for (Prenotabile prenotabile : prenotabili_presenti){
            if(prenotabile instanceof MenuTematico)
                //se è un menù userò il lavoro del menù
                lavoro_tot += ((MenuTematico) prenotabile).getLavoro_menu() * scelte.get(prenotabile);
            else if(prenotabile instanceof Piatto){
                //se è un piatto devo risalire alla ricetta ed usare il lavoro della ricetta in relazione al numero delle porzioni che genera
                /*calcolo il resto della divisione:

                    numero di porzioni del piatto
                    ------------------------------
                    numero di porzioni che la ricetta crea

                    se il resto è diverso da zero allora prendo il valore della divisione (intero) aumentato di uno e moltiplico per il lavoro della ricetta
                 */
                float resto_divisione = scelte.get(prenotabile)%((Piatto) prenotabile).getRicetta().getN_porzioni();
                if(resto_divisione == 0){
                    //se il resto è zero allora è easy
                    lavoro_tot += scelte.get(prenotabile) * ((Piatto) prenotabile).getLavoro_piatto();
                }else{
                    //se il numero dei piatti è diverso non è un multiplo del numero delle porzioni della corrispettiva ricetta allora arrondo per eccesso la divisione e poi faccio la moltiplicazione
                    int valore_divisione = (int) Math.ceil(scelte.get(prenotabile)/((Piatto) prenotabile).getRicetta().getN_porzioni());
                    lavoro_tot += valore_divisione * ((Piatto) prenotabile).getRicetta().getN_porzioni() * ((Piatto) prenotabile).getLavoro_piatto();
                }
            }
        }
        return lavoro_tot;
    }

}
