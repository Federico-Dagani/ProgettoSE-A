/**
package ProgettoSE;

import it.unibs.arnaldo.rovinePerdute.ServiziFileXML.*;
import it.unibs.arnaldo.rovinePerdute.mylib.InputDati;
import it.unibs.arnaldo.rovinePerdute.mylib.MyMenu;

import java.util.ArrayList;

public class MainEsempio {
    public static void main(String[] args) {

        //messaggio di benvenuto
        benvenuto();

        //creazione e stampa menu
        MyMenu menu_num_citta = nuovoMenu(Costanti.NUM_CITTA);
        menu_num_citta.stampaMenuSenzaUscita();

        //scelta numero delle città
        int num_citta = InputDati.leggiInteroPositivo(Costanti.INSERIMENTO_NUM_CITTA);
        while (!controllaNumCitta(num_citta)){
            System.out.println(Costanti.ERRORE_NUM_CITTA);
            num_citta = InputDati.leggiInteroPositivo(Costanti.INSERIMENTO_NUM_CITTA);
        };

        long startTime = System.currentTimeMillis();

        //genera grafo
        Grafo grafo = LetturaFileXML.leggiMappa(fileRoute(num_citta));

        //genera luogo della partenza(sarà sempre il campo base con id=0) e dell'arrivo(con id=dimensione-1)
        Luogo partenza = grafo.getNodo(0);
        Luogo arrivo = grafo.getNodo(grafo.getNumeroNodi()-1);

        //genera navigatore
        Navigatore navigatore = new Navigatore(grafo);

        //genera veicolo
        Veicolo veicolo_metztli = new Veicolo(null, Costanti.METZTLI);
        Veicolo veicolo_tonathiu = new Veicolo(null, Costanti.TONATHIU);

        //calcola il percorso
        System.out.printf(Costanti.CALCOLO_IN_CORSO, veicolo_metztli.getTipologia());
        ArrayList<Luogo> percorso_metztli = navigatore.trovaPercorso(partenza, arrivo, veicolo_metztli);
        System.out.printf(Costanti.CALCOLO_IN_CORSO, veicolo_tonathiu.getTipologia());
        ArrayList<Luogo> percorso_tonathiu = navigatore.trovaPercorso(partenza, arrivo, veicolo_tonathiu);

        //aggiungo il percorso ai veicoli
        veicolo_metztli.setRoute(percorso_metztli);
        veicolo_tonathiu.setRoute(percorso_tonathiu);

        long endTime = System.currentTimeMillis();
        long seconds = (endTime - startTime) / 1000;

        System.out.println("\nProgramma eseguito in " + seconds + " secondi");

        //scrivo il file
        ScritturaFileXML.scriviPercorso(veicolo_metztli, veicolo_tonathiu);
    }

    /**
     * <h3>Metodo che compie uno switch, in base al numero delle città selezionate ritornerà il file che corrispondono</h3>
     * @param num_citta ovvero il nuemro delle città del grafo
     * @return una stringa che corrisponde alla route del file
     */
/*
    private static String fileRoute(int num_citta){
        switch (num_citta){
            case 5:
                return Costanti.FILE_5_CITTA;
            case 12:
                return Costanti.FILE_12_CITTA;
            case 50:
                return Costanti.FILE_50_CITTA;
            case 200:
                return Costanti.FILE_200_CITTA;
            case 2000:
                return Costanti.FILE_2000_CITTA;
            case 10000:
                return Costanti.FILE_10000_CITTA;
            default: return null;
        }
    }

    /**
     * <h3>Metdo che valuta se il nuemero delle città è corretto</h3>
     * @param num_citta ovvero il nuemro delle città del grafo
     * @return vero->il numero delle città corrisponde alle città disponibili in un file<br> false->numero non valido
     */
/*
    private static boolean controllaNumCitta(int num_citta){
        if(num_citta==5 || num_citta==12 || num_citta==50 || num_citta==200 || num_citta==2000 || num_citta==10000) return true;
        else return false;
    }

    /**
     * <h3>Metodo per la stampa di una comunicazione di benvenuto</h3>
     */
/*
    private static void benvenuto(){
        System.out.println(Costanti.CORNICE_SUP);
        System.out.printf("|\t"+Costanti.BENVENUTO+"\t|\n");
        System.out.println(Costanti.CORNICE_INF);
    }

    /**
     * <h3>Metodo per la creazione dei vari menu</h3>
     * @param funzione corrispondente alla tipologia del menu da creare
     * @return menu in base alla funzione specificata come parametro
     */
    /**
    private static MyMenu nuovoMenu(String funzione){
        switch (funzione){
            case Costanti.NUM_CITTA:
                String[] menu_citta = new String[6];
                menu_citta[0] = Costanti.CINQUE;
                menu_citta[1] = Costanti.DODICI;
                menu_citta[2] = Costanti.CINQUANTA;
                menu_citta[3] = Costanti.DUECENTO;
                menu_citta[4] = Costanti.DUEMILA;
                menu_citta[5] = Costanti.DIECIMILA;
                MyMenu menu_num_citta = new MyMenu(Costanti.NUM_CITTA,menu_citta);
                return menu_num_citta;
            default: break;
        }
        return null;
    }
}

*/
