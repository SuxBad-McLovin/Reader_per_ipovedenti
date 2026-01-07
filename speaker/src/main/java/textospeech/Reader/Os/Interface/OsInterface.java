package textospeech.Reader.Os.Interface;

/* fornisce le funzioni per individuare il sistema operativo */
public interface OsInterface 
{
    // NOTA : questo metodo non verrà ereditato da eventuali classi che estendono l'interfaccia
    // Verifica del sistema operativo su cui viene eseguito il file
    public static String checkOS() 
    {
        String platform = null;
        String os = System.getProperty("os.name").toLowerCase(); 

        /* 
        if (os.contains("win")) 
        { 
            platform = "Windows";
        }
        else
        {
            if (os.contains("mac")) 
            {
                platform = "MacOs";
            }
            else 
                if (os.contains("nix") || os.contains("nux")) 
                { 
                platform = "Linux"; 
                }
                else 
                {
                    System.out.println("Sistema operativo non rilevato");
                    System.exit(1);
                }
        }  
        */

        if (os.contains("win")) 
        { 
            platform = "Windows";
        }

        if (os.contains("mac")) 
        {
            platform = "MacOs";
        }
        
        if (os.contains("nix") || os.contains("nux")) 
        { 
            platform = "Linux"; 
        }
        
        // per sicurezza
        if(platform == null)
        {
            throw new NullPointerException("Sistema operativo non individuato");
        }
        else
        {
            return platform;
        }
    } 
}
