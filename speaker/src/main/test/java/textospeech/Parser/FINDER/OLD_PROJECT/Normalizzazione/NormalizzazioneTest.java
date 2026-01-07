package textospeech.Parser.FINDER.OLD_PROJECT.Normalizzazione;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import textospeech.Parser.NORMALIZZAZIONE.Normalizzazione;

public class NormalizzazioneTest
{
    // TEST 1 -> vediamo se viene restituita la stringa corretta
    @Test
    public void NormalizedStringTest()
    {
        String testo = "   //  //  //  //  //\t// <--- mi serve per controllare \"RECURSIVE_end_comment\"";
        testo = Normalizzazione.NormalizedString(testo);
        assertEquals(' ', testo.charAt(2));
        assertEquals('/', testo.charAt(3));
        assertEquals('/', testo.charAt(4));
        assertEquals(' ', testo.charAt(5));

        assertEquals('/', testo.charAt(19));
        assertEquals('/', testo.charAt(20));
        assertEquals(' ', testo.charAt(21));
        assertEquals(' ', testo.charAt(22));
        assertEquals(' ', testo.charAt(23));
        assertEquals('/', testo.charAt(24));
        assertEquals('/', testo.charAt(25));
        assertEquals(' ', testo.charAt(26));
        assertEquals('<', testo.charAt(27));   
    }
}
