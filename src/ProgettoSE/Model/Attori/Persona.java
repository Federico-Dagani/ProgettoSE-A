package ProgettoSE.Model.Attori;

abstract public class Persona {
    //ATTRIBUTI
    private String nome;
    //METODI
    /**
     * <h2>Costruttore della classe Persona</h2>
     * @param nome nome della persona
     */
    public Persona(String nome) {
        this.nome = nome;
    }
    /**
     * <h2>Metodo che ritorna il nome della persona</h2>
     * @return String nome della persona
     */
    public String getNome() {
        return nome;
    }
    /**
     * <h2>Metodo che setta il nome della persona</h2>
     * @param nome nome della persona
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
