package ProgettoSE.ServiziFileXML;

import ProgettoSE.*;
import ProgettoSE.Alimentari.*;
import ProgettoSE.Attori.AddettoPrenotazione;
import ProgettoSE.Attori.Magazziniere;
import ProgettoSE.Menu.Menu;
import ProgettoSE.Menu.MenuTematico;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.*;

public class LetturaFileXML {

    /**
     * <h3>Metodo per la lettura del file
     * * @param filename ovvero il nome del file
     * @return ristorante
     */
    public Ristorante leggiRistorante(String filename){

        XMLInputFactory xmlif;
        XMLStreamReader xmlreader = null;

        //attributi magazziniere
        ArrayList<Bevanda> bevande = new ArrayList<>();
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        ArrayList<Extra> extras = new ArrayList<>();
        Magazzino magazzino = new Magazzino(bevande, extras, ingredienti);
        ArrayList<Alimento> lista_spesa = new ArrayList<>();

        //attributi addetto prenotazione
        ArrayList<Prenotabile> menu = new ArrayList<>();
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();

        //attributi ristorante e creazione oggetto ristorante
        AddettoPrenotazione addetto_prenotazione = new AddettoPrenotazione("addetto",prenotazioni, menu);
        Magazziniere magazziniere = new Magazziniere("magazziniere", magazzino, lista_spesa);
        Ristorante ristorante = new Ristorante(0, 0, addetto_prenotazione, magazziniere);

        Menu menu_carta = new Menu(new ArrayList<Piatto>());
        MenuTematico menu_tematico = new MenuTematico(new ArrayList<Piatto>(), 0, new ArrayList<LocalDate>());
        Ricetta ricetta = new Ricetta(new ArrayList<Ingrediente>(), 0, 0);
        Piatto piatto = new Piatto(null, new ArrayList<LocalDate>(), 0, ricetta);

        //try catch per gestire eventuali eccezioni durante l'inizializzazione
        try{
            xmlif=XMLInputFactory.newInstance();
            xmlreader= xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        }catch(Exception e){
            System.out.println(Costanti.ERRORE_INIZIALIZZAZIONE_READER);
            System.out.println(e.getMessage());
        }

        //try catch per gestire errori durante la lettura dei luoghi
        try {

            //esegue finchè ha eventi ha disposizione
            while (xmlreader.hasNext()) {

                //switcho gli eventi letti
                switch (xmlreader.getEventType()) {

                    //evento inzio lettura documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.printf(Costanti.INIZIO_FILE, Costanti.LETTURA, filename);
                        break;

                    //evento di inzio lettura elemento
                    case XMLStreamConstants.START_ELEMENT:
                        //switcho i vari tipi di elementi
                        switch (xmlreader.getLocalName()) {

                            //lettura tag <ristorante ...>
                            case Costanti.RISTORANTE:

                                //itero sul numero di attributi presenti nel tag ristorante e li leggo
                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {
                                    //switcho sui tipi di attributi
                                    switch (xmlreader.getAttributeLocalName(i)) {

                                        //attributo id
                                        case Costanti.N_POSTI:
                                            //leggo n_posti , visto che ritorna una stringa dal metodo faccio il cast
                                            int n_posti = Integer.parseInt(xmlreader.getAttributeValue(i));
                                            ristorante.setN_posti(n_posti);
                                            break;

                                        //attributo lavoro_persone
                                        case Costanti.LAVORO_PERSONE:
                                            //leggo lavoro_persona , visto che ritorna una stringa dal metodo faccio il cast
                                            int lavoro_persone = Integer.parseInt(xmlreader.getAttributeValue(i));
                                            ristorante.setLavoro_persona(lavoro_persone);
                                            break;
                                    }
                                }
                                break;

                            case Costanti.BEVANDA:

                                String nome_bev = null;
                                float qta_bev = 0;
                                String misura_bev = null;
                                float cons_procapite_bev = 0;

                                //itero sul numero di attributi presenti nel tag ristorante e li leggo
                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {
                                    //switcho sui tipi di attributi
                                    switch (xmlreader.getAttributeLocalName(i)) {

                                        //attributo nome_bev
                                        case Costanti.NOME:
                                            //leggo nome_bev
                                            nome_bev = xmlreader.getAttributeValue(i);
                                            break;

                                        //attributo quantita
                                        case Costanti.QTA:
                                            //leggo quantità , visto che ritorna una stringa dal metodo faccio il cast
                                            qta_bev = Float.parseFloat(xmlreader.getAttributeValue(i));
                                            break;

                                        //attributo misura_bev
                                        case Costanti.MISURA:
                                            //leggo misura_bev
                                            misura_bev = xmlreader.getAttributeValue(i);
                                            break;

                                        //attributo consumo procapite
                                        case Costanti.CONS_PROCAPITE:
                                            cons_procapite_bev = Float.parseFloat(xmlreader.getAttributeValue(i));
                                            break;
                                    }
                                }

                                Bevanda bevanda = new Bevanda(nome_bev, qta_bev, misura_bev, cons_procapite_bev);
                                bevande.add(bevanda);
                                break;

                            case Costanti.EXTRA:

                                String nome_extra = null;
                                float qta_extra = 0;
                                String misura_extra = null;
                                float cons_procapite_extra = 0;

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {

                                        case Costanti.NOME:
                                            nome_extra = xmlreader.getAttributeValue(i);
                                            break;

                                        case Costanti.QTA:
                                            qta_extra = Float.parseFloat(xmlreader.getAttributeValue(i));
                                            break;

                                        case Costanti.MISURA:
                                            misura_extra = xmlreader.getAttributeValue(i);
                                            break;

                                        case Costanti.CONS_PROCAPITE:
                                            cons_procapite_extra = Float.parseFloat(xmlreader.getAttributeValue(i));
                                            break;
                                    }
                                }

                                Extra extra = new Extra(nome_extra, qta_extra, misura_extra, cons_procapite_extra);
                                extras.add(extra);
                                break;

                            case Costanti.INGREDIENTE:

                                String nome_ingrediente = null;
                                float qta_ingrediente = 0;
                                String misura_ingrediente = null;

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {

                                        case Costanti.NOME:
                                            nome_ingrediente = xmlreader.getAttributeValue(i);
                                            break;

                                        case Costanti.QTA:
                                            qta_ingrediente = Float.parseFloat(xmlreader.getAttributeValue(i));
                                            break;

                                        case Costanti.MISURA:
                                            misura_ingrediente = xmlreader.getAttributeValue(i);
                                            break;

                                    }
                                }

                                Ingrediente ingrediente = new Ingrediente(nome_ingrediente, qta_ingrediente, misura_ingrediente);
                                ingredienti.add(ingrediente);
                                break;

                            case Costanti.PIATTO:

                                piatto = new Piatto(null, new ArrayList<LocalDate>(), 0, ricetta);
                                String nome_piatto = null;
                                ArrayList<LocalDate> disponibilita_piatto = new ArrayList<LocalDate>();

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {

                                        case Costanti.NOME:
                                            nome_piatto = xmlreader.getAttributeValue(i);
                                            piatto.setNome(nome_piatto);
                                            break;
                                    }
                                }

                                break;

                            case Costanti.DISPONIBILITA:

                                disponibilita_piatto = new ArrayList<LocalDate>();

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++){

                                    switch (xmlreader.getAttributeLocalName(i)){

                                        case Costanti.INIZIO:
                                            disponibilita_piatto.add(LocalDate.parse(xmlreader.getAttributeValue(i)));
                                            break;

                                        case Costanti.FINE:
                                            disponibilita_piatto.add(LocalDate.parse(xmlreader.getAttributeValue(i)));
                                            piatto.setDisponibilità(disponibilita_piatto);
                                            break;

                                    }
                                }
                                break;

                            case Costanti.RICETTA:

                                ingredienti = new ArrayList<Ingrediente>();
                                ricetta = new Ricetta(new ArrayList<Ingrediente>(), 0, 0);


                                int n_porzioni = 0;
                                float lavoro_porzione = 0;

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {

                                        case Costanti.N_PORZIONI:
                                            ricetta.setN_porzioni(Integer.parseInt(xmlreader.getAttributeValue(i)));
                                            break;

                                        case Costanti.LAVORO_PORZIONE:
                                            ricetta.setLavoro_porzione(Float.parseFloat(xmlreader.getAttributeValue(i)));
                                            break;
                                    }
                                }
                                break;

