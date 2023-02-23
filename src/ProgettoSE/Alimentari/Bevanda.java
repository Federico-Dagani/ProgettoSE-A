package ProgettoSE.Alimentari;

public class Bevanda extends Alimento {

    private final float cons_procapite;
    //costruttore

    public Bevanda(String nome, float qta, String misura, float cons_procapite) {
        super(nome, qta, misura);
        this.cons_procapite = cons_procapite;
    }

    public Bevanda(float cons_procapite) {
        super();
        this.cons_procapite = cons_procapite;
    }

    //getters
    public float getCons_procapite() {
        return cons_procapite;
    }

}
