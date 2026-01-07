package textospeech.Parser.FINDER;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.Gestione_stringhe;
import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI.List_intervals;
import textospeech.Parser.STRUTTURE_DATI.BloccoCollector;
import textospeech.Parser.STRUTTURE_DATI.Graffa;
import textospeech.Parser.STRUTTURE_DATI.GraffaCollector;


public class GraphFinderTest 
{
    String projectRoot = System.getProperty("user.dir");    //<--- la root del progetto
    String filepath = projectRoot + "\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo.txt";     //<--- ho ricostruito il path assoluto
    String filepath2 = projectRoot + "\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo2.txt";
    String filepath3 = projectRoot + "\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo3.txt";
    String filepath4 = projectRoot + "\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo4.txt";

    // TEST DI DEBUG
    // Commentare se non si è il programmatore originale (o aggiustarlo con i vostri path assoluti)
    @Test
    public void PathExistTest()
    {
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo.txt", filepath);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo2.txt", filepath2);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo3.txt", filepath3);
        assertEquals("C:\\Users\\Utente\\Documents\\VERONA\\TIROCINIO\\1)DEFINITIVI\\PORZIONE_3\\speaker\\src\\test\\resources\\PARSER\\TESTOPERBUFFER\\Testo4.txt", filepath4);
    }

    // TEST 1 : inizializzare un oggetto della classe, quindi inizializzare un bufferreader
    @Test
    public void BufferedReaderTest()
    {
        try
        {
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            String A = B_reader.readLine();
            String B = B_reader.readLine();
            String C = B_reader.readLine();
            String D = B_reader.readLine();
            B_reader.close();
            String E = "";

            assertEquals("Ciao sono un file per fare test", A);
            assertEquals("{", B);
            assertEquals("    Hello", C);
            assertEquals("}", D);
            assertNotEquals("ciao", E);
        }
        catch (IOException e) 
        {
            // così mi accorgo che qualcosa non và
            assertEquals(false, true);
            e.printStackTrace();
        }
    }
    //////////////////////////////////////////////////////////////////////
    /// FUNZIONE CHE USEREMO AL POSTO DELLE PRECEDENTI
    /// 

    // TEST 1 : trova le due graffe (aperta e chiusa)?
    @Test
    public void FindFirstGraffaTest()
    {
        try
        {
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            String A = B_reader.readLine();
            String B = B_reader.readLine();
            String C = B_reader.readLine();
            String D = B_reader.readLine();
            B_reader.close();

            // VARIABILI 1 -> trova graffa aperta
            int riga = 2;
            GraphFinder finder = new GraphFinder();
            Graffa Aperta = new Graffa();

            Aperta = finder.FindFirstGraffa(0, B, riga);
            assertEquals(1,Aperta.getColonna());
            assertEquals(riga,Aperta.getRiga());
            assertEquals(true,Aperta.getIsCode());
            assertEquals(true, Aperta.getIsOpen());

            // VARIABILI 1 -> trova graffa aperta
            riga = 4;
            Graffa Chiusa = new Graffa();

            Chiusa = finder.FindFirstGraffa(0, D, riga);
            assertEquals(1,Chiusa.getColonna());
            assertEquals(riga,Chiusa.getRiga());
            assertEquals(true,Chiusa.getIsCode());
            assertEquals(false, Chiusa.getIsOpen());
        }
        catch (IOException e) 
        {
            assertEquals(false, true);
            e.printStackTrace();
        }
    }

    // TEST 2 : valutiamo che non vengano trovate graffe 
    @Test
    public void DontFindFirstGraffaTest()
    {
        try
        {
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            String A = B_reader.readLine();
            String B = B_reader.readLine();
            String C = B_reader.readLine();
            String D = B_reader.readLine();
            B_reader.close();

            // VARIABILI 1 -> trova graffa aperta
            int riga = 1;
            GraphFinder finder = new GraphFinder();
            Graffa vuoto = new Graffa();

            vuoto = finder.FindFirstGraffa(0, A, riga);
            assertEquals(true, vuoto.isEmpty());

            // VARIABILI 1 -> trova graffa aperta
            riga = 3;
            vuoto = finder.FindFirstGraffa(0, C, riga);
            assertEquals(true, vuoto.isEmpty());
            
        }
        catch (IOException e) 
        {
            assertEquals(false, true);
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////

    // TEST 3 : trovare tutte le graffe
    // NOTA: stiamo usando il secondo file per il test
    @Test
    public void FindAllGraphPerLineTest()
    {
        try
        {
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath2)));
            String A = B_reader.readLine();
            B_reader.close();

            // variabili
            GraffaCollector lista = new GraffaCollector();
            int riga = 1;
            GraphFinder finder = new GraphFinder();

            lista = finder.FindAllGraphPerLine(A, riga);
            assertEquals(16, lista.getLista().size());
            // controlliamo la prima graffa 
            assertEquals(1, lista.getLista().get(0).getRiga());
            assertEquals(1, lista.getLista().get(0).getColonna());
            assertEquals(true, lista.getLista().get(0).getIsCode());
            assertEquals(true, lista.getLista().get(0).getIsOpen());
            // controlliamo la seconda graffa
            assertEquals(1, lista.getLista().get(1).getRiga());
            assertEquals(2, lista.getLista().get(1).getColonna());
            assertEquals(true, lista.getLista().get(1).getIsCode());
            assertEquals(false, lista.getLista().get(1).getIsOpen());
            // controlliamo la terza graffa
            assertEquals(1, lista.getLista().get(2).getRiga());
            assertEquals(4, lista.getLista().get(2).getColonna());
            assertEquals(true, lista.getLista().get(2).getIsCode());
            assertEquals(true, lista.getLista().get(2).getIsOpen());
            // controlliamo la quarta graffa
            assertEquals(1, lista.getLista().get(3).getRiga());
            assertEquals(5, lista.getLista().get(3).getColonna());
            assertEquals(true, lista.getLista().get(3).getIsCode());
            assertEquals(true, lista.getLista().get(3).getIsOpen());
            // controlliamo la quinta graffa
            assertEquals(1, lista.getLista().get(4).getRiga());
            assertEquals(6, lista.getLista().get(4).getColonna());
            assertEquals(true, lista.getLista().get(4).getIsCode());
            assertEquals(true, lista.getLista().get(4).getIsOpen());
            // controlliamo la sesta graffa
            assertEquals(1, lista.getLista().get(5).getRiga());
            assertEquals(7, lista.getLista().get(5).getColonna());
            assertEquals(true, lista.getLista().get(5).getIsCode());
            assertEquals(false, lista.getLista().get(5).getIsOpen());
        
            // AGGIUNGERE ALTRI TEST SE SI VUOLE

            // controlliamo la penultima graffa
            assertEquals(1, lista.getLista().get(14).getRiga());
            assertEquals(18, lista.getLista().get(14).getColonna());
            assertEquals(true, lista.getLista().get(14).getIsCode());
            assertEquals(false, lista.getLista().get(14).getIsOpen());
            // controlliamo l'ultima graffa
            assertEquals(1, lista.getLista().get(15).getRiga());
            assertEquals(19, lista.getLista().get(15).getColonna());
            assertEquals(true, lista.getLista().get(15).getIsCode());
            assertEquals(false, lista.getLista().get(15).getIsOpen());
        }
        catch (IOException e) 
        {
            assertEquals(false, true);
            e.printStackTrace();
        }
    }

    // TEST 4 : controlliamo che non venga trovato effettivamente niente
    @Test
    public void DontFindAllGraphPerLineTest()
    {
        try
        {
            BufferedReader B_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath2)));
            String A = B_reader.readLine();
            String B = B_reader.readLine(); 
            B_reader.close();

            // variabili
            GraffaCollector lista = new GraffaCollector();
            int riga = 2;
            GraphFinder finder = new GraphFinder();

            lista.AddLista(finder.FindAllGraphPerLine(B, riga));
            assertEquals(true, lista.isEmpty()); 
        }
        catch (IOException e) 
        {
            assertEquals(false, true);
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////////////////
    /// INDIVIDUARE SE UNA LINEA E' CODICE
    /// 
    
    // TEST 5 : ridurre le sequenze da controllare
    @Test
    public void GetUsefullListKeywordTest()
    {
        try
        {
            BufferedReader C_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath3)));

            // Variabili -> per inizializzare la lista di "righe non codificanti"
            List_intervals listaNonKeywords = new List_intervals();
            listaNonKeywords = Gestione_stringhe.BuildIntervals(filepath3); //<--- ho trovato tutte le porzioni non codificanti
            assertEquals(15, listaNonKeywords.getLista().size());

            // variabili per test vero e proprio
            List_intervals result = new List_intervals();
            int contatore = 1;
            int n_posizioni_da_partire; //<--- indica da che "funzione non codificante partire"
            GraphFinder finder = new GraphFinder();

            while(contatore < 43)   // NOTA : 43 è la lunghezza in linee del file di test
            {
                n_posizioni_da_partire = finder.minNoKeyword;    //<--- per assicurarmi che funzioni

                if(contatore == 1)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(0, n_posizioni_da_partire);
                }

                if(contatore == 2)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(0, n_posizioni_da_partire);
                }

                if(contatore == 3)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(0, n_posizioni_da_partire);
                }

                if(contatore == 4)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(0, n_posizioni_da_partire);
                }

                if(contatore == 5)
                {
                    
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(1, n_posizioni_da_partire);
                }

                if(contatore == 6)
                {   
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(1, n_posizioni_da_partire);
                }

                if(contatore == 7)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(2, n_posizioni_da_partire);
                }

                /* MANCA UN TEST QUI (opzionale) */

                if(contatore == 9)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(2, n_posizioni_da_partire);
                }

                if(contatore == 10)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(2, n_posizioni_da_partire);
                }

                if(contatore == 11)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(2, n_posizioni_da_partire);
                }

                if(contatore == 12)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(3, n_posizioni_da_partire);
                }

                if(contatore == 13)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(3, n_posizioni_da_partire);
                }

                if(contatore == 14)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());  //1
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(4, n_posizioni_da_partire);    //4
                }

                if(contatore == 15)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(4, n_posizioni_da_partire);
                }

                if(contatore == 16)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(5, n_posizioni_da_partire);
                }

                if(contatore == 17)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(5, n_posizioni_da_partire);
                }

                if(contatore == 18)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(6, n_posizioni_da_partire);
                }

                if(contatore == 19)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(6, n_posizioni_da_partire);
                }

                if(contatore == 20)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(7, n_posizioni_da_partire);
                }

                if(contatore == 21)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(7, n_posizioni_da_partire);
                }

                if(contatore == 22)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(8, n_posizioni_da_partire);
                }

                /* MANCANO DEI TEST (opzionali) */

                /* DA TENERE D'OCCHIO QUELLO SOTTO */
                if(contatore == 26)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(10, n_posizioni_da_partire);
                }

                if(contatore == 27)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(10, n_posizioni_da_partire);
                }

                /* MANCANO DEI TEST (opzionale) */

                if(contatore == 30)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(10, n_posizioni_da_partire);
                }

                if(contatore == 31)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(10, n_posizioni_da_partire);
                }

                if(contatore == 32)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(11, n_posizioni_da_partire);
                }

                if(contatore == 33)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(11, n_posizioni_da_partire);
                }

                if(contatore == 34)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(11, n_posizioni_da_partire);
                }

                if(contatore == 35)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(12, n_posizioni_da_partire);
                }

                if(contatore == 36)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(12, n_posizioni_da_partire);
                }

                if(contatore == 37)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(13, n_posizioni_da_partire);
                }

                if(contatore == 38)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(13, n_posizioni_da_partire);
                }

                if(contatore == 39)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(14, n_posizioni_da_partire);
                }

                if(contatore == 40)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(14, n_posizioni_da_partire);
                }

                if(contatore == 41)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(1, result.getLista().size());
                    n_posizioni_da_partire = finder.minNoKeyword;
                    assertEquals(14, n_posizioni_da_partire);
                }
                contatore++;
            }
            C_reader.close();
        }
        catch (IOException e) 
        {
            assertEquals(false, true);
            e.printStackTrace();
        }
    }

    // TEST 6 : controllar se una linea è codificante
    @Test
    public void FindCodingGrafPerLineTest()
    {
        try
        {
            BufferedReader C_reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath4)));
            // Variabili -> per inizializzare la lista di "righe non codificanti"
            List_intervals listaNonKeywords = new List_intervals();
            listaNonKeywords = Gestione_stringhe.BuildIntervals(filepath4); //<--- ho trovato tutte le porzioni non codificanti

            // variabili per test vero e proprio
            List_intervals result = new List_intervals();
            int contatore = 1;
            int n_posizioni_da_partire; //<--- indica da che "funzione non codificante partire"
            GraphFinder finder = new GraphFinder();
            GraffaCollector lista = new GraffaCollector();
            String linea;

            while((linea = C_reader.readLine()) != null)  // NOTA : 43 è la lunghezza in linee del file di test
            {
                n_posizioni_da_partire = finder.minNoKeyword;    //<--- per assicurarmi che funzioni

                if(contatore == 1)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(true, result.isEmpty());
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(0, lista.getLista().size());
                }

                if(contatore == 2)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(0, lista.getLista().size());
                }

                if(contatore == 3)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(0, lista.getLista().size());
                }

                if(contatore == 4)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 5)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 6)
                {   
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 7)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 8)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 9)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 10)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 11)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 12)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(1, lista.getLista().size());
                }

                if(contatore == 13)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    assertEquals(0, result.getLista().size());
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(3, lista.getLista().size());
                }

                if(contatore == 14)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(3, lista.getLista().size());
                }

                if(contatore == 15)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(4, lista.getLista().size());
                }

                if(contatore == 16)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(4, lista.getLista().size());
                }

                if(contatore == 17)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(5, lista.getLista().size());
                }

                // MANCANO DEI TEST //

                if(contatore == 19)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(5, lista.getLista().size());
                }

                if(contatore == 20)
                {
                    result = finder.GetUsefullListKeyword(listaNonKeywords, contatore, n_posizioni_da_partire);
                    lista.AddLista(finder.FindCodingGrafPerLine(linea,contatore,result));
                    assertEquals(6, lista.getLista().size());
                }

                contatore++;
            }
            C_reader.close();
        }
        catch (IOException e) 
        {
            assertEquals(false, true);
            e.printStackTrace();
        }
    }

    // TEST 7 : controllare che la funzione che automatizza il processo di individuazione di linee codificanti funzioni  
    @Test
    public void FindAllCodingGrafTest()
    {

        // Variabili -> per inizializzare la lista di "righe non codificanti"
        List_intervals listaNonKeywords = new List_intervals();
        listaNonKeywords = Gestione_stringhe.BuildIntervals(filepath4); //<--- ho trovato tutte le porzioni non codificanti

        // variabili per test vero e proprio
        GraphFinder finder = new GraphFinder();
        GraffaCollector lista = new GraffaCollector();

        lista.AddLista(finder.FindAllCodingGraf(listaNonKeywords,filepath4));
        assertEquals(6, lista.getLista().size());

        // PRIMA OCCORRENZA
        assertEquals(4, lista.getLista().get(0).getRiga());
        assertEquals(1, lista.getLista().get(0).getColonna());

        // SECONDA OCCORRENZA
        assertEquals(13, lista.getLista().get(1).getRiga());
        assertEquals(4, lista.getLista().get(1).getColonna());

        // TERZA OCCORRENZA
        assertEquals(13, lista.getLista().get(2).getRiga());
        assertEquals(26, lista.getLista().get(2).getColonna());

        // QUARTA OCCORRENZA
        assertEquals(15, lista.getLista().get(3).getRiga());
        assertEquals(4, lista.getLista().get(3).getColonna());

        // QUINTA OCCORRENZA
        assertEquals(17, lista.getLista().get(4).getRiga());
        assertEquals(4, lista.getLista().get(4).getColonna());

        // SESTA OCCORRENZA
        assertEquals(20, lista.getLista().get(5).getRiga());
        assertEquals(1, lista.getLista().get(5).getColonna());

    }
    //////////////////////////////////////////////////////////////////////
    // INDIVIDUARE I BLOCCHI (formati dalle graffe)

    // TEST 8
    @Test
    public void FindBlockTest()
    {
        // Variabili -> per inizializzare la lista di "righe non codificanti"
        List_intervals listaNonKeywords = new List_intervals();
        listaNonKeywords = Gestione_stringhe.BuildIntervals(filepath4); //<--- ho trovato tutte le porzioni non codificanti

        // variabili per test vero e proprio
        GraphFinder finder = new GraphFinder();
        GraffaCollector lista = new GraffaCollector();

        lista = finder.FindAllCodingGraf(listaNonKeywords,filepath4);
        assertEquals(6, lista.getLista().size());

        BloccoCollector Blocchi = new BloccoCollector();
        Blocchi = finder.FindBlock(lista);
        assertEquals(3, Blocchi.getBlocchi().size());
    }

    // TEST 9 -> la funzione per ottenere i blocchi in modo "autonomo"
    @Test
    public void GetBlockTest()
    {
        // variabili per test vero e proprio
        BloccoCollector Blocchi = new BloccoCollector();
        Blocchi = GraphFinder.GetBlock(filepath4);
        assertEquals(3, Blocchi.getBlocchi().size());
    }
}
