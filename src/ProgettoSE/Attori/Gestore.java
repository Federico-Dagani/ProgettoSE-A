package ProgettoSE.Attori;

import ProgettoSE.*;
import ProgettoSE.Menu.MenuTematico;
import ProgettoSE.ServiziFileXML.LetturaFileXML;

import java.time.LocalDate;
import java.util.ArrayList;

public class Gestore extends Persona {
    //ATTRIBUTI
    private Ristorante ristorante;

    //METODI
    public Gestore(String nome, Ristorante ristorante) {
        super(nome);
        this.ristorante = ristorante;
    }
    public Ristorante getRistorante() {
        return ristorante;
    }
    public void setRistorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }
    /**
     * <h2>Metodo che inizializza il ristorante leggendo i dati dal file XML</h2>
     * @return void
     *
     */
    public String inizializzaRistorante() {
        LetturaFileXML letturaFileXML = new LetturaFileXML();
        ristorante = letturaFileXML.leggiRistorante(Costanti.FILE_RISTORANTE);
        String messaggio = controllaMenu();
        messaggio += controllaRicette();
        return messaggio;
    }

    //metodo che passato un menu tematic (che venga appena aggiunto o che gli arrivi da xml) controlla che abbia lavoro< 4/3 del lavoro totale
    // e se non soddisfa la condizione lo elimina e ritorna il messaggio che deve essere stampato(menu aggiunto o menu scartato)
    public String controllaMenu() {
        String messaggio = "";
        ArrayList<Prenotabile> menu_da_eliminare = new ArrayList<>();
        //ciclo i menu_tematici del ristorante in modo da controllare che il lavoro sia < 4/3 del lavoro totale del ristorante (se non lo è lo elimino)
        for (Prenotabile menu_tematico : ristorante.getAddettoPrenotazione().getMenu()) {
            if (menu_tematico instanceof MenuTematico) {
                //controllo se il lavoro è < 4/3 del lavoro totale del ristorante (se non lo è lo elimino)
                if (((MenuTematico) menu_tematico).getLavoro_menu() > ristorante.getLavoro_persona() * 4 / 3) {
                    menu_da_eliminare.add(menu_tematico);
                    messaggio += "\nIl menu tematico " + menu_tematico.getNome() + " è stato scartato perchè il lavoro richiesto è maggiore del 4/3 del lavoro totale del ristorante";
                }
                //controllo
                if (!disponibilitaPiattiCorrette((MenuTematico) menu_tematico)){
                    menu_da_eliminare.add(menu_tematico);
                    messaggio += "\nIl menu tematico " + menu_tematico.getNome() + " è stato scartato perchè contiene piatti non disponibili nelle date del menu";
                }
            }
        }
        menu_da_eliminare.forEach(menu_tematico -> ristorante.getAddettoPrenotazione().getMenu().remove(menu_tematico));
        return messaggio;
    }


    public boolean disponibilitaPiattiCorrette(MenuTematico menu_tematico) {
        for(Piatto piatto : menu_tematico.getPiatti_menu()){
            for(int i=0; i<menu_tematico.getDisponibilità().size(); i+=2){
                if(!piattoDisponibileInData(piatto, menu_tematico.getDisponibilità().get(i), menu_tematico.getDisponibilità().get(i+1)))
                    return false;
            }
        }
        return true;
    }

    private boolean piattoDisponibileInData(Piatto piatto, LocalDate inizio, LocalDate fine){
        for(int i=0; i<piatto.getDisponibilità().size(); i+=2){
            //se trovo almeno una disponibilita del piatto che copre questo intervallo (ovvero una parte della disponibilità del menu tematico) aòòpra ritorno true
            if(data1AnticipaData2(piatto.getDisponibilità().get(i), inizio) && data1AnticipaData2(fine, piatto.getDisponibilità().get(i+1)))
                return true;
        }
        return false;
    }

    private boolean data1AnticipaData2(LocalDate data1, LocalDate data2) {
        if(data1.getMonthValue() < data2.getMonthValue())
            return true;
        else if(data1.getMonthValue() == data2.getMonthValue() && data1.getDayOfMonth() <= data2.getDayOfMonth())
            return true;
        else
            return false;
    }

    public String controllaRicette() {
        String messaggio = "";
        ArrayList<Prenotabile> piatti_da_eliminare = new ArrayList<>();
        //ciclo le ricette del ristorante in modo da controllare che
        for (Prenotabile piatto : ristorante.getAddettoPrenotazione().getMenu()) {
            if (piatto instanceof Piatto) {
                if (((Piatto) piatto).getRicetta().getLavoro_porzione() >= ristorante.getLavoro_persona()) {
                    piatti_da_eliminare.add(piatto);
                    messaggio += "\nIl piatto " + piatto.getNome() + " è stato scartato perchè il lavoro richiesto è maggiore del lavoro totale del ristorante";
                }
                /*
                if (!seDisponibilitaCorrette((piatto).getDisponibilità())) {
                    piatti_da_eliminare.add(piatto);
                    messaggio += "\nIl piatto " + piatto.getNome() + " è stato scartato perchè la disponibilità non è valida";
                }
                */
            }
        }
        piatti_da_eliminare.forEach(piatto -> ristorante.getAddettoPrenotazione().getMenu().remove(piatto));
        return messaggio;
    }


    /**
     * <h2>Metodo che comunica al magazziniere la lista della spesa e al cuoco la lista dei piatti da cucinare
     * @param data_attuale data attuale
     * @throws IllegalArgumentException se la data non è valida
     * @return String messaggio
     */
    public String comunica(LocalDate data_attuale) {
        //precondizione data_attuale non null
        if(data_attuale == null) throw new IllegalArgumentException("Data non valida");
        Prenotazione prenotazione_del_giorno = ristorante.getAddettoPrenotazione().unisciPrenotazioni(ristorante.getAddettoPrenotazione().filtraPrenotazioniPerData(data_attuale));
        ristorante.getMagazziniere().creaListaSpesa(prenotazione_del_giorno);
        String messaggio = ristorante.getMagazziniere().aggiungiSpesaInMagazzino();
        //prob
        ristorante.getMagazziniere().portaInCucina(prenotazione_del_giorno);
        ristorante.getAddettoPrenotazione().rimuoviPrenotazioniVecchie(data_attuale);
        //postcondizione messaggio non null
        assert messaggio != null;
        return messaggio;
    }

}
