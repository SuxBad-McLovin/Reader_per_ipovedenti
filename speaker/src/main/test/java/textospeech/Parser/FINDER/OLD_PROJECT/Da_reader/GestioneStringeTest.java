package textospeech.Parser.FINDER.OLD_PROJECT.Da_reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.ListPos;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.List_intervals;
import textospeech.Parser.NORMALIZZAZIONE.Normalizzazione;


public class GestioneStringeTest 
{
    String projectRoot = System.getProperty("user.dir");    //<--- la root del progetto
    String filePath = projectRoot + "\\src\\test\\resources\\PARSER\\DA_READER\\testo_1.txt";
    String filePath2 = projectRoot + "\\src\\test\\resources\\PARSER\\DA_READER\\testo_2.txt";
    String filePath3 = projectRoot + "\\src\\test\\resources\\PARSER\\DA_READER\\testo_3.txt";
    // è vuota
    String filePath4 = projectRoot + "\\src\\test\\resources\\PARSER\\DA_READER\\testo_4.txt";
    // non contiene caratteri non codificanti
    String filePath5 = projectRoot + "\\src\\test\\resources\\PARSER\\DA_READER\\testo_5.txt";
    // testo con delle combinazioni strane
    String filePath6 = projectRoot + "\\src\\test\\resources\\PARSER\\DA_READER\\testo_6.txt";

