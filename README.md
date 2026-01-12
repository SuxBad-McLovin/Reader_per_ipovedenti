# Reader_per_ipovedenti
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

Il programma dispone di due modalità :
_Lettura di singola riga
_Lettura blocco di istruzioni (sezioni delimitate da graffe)

/////////////////////////
/// SINGOLA RIGA      ///
/////////////////////////
Questo comando dovrebbe funzionare su tutti i sistemi operativi, in caso di problemi
eseguirlo manualmente nella prossima sezione

Per eseguire il programma in versione "SINGOLA RIGA" :
1) Spostarsi nel file che si desidera
2) Spostare il cursore sulla RIGA d'interesse   (la colonna non importa)
3) Premere : Ctrl + k

////////////////////////////////////////////////////
Elenchiamo di seguito come avviare i programmi "manualmente" senza shortcuts
/////////////////////////
/// IN WINDOWS        ///
/////////////////////////

Eseguire il seguente SCRIPT:
Compile_and_execute.ps1

SINTASSI GENERICA

powershell.exe -ExecutionPolicy Bypass -File Compile_and_execute.ps1 'path_to_file'    numero_riga     modalità
                                                                          ^                 ^               ^
                                                                          |                 |               |
                                                                        inserire        numero          modalità
                                                                        path            riga            (1 o 2)
                                                                        a file          interesse
Per eseguire il programma in versione "LETTURA RIGA":

ESEMPIO

powershell.exe -ExecutionPolicy Bypass -File Compile_and_execute.ps1 'C:\Users\Utente\Documents\VERONA\TIROCINIO\1)DEFINITIVI\PORZIONE_3\speaker\src\main\java\textospeech\App.java' 12 1
                                                                                                                   ^
                                                                                                                   |
                                                                                                                  modalità
                                                                                                                  singola
                                                                                                                  riga
Per eseguire il programma in versione "LETTURA FUNZIONE":

ESEMPIO

powershell.exe -ExecutionPolicy Bypass -File Compile_and_execute.ps1 'C:\Users\Utente\Documents\VERONA\TIROCINIO\1)DEFINITIVI\PORZIONE_3\speaker\src\main\java\textospeech\App.java' 12 2
                                                                                                                   ^
                                                                                                                   |
                                                                                                                  modalità
                                                                                                                  lettura 
                                                                                                                  funzione


                                                                          
_E' necessario passare un path assoluto (per sicurezza)
_Il numero di riga deve contenere delle graffe
_La modalità è un intero (in questo caso 2)
