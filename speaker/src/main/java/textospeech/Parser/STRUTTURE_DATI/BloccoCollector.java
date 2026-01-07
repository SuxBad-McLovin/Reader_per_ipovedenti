package textospeech.Parser.STRUTTURE_DATI;

import java.util.ArrayList;
import java.util.List;

public class BloccoCollector 
{
    protected List<Blocco> Blocchi = new ArrayList<Blocco>();

    // GETTER E SETTER

    public List<Blocco> getBlocchi() {
        return Blocchi;
    }

    public void setBlocchi(List<Blocco> blocchi) {
        Blocchi = blocchi;
    }

    // METODI
    /**
     * Si occupa do aggiungere un oggetto di tipo Blocco alla collezione
     * @param blocco
     */
    public void addBlocco(Blocco blocco) 
    {
        if(blocco != null)
        {
            Blocchi.add(blocco);
        }
    }

    public boolean isEmpty() 
    {
        if(Blocchi.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
