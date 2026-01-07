#!/usr/bin/env bash

# =========================
# Parametri
# =========================
# $1 -> file
# $2 -> numero di linea
# $3 -> flag_task

file="$1"
lineNumber="$2"
flag_task="$3"

echo $file

if [[ -z "$file" || -z "$lineNumber" || -z "$flag_task" ]]; then
  echo "Uso: $0 <file> <lineNumber> <flag_task>" >&2
  exit 1
fi


# Decremento lineNumber (contatore parte da 0)
lineNumberAdjusted=$((lineNumber - 1))

# =========================
# Percorsi
# =========================
projectRoot="$(pwd)"
targetDir="$projectRoot/speaker/target/classes"

mkdir -p "$targetDir"

# =========================
# Trovo tutti i file .java (escludendo i test)
# =========================
mapfile -t javaFiles < <(
  find . -type f -name "*.java" \
    ! -path "*/test/*" \
    ! -name "*Test.java"
)

if [[ "${#javaFiles[@]}" -eq 0 ]]; then
  echo "Nessun file .java trovato!" >&2
  exit 1
fi

echo "Trovati ${#javaFiles[@]} file .java da compilare."
echo "Compilo tutti i file insieme..."

# =========================
# Compilazione
# =========================
javac -d "$targetDir" "${javaFiles[@]}"
exitCode=$?

if [[ $exitCode -ne 0 ]]; then
  echo "Errore compilazione!" >&2
  exit "$exitCode"
fi

echo "Compilazione completata con successo!"

# =========================
# Esecuzione classe principale
# =========================
mainClass="textospeech.Main"

echo "Eseguo: $mainClass $file $lineNumberAdjusted $flag_task"

java -cp "$targetDir" "$mainClass" "$file" "$lineNumberAdjusted" "$flag_task"

#java.exe -cp "$targetDir" "$mainClass" "$file" "$lineNumberAdjusted" "$flag_task"

echo "Esecuzione terminata."

# =========================
# Pausa finale
# =========================
read -rp "Premi Invio per uscire..."