                            case Costanti.DISPONIBILITA_MENU:

                                ArrayList<LocalDate> disponibilita_menu = new ArrayList<LocalDate>();

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++){

                                    switch (xmlreader.getAttributeLocalName(i)){

                                        case Costanti.INIZIO:
                                            disponibilita_menu.add(LocalDate.parse(xmlreader.getAttributeValue(i)));
                                            break;

                                        case Costanti.FINE:
                                            disponibilita_menu.add(LocalDate.parse(xmlreader.getAttributeValue(i)));
                                            menu_tematico.aggiungiDisponibilita(disponibilita_menu);
                                            break;

                                    }
                                }
                                break;

                            case Costanti.PORTATA:

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++){

                                    switch (xmlreader.getAttributeLocalName(i)){

                                        case Costanti.NOME:
                                            String nome_portata = xmlreader.getAttributeValue(i);
                                            Piatto portata = menu_carta.getPiatto(nome_portata);
                                            menu_tematico.aggiungiPiatto(portata);
                                            break;
                                    }
                                }
                                break;

                            case Costanti.MENU_TEMATICO:

                                menu_tematico = new MenuTematico(new ArrayList<Piatto>(), 0, new ArrayList<LocalDate>());

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++){

                                    switch (xmlreader.getAttributeLocalName(i)){

                                        case Costanti.NOME:
                                            String nome_menu = xmlreader.getAttributeValue(i);
                                            menu_tematico.setNome(nome_menu);
                                            break;

                                        case Costanti.LAVORO_MENU:
                                            float lavoro_menu = Float.parseFloat(xmlreader.getAttributeValue(i));
                                            menu_tematico.setLavoro_menu(lavoro_menu);
                                            break;
                                    }
                                }
                                break;
                        }
                        //evento di fine lettura elemento
                    case XMLStreamConstants.END_ELEMENT:
                        //se siamo al tag di chiusura della città, aggiungo le connessioni di un singolo luogo al set di connessioni totali
                        switch (xmlreader.getLocalName()) {

                            case Costanti.MAGAZZINO:
                                magazzino.setBevande(bevande);
                                magazzino.setExtras(extras);
                                magazzino.setIngredienti(ingredienti);
                                break;

                            case Costanti.RICETTA:
                                ricetta.setIngredienti(ingredienti);
                                break;

                            case Costanti.PIATTO:
                                if(xmlreader.isEndElement()){
                                    piatto.setLavoro_piatto(ricetta.getLavoro_porzione());
                                    piatto.setRicetta(ricetta);
                                    menu_carta.aggiungiPiatto(piatto);
                                }
                                break;

                            case Costanti.MENU_TEMATICO:
                                if(xmlreader.isEndElement()){
                                    addetto_prenotazione.aggiungiMenu_tematico(menu_tematico);
                                }
                                break;
                        }
                        break;
                }
                xmlreader.next();
            }
        }catch (XMLStreamException e) {
            System.out.printf(Costanti.ERRORE_LETTURA_FILE, filename, e.getMessage());
        }

        addetto_prenotazione.aggiungiMenu_carta(menu_carta);

        return ristorante;
    }
}
