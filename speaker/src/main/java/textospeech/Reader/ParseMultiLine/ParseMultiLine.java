package textospeech.Reader.ParseMultiLine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import textospeech.Parser.FINDER.GraphFinder;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;
import textospeech.Parser.NORMALIZZAZIONE.Normalizzazione;
import textospeech.Parser.STRUTTURE_DATI.BloccoCollector;
import textospeech.Reader.Enfasi.Enfasi2;


public class ParseMultiLine 
{
    /**
     * Raggruppa in una stringa tutte le linee di un testo che sono racchiuse nella funzione che contiene la linea utente
     * @param filepathToBeRead  , il file in cui si trova l'utente
     * @param lineaUtente , la linea in cui si trova l'utente
     * @return
     */
    protected static String LineToRead(String filepathToBeRead, int lineaUtente)
    {
        if(filepathToBeRead == null)
        {
            return null;
        }

        if(filepathToBeRead.isEmpty())
        {
            return null;
        }

        if(filepathToBeRead.isBlank())
        {
            return null;
        }

        // trovo i blocchi di testo
        BloccoCollector Blocchi = new BloccoCollector();
        Blocchi = GraphFinder.GetBlock(filepathToBeRead);

        if(Blocchi == null || Blocchi.isEmpty())
        {
            return null;
        }

        // variabili
         
        try
        {
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepathToBeRead)));

            String daLeggere = "";
            int posInBlocchi = 0; 
            int endCiclo = Blocchi.getBlocchi().size(); // size conta numeri "reali" quindi è come se partisse da 1
            boolean flag = true;
            // codice vero e proprio
            
            // NOTA
            // i blocchi sono ordinati in funzione di chi è stato chiuso per primo, di conseguenza, 
            // si dovrebbe trovare per primo il blocco più piccolo che contiene la nostra riga
            while((posInBlocchi < endCiclo) && (flag))
            {
                if(Blocchi.getBlocchi().get(posInBlocchi).getInizio().getRiga() > lineaUtente)  //<--- si trova oltre la linea dell'utente (probabilmente inutile)
                {
                    posInBlocchi = posInBlocchi + 1;
                }
                else
                {

                    if(Blocchi.getBlocchi().get(posInBlocchi).getFine().getRiga() < lineaUtente)    //<--- se la porzione NON codificante termina prima della riga dell'utente
                    {
                        posInBlocchi = posInBlocchi + 1;
                    }
                    else
                    {
                        int rigaInizio = Blocchi.getBlocchi().get(posInBlocchi).getInizio().getRiga();
                        int colonnaInizio = Blocchi.getBlocchi().get(posInBlocchi).getInizio().getColonna();
                        int rigaFine = Blocchi.getBlocchi().get(posInBlocchi).getFine().getRiga();
                        int colonnaFine = Blocchi.getBlocchi().get(posInBlocchi).getFine().getColonna();

                        // flag : valuta se sono già entrato in uno degli altri due casi
                        boolean flagNotFirstNotEnd = true;

                        int posizione = rigaInizio;
                        // per buffered reader
                        String line = "";    //<--- linea ritornata dal reader
                        int contatore_interno = 0;      //<--- ricordati che bufferedReader parte da 0!!!
                        // problema, come gestire la colonna?
                        while(posizione < (rigaFine+1)) 
                        {
                            while(contatore_interno != posizione)       //<--- scorro fino a trovare la linea dettata dalla posizione
                            {
                                line = B_reader.readLine();
                                contatore_interno = contatore_interno + 1;
                            }

                            ////////////////////////////////////////////////////////////////////////////

                            // se vi fosse la presenza di un TAB questo potrebbe modificare la posizione delle colonne, quindi 
                            // lo sostituiamo con gli spazi corretti
                            if(line.contains("\t"))
                            {
                                line = Normalizzazione.NormalizedString(line);
                            }

                            ////////////////////////////////////////////////////////////////////////////

                            // se la funzione fosse tutta sulla stessa riga
                            if((posizione) == rigaInizio && (rigaInizio == rigaFine))
                            {
                                String temp = "";
                                temp = line;
                                temp.substring(colonnaInizio-1, colonnaFine);

                                daLeggere = daLeggere.concat(line).concat("\n");
                                flagNotFirstNotEnd = false;
                            }
                            else
                            {
                                if(posizione == rigaInizio)
                                {
                                    if(colonnaInizio != 1)
                                    {
                                        daLeggere = line;
                                        // NOTA
                                        // il meno 1 serve per evitare che vengano saltate le graffe aperte

                                        daLeggere = daLeggere.substring(colonnaInizio-1).concat("\n");
                                        flagNotFirstNotEnd = false;
                                    }
                                }

                                if(posizione == rigaFine)
                                {
                                    if(colonnaFine != 1)
                                    {
                                        String temp = "";
                                        temp = line;
                                        temp.substring(0, colonnaFine);
                                        daLeggere = daLeggere.concat(temp).concat("\n");
                                        flagNotFirstNotEnd = false;
                                    }
                                }
                            }

                            // ovvero se è in mezzo
                            if(flagNotFirstNotEnd)
                            {
                                daLeggere = daLeggere.concat(line).concat("\n");
                            }

                            flagNotFirstNotEnd = true;
                            posizione = posizione + 1;
                        }
                        flag = false;
                    }
                }
            }
            B_reader.close();
            
            //daLeggere = "ciaoo";
            return daLeggere;

        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return null;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////

    // dalla linea che passo ottengo il testo leggibile
    /**
     * Gestisce la tokenizzazione e processazione del testo del utente
     * @param testo , il testo dell'utente
     * @return  String , il testo processato
     * 
     * @see ParseMultiLine#Tokenizer()
     * @see ParseMultiLine#SpecificTokenizer()
     */
    protected String ReadableString(String testo)
    {
        // Per sicurezza
        if (testo == null) 
        {
            return null;                    
        }

        // VARIABILI
        String textForReading = "";

        // RIMOZIONE EVENTUALI SPAZI AD INIZIO E FINE
        testo = testo.trim();       //<--- così rimuovo gli spazi all'inizio e alla fine 

       String[] words = Tokenizer(testo);

       // PER SICUREZZA
       if(words == null)
        {
            return null;
        }

        textForReading = SpecificTokenizer(words);
        
        return textForReading;
    }

