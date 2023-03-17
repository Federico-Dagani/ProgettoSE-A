package ProgettoSE.Alimentari;

abstract public class Alimento {
    //ATTRIBUTI
    private String nome;
    private float qta;
    private String misura;
    //METODI

    /**
     * <h2>Costruttore della classe Alimento</h2>
     * @param nome nome dell'alimento
     * @param qta quantità dell'alimento
     * @param misura misura della quantità
     */
    public Alimento(String nome, float qta, String misura) {
        this.nome = nome;
        this.qta = qta;
        this.misura = misura;
    }
    /**
     * <h2>Costruttore di default della classe Alimento</h2>
     */
    public Alimento() { }

    //getters
    public String getNome() {
        return nome;
    }
    public float getQta() {
        return qta;
    }
    public String getMisura() {
        return misura;
    }
    //setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setQta(float qta) {
        this.qta = qta;
    }
    public void setMisura(String misura) {
        this.misura = misura;
    }

}
