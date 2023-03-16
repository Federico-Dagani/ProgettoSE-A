package ProgettoSE.Utilità;

import ProgettoSE.Alimentari.Alimento;
import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Attori.Gestore;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.Piatto;
import ProgettoSE.Prenotabile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Visualizzazione {

    public static boolean stampaMenuDelGiorno(Gestore gestore, LocalDate data) {
        ripulisciConsole();
        if (gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data).isEmpty()) {
            System.out.println("Non ci sono piatti disponibili per il giorno " + data);
            return true;
        } else {
            System.out.println("\nIl menù disponibile per il giorno " + data + " offre queste specialità:");
            System.out.println("(può scegliere sia i piatti all'interno del menù alla carta che i menù tematici presenti) \n");
            for (Prenotabile prenotabile : gestore.getRistorante().getAddettoPrenotazione().calcolaMenuDelGiorno(data)) {
                if (prenotabile instanceof Piatto) {
                    Piatto piatto = (Piatto) prenotabile;
                    System.out.printf("- " + piatto.getNome().toUpperCase());
                    // System.out.printf(" con ingredienti: (");
                    System.out.printf(": (");
                    ArrayList<Alimento> ingredienti = piatto.getRicetta().getIngredienti();
                    for (Alimento ingrediente : ingredienti) {
                        if (ingrediente.equals(piatto.getRicetta().getIngredienti().get(ingredienti.toArray().length - 1)))
                            System.out.printf(ingrediente.getNome() + ".)");
                        else
                            System.out.printf(ingrediente.getNome() + ", ");
                    }
                    System.out.printf("\n\n");

                } else if (prenotabile instanceof MenuTematico) {
                    MenuTematico menu_tematico = (MenuTematico) prenotabile;
                    System.out.printf("- Menù " + menu_tematico.getNome().toUpperCase());
                    System.out.printf(" con i seguenti piatti: ");
                    ArrayList<Piatto> piatti = menu_tematico.getPiatti_menu();
                    for (Piatto piatto : menu_tematico.getPiatti_menu()) {
                        if (piatto.equals(piatti.get(piatti.toArray().length - 1)))
                            System.out.printf("" + piatto.getNome() + ".");
                        else
                            System.out.printf("" + piatto.getNome() + ", ");
                    }
                    System.out.printf("\n\n");

                }
            }
            return false;
        }
    }

    public static void stampaScelte(HashMap<Prenotabile, Integer> scelte) {
        if (scelte.isEmpty()) {
            return;
        }
        System.out.println("Le scelte effettuate finora sono le seguenti: ");
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

    public static void mostraConsumoProcapite(ArrayList<Alimento> alimenti) {
        if (alimenti.get(0) instanceof Bevanda) {
            System.out.println("\nLista delle bevande con i relativi consumi procapite: ");
        } else if (alimenti.get(0) instanceof Extra) {
            System.out.println("\nLista degli extra con i relativi consumi procapite: ");
        }
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

    public static void mostraAlimenti(ArrayList<Alimento> alimenti) {
        if (alimenti.get(0) instanceof Bevanda) {
            System.out.println("\nLista delle bevande presenti nel ristorante: ");
        } else if (alimenti.get(0) instanceof Extra) {
            System.out.println("\nLista degli extra presenti nel ristorante:");
        } else if (alimenti.get(0) instanceof Ingrediente) {
            System.out.println("\nLista degli ingredienti presenti nel ristorante:");
        }
        for (Alimento alimento : alimenti) {
            System.out.printf("- " + alimento.getNome() + "\n");
            //System.out.printf("quantità: " + alimento.getQta() + " " + alimento.getMisura() + "\n");
        }
    }

    public static void mostraCaricoLavoroPersona(Gestore gestore){
        System.out.println("\nIl carico di lavoro per persona è: " + gestore.getRistorante().getLavoro_persona());
    }

    public static void mostraPostiDisponibili(Gestore gestore){
        System.out.println("\nIl numero di posti disponibili nel ristorante è: " + gestore.getRistorante().getN_posti());
    }

    public static void mostraMenuTematici(ArrayList<Prenotabile> menu) {
        System.out.println("\n\nI menu tematici del menù alla carta sono i seguenti: ");
        for (Prenotabile prenotabile : menu) {
            if (prenotabile instanceof MenuTematico) {
                MenuTematico menuTematico = (MenuTematico) prenotabile;
                System.out.println("\nNome " + menuTematico.getNome());
                System.out.println("Periodi disponibilità: ");
                int inizio = 0;
                for (int i = 0; i < menuTematico.getDisponibilità().toArray().length / 2; i++) {
                    System.out.println("Inizio: " + menuTematico.getDisponibilità().get(inizio) + "\tFine: " + menuTematico.getDisponibilità().get(inizio + 1));
                    inizio += 2;
                }
            }
        }
    }

    public static void mostraPiatti(ArrayList<Prenotabile> menu) {
        System.out.println("\n\nI piatti del menù alla carta sono i seguenti: ");
        for (Prenotabile prenotabile : menu) {
            if (prenotabile instanceof Piatto) {
                Piatto piatto = (Piatto) prenotabile;
                System.out.println("\nNome " + piatto.getNome());
                System.out.println("Periodi disponibilità: ");
                int inizio = 0;
                for (int i = 0; i < piatto.getDisponibilità().toArray().length / 2; i++) {
                    System.out.println("Inizio: " + piatto.getDisponibilità().get(inizio) + "\tFine: " + piatto.getDisponibilità().get(inizio + 1));
                    inizio += 2;
                }
            }
        }
    }

    public static void mostraRicette(ArrayList<Prenotabile> menu) {
        System.out.println("\n\nLe ricette del menù alla carta sono le seguenti: ");
        for (Prenotabile prenotabile : menu) {
            if (prenotabile instanceof Piatto) {
                Piatto piatto = (Piatto) prenotabile;
                System.out.println("\n" + piatto.getNome().toUpperCase());
                System.out.println("Ricetta: ");
                for (Alimento ingrediente : piatto.getRicetta().getIngredienti()) {
                    System.out.println("  °\t" + ingrediente.getQta() + " " + ingrediente.getMisura() + " di " + ingrediente.getNome());
                }
            }
        }
    }

    public static void ripulisciConsole() {
        for (int i = 0; i < 30; i++)
            System.out.println();
    }

}
