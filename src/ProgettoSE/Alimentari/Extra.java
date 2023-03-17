package ProgettoSE.Alimentari;

public class Extra extends Alimento {
    //ATTRIBUTI
    private final float cons_procapite;

    /**
     * <h2>Costruttore della classe Extra</h2>
     * @param nome nome dell'extra
     * @param qta quantità dell'extra
     * @param misura misura dell'extra
     * @param cons_procapite consumo pro capite dell'extra
     */
    public Extra(String nome, float qta, String misura, float cons_procapite) {
        super(nome, qta, misura);
        this.cons_procapite = cons_procapite;
    }

    //getter
    public float getCons_procapite() {
        return cons_procapite;
    }
}
