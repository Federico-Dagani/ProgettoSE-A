package ProgettoSE;

import ProgettoSE.Alimentari.*;
import java.util.ArrayList;

public class Magazzino {
    //ATTRIBUTI
    private ArrayList<Bevanda> bevande = new ArrayList<>();
    private ArrayList<Extra> extras = new ArrayList<>() ;
    private ArrayList<Ingrediente> ingredienti = new ArrayList<>();

    //METODI
    //costruttore
    public  Magazzino(){ }

    public Magazzino(ArrayList<Bevanda> bevande, ArrayList<Extra> extras, ArrayList<Ingrediente> ingredienti) {
        this.bevande = bevande;
        this.extras = extras;
        this.ingredienti = ingredienti;
    }

    //get e set

    public ArrayList<Bevanda> getBevande() {
        return bevande;
    }

    public void setBevande(ArrayList<Bevanda> bevande) {
        this.bevande = bevande;
    }

    public ArrayList<Extra> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<Extra> extras) {
        this.extras = extras;
    }

    public ArrayList<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }
}
