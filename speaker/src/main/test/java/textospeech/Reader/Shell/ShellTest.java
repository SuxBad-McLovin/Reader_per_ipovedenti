package textospeech.Reader.Shell;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShellTest 
{
    // TEST 1
    public void RunShellTest()
    {

    } 
    
    // TEST 2
    @Test
    public void DetectShellTest()
    {
        String shell = Shell.DetectShell();
        assertEquals("pwsh",shell);
    }
}
