package ProgettoSE.Utility;

import ProgettoSE.Model.Alimentari.*;
import ProgettoSE.Model.Attori.AddettoPrenotazione.AddettoPrenotazione;
import ProgettoSE.Model.Attori.AddettoPrenotazione.Prenotazione;
import ProgettoSE.Model.Attori.Gestore.Ristorante;
import ProgettoSE.Model.Attori.Magazziniere.Magazziniere;
import ProgettoSE.Model.Attori.Magazziniere.Magazzino;
import ProgettoSE.Model.Produzione.Menu.*;
import ProgettoSE.Model.Produzione.Piatto;
import ProgettoSE.Model.Produzione.Prenotabile;
import ProgettoSE.Model.Produzione.Ricetta;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.*;

public class LetturaFileXML {

    /**
     * <h2>Metodo per la lettura del fileXML che istanzia un oggetto Ristorante completo</h2>
     * <b>Precondizione: </b>Il nome del file non può essere nullo o vuoto<br>
     * <b>Postcondizione: </b>Il risorante non può essere nullo<br>
     * @param filename ovvero il nome del file
     * @return l'oggetto Ristorante completo
     * @throws IllegalArgumentException se il nome del file è nullo o vuoto
     */
    public Ristorante leggiRistorante(String filename) {
        //precondizione: il nome del file non può essere nullo o vuoto
        if(filename == null || filename.equals("")) throw new IllegalArgumentException("Il nome del file non può essere nullo o vuoto");

        XMLInputFactory xmlif;
        XMLStreamReader xmlreader = null;

        //attributi magazziniere
        ArrayList<Alimento> bevande = new ArrayList<>();
        ArrayList<Alimento> ingredienti = new ArrayList<>();
        ArrayList<Alimento> extras = new ArrayList<>();
        Magazzino magazzino = new Magazzino(bevande, extras, ingredienti);
        ArrayList<Alimento> lista_spesa = new ArrayList<>();

        //attributi addetto prenotazione
        ArrayList<Prenotabile> menu = new ArrayList<>();
        ArrayList<Prenotazione> prenotazioni = new ArrayList<>();

        //attributi ristorante e creazione oggetto ristorante
        AddettoPrenotazione addetto_prenotazione = new AddettoPrenotazione(null, prenotazioni, menu);
        Magazziniere magazziniere = new Magazziniere(null, magazzino, lista_spesa);
        Ristorante ristorante = new Ristorante(0, 0, addetto_prenotazione, magazziniere);

        MenuCarta menu_carta = new MenuCarta(new ArrayList<>());
        MenuTematico menu_tematico = new MenuTematico("", new ArrayList<>(), 0, new ArrayList<>());
        Ricetta ricetta = new Ricetta(new ArrayList<>(), 0, 0);
        Piatto piatto = new Piatto(null, new ArrayList<>(), 0, ricetta);

        //try catch per gestire eventuali eccezioni durante l'inizializzazione
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlreader = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
        } catch (Exception e) {
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

                            case Costanti.MAGAZZINIERE:
                                //leggo il nome del magazziniere
                                ristorante.getMagazziniere().setNome(xmlreader.getAttributeValue(0));
                                break;

                            case Costanti.BEVANDA:
                                //creo un oggetto di tipo bevanda e lo aggiungo alla lista bevande
                                Bevanda bevanda = (Bevanda) creaAlimento(xmlreader, Costanti.BEVANDA);
                                bevande.add(bevanda);
                                break;

                            case Costanti.EXTRA:
                                //creo un oggetto di tipo extra e lo aggiungo alla lista extras
                                Extra extra = (Extra) creaAlimento(xmlreader, Costanti.EXTRA);
                                extras.add(extra);
                                break;

                            case Costanti.INGREDIENTE:
                                //creo un oggetto di tipo ingrediente e lo aggiungo alla lista ingredienti
                                Ingrediente ingrediente = (Ingrediente) creaAlimento(xmlreader, Costanti.INGREDIENTE);
                                ingredienti.add(ingrediente);
                                break;

                            case Costanti.ADDETTO_PRENOTAZIONE:
                                //leggo il nome dell'addetto prenotazione
                                ristorante.getAddettoPrenotazione().setNome(xmlreader.getAttributeValue(0));
                                break;

                            case Costanti.PIATTO:
                                //creo un oggetto di tipo piatto e lo aggiungo alla lista menu
                                piatto = new Piatto(null, new ArrayList<>(), 0, ricetta);
                                String nome_piatto;

                                //Questo ciclo for con il seguente switch potrebbe essere eliminato (visto che c'è un solo attributo)
                                //Ho deciso di lasciarlo per le implementazioni future di più attributi di un piatto
                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {
                                        //leggo il nome del piatto
                                        case Costanti.NOME:
                                            nome_piatto = xmlreader.getAttributeValue(i);
                                            piatto.setNome(nome_piatto);
                                            break;
                                    }
                                }
                                break;

