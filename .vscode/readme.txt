///////////////////////////////////////
/// PROGETTO SINTESI VOCALE IN JAVA ///
///////////////////////////////////////
Il seguente programma si è posto come obiettivo quello di leggere brevi Script di CODICE JAVA da parte 
di un sintetizzatore vocale.

VERSIONE JAVA : 21 o più

//////////////////////////////////////////////////
//////////////////////////////////////////////////

/////////////////////////
/// AVVIARE PROGRAMMA ///
/////////////////////////

Per eseguire il programma in versione "SINGOLA RIGA" :
1) Spostarsi nel file che si desidera
2) Spostare il cursore sulla RIGA d'interesse   (la colonna non importa)
3) Premere : Ctrl + k

////////////////////////////////////////////////////

/////////////////////////
/// IN WINDOWS        ///
/////////////////////////

Per eseguire il programma in versioen "LETTURA FUNZIONE":
Eseguire il seguente SCRIPT:
powershell.exe -ExecutionPolicy Bypass -File Compile_and_execute.ps1 'path_to_file'    numero_riga     modalità
                                                                          ^                 ^               ^
                                                                          |                 |               |
                                                                        inserire        numero          modalità
                                                                        path            riga            (numero 2)
                                                                        a file          interesse
                                                                          
_E' necessario passare un path assoluto (per sicurezza)
_Il numero di riga deve contenere delle graffe
_La modalità è un intero (in questo caso 2)

ESEMPI:

//ESECUZIONE TRAMITE SCRIPT
SCRIPT : file.ps1

powershell.exe -ExecutionPolicy Bypass -File Compile_and_execute.ps1 'C:\Users\Utente\Documents\VERONA\TIROCINIO\1)DEFINITIVI\PORZIONE_3\speaker\src\main\java\textospeech\App.java' 12 1

////////////////////////////////////////////////////

Eseguire manualmente:

// COMPILAZIONE FILE

javac -d "speaker/target/classes" (
    Get-ChildItem -Recurse -Filter *.java |
    Where-Object {
        $_.FullName -notmatch '\\test\\' -and
        $_.Name -notmatch 'Test\.java$'
    } |
    Select-Object -ExpandProperty FullName
)

// ESECUZIONE FILE
java -cp speaker\target\classes textospeech.Main "C:\Users\Utente\Documents\VERONA\TIROCINIO\1)DEFINITIVI\PORZIONE_3\speaker\src\main\java\textospeech\App.java"  9       2
                                                                ^                                                                                                 ^       ^
                                                                |                                                                                                 |       |
                                                        file da leggere                                                                                         riga    modalità lettura


////////////////////////////////////////////////////

/////////////////////////
/// IN LINUX(bash)    ///
/////////////////////////
SCRIPT : file.sh

./compile_and_execute.sh "path_assoluto_file_da_leggere" 12 2

ESEMPIO:

./compile_and_execute.sh "/mnt/c/Users/Utente/Documents/VERONA/TIROCINIO/1)DEFINITIVI/PORZIONE_3/speaker/src/main/java/textospeech/App.java" 12 2


////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// DIPENDENZE        ///
/////////////////////////

Per facilitare l'uso del Junit_test è consigliabile disporre di Maven installato
Altrimenti necessario scaricarsi il pacchetto Junit_test

In LINUX è consigliabile scaricare il pacchetto "Espeak" in modo che legga il testo

////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// LOGICA FUNZIONI   ///
/////////////////////////
Si è cercato di mantenere il più coeso possibile metodi con funzioni simili
In file con più metodi, i metodi sono stati inseriti (ove possibile) in modo sequenziale

METODO 1
    ^
    |
METODO 2 richiama METODO 1
    ^
    |
METODO 3 richiama METODO 2

////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// STRUTTURA FILE    ///
/////////////////////////

Il progetto è stato suddiviso in 3 porzioni:

speaker
    |
    |-------->Main
    |
    |-------->test 
    |
    |-------->target

Il main è dove sono contenuti i file.java
in test è contenuta tutta la porzione per il testing
in target vengono posizionati i file compilati di java

////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// STRUTTURA MAIN    ///
/////////////////////////

Il main a sua volta è suddiviso in due porzioni

   Main
    |
    |-------->Parser
    |
    |-------->Reader

Il PARSER , si occupa di andare ad individuare tutte le componenti NON CODIFICANTI del codice, come :
Commenti
Stringhe
Char
Commenti multi riga

E anche di individuare le Graffe

********************

Il READER, si occupa invece di andare ad elaborare quanto viene fatto dal parser in modo da convertire il 
testo in Stringhe leggibili dal programma.


////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// STRUTTURA PARSER  ///
/////////////////////////

Parser
    |
    |-------->FINDER
    |
    |-------->NORMALIZZAZIONE
    |
    |-------->STRUTTURE DATI

In FINDER è dove effettivamente viene eseguita l'analisi

In NORMALIZZAZIONE vi è un metodo utile, che permettere di gestire il caso in cui si trovi nel testo un carattere
di "TAB" il quale causerebbe problemi (non vi sarebbe più corrispondenza fra la posizione che si vede stampata ed
quello che vede il compilatore).

In STRUTTURE DATI vi sono contenute delle strutture dati volte a gestire le Graffe


////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// STRUTTURA FINDER  ///
/////////////////////////

FINDER
    |
    |-------->OLD_PROJECT
    |
    |-------->GraphFinder.java

In OLD_PROJECT si individuano tutti i caratteri NON CODIFICANTI

In GraphFinder.java , si và ad trovare le graffe che costituiscono FUNZIONI

NOTA:
GraphFinder.java dipende molto strettamente da funzioni presenti in OLD_PROJECT


////////////////////////////////////////////////////
////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// STRUTTURA READER  ///
/////////////////////////

FINDER
    |
    |-------->BuildCommand
    |
    |-------->Enfasi
    |
    |-------->Os
    |
    |-------->ParseLine
    |
    |-------->ParseMultiline
    |
    |-------->Shell
    |
    |-------->Main.java
    |
    |-------->App.java

In BuildCommand si genera il PROCESSO che andrà a leggere il testo
************
In Enfasi, è suddiviso in due porzioni :    1) Serve a gestire la modalità 1
                                            2) Serve a gestire la modalità 2 
Sono molto simili, sono DISTINTE in quanto la prima gestisce in modo diverso la lettura se in un commento
La prima è più semplice
************
In Os ci si occupa di individuare il SISTEMA OPERATIVO del computer
************
In ParseLine si passa il testo della linea al LETTORE, utilizzato nella 1)modalità 1
************
In ParseMultiline viene passato come STRINGA, tutte le righe che compongono una funzione 
viene utilizzato nella 2)modalità 2 
************
In shell, si individua il tipo di shell disponibile sul computer
(Praticamente inutile)
************
In Main.java si avvia effettivamente il progetto
************
In App.java si simula per Test la lettura di un file

////////////////////////////////////////////////////
////////////////////////////////////////////////////
////////////////////////////////////////////////////
////////////////////////////////////////////////////

/////////////////////////
/// ESECUZIONE        ///
/////////////////////////

Una volta a runtime ci dovrebbero essere due output possibi:

1) modalità 1 -> lettura della riga indicata

2) modalità 2   -> letura funzione
                -> in caso di errore o assenza di graffe : sintetizzatore vocale reciterà messaggio d'errore