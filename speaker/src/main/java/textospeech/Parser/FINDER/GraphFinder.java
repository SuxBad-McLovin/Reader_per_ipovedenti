package textospeech.Parser.FINDER;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import textospeech.Parser.FINDER.Interface.SearchGraphInterface;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.Gestione_stringhe;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.List_intervals;
import textospeech.Parser.NORMALIZZAZIONE.Normalizzazione;
import textospeech.Parser.STRUTTURE_DATI.Blocco;
import textospeech.Parser.STRUTTURE_DATI.BloccoCollector;
import textospeech.Parser.STRUTTURE_DATI.Graffa;
import textospeech.Parser.STRUTTURE_DATI.GraffaCollector;

public class GraphFinder implements SearchGraphInterface 
{
    int minNoKeyword; 

    //4) TROVARE LE GRAFFE
    //5) SALVARE TUTTE LE GRAFFE APERTE TROVATE
    //6) SALVARE TUTTE LE GRAFFE CHIUSE TROVATE

    ////////////////////////////////////////////////
    ///  più comodo un approccio manuale -> scorrere la lista alla ricerca dei due caratteri (tanto indexOf ha complessità O(n))
    
    /**
     * Individua la prima graffa della riga specificata
     * @param posizione_partenza    , specifica da che colonna parto a guardare, con posizione di partenza INCLUSA
     * @param linea , la linea in cui cerco la stringa
     * @param n_riga    , la riga in cui si trova la linea 
     * @return Graffa , struttura dati per gestire dove si trova una graffa
     * 
     * @see Graffa
     */
    // NOTA : le colonne partono da 1 (dal punto di vista dell'utente), quindi noi faremo lo stesso
    public Graffa FindFirstGraffa(int posizione_partenza,String linea, int n_riga)
    {
        if(n_riga <= 0) //controllo di sicurezza
        {
            n_riga = 1;
        }

        boolean flag = false;   //<--- controlla di non aver già trovato una corrispondenza
        Graffa result = new Graffa();
        int contatore = 0;
        if(posizione_partenza >= 0 && posizione_partenza < linea.length())  // il controllo aggiuntivo, mi serve ad evitare che mi passino un valore superiore alla dimensione della stringa
        {
            contatore = posizione_partenza;
        }
        else
        {
            contatore = 0;
        }
        
        while((contatore != linea.length()) && (flag != true))     // in questo modo se trovo qualcosa, esco subito  
        {
            if(linea.charAt(contatore) == '{')
            {
                result.setRiga(n_riga);
                result.setColonna(contatore + 1);   // NOTA: il +1 serve per mantenerlo sincronizzato con quanto visto dall'utente
                // NON IMPOSTIAMO ISCODE siccome di default è su true, lo analizzeremo in futuro
                result.setIsOpen(true); //<--- per sicurezza
                flag = true;
            }

            if(linea.charAt(contatore) == '}')
            {
                result.setRiga(n_riga);
                result.setColonna(contatore + 1);
                // NON IMPOSTIAMO ISCODE siccome di default è su true, lo analizzeremo in futuro
                result.setIsOpen(false); //<--- qui serve, siccome abbiamo trovato una graffa chiusa
                flag = true;
            }
            contatore++;
        }

        // per fornirci il risultato
        if(result.getRiga() == 0)   //<--- significa che l'oggetto non è stato inizializzato
        {
            return result;  //ritorno la lista vuota
        }
        else
        {
            return result;
        }
    }

    ////////////////////////////////////////////////
    /// FINDALLGRAPHPERLINE ALTERNATIVO
    /// da utilizzare insieme alla funzione di sopra 
    /// mi restituisce tutte le graffe presenti su una riga e le inserisce in una lista di graffe
    /// NOTA : gli ho messo un ritorno, in quanto mi serve per valutare se fosse NULL la riga
    
    /**
     * Individua tutte le graffe presenti su una linea
     * @param linea , la stringa in cui cercare le graffe
     * @param n_riga , la riga in cui si trova la linea
     * @return  listaGraffe , struttura dati che gestisce il salvataggio di tutte le graffe trovate 
     */

