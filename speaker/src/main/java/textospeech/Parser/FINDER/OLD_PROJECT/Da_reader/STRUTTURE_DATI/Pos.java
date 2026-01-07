package textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;

//STRUTTURA DATI -> contiene dove si troveranno sequenze come stringhe e commenti
/**
 * STRUTTURA DATI, mi individua una POSIZIONE.
 * Ogni POSIZIONE  è costituito da due variabili di tipo INT che ne individuano le coordinate
 * CAMPI : RIGHE,COLONNE.
 * POSIZIONE == POS.
 */
public class Pos
{
    private int rig;
    private int col;
    private TypeEnum tipoNonCodificante;

    //COSTRUTTORE
    public Pos()
    {
        rig = 0;
        col = 0;
        tipoNonCodificante = TypeEnum.NODATA;
    }

    Pos(int col,int rig,TypeEnum tipo)
    {
        this.col = col;
        this.rig = rig;
        this.tipoNonCodificante = tipo;
    }

    //GETTER
    /**
     * GETTER, ritorna la coordinata della RIGA 
     * @return rig 
     */
    public int getRig() 
    {
        return rig;
    }

    /**
     * GETTER, ritorna la coordinata della COLONNA
     * @return col
     */
    public int getCol() 
    {
        return col;
    }

    public TypeEnum getTipo()
    {
        return tipoNonCodificante;
    }

    //SETTER
    /**
     * SETTER, modifica la coordinata della RIGA
     * @param rig il nuovo valore della coordinata RIGA
     */
    public void setRig(int rig) 
    {
        this.rig = rig;
    }        

    /**
     * SETTER, modifica la coordinata della COLONNA
     * @param col il nuovo valore della coordinata COLONNA
     */
    public void setCol(int col) 
    {
        this.col = col;
    }

    public void setTipo(TypeEnum tipo)
    {
        this.tipoNonCodificante = tipo;
    }
    /////////////////////////////////////
    // METODI
    /**
     * Aggiunge l'elemento corrente alla lista 
     * @param lista, la lista in cui aggiungere l'elemento
     */
    public void Add(ListPos lista)
    {
        lista.addPos(this);
    }

    // dovrebbe copiarmi l'oggetto
    /**
     * Permette di effettuare una copia dell'elemento corrente 
     * @param daClonare
     * @return Pos , copia dell'elemento corrente
     */
    public static Pos CopyOf(Pos daClonare)
    {
        Pos posizione = new Pos();
        posizione.col = daClonare.col;      //<--- qui posso fare così siccome sto andando a puntare la variabile primitiva 
        // col la quale è un INT (ne sto prendendo il valore)
        posizione.rig = daClonare.rig;
        posizione.tipoNonCodificante = daClonare.tipoNonCodificante;

        return posizione;
    }

    // OVERRIDE TOSTRING
    @Override
    public String toString() 
    {
        return "POS [ riga:" + rig + "  |   colonna:" + col + " ]\n";
    }
}

