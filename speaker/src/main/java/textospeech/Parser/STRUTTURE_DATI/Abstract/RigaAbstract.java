package textospeech.Parser.STRUTTURE_DATI.Abstract;

import textospeech.Parser.STRUTTURE_DATI.GraffaCollector;

public abstract class RigaAbstract 
{
    protected int riga;
    protected int colonna;
    protected boolean isCode;     //<--- variabile per vedere se è codice o commento
    protected boolean isOpenGraph;

    // Costruttore

    public RigaAbstract()
    {
        this.riga = 0;
        this.colonna = 0;
        this.isCode = true;       //<--- di default impostato su true
        this.isOpenGraph = true;  //<--- di default impostato su true
    }

    // Costruttore
    public RigaAbstract (int riga,int colonna)
    {
        this.riga = riga;
        this.colonna = colonna;
        this.isCode = true;       //<--- di default impostato su true      
        this.isOpenGraph = true;  //<--- di default impostato su true
    }

    public RigaAbstract (int riga,int colonna,boolean isOpen)
    {
        this.riga = riga;
        this.colonna = colonna;
        this.isCode = true;       //<--- di default impostato su true      
        this.isOpenGraph = isOpen;  //<--- di default impostato su true
    }

    // SETTER E GETTER -> li condivido con tutti gli altri

    public int getRiga() 
    {
        return riga;
    }

    public void setRiga(int riga) 
    {
        this.riga = riga;
    }

    public int getColonna() 
    {
        return colonna;
    }

    public void setColonna(int colonna) 
    {
        this.colonna = colonna;
    }

    public boolean getIsCode() 
    {
        return isCode;
    }

    public void setIsCode(boolean isCode) 
    {
        this.isCode = isCode;
    }
    
    public boolean getIsOpen()
    {
        return isOpenGraph;
    }

    public void setIsOpen(boolean isOpen)
    {
        this.isOpenGraph = isOpen;
    }

    abstract protected void AddGraffa(GraffaCollector Collector);
    
}
