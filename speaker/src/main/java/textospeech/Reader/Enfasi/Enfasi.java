package textospeech.Reader.Enfasi;

public class Enfasi 
{
    // Risultato lettura: "Visibilità x", con x = public, private, protected
    protected static boolean isVisibility(String word) 
    {
        String [] keywords = { "public", "private", "protected"};
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
    protected static boolean isType(String word) {
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

    // Gestione dell'enfasi sulla lettura delle keyword di linguaggio
    public static String processWord(String word) 
    {   
        // CASO 1 : si tratta di visibilità
        if(isVisibility(word))
        {
            switch(word) 
            {
                case "public"->
                {
                    word = "Pablic ";
                }

                case "protected"->
                {
                    word = "Protect ";
                }

                case "private"->
                {
                    word = "Prai vat ";
                }

                case "default"->
                {
                    word = "defoult ";
                }

            }
            return ("Visibilità " + word + ",\n");
        }
        
        // CASO 2 : si tratta di un tipo
        if(isType(word))
        {
            return ("Tipo " + word + ",\n");
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
                return "Mein \n";
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
                return "Uguale ";
            }
        
            case "+"-> 
            {
                return "Più ";
            }

            case "/" -> 
            {
                return "Diviso ";
            }

            case "<" -> 
            {
                return "Minore ";
            }
            case ">" -> 
            {
                return "Maggiore ";
            }

            case "@" -> 
            {
                return "Chiocciola ";
            }

            case "?" -> 
            {
                return "Punto di domanda. ";
            }

            case "!" -> 
            {
                return "Non. ";
            }

            case "out" -> 
            {
                return "aut\u200B \n";
            }

            case "println" -> 
            {
                return "Printlain" + "\n";
            }

            case "length" -> 
            {
                return "Lengt" + ".\n";
            }

            case "Override" -> 
            {
                return "Overraid" + "\n"; 
            }

            case "false" -> 
            {
                return "Fols" + "\n"; 
            }

            case "true" -> 
            {
                return "True" + "\n"; 
            }

            case "args" -> 
            {
                return "Args" + "\n";
            }

            case "catch" -> 
            {
                return "Catch" + "\n";
            }

            case "return" -> 
            {
                return "Return" + "\n";
            }

            case "extends" -> 
            {
                return "Extends" + "\n";
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
                return "If " + ".,\n"; 
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
         
        if(word.startsWith("\"") && word.endsWith("\"")) 
        {
            return ("Contenuto stringa: " + word + ".\n");
        }
    
        // default
        return (word.toUpperCase() + "\n");   
    }    
}
