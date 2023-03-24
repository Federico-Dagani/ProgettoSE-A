package ProgettoSE.Model.Alimentari;

public class Ingrediente extends Alimento {
    /**
     * <h2>Costruttore della classe Ingrediente</h2>
     * @param nome Nome dell'ingrediente
     * @param qta Quantità dell'ingrediente
     * @param misura Misura dell'ingrediente
     */
    public Ingrediente(String nome, float qta, String misura) {
        super(nome, qta, misura);
    }

    /**
     * <h2>Costruttore di default della classe Ingrediente </h2>
     */
    public Ingrediente(){
        super("", 0, "");
    }
}
