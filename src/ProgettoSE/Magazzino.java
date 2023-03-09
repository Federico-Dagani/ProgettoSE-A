package ProgettoSE;

import ProgettoSE.Alimentari.*;

import java.util.ArrayList;

public class Magazzino {
    //ATTRIBUTI
    private ArrayList<Alimento> bevande = new ArrayList<>();
    private ArrayList<Alimento> extras = new ArrayList<>();
    private ArrayList<Alimento> ingredienti = new ArrayList<>();

    //METODI
    /**
     * <h2>Costruttore che inizializza il magazzino con i dati letti dal file XML
     * @param bevande lista di bevande
     * @param extras
     * @param ingredienti
     * @precondition bevande != null && extras != null && ingredienti != null
     * @throws IllegalArgumentException se uno dei parametri non è valido
     * @return void
     */
    public Magazzino(ArrayList<Alimento> bevande, ArrayList<Alimento> extras, ArrayList<Alimento> ingredienti) {
        this.bevande = bevande;
        this.extras = extras;
        this.ingredienti = ingredienti;
    }

    //getters & setters
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

    /**
     * <h2>Metodo che restituisce un alimento dato il suo nome</h2>
     * <b>Precondizione:</b> il nome dell'alimento non è null
     * @param nome_alimento nome dell'alimento da cercare
     * @throws IllegalArgumentException se il nome dell'alimento non è valido
     * @return Alimento
     */
    public Alimento getAlimento(String nome_alimento) {
        //precondizione: il nome dell'alimento non è null
        if (nome_alimento == null) throw new IllegalArgumentException("Nome alimento non valido");

        for (Alimento alimento : this.ingredienti)
            if (alimento.getNome().equals(nome_alimento)) return alimento;
        for (Alimento alimento : this.bevande)
            if (alimento.getNome().equals(nome_alimento)) return alimento;
        for (Alimento alimento : this.extras)
            if (alimento.getNome().equals(nome_alimento)) return alimento;
        return null;
    }

    /**
     * <h2>Metodo che restituisce un alimento dato il suo nome</h2>
     * <b>Precondizione:</b> l'alimento non è null<br>
     * <b>Postcondizione:</b> l'alimento è stato aggiunto al magazzino
     * @param alimento alimento da aggiungere al magazzino
     * @throws IllegalArgumentException se l'alimento è null
     * @return void
     */
    public void setAlimento(Alimento alimento) {
        //precondizione: l'alimento non è null
        if (alimento == null) throw new IllegalArgumentException("Alimento non valido");
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

        //postcondizione: l'alimento è stato aggiunto al magazzino
        assert getAlimento(nome_alimento).getQta() == qta_alimento;
    }

    /**
     * <h2> Metodo che preleva un certo quantitativo di un alimento dal magazzino</h2>
     * <b>Precondizione:</b> la quantità di alimento da prelevare è maggiore o uguale di 0<br>
     * <b>Postcondizione:</b> la quantità di alimento è stata diminuita
     * @param nome nome dell'alimento da prelevare
     * @param qta quantità di alimento da prelevare
     * @throws IllegalArgumentException se la quantità di alimento da prelevare è minore di 0
     * @return void
     */
    public void prelevaAlimento(String nome, float qta) {
        //precondizione: la quantità di alimento da prelevare è maggiore o uguale di 0
        if (qta < 0) throw new IllegalArgumentException("Quantità non valida");
        float qta_precedente = getAlimento(nome).getQta();
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
        //postcondizione: la quantità di alimento è stata diminuita
        assert getAlimento(nome).getQta() < qta_precedente;
    }

}
