package ProgettoSE.ServiziFileXML;

import ProgettoSE.Alimentari.*;
import ProgettoSE.Attori.*;
import ProgettoSE.Magazzino;
import ProgettoSE.Menu.*;
import ProgettoSE.Prenotazione;
import ProgettoSE.Ristorante;
import ProgettoSE.Costanti;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
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

            int n_posti = 0;
            int lavoro_persone = 0;

            //esegue finchè ha eventi ha disposizione
            while (xmlreader.hasNext()){

                //switcho gli eventi letti
                switch (xmlreader.getEventType()){

                    //evento inzio lettura documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.printf(Costanti.INIZIO_FILE, Costanti.LETTURA, filename);
                        break;

                    //evento di inzio lettura elemento
                    case XMLStreamConstants.START_ELEMENT:
                        //switcho i vari tipi di elementi
                        switch (xmlreader.getLocalName()){

                            //lettura tag <city ...>
                            case Costanti.RISTORANTE:
                                //creo gli attributi del ristorante
                                int n_posti = 0;
                                int lavoro_persone = 0;

                                ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
                                ArrayList<Menu> menu = new ArrayList<>();
                                AddettoPrenotazione addetto_prenotazione = new AddettoPrenotazione("Addetto prenotazione", prenotazioni, menu);

                                ArrayList<Alimento> bevande = new ArrayList<>();
                                ArrayList<Alimento> extras = new ArrayList<>();
                                ArrayList<Alimento> ingredienti = new ArrayList<>();
                                Magazzino magazzino = new Magazzino(bevande, extras, ingredienti);
                                ArrayList<Alimento> lista_spesa = new ArrayList<>();
                                Magazziniere magazziniere = new Magazziniere("Addetto magazzino", magazzino, lista_spesa);

                                //itero sul numero di attributi presenti nel tag ristorante e li leggo
                                for (int i=0; i<xmlreader.getAttributeCount(); i++){
                                    //switcho sui tipi di attributi
                                    switch (xmlreader.getAttributeLocalName(i)){
                                        //attributo id
                                        case Costanti.N_POSTI:
                                            //leggo n_posti , visto che ritorna una stringa dal metodo faccio il cast
                                            n_posti = Integer.parseInt(xmlreader.getAttributeValue(i));
                                            break;
                                        //attributo lavoro_persone
                                        case Costanti.LAVORO_PERSONE:
                                            //leggo lavoro_persona , visto che ritorna una stringa dal metodo faccio il cast
                                            lavoro_persone = Integer.parseInt(xmlreader.getAttributeValue(i));
                                            break;
                                        //attributo magazzino
                                        case Costanti.MAGAZZINO:
                                            for (int j=0; j<xmlreader.getAttributeCount(); j++){
                                                switch (xmlreader.getAttributeLocalName(j)){
                                                    //attributo bevande
                                                    case Costanti.BEVANDE:
                                                        for (int k=0; k<xmlreader.getAttributeCount(); k++){
                                                            switch (xmlreader.getAttributeLocalName(k)){
                                                                Bevanda bevanda = new Bevanda("f", 0, "gfs", 0);
                                                                //attributo nome
                                                                case Costanti.NOME:
                                                                    bevanda.setNome(xmlreader.getAttributeValue(k));
                                                                    break;
                                                                //attributo cognome
                                                                case Costanti.COGNOME:
                                                                    magazziniere.setCognome(xmlreader.getAttributeValue(k));
                                                                    break;
                                                            }
                                                        }
                                                        break;
                                                    //attributo cognome
                                                    case Costanti.COGNOME:
                                                        magazziniere.setCognome(xmlreader.getAttributeValue(j));
                                                        break;
                                                }
                                            }
                                            break;
                                        //atributo y
                                        case Costanti.Y:
                                            pos.setY(Integer.parseInt(xmlreader.getAttributeValue(i)));
                                            break;
                                        //attributo h
                                        case Costanti.H:
                                            pos.setH(Integer.parseInt(xmlreader.getAttributeValue(i)));
                                            break;
                                    }
                                }
                                //uscito dal for aggiungo la posizione al luogo
                                nuovo_luogo.setPosizione(pos);
                                //aggiungo il luogo alla lista dei luoghi letti
                                lista_luoghi.add(nuovo_luogo);
                                break;

                        }
                        break;

                    //evento di fine lettura elemento
                    case XMLStreamConstants.END_ELEMENT:
                        //se siamo al tag di chiusura della città, aggiungo le connessioni di un singolo luogo al set di connessioni totali
                        if(xmlreader.getLocalName().equals(Costanti.CITY)){
                            connessioni.put(nuovo_luogo.getId(), connessioni_luogo_singolo);
                        }
                        break;
                }
                xmlreader.next();
            }
            System.out.printf(Costanti.FINE_FILE, Costanti.LETTURA);
        } catch (XMLStreamException e) {
            System.out.printf(Costanti.ERRORE_LETTURA_FILE, filename, e.getMessage());
        }
        //creo il ristorante da ritornare
        Ristorante ristorante = new Ristorante(n_posti, lavoro_persona, addetto_prenotazioni, magazziniere);
        return ristorante;
    }
}