                            case Costanti.DISPONIBILITA:
                                //leggo le disponibilità del piatto e le aggiungo alla lista disponibilità del piatto
                                piatto.aggiungiDisponibilita(creaDisponibilita(xmlreader));
                                break;

                            case Costanti.RICETTA:
                                //leggo la ricetta del piatto
                                ingredienti = new ArrayList<>();
                                ricetta = new Ricetta(new ArrayList<>(), 0, 0);

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {
                                        //leggo le porzioni della ricetta
                                        case Costanti.N_PORZIONI:
                                            ricetta.setN_porzioni(Integer.parseInt(xmlreader.getAttributeValue(i)));
                                            break;
                                        //leggo il carico di lavoro per porzione della ricetta
                                        case Costanti.LAVORO_PORZIONE:
                                            ricetta.setLavoro_porzione(Float.parseFloat(xmlreader.getAttributeValue(i)));
                                            break;
                                    }
                                }
                                break;

                            case Costanti.DISPONIBILITA_MENU:
                                //leggo le disponibilità del menu e le aggiungo alla lista disponibilità del menu
                                menu_tematico.aggiungiDisponibilita(creaDisponibilita(xmlreader));
                                break;

                            case Costanti.PORTATA:
                                //leggo le portate del menu e le aggiungo alla lista di piatti del menu
                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {
                                        //leggo il nome della portata, la cerco nella lista dei piatti del ristorante e la aggiungo alla lista di piatti del menu tematico
                                        case Costanti.NOME:
                                            String nome_portata = xmlreader.getAttributeValue(i);
                                            Piatto portata = menu_carta.getPiatto(nome_portata);
                                            menu_tematico.aggiungiPiatto(portata);
                                            break;
                                    }
                                }
                                break;

                            case Costanti.MENU_TEMATICO:
                                //creo un oggetto di tipo menu tematico
                                menu_tematico = new MenuTematico("", new ArrayList<>(), 0, new ArrayList<>());

                                for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

                                    switch (xmlreader.getAttributeLocalName(i)) {
                                        //leggo il nome del menu tematico
                                        case Costanti.NOME:
                                            String nome_menu = xmlreader.getAttributeValue(i);
                                            menu_tematico.setNome(nome_menu);
                                            break;
                                        //leggo il carico di lavoro del menu tematico
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
                        //se siamo al tag di chiusura aggiungo tutte le cose lette al magazzino e al menu
                        switch (xmlreader.getLocalName()) {
                            //aggiungo le bevande, gli extras e gli ingredienti al magazzino
                            case Costanti.MAGAZZINO:
                                magazzino.setBevande(bevande);
                                magazzino.setExtras(extras);
                                magazzino.setIngredienti(ingredienti);
                                break;
                            //chiudo la ricetta
                            case Costanti.RICETTA:
                                ricetta.setIngredienti(ingredienti);
                                break;
                            //aggiungo il piatto al menu alla carta
                            case Costanti.PIATTO:
                                //controllo aggiuntivo necessario per evitare di aggiungere un piatto vuoto alla lista dei piatti del menu
                                if (xmlreader.isEndElement()) {
                                    piatto.setLavoro_piatto(ricetta.getLavoro_porzione());
                                    piatto.setRicetta(ricetta);
                                    menu_carta.aggiungiPiatto(piatto);
                                }
                                break;
                            //aggiungo il menu tematico al menu complessivo
                            case Costanti.MENU_TEMATICO:
                                if (xmlreader.isEndElement()) {
                                        addetto_prenotazione.aggiungiMenu_tematico(menu_tematico);
                                }
                                break;
                        }
                        break;
                }
                xmlreader.next();
            }
        } catch (XMLStreamException e) {
            //se si verifica un errore di lettura del file stampo un messaggio di errore
            System.out.printf(Costanti.ERRORE_LETTURA_FILE, filename, e.getMessage());
        }
        //aggiungo il menu alla carta al menu complessivo
        addetto_prenotazione.aggiungiMenu_carta(menu_carta);

        //postcondizione: il ristorante è stato inizializzato con i dati letti dal file XML
        assert ristorante != null;
        //ritorno il ristorante così inizializzato
        return ristorante;
    }

    /**
     * <h2>Metodo che utilizza il polimorfismo per leggere da file XML i dati di un Alimento qualsiasi</h2>
     * <b>Precondizione: </b>xmlreader deve essere un file XML valido
     * @param xmlreader File XML da cui leggere
     * @param tipologia Tipologia di alimento da leggere
     * @return Alimento letto con attributi inizializzati o null se non è un alimento riconosciuto
     * @throws IllegalArgumentException Se xmlreader non è un file XML valido
     */
    public Alimento creaAlimento(XMLStreamReader xmlreader, String tipologia) {
        //precondizione: xmlreader deve essere un file XML valido
        if(xmlreader == null) throw new IllegalArgumentException("xmlreader non valido");

        //inizializzo le variabili che rappresentano gli attributi dei diversi alimenti
        String nome = null;
        float qta = 0;
        String misura = null;
        float cons_procapite = 0;

        //itero sul numero di attributi presenti nel tag ristorante e li leggo
        for (int i = 0; i < xmlreader.getAttributeCount(); i++) {
            //switcho sui tipi di attributi che posso trovare in un alimento
            switch (xmlreader.getAttributeLocalName(i)) {

                case Costanti.NOME:
                    nome = xmlreader.getAttributeValue(i);
                    break;

                case Costanti.QTA:
                    //visto che il metodo getAttributevalue() ritorna una stringa, utilizzo il parse della classe Float
                    qta = Float.parseFloat(xmlreader.getAttributeValue(i));
                    break;

                case Costanti.MISURA:
                    misura = xmlreader.getAttributeValue(i);
                    break;

                case Costanti.CONS_PROCAPITE:
                    cons_procapite = Float.parseFloat(xmlreader.getAttributeValue(i));
                    break;

            }
        }
        //Le varie tipologie di Alimenti possibili nel ristorante
        switch (tipologia) {
            //Attualmente sono 3: Ingrediente, Bevanda e Extra
            case Costanti.INGREDIENTE:
                return new Ingrediente(nome, qta, misura);

            case Costanti.BEVANDA:
                return new Bevanda(nome, qta, misura, cons_procapite);

            case Costanti.EXTRA:
                return new Extra(nome, qta, misura, cons_procapite);
        }
        //altrimenti non ho trovato nessun alimento valido e ritorno null
        return null;
    }

    /**
     * <h2>Metodo che legge da file XML i dati relativi alla disponibilità</h2>
     * <b>Precondizione: </b>xmlreader deve essere un file XML valido<br>
     * <b>Postcondizione: </b> la disponibilità deve essere un numero pari di date e non deve essere vuota<br>
     * @param xmlreader File XML da cui leggere
     * @return ArrayList di LocalDate che contiene le date di inizio e fine disponibilità a coppie<br>(esempio: <i>inizio1,fine1,inizio2,fine2,inizio3,fine3</i>)
     * @throws IllegalArgumentException Se xmlreader non è un file XML valido
     */
    public ArrayList<LocalDate> creaDisponibilita(XMLStreamReader xmlreader) {
        //precondizione: xmlreader deve essere un file XML valido
        if(xmlreader == null) throw new IllegalArgumentException("xmlreader non valido");
        ArrayList<LocalDate> disponibilita = new ArrayList<>();
        for (int i = 0; i < xmlreader.getAttributeCount(); i++) {

            switch (xmlreader.getAttributeLocalName(i)) {
                //leggo il tag INIZIO e aggiungo la data di inizio disponibilità
                case Costanti.INIZIO:
                    disponibilita.add(LocalDate.parse(xmlreader.getAttributeValue(i)));
                    break;
                //leggo il tag FINE e aggiungo la data di fine disponibilità
                case Costanti.FINE:
                    disponibilita.add(LocalDate.parse(xmlreader.getAttributeValue(i)));
                    break;
            }
        }
        //postcondizione: la disponibilità deve essere un numero pari di date e non deve essere vuota
        assert disponibilita.size() % 2 == 0 && disponibilita.size() > 0 : "Disponibilità non valida";
        return disponibilita;
    }
}
