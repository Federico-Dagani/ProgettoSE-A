package ProgettoSE.Alimentari;

public class Bevanda extends Alimento {
    //ATTRIBUTI
    private final float cons_procapite;
    //METODI

    /**
     * <h2>Costruttore della classe Bevanda</h2>
     * @param nome nome della bevanda
     * @param qta quantità di bevanda
     * @param misura misura della quantità
     * @param cons_procapite consumo pro capite della bevanda
     */
    public Bevanda(String nome, float qta, String misura, float cons_procapite) {
        super(nome, qta, misura);
        this.cons_procapite = cons_procapite;
    }

    //getters
    public float getCons_procapite() {
        return cons_procapite;
    }

}
