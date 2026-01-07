package textospeech.Reader.ParseLine.Interface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import textospeech.Reader.Enfasi.Enfasi;

public class ParseLine 
{
    public static String BuildReadableString(String filePath, int numeroLinea)
    {
        try 
        {
            //flag
            boolean isBetweenUppers = false;
            //output
            String textForReading = "";

            //variabili
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            String contentString = "";
            int counter = 0;
            int lineNo = numeroLinea; // la riga da raggiungere (il secondo argomento passato al terminale)

            // Lettura dell'intero file 
            while ((line = reader.readLine()) != null) 
            {
                // Skippo la linea fin quando non raggiungo quella desiderata
                if (counter == lineNo) 
                {
                    line = line.trim();
                    // Linea vuota
                    if (line.length() < 1) 
                    {
                        textForReading = "Linea vuota";
                        //System.out.println("Linea vuota");                     
                    }
                    // linea che inizierà con un commento su linea
                    else
                    if (line.contains("//")) {
                        int commentIndex = line.indexOf("//");
                        String comment = line.substring(commentIndex);
                        comment = comment.replace("//", "");
                        String code = line.substring(0, commentIndex).trim();
                        String[] codes = code.split("((?<=\\W)|(?=\\W))"); 
                        for(String cd : codes) {
                            if (cd.equals("\"")) {
                                if (isBetweenUppers) {
                                    isBetweenUppers = false;
                                    textForReading += Enfasi.processWord("\"" + contentString + "\"");
                                    contentString = "";
                                }
                                else {
                                    isBetweenUppers = true;
                                }
                            }
                            else if (isBetweenUppers) {
                                contentString += cd;
                            }
                            else if (!cd.equals(" ")) {
                                textForReading += Enfasi.processWord(cd);
                            }
                        }
                        textForReading += ("Commento: " + comment + ". Fine commento.\n");
                    }
                    else {
                        String[] words = line.split("((?<=\\W)|(?=\\W))"); 
                        for(String word : words) {
                            // la linea è contenuta tra doppi apici  (contenuto stringa)
                            if (word.equals("\"")) {
                                if (isBetweenUppers) {
                                    isBetweenUppers = false;
                                    textForReading += Enfasi.processWord("\"" + contentString + "\"");
                                    contentString = "";
                                }
                                else {
                                    isBetweenUppers = true;
                                }
                            }
                            else if (isBetweenUppers) {
                                contentString += word;
                            }
                            else if (!word.equals(" ")) {
                                textForReading += Enfasi.processWord(word);
                            }
                        }
                    }
                    break;
                }
                else 
                {
                    counter++;
                }
            }
            reader.close();
            return textForReading + Enfasi.processWord("\n");   //in fondo al testo faccio dire "fine riga"
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        return null;
    }
}
