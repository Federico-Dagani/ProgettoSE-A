package ProgettoSE.Model.Produzione.Menu;

import ProgettoSE.Model.Produzione.Piatto;
import ProgettoSE.Model.Produzione.Prenotabile;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuTematico extends Menu implements Prenotabile {
    //ATTRIBUTI
    private String nome;
    private float lavoro_menu;
    private ArrayList<LocalDate> disponibilità;

    //METODI
    /**
     * <h2>Costruttore di MenuTematico</h2>
     * @param nome nome del menu
     * @param piatti_menu lista dei piatti del menu
     * @param lavoro_menu lavoro del menu
     * @param disponibilità disponibilità del menu
     */
    public MenuTematico(String nome, ArrayList<Piatto> piatti_menu, float lavoro_menu, ArrayList<LocalDate> disponibilità) {
        super(piatti_menu);
        this.lavoro_menu = lavoro_menu;
        this.disponibilità = disponibilità;
        this.nome = nome;
    }

    //GET E SET
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

    /**
     * <h2>Metodo che aggiunge una disponibilità al menu</h2>
     * @param periodo periodo di disponibilità, ovvero un ArrayList di LocalDate, precisamente di 2 elementi
     */
    public void aggiungiDisponibilita(ArrayList<LocalDate> periodo){
        this.disponibilità.add(periodo.get(0));
        this.disponibilità.add(periodo.get(1));
    }
}
