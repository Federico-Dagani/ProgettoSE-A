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

    public HashMap<Alimento, Float> calcolaConsumoBevande(int n_persone) {
        HashMap<Alimento, Float> consumo_bevande = new HashMap<>();
        //doppio cast perchè la consegna richiede che sia arrotondato all' intero superiore ma lo storiamo come float
        magazzino.getBevande().forEach(bevanda -> consumo_bevande.put(bevanda, (float) (int) Math.ceil(n_persone * ((Bevanda) bevanda).getCons_procapite())));
        return consumo_bevande;
    }

    public HashMap<Alimento, Float> calcolaConsumoExtras(int n_persone) {
        HashMap<Alimento, Float> consumo_extras = new HashMap<>();
        magazzino.getExtras().forEach(extra -> consumo_extras.put(extra,(float) (int) Math.ceil(n_persone * ((Extra) extra).getCons_procapite())));
        return consumo_extras;
    }

    public void creaListaSpesa(Prenotazione prenotazione_totale) {

        HashMap<Piatto, Integer> consumi = calcolaPiattiPrenotazione(prenotazione_totale);

        //per ogni piatto, calcolo la quantità di ingredienti necessaria
        consumi.keySet().forEach(piatto -> valutaQtaIngredientiPiatto(piatto, consumi.get(piatto)));

        //ciclare sugli extra e sulle bevande
        prenotazione_totale.getCons_extra().forEach((key, value) -> {
            if (key instanceof Extra) {
                value += value * Costanti.IMPREVISTI_CUCINA;
                float qta_rimanente = magazzino.getAlimento(key.getNome()).getQta() - value;
                if (qta_rimanente < 0)
                    this.lista_spesa.add(new Extra(key.getNome(), Math.abs(qta_rimanente), key.getMisura(), ((Extra) key).getCons_procapite()));
            }
        });

        prenotazione_totale.getCons_bevande().forEach((key, value) -> {
            if (key instanceof Bevanda) {
                value += value * Costanti.IMPREVISTI_CUCINA;
                float qta_rimanente = magazzino.getAlimento(key.getNome()).getQta() - value;
                if (qta_rimanente < 0)
                    this.lista_spesa.add(new Bevanda(key.getNome(), Math.abs(qta_rimanente), key.getMisura(), ((Bevanda) key).getCons_procapite()));
            }
        });

    }

    private void valutaQtaIngredientiPiatto(Piatto piatto, int qta_richiesta_piatto) {

        int n_porzioni_ricetta = piatto.getRicetta().getN_porzioni();   //ricetta x5
        int n_ricette = (int) Math.ceil(qta_richiesta_piatto / n_porzioni_ricetta);  //   14/5   = 3 ricette
        //ciclo sugli ingredienti del piatto
        for (Alimento ingrediente : piatto.getRicetta().getIngredienti()) {
            //per trovare il numero di porzioni da fare mi interessa il numero di porzioni che genera la ricetta, in relazione al numero di porzioni che mi servono di quel piatto
            float qta_richiesta_ingrediente = ingrediente.getQta() * n_ricette;
            qta_richiesta_ingrediente += qta_richiesta_piatto * Costanti.IMPREVISTI_CUCINA;
            float qta_rimanente = magazzino.getAlimento(ingrediente.getNome()).getQta() - qta_richiesta_ingrediente;
            if (qta_rimanente < 0)
                //compro l'ingrediente in una quantità arrotondata per eccesso
                this.lista_spesa.add(new Ingrediente(ingrediente.getNome(), Math.abs(qta_rimanente), ingrediente.getMisura()));
        }
    }

    public String aggiungiSpesaInMagazzino() {
        if (this.lista_spesa.isEmpty())
            return "\nLe disponibilità in magazzino riescono a soddisfare tutte le prenotazioni della giornata senza richiedere l'acquisto di ulteriori alimenti\n";
        String messaggio = "\nIl magazziniere sta aggiornando il magazzino andando ad aggiungere degli alimenti secondo la seguente lista della spesa: \n";
        for (Alimento alimento : lista_spesa) {
            messaggio += "- " + alimento.getNome() + " in quantità pari a: " + String.format("%.2f", alimento.getQta()) + " " + alimento.getMisura() + "\n";
            float qta_in_magazzino = magazzino.getAlimento(alimento.getNome()).getQta();
            float nuova_qta = qta_in_magazzino + alimento.getQta();
            alimento.setQta(nuova_qta);
            magazzino.setAlimento(alimento);
        }
        //svuoto la lista della spesa una volta aggiornato il magazzino
        this.lista_spesa.clear();
        return messaggio;
    }

    //porto in cucina gli ingredienti seguendo le richieste effettive, non quelle delle ricette ma aggiungendoci un 5% in più
    public void portaInCucina(Prenotazione prenotazione_complessiva) {

        HashMap<Piatto, Integer> consumi_prenotazione = calcolaPiattiPrenotazione(prenotazione_complessiva);
        //porto gli ingredienti necessari in cucina
        for (Piatto piatto : consumi_prenotazione.keySet()) {
            for (Alimento ingrediente : piatto.getRicetta().getIngredienti()) {//setto all'ingrediente la quantità richiesta dai clienti (non dalla ricetta)
                float qta_da_prelevare = ingrediente.getQta() / piatto.getRicetta().getN_porzioni() * consumi_prenotazione.get(piatto);
                qta_da_prelevare += qta_da_prelevare * Costanti.ALIMENTI_SCARTATI;
                this.magazzino.prelevaAlimento(ingrediente.getNome(), qta_da_prelevare);
            }
        }
        //porto le bevande necessarie in cucina
        for(Map.Entry<Alimento, Float> cons_bevande : prenotazione_complessiva.getCons_bevande().entrySet()){
            float qta_da_prelevare = cons_bevande.getValue();
            qta_da_prelevare += qta_da_prelevare * Costanti.ALIMENTI_SCARTATI;
            this.magazzino.prelevaAlimento(cons_bevande.getKey().getNome(), qta_da_prelevare);
        }
        //porto gli extra necessari in cucina
        for(Map.Entry<Alimento, Float> cons_extra : prenotazione_complessiva.getCons_extra().entrySet()){
            float qta_da_prelevare = cons_extra.getValue();
            qta_da_prelevare += qta_da_prelevare * Costanti.ALIMENTI_SCARTATI;
            this.magazzino.prelevaAlimento(cons_extra.getKey().getNome(), qta_da_prelevare);
        }

    }

    private HashMap<Piatto, Integer> calcolaPiattiPrenotazione(Prenotazione prenotazione_totale) {
        HashMap<Piatto, Integer> consumi = new HashMap<>();
        for (Prenotabile prenotabile : prenotazione_totale.getScelte().keySet()) {
            //controllo se l'oggetto è un piatto
            if (prenotabile instanceof Piatto) {
                if (!consumi.containsKey((Piatto) prenotabile))
                    consumi.put((Piatto) prenotabile, prenotazione_totale.getScelte().get(prenotabile));
                else
                    consumi.put((Piatto) prenotabile, consumi.get(prenotabile) + prenotazione_totale.getScelte().get(prenotabile));
            } else if (prenotabile instanceof MenuTematico) {
                //ciclo sui piatti presenti nel menu
                for (Piatto piatto : ((MenuTematico) prenotabile).getPiatti_menu()) {
                    if (!consumi.containsKey(piatto))
                        consumi.put(piatto, prenotazione_totale.getScelte().get(prenotabile));
                    else
                        consumi.put(piatto, consumi.get(piatto) + prenotazione_totale.getScelte().get(prenotabile));
                }
            }
        }
        return consumi;
    }


}
