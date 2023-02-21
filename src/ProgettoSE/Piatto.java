package ProgettoSE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Piatto {
    private String nome;
    private ArrayList<LocalDate> disponibilità;
    private float lavoro_piatto; //coincide x def con lavoro_porzione
    private final UUID id;

    public Piatto(String nome, ArrayList<LocalDate> disponibilità, float lavoro_piatto, UUID id) {
        this.nome = nome;
        this.disponibilità = disponibilità;
        this.lavoro_piatto = lavoro_piatto;
        this.id = id;
    }
}
