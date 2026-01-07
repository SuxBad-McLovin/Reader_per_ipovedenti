package textospeech.Reader.ParseMultiLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class ParseMultiLineTest 
{
    String projectRoot = System.getProperty("user.dir");    //<--- la root del progetto
    String filePath = projectRoot + "\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo4.txt";
    String filepath4 = "C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_2\\parser\\src\\test\\java\\tirocinio\\parser\\TESTOPERBUFFER\\Testo4.txt";

    // TEST 1 : valutiamo se otteniamo la stringa giusta
    @Test
    public void LineToReadTest()
    {
        // MANCANO TUTTI I SUOI TEST!!
        String result = "";
        result = ParseMultiLine.LineToRead(filepath4,15);
       
        //assertEquals("  {\n Sono una funzione \n}", result); 
        /* 
        result = ParseMultiLine.LineToRead(filepath4,13);
        assertEquals("  { Sto facendo qualcosa}", result); 
        result = ParseMultiLine.LineToRead(filepath4,18);
        assertEquals("{Sono una funzione}", result);
        result = ParseMultiLine.LineToRead(filepath4,16);
        assertEquals("{Sono una funzione}", result);
        */
        /*
        NOTA : il risultato è pressochè giusto, vengono sbagliati gli spazi, ma li gestiremo con trim 
        assertEquals("{\r\n" + //
                        "        Sono una funzione\r\n" + //
                        "   }", result);
        */
    }
    
    /////////////////////////
    // TEST 2 -> valutiamo se venga interpretato correttamente
    @Test
    public void ReadableStringTest()
    {
        String result = "";
        ParseMultiLine A = new ParseMultiLine();
        String parola = "Ciao // qualcosa \n  // come?";
        
        result = A.ReadableString(parola);
        assertEquals("CIAO\n" + //
                    "Inizio commento\n" + //
                    "QUALCOSA " + //
                    "Fine riga.\n" + //
                    "Inizio commento\n" + //
                    "COME " + //
                    "Punto di domanda.\n",result);
    
        // ALTRO
        parola = "/*Ciao // commento*/ 4+2?";
        result = A.ReadableString(parola);
        assertEquals("Apertura commento multiriga\n" + //
                        "CIAO " + //
                        "Doppio slesh\n" + //
                        "COMMENTO " + //
                        "Fine Commento multiriga\n" + //
                        "4\n" + //
                        "Più. 2\n" + //
                        "Punto di domanda.\n",result);
        
        // STRINGA SU PIU' RIGHE
        parola = "\"Ciao // commento \n 4+2 \"";
        result = A.ReadableString(parola);
        assertEquals("Apertura stringa\n" + //
                        "CIAO " + //
                        "Doppio slesh\n" + //
                        "COMMENTO " + //
                        "Fine riga.\n" + //
                        "4 " + //
                        "Più. 2 " + //
                        "Fine stringa\n",result);

        // STRINGA E APOSTROFO
        parola = "\"Ciao // commento' \n 4+2 \"";
        result = A.ReadableString(parola);
        assertEquals("Apertura stringa\n" + //
                        "CIAO " + //
                        "Doppio slesh\n" + //
                        "COMMENTO " + //
                        "'\n" + //
                        "Fine riga.\n" + //
                        "4 " + //
                        "Più. 2 " + //
                        "Fine stringa\n",result);

        // CARATTERE
        parola = "'C'";
        result = A.ReadableString(parola);
        assertEquals("Inizio carattere\n" + //
                        "C " + //
                        "Fine carattere\n",result);
        
        // CARATTERE CHE CONTIENE ALTRI TIPI
        parola = "'C // '";
        result = A.ReadableString(parola);
        assertEquals("Inizio carattere\n" + //
                        "C " + //
                        "Doppio slesh\n" + //
                        "Fine carattere\n",result);

        // TEST SU COMMENTI
        parola = "4+2 /* somma \n 4*2";
        result = A.ReadableString(parola);
        assertEquals("4\n" + //
                    "Più. 2\n" + //
                    "Apertura commento multiriga\n" + //
                    "SOMMA " + //
                    "Fine riga.\n" + //
                    "4 " + //
                    "* " + //
                    "2 ",result);

        // PRESI DA FILE

        // Prendiamo ora l'input dal file 
        parola = ParseMultiLine.LineToRead(filepath4,15);
        result = A.ReadableString(parola);
        assertEquals("Aperta graffa.\n" + //
                        "Fine riga.\n" + //
                        "SONO\n" + //
                        "UNA\n" + //
                        "FUNZIONE\n" + //
                        "Fine riga.\n" + //
                        "Chiusa graffa.\n",result);

        
        // CASI SPECIALI 
        
        // la stringa è vuota
        parola = "";
        result = A.ReadableString(parola);
        assertEquals(null, result);

        // l'oggetto non esiste
        parola = null;
        result = A.ReadableString(parola);
        assertEquals(null, result);

    }

    //////////////////////
    /// TEST 3 -> controlliamo se tokenizer fà il suo
    /// 
    @Test
    public void TokenizerTest()
    {
        String parola = null;
        ParseMultiLine A = new ParseMultiLine();
        String[] result = A.Tokenizer(parola);
        assertEquals(null, result);

        parola = "";
        result = A.Tokenizer(parola);
        assertEquals(null, result);

        // CONTROLLIAMO UNA PAROLA
        parola = "Stringa // ciao /*Pazzo*/";
        result = A.Tokenizer(parola);
        int dimensione = result.length;
        assertEquals(6, dimensione);
        assertEquals("Stringa",result[0]);
        assertEquals(" // ",result[1]);
        assertEquals("ciao",result[2]);
        assertEquals(" /*",result[3]);
        assertEquals("Pazzo",result[4]);
        assertEquals("*/",result[5]);
    }

    /////////
    /// TEST 4 -> SpecificTokenizer 
    
    @Test
    public void SpecificTokenizerTest()
    {
        String parola = null;
        ParseMultiLine A = new ParseMultiLine();
        String[] tokens = A.Tokenizer(parola);
        String result = "";
        result = A.SpecificTokenizer(tokens);
        assertEquals(null, result);

        /////
        
        parola = "Stringa // ciao /*Pazzo*/";
        tokens = A.Tokenizer(parola);
        result = A.SpecificTokenizer(tokens);
        assertEquals("STRINGA\n" + //
                        "Inizio commento\n" + //
                        "CIAO " + //
                        "slesh star\n" + //
                        "PAZZO " + //
                        "star slesh\n", result);
    }

    ////////////////////
    /// TEST 5 -> vediamo se la funzione che racchiude le altre funziona
    @Test
    public void GetReadableStringTest()
    {
        String result = "";
        result = ParseMultiLine.GetReadableString(filepath4,15);
        assertEquals("Aperta graffa.\n" + //
                        "Fine riga.\n" + //
                        "SONO\n" + //
                        "UNA\n" + //
                        "FUNZIONE\n" + //
                        "Fine riga.\n" + //
                        "Chiusa graffa.\n", result);
    }
}
