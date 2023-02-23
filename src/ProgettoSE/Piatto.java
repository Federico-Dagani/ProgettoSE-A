package ProgettoSE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Piatto implements Prenotabile {
    private String nome;
    private ArrayList<LocalDate> disponibilità;
    private float lavoro_piatto; //coincide x def con lavoro_porzione
    private Ricetta ricetta;

    public Piatto(String nome, ArrayList<LocalDate> disponibilità, float lavoro_piatto, Ricetta ricetta) {
        this.nome = nome;
        this.disponibilità = disponibilità;
        this.lavoro_piatto = lavoro_piatto;
        this.ricetta = ricetta;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<LocalDate> getDisponibilità() {
        return disponibilità;
    }

    public void setDisponibilità(ArrayList<LocalDate> disponibilità) {
        this.disponibilità = disponibilità;
    }

    public float getLavoro_piatto() {
        return lavoro_piatto;
    }

    public void setLavoro_piatto(float lavoro_piatto) {
        this.lavoro_piatto = lavoro_piatto;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }
}
