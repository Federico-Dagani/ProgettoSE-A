package ProgettoSE;

import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Attori.Cliente;

import java.util.Date;
import java.util.HashMap;


public class Prenotazione  {
 private Cliente nominativo;
 private int n_coperti;
 private Date data;
 private HashMap<Prenotabile, Integer> coppie;
    //N.B. bisogna fare un controllo che la somma di tutti gli Integer sia >= n_coperti
 private HashMap<Bevanda,Float> cons_bevande;  //calcolo del cons_procapite delle bevande * n_coperti
 private HashMap<Extra,Float> cons_extra; //calcolo del cons_procapite degli extra * n_coperti





}
