package textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI;

import java.util.ArrayList;
import java.util.List;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;


/** 
 * STRUTTURA DATI
 * Questa classe contiene le informazioni per costruire una struttura dati di tipo: LISTA INTERVALLI.
 * LISTA INTERVALLI == Arraylist<Intervals>.
 * DIPENDENZE = java.util.ArrayList;
*/
public class List_intervals 
{
    private List<Intervals> Lista = new ArrayList<Intervals>();

    //COSTRUTTORE
    public List_intervals()    // <--- se non gli passo niente, allora mi crea una nuova lista 
    {
        this.Lista = new ArrayList<Intervals>();
    }

    public List_intervals(Intervals Lista) // <--- se gli passo un intervallo me lo aggiunge ad una lista già inizializzata
    {
        this.Lista.add(Lista); 
    }

    //GETTER
    /**
     * Si tratta di un GETTER
     * @return Lista 
     */
    public List<Intervals> getLista()
    {
        return Lista;
    }

    //SETTER
    /**
     * Si tratta di un SETTER per la classe List_intervals
     * @param intervallo una List_intervals già inizializzata
     */
    public void setList_intervals(List<Intervals> intervallo) 
    {
        Lista = intervallo;
    }

    // METODO --->aggiunge un elemento alla lista
    /**
     * Aggiunge elementi di tipo Intervals ad List_intervals
     * @param intervallo da aggiungere alla List_intervals
     */
    public void AddIntervallo(Intervals intervallo)
    {
        if(intervallo != null && intervallo.getType() != TypeEnum.NODATA)
        {
            Intervals A = new Intervals();  //<--- se non facessi una copia, aggiungerei solo delle reference il che è rischioso
            // NOTA
            // tutti gli oggetti sono passati come reference
            // solo le variabili "base" vengono copiate (tipo le enum)
            A = Intervals.CopyOf(intervallo);
            
            this.Lista.add(A);
        }

        // ALTRIMENTI NON FARE NIENTE
    }

    //TOSTRING
    @Override
    public String toString() 
    {
        String A="";
        for(int i=0;i<Lista.size();i++)
        {
            A = A + "INTERVALLO numero: "+ i + Lista.get(i)+"\n"; 
        }

        return A;
    }

    public boolean isEmpty() 
    {
        if(Lista.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
