package textospeech.Parser.FINDER.OLD_PROJECT.Da_reader;

// in questa classe agisco per ottenere informazioni

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.Intervals;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.ListPos;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.List_intervals;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.Pos;
import textospeech.Parser.NORMALIZZAZIONE.Normalizzazione;
/**
 * CLASSE.
 * Principale, contiene le funzioni per identificare commenti,stringhe e char
 * DIPENDENZE = java.io.BufferedReader,java.io.IOException,java.util.ArrayList.
 * DIPENDENZE PACKAGE CORRENTE = Line_type,List_intervals,SortbyPosition,Container_riga
 * 
 */
public class Gestione_stringhe 
{
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CODICE VERO E PROPRIO
    /**
     * Ritorna tutti gli elementi non codificanti trovati e la relativa posizione.
     * 
     * ATTENZIONE!
     * Nessun controllo viene effettuato per verificare se un carattere è effettivamente composto da un solo elemento
     * @param filePath , la path del file da analizzare
     * @return List_intervals , composta dagli elementi non codificanti (non contiene quelli mascherati da altri)
     * 
     * @see List_intervals
     */
    public static List_intervals BuildIntervals(String filePath)  // <--- volendo si potrebbe passare come argomento il reader, si vedrà
    {
        // oggetti
        Gestione_stringhe A = new Gestione_stringhe();
        ListPos ListaDiPosizioni = new ListPos();
        
        //funzioni
        ListaDiPosizioni = A.AllNonCoding(filePath);
        
        // per abbreviare
        if(ListaDiPosizioni == null)
        {
            return null;
        }
        if(ListaDiPosizioni.getLista().isEmpty())
        {
            return null;
        }

        // l'output
        return A.BuildNonCoding(ListaDiPosizioni);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //UTILITY

    /////////////////////////////
    // TROVO GLI ELEMENTI NON CODIFICANTI
    /**
     * Restituisce tutti gli elementi non codificanti in una linea 
     * @param parola , la linea analizzata
     * @param numeroRiga , la riga in cui si trova
     * @return ListPos , lista di posizioni 
     * 
     * @see ListPos
    */
    protected ListPos NonCodingPerLine(String parola,int numeroRiga)
    {
        // per sicurezza
        if(parola == null || numeroRiga < 0)
        {
            return null;
        }

        // VARIABILI
        ListPos lista = new ListPos();
        int contatore = 0;

        // FLAG
        boolean isFakeStringFlag = false;       //<--- aggiunto dopo

        // PASSIAMO IL NUMERO DI RIGA CHE SIA LEGGIBILE DAL UTENTE (ovvero che parte da 1)
        numeroRiga = numeroRiga + 1;

        ///////////////////////////////////////

        // CASO : stringa composta da un'unico carattere
        if(parola.length() == 1)
        {
            Pos notCharacter = new Pos();
            if(parola == "\"")
            {
                notCharacter.setRig(numeroRiga);
                notCharacter.setCol(1);
                notCharacter.setTipo(TypeEnum.STRING);
            }

            if(parola == "'")
            {
                notCharacter.setRig(numeroRiga);
                notCharacter.setCol(1);
                notCharacter.setTipo(TypeEnum.CHAR);
            }

            //se l'elemento è stato inizializzato 
            if(notCharacter.getTipo() != TypeEnum.NODATA)
            {
                notCharacter.Add(lista);        //<--- aggiungerlo alla lista
                return lista;
            }

            return null;
        }

        // CASO : la stringa è più grande di un singolo carattere
    
        while(contatore < parola.length())
        {
            String pattern = "";
            int truePositionStringChar = 0;     //<--- questa variabile mi serve per trovare la corretta posizione di stringhe e char
        
            /*
            NOTA : un controllo sull'underflow è inutile
             */

            // CONTROLLO OVERFLOW
            if((contatore < parola.length()-1))
            {
                pattern = parola.substring(contatore, contatore+2);     //<--- nota, contatore+2 siccome prendo una finestra da 2 elementi
            }
            else    //<--- serve per evitare overflow ma controllare anche l'ultimo carattere
            {
                pattern = parola.substring(contatore, contatore+1);
            }
            
            Pos noncoding = new Pos();

            if(pattern.contains("//"))
            {
                noncoding.setTipo(TypeEnum.COMMENT);
            }

            if(pattern.contains("/*"))
            {    
                noncoding.setTipo(TypeEnum.COMMENT_OPEN);
            }

            if(pattern.contains("*/"))
            {    
                noncoding.setTipo(TypeEnum.COMMENT_CLOSE);
            }

            if(pattern.contains("\""))
            {   
                if(pattern.contains("\\") == false)     //<--- prima non c'era
                {
                    truePositionStringChar = pattern.indexOf("\"");  //<--- pattern.indexof può ritornare solo 0 o 1
                    noncoding.setTipo(TypeEnum.STRING);
                }
                else
                {
                    isFakeStringFlag = true;        //<--- indica che ho trovato nel testo \"
                }
                
            }

            if(pattern.contains("\'"))
            {
                if(pattern.contains("\\") == false)
                {
                    truePositionStringChar = pattern.indexOf("\'");    
                    noncoding.setTipo(TypeEnum.CHAR);
                }
                else
                {
                    isFakeStringFlag = true;        //<--- indica che ho trovato nel testo \'
                }
            }

            //se l'elemento è stato inizializzato 
            if(noncoding.getTipo() != TypeEnum.NODATA)
            {
                // NOTA
                noncoding.setRig(numeroRiga);
                // truePositionStringChar è diverso da zero, solo se proviene da una stringa o da un carattere
                noncoding.setCol(contatore + 1 + truePositionStringChar);    //stiamo utilizzando la notazione dal punto di vista dell'utente
                noncoding.Add(lista);        //<--- aggiungerlo alla lista
                contatore = contatore + 1;          //<--- per evitare di ricontare più volte le stesse posizioni (////)
                                                    // esempio : //// -> caso 1 : // + //
                                                    // caso 2 : //+ // + //     <--- perchè non sono avanzato di 2

                // NOTA : 
                // se ho la seguente stringa : "C"
                // il mio controllo è -> 
                // ciclo 1 -> _"   (uno spazio e i doppi apici)
                // ciclo 2 -> "C
                // ciclo 3 -> C"

                // quindi, se non avanzo di 1 quando trovo un DOPPIO APICE, finisco per contarlo due volte
            }

            // se è stato trovato un elemento \" o \' in una stringa, per evitare che venga interpretato come stringa
            if(isFakeStringFlag)
            {
                isFakeStringFlag = false;
                contatore = contatore + 1;
            }

            contatore = contatore + 1;
        }

        if(lista.getLista().isEmpty())
        {
            return null;
        }
        return lista;
    }

    ///////////////////////////////////
    /// PER OTTENERE TUTTI GLI ELEMENTI NON CODIFICANTI DI OGNI RIGA
    
    /**
     * Restituisce tutti gli elementi non codificanti del file
     * @param filePath , il path al file che analizziamo
     * @return ListPos , contiene tutti gli elementi non codificanti 
     * 
     * @see ListPos 
     */
    protected ListPos AllNonCoding(String filePath)
    {
        // reader
        try 
        {
            // reader
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

            // variabili
            int n_riga = 0;                                 //<--- gestire la riga (questo valore è per la lettura)  
            String line = "";                               //<--- la riga ricevuta dal reader
            ListPos listaDiPos = new ListPos();             //<--- la lista che raggrupperà tutti gli elementi non codificanti

            while((line = B_reader.readLine()) != null)
            {
                if(line.contains("\t"))
                {
                    line = Normalizzazione.NormalizedString(line);                    
                }

                listaDiPos.addLista(NonCodingPerLine(line, n_riga));    //<--- salvo tutti gli elementi nella stessa lista
                n_riga = n_riga + 1;                                    //<--- incrementa la posizione della riga
            }

            // chiudiamo il reader
            B_reader.close();

            // ritorna quanto trovato
            if(listaDiPos != null)
            {
                return listaDiPos;
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        return null;    //<--- di default
    }


    ///////////////////////////////////////////////
    /// PER TROVARE GLI INTERVALLI CHE SONO EFFETTIVAMENTE NON CODIFICANTI
    /// ESEMPIO : una stringa dopo un commento NON è un altro elemento non codificante, è solo parte di un commento

    // PREPROCESSING -> genero gli intervalli

    // METODO -> BuildNonCoding
    /**
     * Individua solo gli elementi non codificanti principali (quelli non codificanti non vengono considerati)
     * @param listaElementiNonCodificanti , sono tutti gli elementi non codificanti trovati 
     * @return List_intervals , tutti gli elementi non codificanti principali
     * 
     * @see ListPos
     * @see List_intervals
     */
    protected List_intervals BuildNonCoding(ListPos listaElementiNonCodificanti)
    {
        // per sicurezza
        if(listaElementiNonCodificanti == null)
        {
            return null;
        }

        if(listaElementiNonCodificanti.getLista().isEmpty())
        {
            return null;
        }

        // VARIABILI
        List_intervals output = new List_intervals();   //<--- dove mi salvo l'output
        Intervals intervallo = new Intervals();         //<--- un intervallo
        Pos init = new Pos();                           //<--- pos in cui inizia una porzione non codificante
        Pos end = new Pos();                            //<--- pos in cui termina la porzione
        int contatore = 0;                              //<--- la posizione attuale nella lista che contiene gli elementi non codificanti
        TypeEnum oldType = TypeEnum.NODATA;             //<--- per verificare se posso la flag
        int rigaCommento = 0;                           //<--- controlla se la riga è la stessa di quella di un commento aperto

        //flags
        boolean flag_SomethingIsOpen = false;           //<--- controlla se qualcosa di non codificante è aperto

        // CODICE VERO E PROPRIO
        while(contatore < listaElementiNonCodificanti.getLista().size())
        {
            if(flag_SomethingIsOpen == false)
            {
                // ovvero un commento è stato aperto
                if(rigaCommento != 0)
                {
                    // controllo se la riga della parte non codificante è maggiore della riga in cui ho trovato il commento
                    if(listaElementiNonCodificanti.getLista().get(contatore).getRig() > rigaCommento)
                    {
                        rigaCommento = 0;
                    }
                }

                // ovvero non è aperto un commento
                if(rigaCommento == 0)
                {
                    switch(listaElementiNonCodificanti.getLista().get(contatore).getTipo())
                    {
                        case NODATA->
                        {
                            return null;    //<--- evidentemente c'era un errore
                        }

                        case COMMENT->
                        {
                            init.setRig(listaElementiNonCodificanti.getLista().get(contatore).getRig());
                            init.setCol(listaElementiNonCodificanti.getLista().get(contatore).getCol());
                            init.setTipo(listaElementiNonCodificanti.getLista().get(contatore).getTipo());
                            end.setRig(listaElementiNonCodificanti.getLista().get(contatore).getRig() + 1);
                            end.setCol(0);
                            end.setTipo(listaElementiNonCodificanti.getLista().get(contatore).getTipo());   
                            intervallo.setEnd(end);
                            intervallo.setInit(init);
                            intervallo.setType(TypeEnum.COMMENT); 
                            output.AddIntervallo(intervallo);
                            // se ci sono altre cose sulla stessa riga del commento, non devono essere visibili
                            rigaCommento = intervallo.getInit().getRig();   //<--- la variabile che useremo per questo caso specifico
                        }

                        case STRING->
                        {
                            flag_SomethingIsOpen = true;
                            oldType = TypeEnum.STRING;
                        }

                        case CHAR->
                        {
                            flag_SomethingIsOpen = true;
                            oldType = TypeEnum.CHAR;
                        }

                        case COMMENT_OPEN->
                        {
                            flag_SomethingIsOpen = true;
                            oldType = TypeEnum.COMMENT_OPEN;
                        }

                        case COMMENT_CLOSE->
                        {
                            return null;    //<--- evidentemente c'era un errore
                        }

                        case MULTI->
                        {
                            return null;    //<--- evidentemente c'è un errore (multi serve solo a definire il tipo di un intervallo)
                        }

                    }

                    // il caso COMMENT, viene gestito a parte
                    if(listaElementiNonCodificanti.getLista().get(contatore).getTipo() != TypeEnum.COMMENT)
                    {
                        init.setRig(listaElementiNonCodificanti.getLista().get(contatore).getRig());
                        init.setCol(listaElementiNonCodificanti.getLista().get(contatore).getCol());
                        init.setTipo(listaElementiNonCodificanti.getLista().get(contatore).getTipo());
                    }
                }
                
            }
            else
            {
                switch (listaElementiNonCodificanti.getLista().get(contatore).getTipo()) 
                {
                    case NODATA->
                    {
                        return null;    //<--- evidentemente c'era un errore
                    }

                    case COMMENT->
                    {
                        // non fare niente
                    }

                    case STRING->
                    {
                        // ovvero se ho trovato la stringa per chiudere 
                        if(oldType == TypeEnum.STRING)
                        {
                            end.setRig(listaElementiNonCodificanti.getLista().get(contatore).getRig());
                            end.setCol(listaElementiNonCodificanti.getLista().get(contatore).getCol());
                            end.setTipo(listaElementiNonCodificanti.getLista().get(contatore).getTipo());
                            intervallo.setInit(init);
                            intervallo.setEnd(end);
                            intervallo.setType(oldType);
                            output.AddIntervallo(intervallo);
                            flag_SomethingIsOpen = false;
                            oldType = TypeEnum.NODATA;  //<--- lo "azzeriamo"
                        }

                        // ALTRIMENTI NON FACCIO NIENTE
                    }

                    case CHAR->
                    {
                        
                        // ovvero se ho trovato il carattere per chiudere 
                        if(oldType == TypeEnum.CHAR)
                        {
                            end.setRig(listaElementiNonCodificanti.getLista().get(contatore).getRig());
                            end.setCol(listaElementiNonCodificanti.getLista().get(contatore).getCol());
                            end.setTipo(listaElementiNonCodificanti.getLista().get(contatore).getTipo());
                            intervallo.setInit(init);
                            intervallo.setEnd(end);
                            intervallo.setType(oldType);
                            output.AddIntervallo(intervallo);
                            flag_SomethingIsOpen = false;
                            oldType = TypeEnum.NODATA;  //<--- lo "azzeriamo"
                        }

                        // ALTRIMENTI NON FACCIO NIENTE
                    }

                    case COMMENT_OPEN->
                    {
                        // NON DEVO FARE NIENTE   
                    }

                    case COMMENT_CLOSE->
                    {
                        // ovvero se ho trovato il carattere per chiudere 
                        if(oldType == TypeEnum.COMMENT_OPEN)
                        {
                            end.setRig(listaElementiNonCodificanti.getLista().get(contatore).getRig());
                            end.setCol(listaElementiNonCodificanti.getLista().get(contatore).getCol());
                            end.setTipo(listaElementiNonCodificanti.getLista().get(contatore).getTipo());
                            intervallo.setInit(init);
                            intervallo.setEnd(end);
                            intervallo.setType(TypeEnum.MULTI);
                            output.AddIntervallo(intervallo);
                            flag_SomethingIsOpen = false;
                            oldType = TypeEnum.NODATA;  //<--- lo "azzeriamo"
                        }

                        // ALTRIMENTI NON FACCIO NIENTE
                    }

                    case MULTI ->
                    {
                        return null;        //<--- evidentemente c'era un problema
                    }
                }
            }
            contatore = contatore + 1; 
        }

        return output;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // OVERRIDES

    /*
     * Questa, andrà a stampare tutti gli elementi
     */
    /* 
    @Override
    public String toString()
    {
        String Stampa = "";
        for(int i=0 ; i < General.getLista().size() ; i++)
        {
            Stampa = Stampa +"INTERVALLO:"+i+General.getLista().get(i).toString();
        }
        return Stampa;
    }
    */
}