    protected GraffaCollector FindAllGraphPerLine(String linea,int n_riga)
    {
        // NOTA
        // la gestione del caso null della funzione prima viene gestito dentro il while

        GraffaCollector listaGraffe = new GraffaCollector();
        int lastColonna = 0;
        Graffa graffa = new Graffa();
        if(n_riga <= 0) //<--- non è possibile avere una riga minore di 1 (guardiamo sempre dal punto di vista dell'utente)
        {
            n_riga = 1;
        }

        while(lastColonna < linea.length())
        {
            graffa = FindFirstGraffa(lastColonna,linea,n_riga);
            
            if((graffa != null) && (graffa.getRiga() != 0))
            {
                graffa.AddGraffa(listaGraffe);  // aggiungo la graffa nella lista specificata
                lastColonna = graffa.getColonna();
            }
            else
            {
                lastColonna = linea.length();   // così termina il ciclo
            }
        }

        // controllo se la lista è vuota
        if(listaGraffe.isEmpty())
        {
            return listaGraffe;     // ritornerà una lista vuota
        }
        else
        {
            return listaGraffe;
        }

    }
    /////////////////////////////////////////////////////////////////////////

    //7) IMPORTARE COMMENTI ECC. DA PROGETTO TIROCINIO <--- STEP IMPORTANTE
    // lo passo come argomento
    //8) TROVARE EVENTUALI RISCONTRI
    // NOTA : listaNonKeywords è già stata ridotta da quella iniziale!
    // NOTA : cambiare il nome di GraffaCollector (non è necessario che la lista sia vuota)

