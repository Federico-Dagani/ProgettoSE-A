package ProgettoSE.Attori;

import ProgettoSE.*;
import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;

import java.util.ArrayList;
import java.util.HashMap;

public class Magazziniere extends Persona {
    //ATTRIBUTI
    public Magazziniere(String nome) {
        super(nome);
    }
    private Magazzino magazzino;
    private ArrayList<Alimento> lista_spesa;
    //METODI
    //costruttore
    public Magazziniere(String nome, Magazzino magazzino, ArrayList<Alimento> lista_spesa) {
        super(nome);
        this.magazzino = magazzino;
        this.lista_spesa = lista_spesa;
    }
    //getters
    public Magazzino getMagazzino() {
        return magazzino;
    }
    public ArrayList<Alimento> getLista_spesa() {
        return lista_spesa;
    }
    //setters
    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }
    public void setLista_spesa(ArrayList<Alimento> lista_spesa) {
        this.lista_spesa = lista_spesa;
    }

    public HashMap<Alimento, Float> calcolaConsumoAlimento(int n_persone, String tipologia){

        HashMap<Alimento, Float> consumi = new HashMap<>();
        for (Alimento alimento : magazzino.getBevande()){
            if(tipologia.equals(Costanti.BEVANDA) && alimento instanceof Bevanda)
                consumi.put(alimento, n_persone * ((Bevanda) alimento).getCons_procapite());
            else if(tipologia.equals(Costanti.EXTRA) && alimento instanceof Extra)
                consumi.put(alimento, n_persone * ((Extra) alimento).getCons_procapite());
        }
        return consumi;
    }


    public void calcolaListaSpesa(Prenotazione prenotazione_totale){

        for(Prenotabile prenotabile : prenotazione_totale.getScelte().keySet()){
            if(prenotabile instanceof Piatto);
        }
    }

}
