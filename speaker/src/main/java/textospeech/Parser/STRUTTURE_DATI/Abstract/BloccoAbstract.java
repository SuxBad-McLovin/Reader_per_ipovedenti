package textospeech.Parser.STRUTTURE_DATI.Abstract;

import textospeech.Parser.STRUTTURE_DATI.BloccoCollector;
import textospeech.Parser.STRUTTURE_DATI.Graffa;

public abstract class BloccoAbstract 
{
    // variabili precedenti già inizializzate (le consideremo come INIZIALI)
    protected Graffa inizio;
    protected Graffa fine;

    // COSTRUTTORE
    public BloccoAbstract(Graffa inizio, Graffa fine)
    {
        this.inizio = inizio;
        this.fine = fine;
    }

    // GETTER E SETTER
    public Graffa getInizio() 
    {
        return inizio;
    }

    public void setInizio(Graffa inizio) 
    {
        this.inizio = inizio;
    }

    public Graffa getFine() 
    {
        return fine;
    }

    public void setFine(Graffa fine) 
    {
        this.fine = fine;
    }  
    
    // METODI
    protected abstract void addBlocco(BloccoCollector listaBlocchi);
}