    // TEST DI DEBUG
    // Commentare se non si è il programmatore originale (o aggiustarlo con i vostri path assoluti)
    @Test
    public void PathExistTest()
    {
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\DA_READER\\testo_1.txt", filePath);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\DA_READER\\testo_2.txt", filePath2);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\DA_READER\\testo_3.txt", filePath3);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\DA_READER\\testo_4.txt", filePath4);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\DA_READER\\testo_5.txt", filePath5);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\DA_READER\\testo_6.txt", filePath6);
    }

    // TEST -> valutiamo se funziona la funzione che automatiza
    @Test
    public void BuildIntervalsTest()
    {
        List_intervals lista = new List_intervals();
        lista = Gestione_stringhe.BuildIntervals(filePath);
        assertEquals(2, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getType());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getType());
        // CONTROLLIAMO LE POSIZIONI
        assertEquals(2, lista.getLista().get(0).getInit().getCol());
        assertEquals(8, lista.getLista().get(0).getEnd().getCol());
        assertEquals(1, lista.getLista().get(0).getInit().getRig());
        assertEquals(1, lista.getLista().get(0).getEnd().getRig());

        assertEquals(12, lista.getLista().get(1).getInit().getCol());
        assertEquals(0, lista.getLista().get(1).getEnd().getCol());
        assertEquals(1, lista.getLista().get(1).getInit().getRig());
        assertEquals(2, lista.getLista().get(1).getEnd().getRig());

        // CONTROLLIAMO SE I TIPI DELLE POSIZIONI SONO CORRETTI
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getInit().getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getEnd().getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getInit().getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getEnd().getTipo());


        ///////////////
        /// NUOVO TEST -> filepath 2
        lista = Gestione_stringhe.BuildIntervals(filePath2);
        assertEquals(3, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getType());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getType());
        assertEquals(TypeEnum.MULTI, lista.getLista().get(2).getType());
        // CONTROLLIAMO LE POSIZIONI
        assertEquals(2, lista.getLista().get(0).getInit().getCol());
        assertEquals(8, lista.getLista().get(0).getEnd().getCol());
        assertEquals(1, lista.getLista().get(0).getInit().getRig());
        assertEquals(1, lista.getLista().get(0).getEnd().getRig());

        assertEquals(12, lista.getLista().get(1).getInit().getCol());
        assertEquals(0, lista.getLista().get(1).getEnd().getCol());
        assertEquals(1, lista.getLista().get(1).getInit().getRig());
        assertEquals(2, lista.getLista().get(1).getEnd().getRig());

        assertEquals(6, lista.getLista().get(2).getInit().getCol());
        assertEquals(17, lista.getLista().get(2).getEnd().getCol());
        assertEquals(3, lista.getLista().get(2).getInit().getRig());
        assertEquals(5, lista.getLista().get(2).getEnd().getRig());



        // CONTROLLIAMO SE I TIPI DELLE POSIZIONI SONO CORRETTI
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getInit().getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getEnd().getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getInit().getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getEnd().getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(2).getInit().getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(2).getEnd().getTipo());

        ////////////////////
        /// NUOVO TEST -> filepath3
        /// 
        lista = Gestione_stringhe.BuildIntervals(filePath3);
        assertEquals(6, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getType());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getType());
        assertEquals(TypeEnum.MULTI, lista.getLista().get(2).getType());
        assertEquals(TypeEnum.STRING, lista.getLista().get(3).getType());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(4).getType());
        assertEquals(TypeEnum.MULTI, lista.getLista().get(5).getType());
        // CONTROLLIAMO LE POSIZIONI
        assertEquals(2, lista.getLista().get(0).getInit().getCol());
        assertEquals(8, lista.getLista().get(0).getEnd().getCol());
        assertEquals(1, lista.getLista().get(0).getInit().getRig());
        assertEquals(1, lista.getLista().get(0).getEnd().getRig());

        assertEquals(12, lista.getLista().get(1).getInit().getCol());
        assertEquals(0, lista.getLista().get(1).getEnd().getCol());
        assertEquals(1, lista.getLista().get(1).getInit().getRig());
        assertEquals(2, lista.getLista().get(1).getEnd().getRig());

        assertEquals(1, lista.getLista().get(3).getInit().getCol());
        assertEquals(31, lista.getLista().get(3).getEnd().getCol());
        assertEquals(6, lista.getLista().get(3).getInit().getRig());
        assertEquals(6, lista.getLista().get(3).getEnd().getRig());

        assertEquals(1, lista.getLista().get(4).getInit().getCol());
        assertEquals(6, lista.getLista().get(4).getEnd().getCol());
        assertEquals(7, lista.getLista().get(4).getInit().getRig());
        assertEquals(7, lista.getLista().get(4).getEnd().getRig());

        assertEquals(10, lista.getLista().get(5).getInit().getCol());
        assertEquals(21, lista.getLista().get(5).getEnd().getCol());
        assertEquals(7, lista.getLista().get(5).getInit().getRig());
        assertEquals(7, lista.getLista().get(5).getEnd().getRig());

        // CONTROLLIAMO SE I TIPI DELLE POSIZIONI SONO CORRETTI
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getInit().getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getEnd().getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getInit().getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getEnd().getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(2).getInit().getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(2).getEnd().getTipo());

        /////////////////////////////
        /// CASI PARTICOLARI
        
        // file vuoto
        lista = Gestione_stringhe.BuildIntervals(filePath4);
        assertEquals(null, lista);
         
        //file che non contiene caratteri non codificanti
        lista = Gestione_stringhe.BuildIntervals(filePath5);
        assertEquals(null, lista);

        ////////////////////////////////
        /// TEST COMPLESSO -> filepath 6
        lista = Gestione_stringhe.BuildIntervals(filePath6);
        assertEquals(15, lista.getLista().size());
    } 


    //////////////////////
    /// TEST 2 -> controlliamo se trova tutte le sequenze non codificanti
    
    @Test
    public void NonCodingPerLineTest()
    {
        // variabili
        Gestione_stringhe A = new Gestione_stringhe();
        ListPos lista = new ListPos();
        String testo = "/*Ciao*/";

        // CASO 1 -> commenti multiriga
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(2, lista.getLista().size());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(1).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(1, lista.getLista().get(0).getCol());
        assertEquals(7, lista.getLista().get(1).getCol());

        // CASO 2 -> commenti
        testo = "ciao // altro";
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(1, lista.getLista().size());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(0).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(6, lista.getLista().get(0).getCol());

        // CASO 3 -> stringa
        testo = " \"ciao\" ";
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(2, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(1).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(7, lista.getLista().get(1).getCol());

        // CASO 4 -> carattere
        testo = " 'ciao' ";
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(2, lista.getLista().size());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(1).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(7, lista.getLista().get(1).getCol());

        //////////////////
        /// CASI PARTICOLARI
        
        //stringa vuota
        testo = "";
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(null, lista);

        //viene passato null
        testo = null;
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(null, lista);

        // viene passato un numero di riga errato (minore di 0)
        testo = "Ciao";
        lista = A.NonCodingPerLine(testo,-1);
        assertEquals(null, lista);

        // mix dei due 
        testo = null;
        lista = A.NonCodingPerLine(testo,-1);
        assertEquals(null, lista);


        // stringa contenente un'unico carattere
        testo = "a";
        lista = A.NonCodingPerLine(testo, 0);
        assertEquals(null, lista);

        // stringa contenente unico carattere "
        testo = "\"";
        lista = A.NonCodingPerLine(testo, 0);
        assertEquals(1, lista.getLista().size());

        // stringa contenente unico carattere "
        testo = "'";
        lista = A.NonCodingPerLine(testo, 0);
        assertEquals(1, lista.getLista().size());

        // COMBINAZIONI

        //commento dentro una stringa
        testo = " \"//ciao\" ";
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(3, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(2).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(3, lista.getLista().get(1).getCol());
        assertEquals(9, lista.getLista().get(2).getCol());

        // altri test eventuali 


        // TEST IMPORTANTI

        // si riescono ad evitare di classificare \" come stringa
        testo = " \" \\\"ciao\\\" \" ";     //--> nel testo viene interpretato come " \"ciao\" "
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(2, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(1).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(13, lista.getLista().get(1).getCol());

        // si riescono ad evitare di classificare \' come dei char
        testo = " ' \\\'ciao\\\' ' ";     //--> nel testo viene interpretato come " ' \'ciao\' ' "
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(2, lista.getLista().size());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(1).getTipo());
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(13, lista.getLista().get(1).getCol());


        // TEST COMPLESSO
        testo = "   //  //  //  //  //\t// <--- mi serve per controllare \"RECURSIVE_end_comment\"";     //--> nel testo viene interpretato come " ' \'ciao\' ' "
        testo = Normalizzazione.NormalizedString(testo);    //<--- serve per contare correttamente la posizione
        lista = A.NonCodingPerLine(testo,0);
        assertEquals(8, lista.getLista().size());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(1).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(2).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(3).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(4).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(5).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(6).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(7).getTipo());

        assertEquals(4, lista.getLista().get(0).getCol());
        assertEquals(8, lista.getLista().get(1).getCol());
        assertEquals(12, lista.getLista().get(2).getCol());
        assertEquals(16, lista.getLista().get(3).getCol());
        assertEquals(20, lista.getLista().get(4).getCol());
        assertEquals(25, lista.getLista().get(5).getCol());
        assertEquals(58, lista.getLista().get(6).getCol());
        assertEquals(80, lista.getLista().get(7).getCol());

    }

    ////////////////////////////////////////
    /// TEST 3 -> AllNonCoding, ovvero trovare tutti gli elementi non codificanti di tutte le righe
    
    @Test
    public void AllNonCodingTest()
    {
        // variabili
        Gestione_stringhe A = new Gestione_stringhe();
        ListPos lista = new ListPos();

        // CASO 1 -> filepath 1 (tutto su stessa riga)
        lista = A.AllNonCoding(filePath);
        assertEquals(3, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(1).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(2).getTipo());
        
        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(1, lista.getLista().get(1).getRig());
        assertEquals(1, lista.getLista().get(2).getRig());

        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(8, lista.getLista().get(1).getCol());
        assertEquals(12, lista.getLista().get(2).getCol());
        

        // CASO 2 -> filepath 2 (su più righe)
        lista = A.AllNonCoding(filePath2);
        assertEquals(8, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(1).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(2).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(3).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(4).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(5).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(6).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(7).getTipo());

        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(1, lista.getLista().get(1).getRig());
        assertEquals(1, lista.getLista().get(2).getRig());
        assertEquals(3, lista.getLista().get(3).getRig());
        assertEquals(4, lista.getLista().get(4).getRig());
        assertEquals(4, lista.getLista().get(5).getRig());
        assertEquals(5, lista.getLista().get(6).getRig());
        assertEquals(5, lista.getLista().get(7).getRig());
        
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(8, lista.getLista().get(1).getCol());
        assertEquals(12, lista.getLista().get(2).getCol());
        assertEquals(6, lista.getLista().get(3).getCol());
        assertEquals(12, lista.getLista().get(4).getCol());
        assertEquals(24, lista.getLista().get(5).getCol());
        assertEquals(1, lista.getLista().get(6).getCol());
        assertEquals(17, lista.getLista().get(7).getCol());

        // CASO 3 -> filepath 3 (più lungo e più complesso)
        lista = A.AllNonCoding(filePath3);
        assertEquals(17, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(1).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(2).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(3).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(4).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(5).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(6).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(7).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(8).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(9).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(10).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(11).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(12).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(13).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(14).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(15).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(16).getTipo());

        assertEquals(1, lista.getLista().get(0).getRig());
        assertEquals(1, lista.getLista().get(1).getRig()); 
        assertEquals(1, lista.getLista().get(2).getRig());
        assertEquals(3, lista.getLista().get(3).getRig());
        assertEquals(4, lista.getLista().get(4).getRig());
        assertEquals(4, lista.getLista().get(5).getRig());
        assertEquals(5, lista.getLista().get(6).getRig());
        assertEquals(5, lista.getLista().get(7).getRig());
        assertEquals(6, lista.getLista().get(8).getRig());
        assertEquals(6, lista.getLista().get(9).getRig());
        assertEquals(6, lista.getLista().get(10).getRig());
        assertEquals(6, lista.getLista().get(11).getRig());
        assertEquals(6, lista.getLista().get(12).getRig());
        assertEquals(7, lista.getLista().get(13).getRig());
        assertEquals(7, lista.getLista().get(14).getRig());
        assertEquals(7, lista.getLista().get(15).getRig());
        assertEquals(7, lista.getLista().get(16).getRig());
        
        assertEquals(2, lista.getLista().get(0).getCol());
        assertEquals(8, lista.getLista().get(1).getCol());
        assertEquals(12, lista.getLista().get(2).getCol());
        assertEquals(6, lista.getLista().get(3).getCol());
        assertEquals(12, lista.getLista().get(4).getCol());
        assertEquals(24, lista.getLista().get(5).getCol());
        assertEquals(1, lista.getLista().get(6).getCol());
        assertEquals(17, lista.getLista().get(7).getCol());
        assertEquals(1, lista.getLista().get(8).getCol());
        assertEquals(8, lista.getLista().get(9).getCol());
        assertEquals(16, lista.getLista().get(10).getCol());
        assertEquals(19, lista.getLista().get(11).getCol());
        assertEquals(31, lista.getLista().get(12).getCol());
        assertEquals(1, lista.getLista().get(13).getCol());
        assertEquals(6, lista.getLista().get(14).getCol());
        assertEquals(10, lista.getLista().get(15).getCol());
        assertEquals(21, lista.getLista().get(16).getCol());
        
        //////////////////////////
        /// CASO 4 -> filepath 6 , molti casi complicati
        lista = A.AllNonCoding(filePath6);
        assertEquals(36, lista.getLista().size());
        assertEquals(TypeEnum.STRING, lista.getLista().get(0).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(1).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(2).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(3).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(4).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(5).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(6).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(7).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(8).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(9).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(10).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(11).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(12).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(13).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(14).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(15).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(16).getTipo());
        assertEquals(TypeEnum.CHAR, lista.getLista().get(17).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(18).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(19).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(20).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(21).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(22).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(23).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(24).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(25).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(26).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(27).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(28).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(29).getTipo());
        assertEquals(TypeEnum.STRING, lista.getLista().get(30).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(31).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(32).getTipo());
        assertEquals(TypeEnum.COMMENT, lista.getLista().get(33).getTipo());
        assertEquals(TypeEnum.COMMENT_OPEN, lista.getLista().get(34).getTipo());
        assertEquals(TypeEnum.COMMENT_CLOSE, lista.getLista().get(35).getTipo());

        // RIGHE

        assertEquals(5, lista.getLista().get(0).getRig());
        assertEquals(5, lista.getLista().get(1).getRig());
        assertEquals(5, lista.getLista().get(2).getRig());
        assertEquals(7, lista.getLista().get(3).getRig());
        assertEquals(9, lista.getLista().get(4).getRig());
        assertEquals(12, lista.getLista().get(5).getRig());
        assertEquals(14, lista.getLista().get(6).getRig());
        assertEquals(14, lista.getLista().get(7).getRig());
        assertEquals(14, lista.getLista().get(8).getRig());
        assertEquals(14, lista.getLista().get(9).getRig());
        assertEquals(14, lista.getLista().get(10).getRig());
        assertEquals(14, lista.getLista().get(11).getRig());
        assertEquals(14, lista.getLista().get(12).getRig());
        assertEquals(14, lista.getLista().get(13).getRig());
        assertEquals(16, lista.getLista().get(14).getRig());
        assertEquals(16, lista.getLista().get(15).getRig());
        assertEquals(18, lista.getLista().get(16).getRig());
        assertEquals(18, lista.getLista().get(17).getRig());
        assertEquals(20, lista.getLista().get(18).getRig());
        assertEquals(20, lista.getLista().get(19).getRig());
        assertEquals(22, lista.getLista().get(20).getRig());
        assertEquals(22, lista.getLista().get(21).getRig());
        assertEquals(24, lista.getLista().get(22).getRig());
        assertEquals(24, lista.getLista().get(23).getRig());
        assertEquals(26, lista.getLista().get(24).getRig());
        assertEquals(26, lista.getLista().get(25).getRig());
        assertEquals(26, lista.getLista().get(26).getRig());
        assertEquals(30, lista.getLista().get(27).getRig());
        assertEquals(32, lista.getLista().get(28).getRig());
        assertEquals(34, lista.getLista().get(29).getRig());
        assertEquals(35, lista.getLista().get(30).getRig());
        assertEquals(37, lista.getLista().get(31).getRig());
        assertEquals(37, lista.getLista().get(32).getRig());
        assertEquals(39, lista.getLista().get(33).getRig());
        assertEquals(41, lista.getLista().get(34).getRig());
        assertEquals(41, lista.getLista().get(35).getRig());

        // COLONNE

        assertEquals(4, lista.getLista().get(0).getCol());
        assertEquals(6, lista.getLista().get(1).getCol());
        assertEquals(8, lista.getLista().get(2).getCol());
        assertEquals(4, lista.getLista().get(3).getCol());
        assertEquals(4, lista.getLista().get(4).getCol());
        assertEquals(5, lista.getLista().get(5).getCol());
        assertEquals(4, lista.getLista().get(6).getCol());
        assertEquals(8, lista.getLista().get(7).getCol());
        assertEquals(12, lista.getLista().get(8).getCol());
        assertEquals(16, lista.getLista().get(9).getCol());
        assertEquals(20, lista.getLista().get(10).getCol());
        assertEquals(25, lista.getLista().get(11).getCol());
        assertEquals(58, lista.getLista().get(12).getCol());
        assertEquals(80, lista.getLista().get(13).getCol());

        assertEquals(4, lista.getLista().get(14).getCol());
        assertEquals(9, lista.getLista().get(15).getCol());

        assertEquals(4, lista.getLista().get(16).getCol());
        assertEquals(16, lista.getLista().get(17).getCol());

        assertEquals(4, lista.getLista().get(18).getCol());
        assertEquals(9, lista.getLista().get(19).getCol());

        assertEquals(4, lista.getLista().get(20).getCol());
        assertEquals(14, lista.getLista().get(21).getCol());

        assertEquals(4, lista.getLista().get(22).getCol());
        assertEquals(9, lista.getLista().get(23).getCol());

        assertEquals(4, lista.getLista().get(24).getCol());
        assertEquals(7, lista.getLista().get(25).getCol());
        assertEquals(15, lista.getLista().get(26).getCol());

        assertEquals(4, lista.getLista().get(27).getCol());

        assertEquals(5, lista.getLista().get(28).getCol());

        assertEquals(4, lista.getLista().get(29).getCol());

        assertEquals(4, lista.getLista().get(30).getCol());

        assertEquals(4, lista.getLista().get(31).getCol());
        assertEquals(36, lista.getLista().get(32).getCol());

        assertEquals(4, lista.getLista().get(33).getCol());

        assertEquals(4, lista.getLista().get(34).getCol());
        assertEquals(16, lista.getLista().get(35).getCol());
    }

    //////////////////////////////////////////////
    /// TEST 5 -> BuildNonCoding , controllare se individua solo le frequenze che sono davvero non codificanti
    @Test
    public void BuildNonCodingTest()
    {
        // variabili
        Gestione_stringhe A = new Gestione_stringhe();
        ListPos lista = new ListPos();
        List_intervals listaIntervalli = new List_intervals();
        

        // CASO -> filepath 6 , il più complesso
        lista = A.AllNonCoding(filePath6);
        listaIntervalli = A.BuildNonCoding(lista);
        assertEquals(15, listaIntervalli.getLista().size());
    }
    
    
}