    /**
     * Si occupa di trovare tutte le graffe che costituiscono effettivamente parte codificante del testo (ad esempio, una graffa 
     * all' interno di un commento è NON CODIFICANTE)
     * 
     * @param linea , la linea da analizzare
     * @param n_riga , la posizione della riga
     * @param reducedListaNonKeywords , lista di tutti gli elementi non codificanti presenti nell'intero file, ma ridotti a quelli d'interesse
     * @return lista , la lista in cui salvo le varie graffe trovate
     * 
     * @see List_intervals
    */
    protected GraffaCollector FindCodingGrafPerLine(String linea, int n_riga, List_intervals reducedListaNonKeywords)
    {
        if(reducedListaNonKeywords == null)
        {
            return null;
        }

        if(reducedListaNonKeywords.isEmpty())
        {
            GraffaCollector A = new GraffaCollector();
            A = FindAllGraphPerLine(linea,n_riga);     //<--- salva la posizione delle graffe in una linea 
            return A;   //<--- ritorno una lista vuota
        }

        if(n_riga <= 0)     // controllo di sicurezza
        {
            n_riga = 1;
        }

        // CONTATORI
        int contatoreNonKeyword = 0;    //<--- tiene conto della posizione nella LISTA delle porzioni NON CODIFICANTI
        int contatorePosizione = 0;     //<--- tiene conto delle GRAFFE trovate che sto CONTROLLANDO
        
        // salvo le graffe trovate in una linea
        GraffaCollector lista = new GraffaCollector();
        GraffaCollector temp = new GraffaCollector();
        temp = FindAllGraphPerLine(linea,n_riga);     //<--- salva la posizione delle graffe in una linea

        // CONTROLLO DI SICUREZZA
        if(temp == null)            // ovvero se non ho trovato graffe su quella linea
        {
            return null;
        }

        // PER ALLEGGERIRE LA NOTAZIONE (sorte di alias)

        // 1) variabili per dimensioni
        int dim_lista_temp_graffe = temp.getLista().size();
        int dim_lista_non_coding = reducedListaNonKeywords.getLista().size();

        // 2) variabili per posizione nel codice (della parte NON CODIFICANTE)
        int rigaInit;
        int rigaEnd;
        int colonnaInit;
        int colonnaEnd;

        // 3) variabili per la posizione delle GRAFFE
        int colonnaGraffa;

        //controllo che non siano nulli
        if((dim_lista_temp_graffe != 0) && (dim_lista_non_coding != 0)) // <--- controllo che la lista non sia vuota
        {
            // Inizializziamo le variabili (in modo da scrivere if più compatti)

            // 2) inizializzazione per la lista con porzioni NON CODIFICANTI
            rigaInit = reducedListaNonKeywords.getLista().get(contatoreNonKeyword).getInit().getRig();
            rigaEnd = reducedListaNonKeywords.getLista().get(contatoreNonKeyword).getEnd().getRig(); 
            colonnaInit = reducedListaNonKeywords.getLista().get(contatoreNonKeyword).getInit().getCol();
            colonnaEnd = reducedListaNonKeywords.getLista().get(contatoreNonKeyword).getEnd().getCol();

            // 3) inizializzazione per la lista con le GRAFFE
            colonnaGraffa = temp.getLista().get(contatorePosizione).getColonna();
            
            // CONTROLLI

            // ovvero finchè non si è controllato ogni graffa trovata
            while(contatorePosizione < dim_lista_temp_graffe)
            {
                // possiamo ciclare tranquillamente fino alla fine, siccome abbiamo già preso una sottoporzione della lista iniziale
                while(contatoreNonKeyword < dim_lista_non_coding)
                {
                    // CASO 1 : INIZIO non codificante sulla stessa riga della graffa
                    if(n_riga == rigaInit)
                    {
                        //CASO 1.2 : fine non codificante sulla stessa riga
                        if(n_riga == rigaEnd)
                        {
                            // se la posizione della colonna è fra le due allora è NON CODIFICANTE
                            if((colonnaGraffa >= colonnaInit) && (colonnaGraffa <= colonnaEnd) )
                            {
                                temp.getLista().get(contatorePosizione).setIsCode(false);
                            }
                        }
                        else    // CASO 1.3 : se è solo l'INIZIO su quella riga
                        {
                            // controllo che si trovi PRIMA di INIZIO
                            if(colonnaGraffa >= colonnaInit)
                            {
                                temp.getLista().get(contatorePosizione).setIsCode(false);
                            }
                        }
                    
                    }
                    else    //<--- CASO 2 : se l'INIZIO è PRIMA della riga che sto considerando
                    {
                        // se si trova fra INIZIO e FINE di una porzione NON codificante
                        if(n_riga < rigaEnd)
                        {
                            temp.getLista().get(contatorePosizione).setIsCode(false);
                        }
                        else    // se non è IN MEZZO
                        {   
                            // se la posizione della colonna è PRIMA della fine, allora non è codificante
                            if(colonnaGraffa <= colonnaEnd)
                            {
                                temp.getLista().get(contatorePosizione).setIsCode(false);
                            }
                        }
                        
                    }
                    temp.getLista().get(contatorePosizione).AddGraffa(lista);      //<--- mi salvo i valori in lista (questo è il metodo corretto)
                    contatoreNonKeyword++;
                }
                contatoreNonKeyword = 0;    //<--- resetto il contatore che tiene conto dei commenti
                contatorePosizione++;
            }
        }
        else    //<--- se una delle due liste fosse vuota
        {
            if((temp.getLista().size() != 0))   //<--- se è vuota solo la lista che contiene i "commenti"
            {
                int dim = temp.getLista().size();
                int i = 0;
                while(i<dim)
                {
                    temp.getLista().get(i).AddGraffa(lista);
                    i = i + 1;
                }
                
            }
            // altrimenti niente
        }
        
        // MI ACCERTO DELL'OUTPUT

        if(lista.isEmpty())
        {
            return lista;       // ritorno una lista vuota
        }
        else
        {
            return lista;
        }
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Individua tutte le graffe del file (solo quelle codificanti)
     * @param ListaNonKeywords , lista contenente tutti gli elementi non codificanti
     * @param filepathToBeRead , path del file da leggere
     * @return lista , la lista in cui vengono salvati le varie graffe
     * 
     * @see List_intervals
     */

    protected GraffaCollector FindAllCodingGraf(List_intervals ListaNonKeywords,String filepathToBeRead)
    {
        if(ListaNonKeywords == null)
        {
            return null;
        }

        /*
        // QUESTO CASO VIENE GESTITO ALL'INTERNO DEL TRY
        if(ListaNonKeywords.isEmpty())  
        {
        
        }
        */

        int riga = 1;   // è stato cambiato ad 1 (per comodità dell'utente) 
        String line;
        List_intervals listaInterestingNonKeyword = new List_intervals();
        GraffaCollector lista = new GraffaCollector();
        try 
        {
            BufferedReader b_Reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepathToBeRead)));

