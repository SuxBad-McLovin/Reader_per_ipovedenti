package textospeech.Reader.Shell;

public class Shell 
{
    // verifico che versione di shell è installata, così da poter essere sicuro di cosa usare 
    public static String DetectShell()
    {
        String shell = System.getenv("SHELL");
        String comspec = System.getenv("ComSpec");
        String pwshChannel = System.getenv("POWERSHELL_DISTRIBUTION_CHANNEL");

        // --- Linux/macOS ---
        if (shell != null) 
        {
            return shell; // /bin/bash, /bin/zsh, /usr/bin/fish ...
        }

        // --- Windows: PowerShell Core ---
        if (pwshChannel != null) 
        {
            return "pwsh";
        }

        // --- Windows: CMD o Windows PowerShell ---
        if (comspec != null) 
        {
            if (comspec.toLowerCase().contains("cmd.exe")) 
            {
                return "cmd";
            }
        }

        // --- Windows PowerShell (fallback) ---
        return "powershell";
    }
    
}
