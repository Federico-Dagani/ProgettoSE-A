package ProgettoSE.Attori;

import ProgettoSE.Alimentari.Bevanda;
import ProgettoSE.Alimentari.Extra;
import ProgettoSE.Alimentari.Ingrediente;
import ProgettoSE.Costanti;
import ProgettoSE.Magazzino;
import ProgettoSE.Prenotazione;
import ProgettoSE.Ristorante;
import ProgettoSE.ServiziFileXML.LetturaFileXML;

import java.util.ArrayList;

public class Gestore extends Persona {

    public Gestore(String nome) {
        super(nome);
    }

    Ristorante ristorante;
/*
    public void inizializza() {
        //fare lettura da csv

        int n_posti = 100;
        int lavoro_persona = 0;
        ArrayList prenotazioni = new ArrayList<Prenotazione>();
        Ristorante ristorante = new Ristorante(n_posti, lavoro_persona, prenotazioni);
        Magazzino magazzino = new Magazzino();
        ArrayList<Bevanda> bevande = new ArrayList<>();
        ArrayList<Extra> extras = new ArrayList<>();
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //creare piatti
            Bevanda bevanda = new Bevanda("Coca Cola", 1.5f, "L", 0.5f);
            bevande.add(bevanda);
            Extra extra = new Extra("Cipolla", 0.5f, "Kg", 0.5f);
            extras.add(extra);
            Ingrediente ingrediente = new Ingrediente("Pomodoro", 0.5f, "Kg");
            ingredienti.add(ingrediente);
            //creare menu
            //creare prenotazion
        }

    }*/

    //METODI
    public void inizializzaRistorante() {
        LetturaFileXML letturaFileXML = new LetturaFileXML();
        ristorante = letturaFileXML.leggiRistorante(Costanti.FILE_RISTORANTE);
    }

}
