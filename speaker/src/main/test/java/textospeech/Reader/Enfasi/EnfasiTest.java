package textospeech.Reader.Enfasi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;

public class EnfasiTest 
{
    // TEST 1
    // controlliamo se "isVisibility" funziona
    @Test
    public void isVisibilityTest()
    {
        boolean result = false;
        String parola = "public";
        result = Enfasi2.isVisibility(parola);
        assertEquals(true, result);

        /////////
        parola = "private";
        result = Enfasi2.isVisibility(parola);
        assertEquals(true, result);

        //////////
        parola = "protected";
        result = Enfasi2.isVisibility(parola);
        assertEquals(true, result);

        //////////
        parola = "default";
        result = Enfasi2.isVisibility(parola);
        assertEquals(true, result);

        //////////
        parola = "ciao";
        result = Enfasi2.isVisibility(parola);
        assertEquals(false, result);
    }  
    
    
    //////////////////////////////////////////
    /// TEST 2 -> controlliamo isType 
    @Test
    public void isTypeTest()
    {
        boolean result = false;
        String parola = "int";
        result = Enfasi2.isType(parola);
        assertEquals(true, result);

        /////////
        parola = "float";
        result = Enfasi2.isType(parola);
        assertEquals(true, result);

        //////////
        parola = "double";
        result = Enfasi2.isType(parola);
        assertEquals(true, result);

        //////////
        parola = "boolean";
        result = Enfasi2.isType(parola);
        assertEquals(true, result);

        //////////
        parola = "String";
        result = Enfasi2.isType(parola);
        assertEquals(true, result);

        //////////
        parola = "void";
        result = Enfasi2.isType(parola);
        assertEquals(true, result);

        //////////
        parola = "ciao";
        result = Enfasi2.isType(parola);
        assertEquals(false, result);
    }

    ///////////////////////
    /// TEST 3 -> controlliamo "isNonCoding"
    
    @Test
    public void isNonCodingTest()
    {
        boolean result = false;
        String parola = "//";
        result = Enfasi2.isNonCoding(parola);
        assertEquals(true, result);

        /////////
        parola = "/*";
        result = Enfasi2.isNonCoding(parola);
        assertEquals(true, result);

        //////////
        parola = "*/";
        result = Enfasi2.isNonCoding(parola);
        assertEquals(true, result);

        //////////
        parola = "\"";
        result = Enfasi2.isNonCoding(parola);
        assertEquals(true, result);

        //////////
        parola = "\'";
        result = Enfasi2.isNonCoding(parola);
        assertEquals(true, result);

        //////////
        parola = "ciao";
        result = Enfasi2.isNonCoding(parola);
        assertEquals(false, result);
    }

    ////////////////////////
    /// TEST 4 -> testiamo "processWord"
    /// 
    
    @Test
    public void ProcessWordTest()
    {
        String parola = "/*";
        boolean flag = false;
        TypeEnum oldType = TypeEnum.NODATA;
        String result = Enfasi2.processWord(parola,flag,oldType);

        assertEquals("Apertura commento multiriga\n", result);

        // TEST SUCCESSIVI
        parola = "//";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Inizio commento\n", result);

        // ALTRO
        parola = "*/";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals(null, result);

        // ALTRO
        parola = "\"";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Apertura stringa\n", result);

        // ALTRO
        parola = "\'";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Inizio carattere\n", result);

        ////////////////////////////////
        // TESTIAMO ORA CON LA FLAG SU TRUE

        flag = true;
        parola = "//";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Doppio slesh\n", result);

        flag = true;
        parola = "/*";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("slesh star\n", result); 

        // CASO PARTICOLARE
        // CASO 1 -> solo la flag è vera 

        flag = true;
        parola = "*/";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("star slesh\n", result);

        // CASO 2 -> oldType coincide con l'elemento 
        flag = true;
        oldType = TypeEnum.COMMENT_OPEN;
        parola = "*/";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Fine Commento multiriga\n", result);

        // CASO STRINGA
        // CASO 1 -> solo la flag è vera 

        flag = true;
        parola = "\"";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("\"\n", result);

        // CASO 2 -> oldType coincide con l'elemento 
        flag = true;
        oldType = TypeEnum.STRING;
        parola = "\"";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Fine stringa\n", result);

        // CASO CARATTERE
        // CASO 1 -> solo la flag è vera 

        flag = true;
        parola = "'";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("'\n", result);

        // CASO 2 -> oldType coincide con l'elemento 
        flag = true;
        oldType = TypeEnum.CHAR;
        parola = "'";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Fine carattere\n", result);


        ///////////////////////////
        /// CONTROLLIAMO LE PAROLE ORA
        parola = "public";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Visibilità Pablic,\n", result);

        parola = "protected";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Visibilità Protect,\n", result);

        parola = "default";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Visibilità defoult,\n", result);

        parola = "private";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Visibilità Prai vat,\n", result);

        //////////
        /// Controlliamo ora i tipi
        parola = "int";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Tipo int,\n", result);

        parola = "float";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Tipo float,\n", result);

        parola = "double";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Tipo double,\n", result);

        parola = "boolean";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Tipo boolean,\n", result);

        parola = "String";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Tipo String,\n", result);

        parola = "void";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Tipo void,\n", result);

        ////////////////
        /// CONTROLLIAMO ORA I CASI GENERICI
        
        parola = "class";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Classe ", result);

        parola = "static";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Componente Statico. ", result);

        parola = "main";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Funzione mein \n", result);

        parola = "{";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Aperta graffa.\n", result);

        parola = "}";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Chiusa graffa.\n", result);

        parola = "(";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Aperta tonda.\n", result);

        parola = ")";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Chiusa tonda.\n", result);

        parola = "[";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Aperta quadra.\n", result);

        parola = "]";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Chiusa quadra.\n", result);

        parola = ";";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals(".\n", result);

        parola = "=";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Uguale. ", result);

        parola = "+";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Più. ", result);

        parola = "/";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Diviso. ", result);

        parola = "<";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Minore. ", result);

        parola = ">";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Maggiore. ", result);

        parola = "@";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Chiocciola.", result);

        parola = "?";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Punto di domanda.\n", result);

        parola = "!";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("! ", result);

        parola = "out";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("aut\u200B .\n", result);

        parola = "println";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Printlain.\n", result);

        parola = "length";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Lengt.\n", result);

        parola = "Override";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Overraid.\n", result);

        parola = "false";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Fols.\n", result);

        parola = "true";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("True.\n", result);

        parola = "args";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Args.\n", result);

        parola = "catch";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Catch.\n", result);

        parola = "catch";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Catch.\n", result);

        parola = "return";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Return.\n", result);

        parola = "extends";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Extends.\n", result);

        parola = ".";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Punto ", result);

        parola = ",";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Virgola, ", result);

        parola = "if";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("If \n", result);

        parola = "this";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("This. ", result);

        parola = "\n";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("Fine riga.\n", result);

        ///////////////
        /// TESTIAMO UN CASO DI DEFAULT
        
        parola = "Ciaooo";
        result = Enfasi2.processWord(parola,flag,oldType);
        assertEquals("CIAOOO ", result);

    }   

}
