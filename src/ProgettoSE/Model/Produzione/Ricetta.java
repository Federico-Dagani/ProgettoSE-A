package ProgettoSE.Model.Produzione;

import ProgettoSE.Model.Alimentari.Alimento;
import java.util.ArrayList;

public class Ricetta {
    //ATTRIBUTI
    private ArrayList<Alimento> ingredienti;
    private int n_porzioni;
    private float lavoro_porzione;

    //METODI

    /**
     * <h2>Metodo costruttore della classe Ricetta</h2>
     * @param ingredienti lista degli ingredienti
     * @param n_porzioni numero di porzioni
     * @param lavoro_porzione lavoro per porzione
     */
    public Ricetta(ArrayList<Alimento> ingredienti, int n_porzioni, float lavoro_porzione) {
        this.ingredienti = ingredienti;
        this.n_porzioni = n_porzioni;
        this.lavoro_porzione = lavoro_porzione;
    }

    /**
     * <h2>Metodo costruttore di default della classe Ricetta</h2>
     */
    public Ricetta(){
        this.ingredienti = new ArrayList<>();
        this.n_porzioni = 0;
        this.lavoro_porzione = 0;
    }

    //getters e setters
    public ArrayList<Alimento> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Alimento> ingredienti) {
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
