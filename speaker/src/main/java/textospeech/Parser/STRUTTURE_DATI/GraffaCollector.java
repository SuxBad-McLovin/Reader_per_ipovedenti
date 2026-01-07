package textospeech.Parser.STRUTTURE_DATI;

import java.util.ArrayList;
import java.util.List;

public class GraffaCollector
{
    protected List<Graffa> lista = new ArrayList<Graffa>();

    // GETTER E SETTER

    public List<Graffa> getLista() {
        return lista;
    }

    public void setlista(List<Graffa> lista) {
        this.lista = lista;
    }

    // METODI

    // mi serve per usarlo nella classe Graffa
    protected void AddGraffa(Graffa graffa)
    {
        if ((graffa != null) && (graffa.getIsCode())) 
        {
            lista.add(graffa);  //<--- il metodo delle liste
        }
        /* 
        // VALUTARE SE TENERE
        else
        {
            throw new NullPointerException("L'oggetto non può essere null!");
        }
        */
    }

    //mi accerto che la lista sia vuota
    public boolean isEmpty() 
    {
        if(lista.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // copio
    public List<Graffa> CopyOfGraffaCollector(GraffaCollector lista)
    {
        List<Graffa> A = new ArrayList<Graffa>();
        A = lista.getLista();
        return A;
    }

    public void AddLista(GraffaCollector collezione)
    {
        if(collezione == null || collezione.isEmpty())
        {

        }
        else
        {
            this.lista.addAll(CopyOfGraffaCollector(collezione));
        }
    }
}
