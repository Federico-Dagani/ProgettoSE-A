package ProgettoSE;


import ProgettoSE.Alimentari.Ingrediente;

import java.util.ArrayList;
import java.util.HashMap;

public class    Ricetta {
    private ArrayList<Ingrediente> ingredienti;
    private int n_porzioni;
    private float lavoro_porzione;

    public Ricetta(ArrayList<Ingrediente> ingredienti, int n_porzioni, float lavoro_porzione) {
        this.ingredienti = ingredienti;
        this.n_porzioni = n_porzioni;
        this.lavoro_porzione = lavoro_porzione;
    }

    public ArrayList<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public int getN_porzioni() {
        return n_porzioni;
    }

    public void setN_porzioni(int n_porzioni) {
        this.n_porzioni = n_porzioni;
    }

    public float getLavoro_porzione() {
        return lavoro_porzione;
    }

    public void setLavoro_porzione(float lavoro_porzione) {
        this.lavoro_porzione = lavoro_porzione;
    }
}
