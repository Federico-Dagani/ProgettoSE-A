package ProgettoSE.View;

import ProgettoSE.Model.Alimentari.Alimento;
import ProgettoSE.Model.Alimentari.Bevanda;
import ProgettoSE.Model.Alimentari.Extra;
import ProgettoSE.Model.Alimentari.Ingrediente;
import ProgettoSE.Model.Attori.Gestore.Gestore;
import ProgettoSE.Model.Produzione.Menu.MenuTematico;
import ProgettoSE.Model.Produzione.Piatto;
import ProgettoSE.Model.Produzione.Prenotabile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Visualizzazione {
    /**
     * <h2>Metodo che stampa a video il menu del giorno</h2>
     * <b>Precondizione: </b>gestore e data non nulli<br>
     * @param gestore gestore del ristorante
     * @param data data di cui si vuole visualizzare il menu
     * @return true se il menu è vuoto, false altrimenti
     * @throws IllegalArgumentException se i parametri non sono validi
     */
    public static boolean stampaMenuDelGiorno(Gestore gestore, LocalDate data) {
        //precondizione: gestore e data non nulli
        if(gestore == null || data == null) throw new IllegalArgumentException("Parametri non validi");
        ripulisciConsole();
        //se il menu è vuoto stampo un messaggio e ritorno true
        if (gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data).isEmpty()) {
            System.out.println("Non ci sono piatti disponibili per il giorno " + data);
            return true;
        } else {
            System.out.println("\nIl menù disponibile per il giorno " + data + " offre queste specialità:");
            System.out.println("(può scegliere sia i piatti all'interno del menù alla carta che i menù tematici presenti) \n");
            //ciclo l'arraylist di prenotabili e stampo a video i piatti e i menù tematici
            for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data)) {
                //stampo i piatti
                if (prenotabile instanceof Piatto) {
                    Piatto piatto = (Piatto) prenotabile;
                    System.out.printf("- " + piatto.getNome().toUpperCase());
                    // System.out.printf(" con ingredienti: (");
                    System.out.printf(": (");
                    ArrayList<Alimento> ingredienti = piatto.getRicetta().getIngredienti();
                    for (Alimento ingrediente : ingredienti) {
                        //gestisco l'ultimo elemento mettendo un punto al posto della virgola
                        if (ingrediente.equals(piatto.getRicetta().getIngredienti().get(ingredienti.toArray().length - 1)))
                            System.out.printf(ingrediente.getNome() + ".)");
                        else
                            System.out.printf(ingrediente.getNome() + ", ");
                    }
                    System.out.printf("\n\n");
                //stampo i menù tematici
                } else if (prenotabile instanceof MenuTematico) {
                    MenuTematico menu_tematico = (MenuTematico) prenotabile;
                    System.out.printf("- Menù " + menu_tematico.getNome().toUpperCase());
                    System.out.printf(" con i seguenti piatti: ");
                    //stampo i piatti nel menu tematico
                    ArrayList<Piatto> piatti = menu_tematico.getPiatti_menu();
                    for (Piatto piatto : menu_tematico.getPiatti_menu()) {
                        //gestisco l'ultimo elemento mettendo un punto al posto della virgola
                        if (piatto.equals(piatti.get(piatti.toArray().length - 1)))
                            System.out.printf("" + piatto.getNome() + ".");
                        else
                            System.out.printf("" + piatto.getNome() + ", ");
                    }
                    System.out.printf("\n\n");

                }
            }
            //ritorno false perchè il menu non è vuoto
            return false;
        }
    }
    /**
     * <h2>Metodo che stampa a video la lista dei piatti e menu scelti fino a quel momento in una prenotazione</h2>
     * <b>Precondizione: </b>mappa scelte non nulla<br>
     * @param scelte HashMap contenente i piatti e i menù tematici scelti finora con relativa quantità
     * @throws IllegalArgumentException se i parametri non sono validi
     */
    public static void stampaScelte(HashMap<Prenotabile, Integer> scelte) {
        //precondizione: scelte non nulle
        if(scelte == null) throw new IllegalArgumentException("Parametri non validi");
        //se è vuoto evito di stampare delle stringhe inutili ed esco dal metodo
        if (scelte.isEmpty()) {
            return;
        }
        System.out.println("Le scelte effettuate finora sono le seguenti: ");
        //scorro le key dell'hashmap e stampo a video i piatti e i menù tematici con la relativa value che rappresenta la quantità scelta
        for (Prenotabile prenotabile : scelte.keySet()) {
            if (prenotabile instanceof Piatto) {
                System.out.printf("- " + prenotabile.getNome() + ", ");
                System.out.printf("quantità: " + scelte.get(prenotabile) + "\n");
            } else if (prenotabile instanceof MenuTematico) {
                System.out.printf("- Menù " + prenotabile.getNome() + ", ");
                System.out.printf("quantità: " + scelte.get(prenotabile) + "\n");
            }
        }
    }

    /**
     * <h2>Metodo che utilizza il polimorfismo per stampare a video la lista di extra o di bevande con relativi consumi procapite</h2>
     * <b>Precondizione: </b>arraylist alimenti non nullo<br>
     * @param alimenti ArrayList di tipo Alimento contenente gli extra o le bevande
     * @throws IllegalArgumentException se i parametri non sono validi
     */
    public static void mostraConsumoProcapite(ArrayList<Alimento> alimenti) {
        //precodizione: alimenti non nullo
        if(alimenti == null) throw new IllegalArgumentException("Parametri non validi");
        //stampo a video il titolo sfruttando il polimorfismo
        if (alimenti.get(0) instanceof Bevanda) {
            System.out.println("\nLista delle bevande con i relativi consumi procapite: ");
        } else if (alimenti.get(0) instanceof Extra) {
            System.out.println("\nLista degli extra con i relativi consumi procapite: ");
        }
        //stampo a video gli alimenti con i relativi consumi procapite sfruttando il polimorfismo
        for (Alimento alimento : alimenti) {
            if (alimento instanceof Bevanda) {
                System.out.printf("- " + alimento.getNome() + ", ");
                System.out.printf("consumo procapite: " + ((Bevanda) alimento).getCons_procapite() + "\n");
            } else if (alimento instanceof Extra) {
                System.out.printf("- " + alimento.getNome() + ", ");
                System.out.printf("consumo procapite: " + ((Extra) alimento).getCons_procapite() + "\n");
            }
        }
    }

    /**
     * <h2>Metodo che utilizza il polimorfismo per stampare a video la lista dei nomi di extra o di bevande</h2>
     * <b>Precondizione: </b>arraylist alimenti non nullo<br>
     * @param alimenti ArrayList di tipo Alimento contenente gli extra o le bevande
     * @throws IllegalArgumentException se alimenti è nullo
     */
    public static void mostraAlimenti(ArrayList<Alimento> alimenti) {
        //precondizione: alimenti non nullo
        if(alimenti == null) throw new IllegalArgumentException("Parametri non validi");
        //stampo a video il titolo sfruttando il polimorfismo
        if (alimenti.get(0) instanceof Bevanda) {
            System.out.println("\nLista delle bevande presenti nel ristorante: ");
        } else if (alimenti.get(0) instanceof Extra) {
            System.out.println("\nLista degli extra presenti nel ristorante:");
        } else if (alimenti.get(0) instanceof Ingrediente) {
            System.out.println("\nLista degli ingredienti presenti nel ristorante:");
        }
        //stampo a video gli alimenti sfruttando il polimorfismo
        for (Alimento alimento : alimenti) {
            System.out.printf("- " + alimento.getNome() + "\n");
        }
    }

    /**
     * <h2>Metodo che stampa a video il carico di lavoro per persona</h2>
     * <b>Precondizione: </b>gestore non nullo<br>
     * @param gestore Gestore del ristorante
     * @throws IllegalArgumentException se i parametri non sono validi
     */
    public static void mostraCaricoLavoroPersona(Gestore gestore){
        //precondizione: gestore non nullo
        if(gestore == null) throw new IllegalArgumentException("Il gestore non può essere nullo");
        System.out.println("\nIl carico di lavoro per persona è: " + gestore.getRistorante().getLavoro_persona());
    }

    /**
     * <h2>Metodo che stampa a video il numero di posti disponibili nel ristorante</h2>
     * <b>Precondizione: </b>gestore non nullo<br>
     * @param gestore Gestore del ristorante
     * @throws IllegalArgumentException se il gestore è nullo
     */
    public static void mostraPostiDisponibili(Gestore gestore){
        //precondizione: gestore non nullo
        if(gestore == null) throw new IllegalArgumentException("Il gestore non può essere nullo");
        System.out.println("\nIl numero di posti disponibili nel ristorante è: " + gestore.getRistorante().getN_posti());
    }

    /**
     * <h2>Metodo che stampa a video i menu tematici presenti nel menu e le relative disponibilità</h2>
     * <b>Precondizione: </b>menu non nullo<br>
     * @param menu ArrayList di tipo Prenotabile contenente i menu tematici e i piatti
     * @throws IllegalArgumentException se il menu è nullo
     */
    public static void mostraMenuTematici(ArrayList<Prenotabile> menu) {
        //precondizione: menu non nullo
        if(menu == null) throw new IllegalArgumentException("Il menu non può essere nullo");
        System.out.println("\n\nI menu tematici del menù alla carta sono i seguenti: ");
        for (Prenotabile prenotabile : menu) {
            //ciclo su i prenotabili e stampo a video solo i menu tematici
            if (prenotabile instanceof MenuTematico) {
                MenuTematico menuTematico = (MenuTematico) prenotabile;
                System.out.println("\nNome " + menuTematico.getNome());
                //stampo i relativi periodi di disponibilità in righe diverse
                System.out.println("Periodi disponibilità: ");
                int inizio = 0;
                for (int i = 0; i < menuTematico.getDisponibilità().toArray().length / 2; i++) {
                    System.out.println("Inizio: " + menuTematico.getDisponibilità().get(inizio) + "\tFine: " + menuTematico.getDisponibilità().get(inizio + 1));
                    inizio += 2;
                }
            }
        }
    }

    /**
     * <h2>Metodo che stampa a video i piatti presenti nel menu e le relative disponibilità</h2>
     * <b>Precondizione: </b>menu non nullo<br>
     * @param menu ArrayList di tipo Prenotabile contenente i piatti e i menu tematici
     * @throws IllegalArgumentException se il menu è nullo
     */
    public static void mostraPiatti(ArrayList<Prenotabile> menu) {
        //precondizione: menu non nullo
        if(menu == null) throw new IllegalArgumentException("Il menu non può essere nullo");
        System.out.println("\n\nI piatti del menù alla carta sono i seguenti: ");
        for (Prenotabile prenotabile : menu) {
            if (prenotabile instanceof Piatto) {
                Piatto piatto = (Piatto) prenotabile;
                System.out.println("\nNome " + piatto.getNome());
                //stampo i relativi periodi di disponibilità in righe diverse
                System.out.println("Periodi disponibilità: ");
                int inizio = 0;
                for (int i = 0; i < piatto.getDisponibilità().toArray().length / 2; i++) {
                    System.out.println("Inizio: " + piatto.getDisponibilità().get(inizio) + "\tFine: " + piatto.getDisponibilità().get(inizio + 1));
                    inizio += 2;
                }
            }
        }
    }

    /**
     * <h2>Metodo che stampa a video le ricette dei piatti presenti nel menu</h2>
     * <b>Precondizione: </b>menu non nullo<br>
     * @param menu ArrayList di tipo Prenotabile contenente i piatti e i menu tematici
     * @throws IllegalArgumentException se il menu è nullo
     */
    public static void mostraRicette(ArrayList<Prenotabile> menu) {
        //precondizione: menu non nullo
        if(menu == null) throw new IllegalArgumentException("Il menu non può essere nullo");
        System.out.println("\n\nLe ricette del menù alla carta sono le seguenti: ");
        for (Prenotabile prenotabile : menu) {
            if (prenotabile instanceof Piatto) {
                Piatto piatto = (Piatto) prenotabile;
                System.out.println("\n" + piatto.getNome().toUpperCase());
                System.out.println("Ricetta: ");
                //ciclo su tutti gli ingredienti del piatto e stampo a video su righe diverse
                for (Alimento ingrediente : piatto.getRicetta().getIngredienti()) {
                    System.out.println("  °\t" + ingrediente.getQta() + " " + ingrediente.getMisura() + " di " + ingrediente.getNome());
                }
            }
        }
    }

    /**
     * <h2>Metodo utilizzato nella stampa a video per pulire l'interfaccia</h2>
     */
    public static void ripulisciConsole() {
        for (int i = 0; i < 30; i++)
            System.out.println();
    }
}
