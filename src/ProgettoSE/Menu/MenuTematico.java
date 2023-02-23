package ProgettoSE.Menu;

import ProgettoSE.Menu.Menu;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuTematico extends Menu implements Prenotabile {

    private String nome;
    private float lavoro_menu;
    private ArrayList<LocalDate> disponibilità;

    public MenuTematico(ArrayList<Piatto> piatti_menu, float lavoro_menu, ArrayList<LocalDate> disponibilità) {
        super(piatti_menu);
        this.lavoro_menu = lavoro_menu;
        this.disponibilità = disponibilità;
    }



    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getLavoro_menu() {
        return lavoro_menu;
    }

    public void setLavoro_menu(float lavoro_menu) {
        this.lavoro_menu = lavoro_menu;
    }

    public ArrayList<LocalDate> getDisponibilità() {
        return disponibilità;
    }

    public void setDisponibilità(ArrayList<LocalDate> disponibilità) {
        this.disponibilità = disponibilità;
    }

    public void aggiungiDisponibilita(ArrayList<LocalDate> periodo){
        this.disponibilità.add(periodo.get(0));
        this.disponibilità.add(periodo.get(1));
    }
}
