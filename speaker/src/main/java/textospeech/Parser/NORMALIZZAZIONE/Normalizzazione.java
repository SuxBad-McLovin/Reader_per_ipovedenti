package textospeech.Parser.NORMALIZZAZIONE;

// obiettivo di questa classe, trasformare i TAB in semplici spazi, NON è il metodo migliore!!!
public class Normalizzazione 
{
    /**
     * Si occupa di ritornare una stringa in cui i TAB, vengono sostituiti con il corretto numero di spazi, in modo
     * da mantenere la corretta spaziature nelle colonne 
     * @param daNormalizzare , la stringa da modificare
     * @return
     */
    public static String NormalizedString(String daNormalizzare)
    {
        int tabSize = 4;
        StringBuilder normalized = new StringBuilder();
        int colonna = 0;

        for (char c : daNormalizzare.toCharArray()) {
            if (c == '\t') {
                int spazi = tabSize - (colonna % tabSize); // numero di spazi da aggiungere
                normalized.append(" ".repeat(spazi));       // aggiunge x spazi, che sono calcolati nel passo prima
                colonna += spazi;
            } else {
                normalized.append(c);
                colonna++;
            }
        }

        String normalizedLine = normalized.toString();
        return normalizedLine;
    }
}
