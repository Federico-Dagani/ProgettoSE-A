package ProgettoSE.ServiziFileXML;

import it.unibs.arnaldo.rovinePerdute.Costanti;
import it.unibs.arnaldo.rovinePerdute.Veicolo;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;

public class ScritturaFileXML {

    public static void scriviPercorso(Veicolo veicolo_metztli, Veicolo veicolo_tonathium){
        //inizializzazione
        XMLOutputFactory xmlof;
        XMLStreamWriter xmlw = null;
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(Costanti.FILE_PERCORSO), Costanti.ENCODING);
            xmlw.writeStartDocument(Costanti.ENCODING, Costanti.VERSION);
        } catch (Exception e) {
            System.out.printf(Costanti.ERRORE_INIZIALIZZAZIONE_WRITER, e.getMessage());
        }
        //avviso l'utente dell'inzio della strittura del file
        System.out.printf(Costanti.INIZIO_FILE, Costanti.SCRITTURA, Costanti.FILE_PERCORSO);
        try{
            xmlw.writeStartElement(Costanti.ROUTES);
                scriviRoute(veicolo_tonathium, xmlw);
                scriviRoute(veicolo_metztli, xmlw);
            xmlw.writeEndElement();//chiusura tag routes

            xmlw.writeEndDocument(); // scrittura della fine del documento
            xmlw.flush(); // svuota il buffer e procede alla scrittura
            xmlw.close(); // chiusura del documento e delle risorse impiegate
            System.out.printf(Costanti.FINE_FILE, Costanti.SCRITTURA);
        }catch(XMLStreamException e){
            System.out.printf(Costanti.ERRORE_SCRITTURA_FILE, e.getMessage());
        }
    }

    /**
     * <h3>Metodo per la Scrittura del percorso</h3>
     * @param veicolo ovvero il veicolo che contiene il suo percorso e la sua tipologia
     * @param xmlw
     */
    private static void scriviRoute(Veicolo veicolo, XMLStreamWriter xmlw){
        try {
            xmlw.writeStartElement(Costanti.ROUTE);
                //scrivo gli attributi del tag<route...>
                xmlw.writeAttribute(Costanti.TEAM, veicolo.getTipologia());
                xmlw.writeAttribute(Costanti.COST, String.valueOf(veicolo.getFuel()));
                xmlw.writeAttribute(Costanti.CITIES, String.valueOf(veicolo.getRoute().size()));
                    //ora scrivo il percorso
                    for (int i=0; i<veicolo.getRoute().size(); i++){
                        xmlw.writeStartElement(Costanti.CITY);
                            //scrivo i suoi attributi del tag<city...>
                            xmlw.writeAttribute(Costanti.ID, String.valueOf(veicolo.getRoute().get(i).getId()));
                            xmlw.writeAttribute(Costanti.NAME, String.valueOf(veicolo.getRoute().get(i).getNome()));
                        xmlw.writeEndElement();
                    }
            xmlw.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
