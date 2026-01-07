package textospeech.Reader.BuildCommand;

import org.junit.jupiter.api.Test;

public class BuildCommandTest 
{
    // TEST 1
    
    // TEST 2
    @Test
    public void ReadTest()
    {
        /* 
        // Rendirizzo l'output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        */

        // comando da testare
        BuildCommand.Read("Windows","pwsh","public");

        /* 
        // salvo ciò che viene stampato dal comando
        String output = outContent.toString();

        assertEquals("Zitta brutta scimmia",output);

        // ripristino l'output di default
        System.setOut(originalOut);
        */
    }
}


