package ProgettoSE.Attori;

import ProgettoSE.*;
import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Menu.MenuTematico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Magazziniere extends Persona {
    //ATTRIBUTI
    public Magazziniere(String nome) {
        super(nome);
    }
    private Magazzino magazzino;
    private ArrayList<Alimento> lista_spesa;
    //METODI
    //costruttore
    public Magazziniere(String nome, Magazzino magazzino, ArrayList<Alimento> lista_spesa) {
        super(nome);
        this.magazzino = magazzino;
        this.lista_spesa = lista_spesa;
    }
    //getters
    public Magazzino getMagazzino() {
        return magazzino;
    }
    public ArrayList<Alimento> getLista_spesa() {
        return lista_spesa;
    }
    //setters
    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }
    public void setLista_spesa(ArrayList<Alimento> lista_spesa) {
        this.lista_spesa = lista_spesa;
    }

    public HashMap<Alimento, Float> calcolaConsumoAlimento(int n_persone, String tipologia){

        HashMap<Alimento, Float> consumi = new HashMap<>();
        for (Alimento alimento : magazzino.getBevande()){
            if(tipologia.equals(Costanti.BEVANDA) && alimento instanceof Bevanda)
                consumi.put(alimento, n_persone * ((Bevanda) alimento).getCons_procapite());
            else if(tipologia.equals(Costanti.EXTRA) && alimento instanceof Extra)
                consumi.put(alimento, n_persone * ((Extra) alimento).getCons_procapite());
        }
        return consumi;
    }

    /*
    Problema:
        ricetta kebab fa 2 porzioni
        ordino 1 kebab dal menu alla carta
        ordino 1 kebab dal menu classic
        --> prenderà gli ingredienti per 4 kebab, inutilmente
     */

    public void creaListaSpesa(Prenotazione prenotazione_totale){
        //ciclo sui prenotabili (piatto o menu tematico)
        for(Prenotabile prenotabile : prenotazione_totale.getScelte().keySet()){
            //controllo se l'oggetto è un piatto
            if(prenotabile instanceof Piatto){
                valutaQtaIngredientiPiatto(prenotabile, prenotazione_totale.getScelte().get(prenotabile));
            }else if(prenotabile instanceof MenuTematico){
                //ciclo sui piatti presenti nel menu
                for (Piatto piatto : ((MenuTematico) prenotabile).getPiatti_menu()){
                    valutaQtaIngredientiPiatto(piatto, prenotazione_totale.getScelte().get(prenotabile));
                }
            }
        }
        //ciclare sugli extra e sulle bevande
        for(Map.Entry<Alimento, Float> alimento : prenotazione_totale.getCons_extra().entrySet()){
            if(alimento.getKey() instanceof Extra){
                float qta_rimanente = magazzino.getAlimento(alimento.getKey().getNome()).getQta() - alimento.getValue();
                if(qta_rimanente < 0)
                    this.lista_spesa.add(new Extra(alimento.getKey().getNome(), Math.abs(qta_rimanente), alimento.getKey().getMisura(), ((Extra) alimento.getKey()).getCons_procapite()));
            }
        }

        for(Map.Entry<Alimento, Float> alimento : prenotazione_totale.getCons_bevande().entrySet()){
            if(alimento.getKey() instanceof Bevanda){
                float qta_rimanente = magazzino.getAlimento(alimento.getKey().getNome()).getQta() - alimento.getValue();
                if(qta_rimanente < 0)
                    this.lista_spesa.add(new Bevanda(alimento.getKey().getNome(), Math.abs(qta_rimanente), alimento.getKey().getMisura(), ((Bevanda) alimento.getKey()).getCons_procapite()));
            }
        }

    }
    public void creaListaSpesa2(Prenotazione prenotazione_totale){
        HashMap<Piatto, Integer> consumi = new HashMap<>();
        //creo la mappa dei consumi (ogni piatto con la quantità totale)
        for(Prenotabile prenotabile : prenotazione_totale.getScelte().keySet()){
            //controllo se l'oggetto è un piatto
            if(prenotabile instanceof Piatto){
                if (!consumi.containsKey((Piatto) prenotabile))
                        consumi.put((Piatto) prenotabile, prenotazione_totale.getScelte().get(prenotabile));
                else
                    consumi.put((Piatto) prenotabile, consumi.get(prenotabile) + prenotazione_totale.getScelte().get(prenotabile));
            }else if(prenotabile instanceof MenuTematico){
                //ciclo sui piatti presenti nel menu
                for (Piatto piatto : ((MenuTematico) prenotabile).getPiatti_menu()){
                    if (!consumi.containsKey(piatto))
                        consumi.put(piatto, prenotazione_totale.getScelte().get(prenotabile));
                    else
                        consumi.put(piatto, consumi.get(piatto) + prenotazione_totale.getScelte().get(prenotabile));
                }
            }
        }
        //per ogni piatto, calcolo la quantità di ingredienti necessaria
        for (Piatto piatto : consumi.keySet()){
            valutaQtaIngredientiPiatto2(piatto, consumi.get(piatto));
        }

        //ciclare sugli extra e sulle bevande
        for(Map.Entry<Alimento, Float> alimento : prenotazione_totale.getCons_extra().entrySet()){
            if(alimento.getKey() instanceof Extra){
                float qta_rimanente = magazzino.getAlimento(alimento.getKey().getNome()).getQta() - alimento.getValue();
                if(qta_rimanente < 0)
                    this.lista_spesa.add(new Extra(alimento.getKey().getNome(), Math.abs(qta_rimanente), alimento.getKey().getMisura(), ((Extra) alimento.getKey()).getCons_procapite()));
            }
        }

        for(Map.Entry<Alimento, Float> alimento : prenotazione_totale.getCons_bevande().entrySet()){
            if(alimento.getKey() instanceof Bevanda){
                float qta_rimanente = magazzino.getAlimento(alimento.getKey().getNome()).getQta() - alimento.getValue();
                if(qta_rimanente < 0)
                    this.lista_spesa.add(new Bevanda(alimento.getKey().getNome(), Math.abs(qta_rimanente), alimento.getKey().getMisura(), ((Bevanda) alimento.getKey()).getCons_procapite()));
            }
        }

    }


    private void valutaQtaIngredientiPiatto(Prenotabile prenotabile, int qta_richiesta_piatto){

        int n_porzioni_ricetta = ((Piatto) prenotabile).getRicetta().getN_porzioni();
        int n_porzioni = (int) Math.ceil(qta_richiesta_piatto/n_porzioni_ricetta);
        //ciclo sugli ingredienti del piatto
        for (Alimento ingrediente : ((Piatto) prenotabile).getRicetta().getIngredienti()) {
            //per trovare il numero di porzioni da fare mi interessa il numero di porzioni che genera la ricetta, in relazione al numero di porzioni che mi servono di quel piatto
            float qta_richiesta_ingrediente = ingrediente.getQta() * n_porzioni;
            float qta_rimanente = magazzino.getAlimento(ingrediente.getNome()).getQta() - qta_richiesta_ingrediente;
            if (qta_rimanente < 0)
                //compro l'ingrediente in una quantità arrotondata per eccesso
                this.lista_spesa.add(new Ingrediente(ingrediente.getNome(), (float) Math.ceil(Math.abs(qta_richiesta_ingrediente)), ingrediente.getMisura()));
        }
    }
    private void valutaQtaIngredientiPiatto2(Piatto piatto, int qta_richiesta_piatto){

        int n_porzioni_ricetta = piatto.getRicetta().getN_porzioni();
        int n_porzioni = (int) Math.ceil(qta_richiesta_piatto/n_porzioni_ricetta);
        //ciclo sugli ingredienti del piatto
        for (Alimento ingrediente : piatto.getRicetta().getIngredienti()) {
            //per trovare il numero di porzioni da fare mi interessa il numero di porzioni che genera la ricetta, in relazione al numero di porzioni che mi servono di quel piatto
            float qta_richiesta_ingrediente = ingrediente.getQta() * n_porzioni;
            float qta_rimanente = magazzino.getAlimento(ingrediente.getNome()).getQta() - qta_richiesta_ingrediente;
            if (qta_rimanente < 0)
                //compro l'ingrediente in una quantità arrotondata per eccesso
                this.lista_spesa.add(new Ingrediente(ingrediente.getNome(), (float) Math.ceil(Math.abs(qta_richiesta_ingrediente)), ingrediente.getMisura()));
        }
    }

    public String aggiornaMagazzino(){
        if(this.lista_spesa.isEmpty())
            return "\nLe disponibilità in magazzino riescono a soddisfare tutte le prenotazioni della giornata senza richiedere l'acquisto di ulteriori alimenti\n";
        String messaggio = "\nIl magazziniere sta aggiornando il magazzino andando ad aggiungere degli alimenti secondo la seguente lista della spesa: \n";
        for(Alimento alimento : lista_spesa){
            messaggio += "- " + alimento.getNome() + " in quantità pari a: " + alimento.getQta() + " " + alimento.getMisura() + "\n";
            float qta_in_magazzino = magazzino.getAlimento(alimento.getNome()).getQta();
            float nuova_qta = qta_in_magazzino + alimento.getQta();
            alimento.setQta(nuova_qta);
            magazzino.setAlimento(alimento);
        }
        //svuoto la lista della spesa una volta aggiornato il magazzino
        this.lista_spesa.clear();
        return messaggio;
    }

}
