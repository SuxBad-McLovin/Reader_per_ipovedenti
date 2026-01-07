package textospeech.Parser.STRUTTURE_DATI;

import textospeech.Parser.STRUTTURE_DATI.Abstract.BloccoAbstract;

public class Blocco extends BloccoAbstract
{
    // VEDIAMO SE AGGIUNGERE ALTRE VARIABILI


    // COSTRUTTORE DEFAULT
    public Blocco (Graffa inizio, Graffa fine)
    {
        super(inizio, fine);
    }

    //getter e setter
    @Override
    public Graffa getFine() 
    {
        return super.getFine();
    }

    @Override
    public Graffa getInizio() 
    {
        return super.getInizio();
    }

    @Override
    public void setFine(Graffa fine) 
    {
        super.setFine(fine);
    }

    @Override
    public void setInizio(Graffa inizio) 
    {
        super.setInizio(inizio);
    }

    /**
     * Si occupa di aggiungere l'elemento corrente alla lista
     * @param lista , la lista in cui viene salvato l'oggetto corrente
     */
    @Override
    protected void addBlocco(BloccoCollector lista)
    {
        lista.addBlocco(this);
    }
}