            if(ListaNonKeywords.isEmpty())      // ovvero se non ci dovessero essere commenti
            {
                while((line = b_Reader.readLine()) != null)
                { 
                    GraffaCollector temp = new GraffaCollector();
                    temp = FindAllGraphPerLine(line,riga);       
                    lista.AddLista(temp);       // aggiunge tutti gli elementi in coda
                    riga++;
                }
            }
            else
            {
                while((line = b_Reader.readLine()) != null)
                {
                
                    if(line.contains("\t"))
                    {
                        line = Normalizzazione.NormalizedString(line);
                    }

                    // NOTA : in questa fase, mi occupo anche di aggiornare minNoKeyword (in background) tramite GetUserfullListKeyword
                    listaInterestingNonKeyword = GetUsefullListKeyword(ListaNonKeywords,riga,minNoKeyword);
                    if(listaInterestingNonKeyword.isEmpty() || listaInterestingNonKeyword == null)
                    {
                        lista.AddLista(FindAllGraphPerLine(line, riga));    //<--- evitiamo di controllare se è effettivamente
                                                                            // codificante (non è tra dei commenti)
                    }
                    else
                    {
                        lista.AddLista(FindCodingGrafPerLine(line,riga,listaInterestingNonKeyword)); 
                    }
                    riga++;
                }
                b_Reader.close();
                
            }
            
            // per sicurezza
            if(lista.isEmpty())
            {
                return null;
            }
            else
            {
                return lista;
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        return null;        // di default ritorna null
    }

    ///////////////////////////////
    // UTILITY 
    // 1) controllare quali fra gli elementi della lista possono risultare interessanti 

    // RICORDA : minNoKeyword è l'indice della lista delle sequenze non codificanti!!!
    /**
     * Metodi di utility, si pone l'obiettivo di ridurre i controlli per individuare le porzioni non codificanti
     * @param listaNonKeywords , contiene tutti gli elementi non codificanti
     * @param riga_considerata , la riga che stiamo considerando ora
     * @param pos_già_considerate , gli elementi non codificanti già considerati
     * @return List_intervals , gli intervalli che ha senso considerare
     * 
     * @see List_intervals
     */
    protected List_intervals GetUsefullListKeyword(List_intervals listaNonKeywords,int riga_considerata,int pos_già_considerate)
    {
        if(listaNonKeywords == null || listaNonKeywords.isEmpty())
        {
            return null;
        }

        if(riga_considerata < 0)   // per sicurezza
        {
            riga_considerata = 0;
        }
        
        // FLAGS
        boolean flag = true;
        boolean flag_skip = true;   

        List_intervals result = new List_intervals();
        int pos_in_lista = pos_già_considerate;
        int min = listaNonKeywords.getLista().size();   // il + 1 NON serve, basta il size() ad accertarsi che il valore di min all'inizio sia massimo
        while(flag)
        {
            if(riga_considerata >= listaNonKeywords.getLista().get(pos_in_lista).getInit().getRig())
            {
                if(riga_considerata < listaNonKeywords.getLista().get(pos_in_lista).getEnd().getRig()) //<--- allora sono fra inizio e fine
                {
                    // CASO 1 : COMMENTO
                    if(listaNonKeywords.getLista().get(pos_in_lista).getType() == TypeEnum.COMMENT)
                    {
                        min = pos_in_lista + 1;
                    }
                }

                if(riga_considerata == listaNonKeywords.getLista().get(pos_in_lista).getEnd().getRig())
                {
                    // CASO 2 : TERMINE ULTIMO DI BLOCCO
                    min = pos_in_lista + 1;
                }

                // potrebbe capitare che la riga considerata sia dopo l'INIZIO ma anche DOPO la FINE
                if(riga_considerata > listaNonKeywords.getLista().get(pos_in_lista).getEnd().getRig())
                {
                    flag_skip = false;
                }

                if(flag_skip != false)
                {
                    // AGGIUNGI ELEMENTO
                    result.AddIntervallo(listaNonKeywords.getLista().get(pos_in_lista));
                    
                }
                else
                {
                    flag_skip = true;
                    min = pos_in_lista + 1;
                }
            }
            else    //<--- se l'inizio dell'elemento in lista viene dopo allora possiamo terminare
            {
                if(min == listaNonKeywords.getLista().size())
                {
                    min = pos_in_lista;
                }
                flag = false;
            }

            // BLOCCO DI SICUREZZA (PER EVITARE OVERFLOW)

            // PER MIN
            // per sicurezza (min non dovrebbe essere più grande della dimensione della lista)
            // serve solo a fornire un punto di partenza per evitare di scorrere tutta la lista (la funzione che serve nelle altre funzioni)
            if(min >= listaNonKeywords.getLista().size())
            {
                min = pos_in_lista;
            }

            // PER CONTATORE
            if(pos_in_lista >= listaNonKeywords.getLista().size() - 1)
            {
                flag = false;
                pos_in_lista = pos_in_lista - 1;
                
            }
            else    //<--- incremento la posizione nella lista
            {
                pos_in_lista = pos_in_lista + 1;
            }

            
            
        // RICORDA : minNoKeyword è l'indice della lista delle sequenze non codificanti!!!
        }
        minNoKeyword = min;
        // RICORDA : minNoKeyword è l'indice della lista delle sequenze non codificanti!!!
        return result;
    }

