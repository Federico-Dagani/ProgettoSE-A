package ProgettoSE;

import ProgettoSE.Alimentari.*;

import java.util.ArrayList;

public class Magazzino {
    //ATTRIBUTI
    private ArrayList<Alimento> bevande = new ArrayList<>();
    private ArrayList<Alimento> extras = new ArrayList<>();
    private ArrayList<Alimento> ingredienti = new ArrayList<>();

    //METODI
    //costruttore vuoto
    public Magazzino() {
    }

    /**
     * <h2>Costruttore che inizializza il magazzino con i dati letti dal file XML
     * @param bevande lista di bevande
     * @param extras
     * @param ingredienti
     * @throws IllegalArgumentException se uno dei parametri non è valido
     * @return void
     */
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

    public Alimento getAlimento(String nome_alimento) {
        for (Alimento alimento : this.ingredienti)
            if (alimento.getNome().equals(nome_alimento)) return alimento;
        for (Alimento alimento : this.bevande)
            if (alimento.getNome().equals(nome_alimento)) return alimento;
        for (Alimento alimento : this.extras)
            if (alimento.getNome().equals(nome_alimento)) return alimento;
        return null;
    }

    public void setAlimento(Alimento alimento) {
        String nome_alimento = alimento.getNome();
        float qta_alimento = alimento.getQta();

        if (alimento instanceof Ingrediente)
            for (Alimento ingrediente : ingredienti)
                if (nome_alimento.equals(ingrediente.getNome())) ingrediente.setQta(qta_alimento);
        if (alimento instanceof Bevanda)
            for (Alimento bevanda : bevande)
                if (nome_alimento.equals(bevanda.getNome())) bevanda.setQta(qta_alimento);
        if (alimento instanceof Extra)
            for (Alimento extra : extras)
                if (nome_alimento.equals(extra.getNome())) extra.setQta(qta_alimento);
    }

    public void prelevaAlimento(String nome, float qta) {
        ingredienti.forEach(ingrediente -> {
            if (ingrediente.getNome().equals(nome))
                ingrediente.setQta(ingrediente.getQta() - qta);
        });
        bevande.forEach(bevanda -> {
            if (bevanda.getNome().equals(nome))
                bevanda.setQta(bevanda.getQta() - qta);
        });
        extras.forEach(extra -> {
            if(extra.getNome().equals(nome))
                extra.setQta(extra.getQta() - qta);
        });
    }

}