////////////////////////////////////////////////////////////////////////////////////

    /**
     * Si occupa di andare a suddividere il testo in token, dove ogni token è una parola
     * @param daTokenizzare
     * @return Stringa_di_token, racchiude tutte le parole trovate
     */
    // MI SEPARA IN TOKEN
    // NOTA:
    // 1) Se vengono trovati dei caratteri "//" questi si portano dietro anche gli spazi
    // 2) Tutti gli spazi vengono salvati 
    protected String[] Tokenizer(String daTokenizzare)
    {
        //PER SICUREZZA
        if(daTokenizzare == null)
        {
            return null;
        }

        if(daTokenizzare.isEmpty())
        {
            return null;
        }

        if(daTokenizzare.isBlank())
        {
            return null;
        }

        // CODICE VERO E PROPRIO

        String[] words = daTokenizzare.split("(?<!/)((?<=\\W)|(?=\\W))(?!/)"); //<--- da cambiare, mi rimuove gli \n
        return words;
    }

////////////////////////////////////////////////////////////////////////////////////

    // MI RITORNA LA PAROLA LAVORATA
    /**
     *  Si occupa di dare la giusta enfasi a tutti i token
     * @param tokens , tutti i token(parole) che formano il testo 
     * @return String , i vari token dopo essere stati processati, vengono uniti in un unica stringa 
    */ 

    protected String SpecificTokenizer(String[] tokens)
    {
        // CONTROLLO DI SICUREZZA
        if(tokens == null)
        {
            return null;
        }

        // VARIABILI
        boolean flagInsideNonCoding = false;
        TypeEnum oldType = TypeEnum.NODATA;
        String textForReading = "";     //<--- la variabile di ritorno

        // SCORRIAMO I VARI TOKENS
        for(String word : tokens)
        {
            // CONTROLLIAMO CHE NON SIA VUOTA

            if(word.equals(" "))
            {
                // NON FARE NIENTE
            }
            else    //<--- se la parola NON è un carattere di spazio
            {
                // SE TROVO DEI "/", RIMUOVO GLI SPAZI CHE IL TOKENIZER SI PORTA DIETRO
                if(word.contains("/"))
                {
                    word = word.trim(); //<--- rimuovo gli spazi
                }

                String temp = "";
                temp = Enfasi2.processWord(word,flagInsideNonCoding,oldType);
                if(temp != null)
                {
                    textForReading = textForReading.concat(temp);
                }
                else
                {
                    return null;
                }

                // GESTIONE DI FLAG E TIPO PARTE NON CODIFICANTE
                if(flagInsideNonCoding == false)
                {
                    switch (word) 
                    {
                        case "//"->
                        {
                            flagInsideNonCoding = true;
                            oldType = TypeEnum.COMMENT;
                        }

                        case "/*"->
                        {
                            flagInsideNonCoding = true;
                            oldType = TypeEnum.COMMENT_OPEN;
                        }

                        case "\""->
                        {
                            flagInsideNonCoding = true;
                            oldType = TypeEnum.STRING;
                        }

                        case "'"->
                        {
                            flagInsideNonCoding = true;
                            oldType = TypeEnum.CHAR;
                        }
                    }
                }
                else    //<--- ovvero, se ho chiuso quel blocco non codificante
                {
                    switch (word) 
                    {

                        case "*/"->
                        {
                            if(oldType == TypeEnum.COMMENT_OPEN)
                            {
                                flagInsideNonCoding = false;
                                oldType = TypeEnum.NODATA;
                            }
                            
                        }

                        case "\""->
                        {
                            if(oldType == TypeEnum.STRING)
                            {
                                flagInsideNonCoding = false;
                                oldType = TypeEnum.NODATA;
                            }
                        }

                        case "\'"->
                        {
                            if(oldType == TypeEnum.CHAR)
                            {
                                flagInsideNonCoding = false;
                                oldType = TypeEnum.NODATA;
                            }
                        }

                        case "\n"->
                        {
                            if(oldType == TypeEnum.COMMENT)
                            {
                                flagInsideNonCoding = false;
                                oldType = TypeEnum.NODATA;
                            }
                        }
                    }
                }
            }
        }
        return textForReading;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////
    /// METODO CHE RACCHIUDE GLI ALTRI (in modo da dover chiamare un'unica funzione)
    /// 
    
    /**
     * Restituisce il testo sottoforma di stringa, dopo esser stata rimaneggiata per aggiungere l'enfasi
     * @param filepath , il filepath al file utilizzato dall'utente
     * @param lineaUtente , la linea in cui si trova l'utente
     * @return
     */
    public static String GetReadableString(String filepath, int lineaUtente)
    {
        ParseMultiLine A = new ParseMultiLine();
        String textForReading = "";
        textForReading = LineToRead(filepath,lineaUtente);
        if(textForReading == null)  //<--- ci evitiamo un ciclo
        {
            return null;
        }
        textForReading = A.ReadableString(textForReading);
        return textForReading;
    }
}