    ///////////////////////////////
    //9) ACCOPPIARE LE GRAFFE IN MODO DA FORMARE I BLOCCHI
    //10) (Opzionale) IDENTIFICARE I VARI BLOCCHI
    //11) (Opzionale) LIMITARE LA CLASSIFICAZIONE SOLO ATTORNO A CIO' CHE UTENTE HA SELEZIONATO (Massimizzare velocità)

    // STEP 2 ---> raggruppare le liste
    /**
     * Individa la coppia di graffe che compone un blocco (tendenzialmente individua funzioni)
     * @param liste , la lista che contiene le varie graffe
     * @return BloccoCollector
     * 
     * @see GraffaCollector
     * @see BloccoCollector
     */
    protected BloccoCollector FindBlock(GraffaCollector liste)
    {
        if(liste.isEmpty() || liste == null)
        {
            return null;
        }
    
        BloccoCollector listaBlocchi = new BloccoCollector();
        // controllo preliminare
        /* 
        if(liste.getA().size() != liste.getB().size())
        {
            throw new IllegalStateException("ERRORE : Le graffe che costituiscono i blocchi non corrispondono in dimensioni");
        }
        */
        Graffa aperta = new Graffa();
        Graffa chiusa = new Graffa();
        int contatore = 0;
        
        while(liste.getLista().size() > 0)
        {
            if(liste.getLista().get(contatore).getIsOpen() == false)
            {
                chiusa = liste.getLista().remove(contatore);     // NOTA : il remove ritorna anche il valore che è stato rimosso

                contatore = contatore - 1;  

                // NOTA : dato che ho rimosso l'elemento, mi devo anche spostare a sinistra

                aperta = liste.getLista().remove(contatore);

                //aggiungo alla lista di blocchi quelli che ho trovato
                Blocco A =new Blocco(aperta, chiusa);
                listaBlocchi.addBlocco(A);
            }
            // per sicurezza
            if(contatore < liste.getLista().size()-1)
            {
                contatore = contatore + 1;
            }
            else
            {
                contatore = contatore - 1;
            }   
            
        }
        return listaBlocchi;
    }

    ////////////////////////////////////////////////////////////////////
    
    /**
     * Fà da interfaccia per la classe, restituendo i vari blocchi (funzioni per lo più) presenti
     * @param filepathToBeRead  , il path al file considerato dall'utente 
     * @return BloccoCollector  , in cui salvo tutte le funzioni (principalemente) trovate
     */

    public static BloccoCollector GetBlock(String filepathToBeRead)
    {
        // Variabili -> per inizializzare la lista di "righe non codificanti"
        List_intervals listaNonKeywords = new List_intervals();
        listaNonKeywords = Gestione_stringhe.BuildIntervals(filepathToBeRead); //<--- ho trovato tutte le porzioni non codificanti

        // variabili per test vero e proprio
        GraphFinder finder = new GraphFinder();
        GraffaCollector lista = new GraffaCollector();

        lista = finder.FindAllCodingGraf(listaNonKeywords,filepathToBeRead);
        if(lista == null || lista.isEmpty())
        {
            return null;
        }

        BloccoCollector Blocchi = new BloccoCollector();
        Blocchi = finder.FindBlock(lista); 

        return Blocchi;           

    } 
}
