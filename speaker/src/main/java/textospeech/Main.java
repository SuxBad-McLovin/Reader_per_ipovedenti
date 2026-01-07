package textospeech;

import textospeech.Reader.BuildCommand.BuildCommand;
import textospeech.Reader.Os.Interface.OsInterface;
import textospeech.Reader.ParseLine.Interface.ParseLine;
import textospeech.Reader.ParseMultiLine.ParseMultiLine;
import textospeech.Reader.Shell.Shell;

public class Main 
{
    
    public static String platform;
    public static String shell;
    
    public static void main(String[] args) 
    {     
        // Argomenti richiesti: nome file e numero riga di cui ne si vuole sentire la lettura tramite Reader.
        if (args.length < 2) 
        {
            System.out.println("Utilizzo: java Main <Java File> <line n.>");
            return;
        }
        // Invocando il Reader di default del sistema operativo, verifico in quale OS viene eseguito il programma [Windows, Linux, MacOs]
        platform = OsInterface.checkOS();
        shell = Shell.DetectShell();
        if(shell == null)
        {
            throw new NullPointerException("La shell non è stata individuata!");
        }

        String filepath = args[0];
        String textForReading = "";
        int Flag_task = Integer.parseInt(args[2]);  //<--- mi serve per selezionare che task eseguire
        int numeroLinea = Integer.parseInt(args[1]); // la riga da raggiungere (il secondo argomento passato al terminale)
        
        // CODICE VERO E PROPRIO

        switch (Flag_task) 
        {
            // task minima : lettura singola riga
            case 1 ->
            {
                textForReading = ParseLine.BuildReadableString(filepath, numeroLinea);
                System.out.println(textForReading); //<--- lui stampa cosa debba venir letto dal reader
                BuildCommand.Read(platform,shell,textForReading);
            }

            // task 2 : esecuzione blocco di istruzioni
            case 2 ->
            {
                textForReading = ParseMultiLine.GetReadableString(filepath, numeroLinea);
                if(textForReading == null)
                {
                    textForReading = "Errore, selezionare una riga che sia racchiusa fra graffe";
                }
                System.out.println(textForReading);
                BuildCommand.Read(platform, shell, textForReading);
            }
        }
    }
}
