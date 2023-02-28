package ProgettoSE.Alimentari;

import ProgettoSE.Alimentari.Alimento;

public class Extra extends Alimento {
    private final float cons_procapite;

    public Extra(String nome, float qta, String misura, float cons_procapite) {
        super(nome, qta, misura);
        this.cons_procapite = cons_procapite;
    }

    public float getCons_procapite() {
        return cons_procapite;
    }
}
