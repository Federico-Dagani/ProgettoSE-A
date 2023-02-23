package ProgettoSE.ServiziFileXML;

import ProgettoSE.Alimentari.*;
import ProgettoSE.Attori.*;
import ProgettoSE.Menu.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LetturaFileXML {

    /**
     * <h3>Metodo per la lettura del file (contenente le città da leggere) che produce un grafo che contiene un Set di Luoghi e una Map indicante le connessioni che associa ad ogni Id un Set di Id </h3>
     * @param filename ovvero il nome del file
     * @return Grafo che identifica l'intera mappa
     */
    public static  leggiMappa(String filename){
        XMLInputFactory xmlif;
        XMLStreamReader xmlreader = null;
        //creo un Set di luoghi, e un Map che rappresenta le connessioni, che avrà come key l'id del luogo e come value le sue connessioni, ovvero un set di id degli altri luoghi
        Set<Luogo> lista_luoghi = new HashSet<>();
        Map<Integer, Set<Integer>> connessioni = new HashMap<>();
        Set<Integer> connessioni_luogo_singolo = new HashSet<>();


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
            //creo il nuovo_luogo e la posizione
            Luogo nuovo_luogo = new Luogo();
            Posizione pos = new Posizione();
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
                            case Costanti.CITY:
                                //quando entro nel tag city vuol dire che ho una nuova città da aggiungere, quindi resetto i parametri del luogo, pos
                                nuovo_luogo = new Luogo();
                                pos = new Posizione();
                                //azzero il set delle connessioni del nuovo luogo singolo
                                connessioni_luogo_singolo = new HashSet<>();
                                //itero sul numero di attributi presenti nel tag (anche se sono sempre 5)
                                for (int i=0; i<xmlreader.getAttributeCount(); i++){
                                    //switcho sui tipi di attributi
                                    switch (xmlreader.getAttributeLocalName(i)){
                                        //attributo id
                                        case Costanti.ID:
                                            //aggiungo l'id, visto che ritorna una stringa dal metodom faccio il cast
                                            nuovo_luogo.setId(Integer.parseInt(xmlreader.getAttributeValue(i)));
                                            break;
                                        //attributo name
                                        case Costanti.NAME:
                                            //aggiungo il nome
                                            nuovo_luogo.setNome(xmlreader.getAttributeValue(i));
                                            break;
                                        //attributo x
                                        case Costanti.X:
                                            pos.setX(Integer.parseInt(xmlreader.getAttributeValue(i)));
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

                            //lettura tag <link ...>
                            case Costanti.LINK:
                                //aggiungo l'id al set relativo alle connessioni di un singolo luogo
                                connessioni_luogo_singolo.add(Integer.parseInt(xmlreader.getAttributeValue(0)));
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
        //creo il grafo da ritornare
        Grafo grafo = new Grafo(lista_luoghi, connessioni);
        return grafo;
    }
}
