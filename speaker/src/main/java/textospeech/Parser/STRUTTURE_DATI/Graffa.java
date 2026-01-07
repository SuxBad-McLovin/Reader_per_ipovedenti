package textospeech.Parser.STRUTTURE_DATI;

import textospeech.Parser.STRUTTURE_DATI.Abstract.RigaAbstract;

public class Graffa extends RigaAbstract
{
    //variabili già inizializzate
    
    public Graffa()
    {
        super();
    }

    public Graffa(int riga,int colonna)
    {
        super(riga, colonna);
    }

    public Graffa(int riga,int colonna,boolean isOpen)
    {
        super(riga, colonna, isOpen);
    }

    /**
     * Aggiunge l'elemento corrente alla lista
     * @param Collector , la lista in cui aggiungere gli elementi di tipo graffa
     */
    @Override
    public void AddGraffa(GraffaCollector Collector)
    {
        // controllo preliminare (lo aggiungo solo se è codificante)
        if(this.isCode && (this != null))
        {
            Collector.AddGraffa(this);    //<--- richiamo con il metodo del collector
        }

        /*
        // VALUTARE SE TENERE 
        else
        {
            throw new IllegalStateException("L'oggetto per poter essere aggiunto ad una lista deve essere codificante");
        }
        */
    }

    public boolean isEmpty() 
    {
        if(this.riga == 0 || this.colonna == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
