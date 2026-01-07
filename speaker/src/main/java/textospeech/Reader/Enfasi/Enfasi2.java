package textospeech.Reader.Enfasi;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;

public class Enfasi2 
{
        // Risultato lettura: "Visibilità x", con x = public, private, protected
    /**
     * Valuta se si tratta di una parola che denota visibilità 
     * @param word , la parola da analizzare
     * @return
     */
    protected static boolean isVisibility(String word) 
    {
        String [] keywords = { "public", "private", "protected","default"};
        for (String keyword : keywords) 
        {
            if (keyword.equals(word)) 
            {
                return true;
            }
        }     
        return false;
    }

    // Risultato lettura: "Tipo x", con x il tipo analizzato
    /**
     * Valuta se si tratta di un tipo
     * @param word  , la parola
     * @return 
     */
    protected static boolean isType(String word) 
    {
        String [] keywords = { "int", "float", "double", "boolean", "String", "void"};
        for (String keyword : keywords) 
        {
            if (keyword.equals(word)) 
            {
                return true;
            }
        }     
        return false;
    }

    // Risultato lettura: "Tipo x", con x il tipo analizzato
    /**
     * Valuta se una parola ricade nella casistica di una porzione non codificante
     * @param word 
     * @return boolean , true se è una porzione non codificante, false altrimenti
     */
    protected static boolean isNonCoding(String word) 
    {
        String [] keywords = { "//","/*","*/","\"","\'"};
        for (String keyword : keywords) 
        {
            if (keyword.equals(word)) 
            {
                return true;
            }
        }     
        return false;
    }

    // Gestione dell'enfasi sulla lettura delle keyword di linguaggio
    /**
     * gestisce l'enfasi per specifiche parole 
     * @param word , la stringa da analizzare
     * @param flagInsideNonCoding , flag che verifica se la parola si trova dentro una porzione non codificante
     * @param oldType , considera il vecchio tipo, se dentro una prozione non codificante
     * @return String , la parola processata
     */
    public static String processWord(String word,boolean flagInsideNonCoding,TypeEnum oldType) 
    {   
        // CASO 1 : si tratta di visibilità
        if(isVisibility(word))
        {
            switch(word) 
            {
                case "public"->
                {
                    word = "Pablic";
                }

                case "protected"->
                {
                    word = "Protect";
                }

                case "private"->
                {
                    word = "Prai vat";
                }

                case "default"->
                {
                    word = "defoult";
                }

            }
            return ("Visibilità " + word + ",\n");
        }
        
        // CASO 2 : si tratta di un tipo
        if(isType(word))
        {
            return ("Tipo " + word + ",\n");
        }
        
        // CASO 3 : potrebbe essere un commento
        if(isNonCoding(word))
        {
            // CASO 3.1 -> è davvero un elemento non codificante
            if(flagInsideNonCoding == false)
            {
                switch (word) 
                {
                    case "//"->
                    {
                        word = "Inizio commento";
                        //oldType = NonCodingType.COMMENT;
                    }

                    case "/*"->
                    {
                        word = "Apertura commento multiriga";
                        //oldType = NonCodingType.COMMENT_OPEN;
                    }
                    
                    case "*/"->
                    {
                        word = "Errore, chiusura commento multiriga senza apertura";
                        return null;    //<--- decidere cosa fare ahah
                    }

                    case "\""->
                    {
                        word = "Apertura stringa";
                        //oldType = NonCodingType.STRING;
                    }

                    case "'"->
                    {
                        word = "Inizio carattere";
                        //oldType = NonCodingType.CHAR;
                    }
                }

                //flagInsideNonCoding = true;
            }

            // CASO 3.2 -> non è lui ad essere un elemento non codificante
            else     //<--- ovvero sono dentro
            {
                switch (word) 
                {
                    case "//"->
                    {
                        word = "Doppio slesh";
                    }

                    case "/*"->
                    {
                        word = "slesh star";
                    }
                    
                    case "*/"->
                    {
                        if(oldType == TypeEnum.COMMENT_OPEN)
                        {
                            word = "Fine Commento multiriga";
                            //oldType = NonCodingType.NODATA; //<--- aggiorno, siccome per ora non ho info
                        }
                        else
                        {
                            word = "star slesh";
                        }
                    }

                    case "\""->
                    {
                        if(oldType == TypeEnum.STRING)
                        {
                            word = "Fine stringa";
                            //oldType = NonCodingType.NODATA; //<--- aggiorno, siccome per ora non ho info
                        }
                        else
                        {
                            // non so se abbia senso dire "doppi apic"
                        }
                    }

                    case "'"->
                    {
                        if(oldType == TypeEnum.CHAR)
                        {
                            word = "Fine carattere";
                            //oldType = NonCodingType.NODATA; //<--- aggiorno, siccome per ora non ho info
                        }
                        else
                        {
                            // si potrebbe dire "apostrofo", ma non risulta praticamente mai utile
                        }
                        
                    }
                }
            }

            return word + "\n";
        }

        // ALTRI CASI
        switch(word)
        {
            case "class"->
            {
                return "Classe ";
            }

            case "static"->
            {
                return "Componente Statico. ";
            }

            case "main"->
            {
                return "Funzione mein \n";
            }

            case "{"->
            {
                return "Aperta graffa.\n";
            }

            case "}"-> 
            {
                return "Chiusa graffa.\n";
            }

            case "("-> 
            {
                return "Aperta tonda.\n";
            }

            case ")"-> 
            {
                return "Chiusa tonda.\n";
            }

            case "["-> 
            {
                return "Aperta quadra.\n";
            }
            case "]"-> 
            {
                return "Chiusa quadra.\n";
            }

            case ";"-> 
            { 
                return ".\n";
            }

            case "="-> 
            {
                return "Uguale. ";
            }
        
            case "+"-> 
            {
                return "Più. ";
            }

            case "/" -> 
            {
                return "Diviso. ";
            }

            case "<" -> 
            {
                return "Minore. ";
            }
            case ">" -> 
            {
                return "Maggiore. ";
            }

            case "@" -> 
            {
                return "Chiocciola.";
            }

            case "?" -> 
            {
                return "Punto di domanda.\n";
            }

            case "!" -> 
            {
                if(flagInsideNonCoding == false)
                {
                    return "Non. ";
                }
                else
                {

                }
            }

            case "out" -> 
            {
                return "aut\u200B .\n";
            }

            case "println" -> 
            {
                return "Printlain" + ".\n";
            }

            case "length" -> 
            {
                return "Lengt" + ".\n";
            }

            case "Override" -> 
            {
                return "Overraid" + ".\n"; 
            }

            case "false" -> 
            {
                return "Fols" + ".\n"; 
            }

            case "true" -> 
            {
                return "True" + ".\n"; 
            }

            case "args" -> 
            {
                return "Args" + ".\n";
            }

            case "catch" -> 
            {
                return "Catch" + ".\n";
            }

            case "return" -> 
            {
                return "Return" + ".\n";
            }

            case "extends" -> 
            {
                return "Extends" + ".\n";
            }

            case "." -> 
            {
                return "Punto ";
            }

            case "," -> 
            {
                return "Virgola, ";
            }

            case "if" -> 
            {
                return "If " + "\n"; 
            }

            case "this" -> 
            {
                return "This. ";
            }

            case "\n" ->
            {
                return "Fine riga.\n";
            }
        }
    
        // default
        if(flagInsideNonCoding) //<-- se sono all'interno di qu
        {
            return (word.toUpperCase().concat(" ")) ;    
        }
        else
        {
            return (word.toUpperCase().concat("\n")) ;
        }
    }    
}
