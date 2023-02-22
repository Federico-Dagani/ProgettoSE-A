package ProgettoSE.Alimentari;

abstract public class Alimento {
    private String nome;
    private float qta;

    public Alimento(String nome, float qta, String misura) {
        this.nome = nome;
        this.qta = qta;
        this.misura = misura;
    }

    private String misura;
}
