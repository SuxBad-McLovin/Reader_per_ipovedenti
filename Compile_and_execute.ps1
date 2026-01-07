# salvo i parametri ottenuti dal 
param
(
    [string]$file,        # Primo parametro: il file
    [int]$lineNumber,      # Secondo parametro: numero di linea
    [int]$flag_task       # Terzo parametro: per scoprire che task lo ha richiamato
)

# Decremento lineNumber (siccome il contatore delle righe parte da zero, ma è più leggibile se parte da 1)
$lineNumberAdjusted = $lineNumber - 1

# Percorsi
$projectRoot = Get-Location
$targetDir = Join-Path $projectRoot "speaker/target/classes"

# ==== Trovo tutti i file .java da compilare, escludendo i file di test ====
$javaFiles = Get-ChildItem -Recurse -Filter *.java |
    Where-Object {
        $_.FullName -notmatch '\\test\\' -and
        $_.Name -notmatch 'Test\.java$'
    } |
    Select-Object -ExpandProperty FullName

if ($javaFiles.Count -eq 0) 
{
    Write-Error "Nessun file .java trovato!"
    exit 1
}

# NOTA -> $_.FullName -notmatch '\\test\\' : seleziono solo i file che non contengono TEST nel nome
# NOTA -> $_.Name -notmatch 'Test\.java$'  : e controllo anche che non siano nella cartella TEST (per sicurezza)

Write-Host "Trovati $($javaFiles.Count) file .java da compilare."

# NOTA : potrebbe essere efficientizzato andando a compilare solo quello che serve, ma renderebbe le cose più complesse
Write-Host "Compilo tutti i file insieme..."
javac -d $targetDir $javaFiles
if ($LASTEXITCODE -ne 0) {
    Write-Error "Errore compilazione!"
    exit $LASTEXITCODE
}

Write-Host "Compilazione completata con successo!"

# ==== Esecuzione della classe principale ====
$mainClass = "textospeech.Main"
Write-Host "Eseguo: $mainClass $file $lineNumberAdjusted"
java -cp "`"$targetDir`"" $mainClass $file $lineNumberAdjusted  $flag_task

Write-Host "Esecuzione terminata."

# ==== Pausa per leggere eventuali messaggi ====
Read-Host -Prompt "Premi Invio per uscire..."       # ricevo un input dall'utente (mi serve solo per evitare che il processo termini)
                                                    # così da leggere eventuale output

