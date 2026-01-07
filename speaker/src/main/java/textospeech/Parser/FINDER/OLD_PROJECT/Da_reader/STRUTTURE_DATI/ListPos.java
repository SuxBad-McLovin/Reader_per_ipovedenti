package textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI;

import java.util.ArrayList;
import java.util.List;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;

public class ListPos 
{
    List<Pos> Lista = new ArrayList<Pos>();
  
    // costruttore non necessario
    
    // GETTER E SETTER
    public List<Pos> getLista() 
    {
        return Lista;
    }

    public void setLista(List<Pos> lista) 
    {
        Lista = lista;
    }

    // METODI

    // ora vogliamo aggiungere alla lista un elemento
    /**
     * Aggiunge un elemento Pos alla lista 
     * @param nonCodificante , elemento da aggiungere
     * 
     * @see Pos  
     */
    public void addPos(Pos nonCodificante)
    {   
        // per sicurezza -> se il tipo è NODATA significa che non è stato inizializzato
        if(nonCodificante.getTipo() == TypeEnum.NODATA)
        {
            // non fare niente
        }
        else
        {
            Lista.add(nonCodificante);
        }
        
    }

    // vogliamo unire tutti gli elementi trovati (in ogni riga), a formare un'unica lista -> maggiore maneggiabilità
    /**
     * Permette di aggiungere alla lista, una lista già inizializzata 
     * @param otherLista , altra lista ListPos
     */
    public void addLista(ListPos otherLista)
    {
        // Per sicurezza -> se la lista passata è nulla allora non fare niente
        if(otherLista == null)
        {
            // non fare nulla
        }
        else
        {
            this.Lista.addAll(otherLista.Lista);
        }
        
    }
}
