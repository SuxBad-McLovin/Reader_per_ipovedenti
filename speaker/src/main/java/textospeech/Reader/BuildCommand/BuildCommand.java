package textospeech.Reader.BuildCommand;

public class BuildCommand 
{
    /**
     * Si occupa di generare un processo per la lettura del testo
     * @param Os    , ottiene il sistema operativo
     * @param Shell , fornisce la shell preferenziale con cui richiamare il reader
     * @param word  , la stringa da leggere (potrebbe riguardare anche più righe)
     * @return  ProcessBuilder , null se non viene riconosciuto il sistema operativo 
     */
    protected static ProcessBuilder BuildReaderProcess(String Os,String Shell,String word)
    {
        String dir_to_shell;
        if(Os == "Linux")
        {
            ProcessBuilder pb = new ProcessBuilder
            (
        "espeak",
                    word
            );
            return pb;
        }

        if(Os == "Windows")
        {   
            // per sicurezza, rimuoviamo gli apici singoli
            word = word.replace("'", "''"); //<--- powershell, sbaglierebbe ad interpretare
            dir_to_shell = "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe";
            ProcessBuilder pb = new ProcessBuilder();
            pb.command(dir_to_shell,
                        "-command",
                        "Add-Type -AssemblyName System.Speech;",
                        "$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer;",
                        "$speak.Speak('" + word + "');"
                    );
            return pb;
        }

        if(Os == "MacOs")
        {
             ProcessBuilder pb = new ProcessBuilder
            (
        "say",
                    word
            );
            return pb;
        }

        // DEFAULT 
        return null;
    }   

    /**
     * Si occupa di far terminare il processo di lettura se l'utente lo desidera tramite la shortcut : "CTRL + C"
     * @param process , il processo di lettura che deve essere eventualmente terminato
     */
    protected static void StopRead(Process process) 
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nCTRL+C rilevato → interrompo la voce...");
            process.destroy();
        }));
    }

    
    /**
     * Questo metodo si occupa di far avviare il System.Speech , viene gestita anche un' eventuale terminazione arbitraria
     * da parte dell'utente.
     * 
     * Per dettagli vedere :
     * -Comando di shell con cui si genererà il processo {@link BuildCommand#BuildReaderProcess()}.
     * -Comando per terminazione manuale {@link BuildCommand#StopRead()}
     * 
     * @param os    , il sistema operativo
     * @param shell , la shell disponibile
     * @param word  , la stringa da leggere 
     */
    public static void Read(String os,String shell,String word)
    {
        ProcessBuilder processoBuilder = BuildReaderProcess(os,shell,word);
        
        try
        {
            Process processoLettura = processoBuilder.start();
            BuildCommand.StopRead(processoLettura);
            processoLettura.waitFor();
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }


}
