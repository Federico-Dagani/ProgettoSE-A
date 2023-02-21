package ProgettoSE;

public class Bevanda extends Alimento{
    private final float cons_procapite;

    public Bevanda(String nome, float qta, String misura, float cons_procapite) {
        super(nome, qta, misura);
        this.cons_procapite = cons_procapite;
    }
}
