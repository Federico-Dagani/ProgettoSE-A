package ProgettoSE.Attori;

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Magazzino;

import java.util.ArrayList;

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


}
