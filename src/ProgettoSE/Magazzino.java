package ProgettoSE;

import ProgettoSE.Alimentari.*;
import java.util.ArrayList;

public class Magazzino {
    //ATTRIBUTI
    private ArrayList<Alimento> bevande = new ArrayList<>();
    private ArrayList<Alimento> extras = new ArrayList<>() ;
    private ArrayList<Alimento> ingredienti = new ArrayList<>();

    //METODI
    //costruttore
    public  Magazzino(){ }

    public Magazzino(ArrayList<Alimento> bevande, ArrayList<Alimento> extras, ArrayList<Alimento> ingredienti) {
        this.bevande = bevande;
        this.extras = extras;
        this.ingredienti = ingredienti;
    }

    //get e set

    public ArrayList<Alimento> getBevande() {
        return bevande;
    }

    public void setBevande(ArrayList<Alimento> bevande) {
        this.bevande = bevande;
    }

    public ArrayList<Alimento> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<Alimento> extras) {
        this.extras = extras;
    }

    public ArrayList<Alimento> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Alimento> ingredienti) {
        this.ingredienti = ingredienti;
    }
}
