package ProgettoSE.Alimentari;

abstract public class Alimento {
    private String nome;
    private float qta;
    private String misura;

    public Alimento(String nome, float qta, String misura) {
        this.nome = nome;
        this.qta = qta;
        this.misura = misura;
    }

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
