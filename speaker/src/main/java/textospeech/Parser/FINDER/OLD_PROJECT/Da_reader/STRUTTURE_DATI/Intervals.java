package textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.STRUTTURE_DATI;

import textospeech.Parser.FINDER.OLD_PROJECT.Da_reader.ENUM.TypeEnum;

// STRUTTURA DATI --> contiene un singolo costrutto INTERVALLO = [POS_init,POS_end]
    /**
     * STRUTTURA DATI, mi individua un INTERVALLO.
     * Ogni INTERVALLO  è costituito da 3 variabili di tipo POS che ne individuano il TIPO, e la POSIZIONE di INIZIO e FINE.
     * CAMPI : INIT,END,TYPE_LINE.
     * INTERVALLO == POS.
     */
    public class Intervals
    {
        private Pos init;
        private Pos end;
        private TypeEnum tipoIntervallo;
        
        //COSTRUTTORE
        public Intervals()
        {
            this.init = new Pos();
            this.end = new Pos();
            this.tipoIntervallo = TypeEnum.NODATA; // <--- come assegnare un valore alla enum in un costruttore?
        }

        protected Intervals(Pos Init , Pos End , TypeEnum type_input)
        {
            this.init = Init;
            this.end = End;
            this.tipoIntervallo = type_input;
        }

        //GETTERS

        /**
         * GETTER, ritorna la POSIZIONE in cui INIZIA la sequenza
         * @return init  
         */
        public Pos getInit() 
        {
            return init;
        }

        /**
         * GETTER, ritorna la POSIZIONE in cui FINISCE la sequenza
         * @return end  
         */
        public Pos getEnd() 
        {
            return end;
        }

        /**
         * GETTER, ritorna il tipo
         * @return type_line  
         */
        public TypeEnum getType()
        {
            return tipoIntervallo;
        }

        //SETTER
        /**
         * SETTER, modifica la POSIZIONE di INIZIO
         * @param init il nuovo valore della POSIZIONE di INIZIO
         */
        public void setInit(Pos init) 
        {
            this.init = init;
        }

        /**
         * SETTER, modifica la POSIZIONE di FINE
         * @param init il nuovo valore della POSIZIONE di FINE
         */
        public void setEnd(Pos end) 
        {
            this.end = end;
        }

        /**
         * SETTER, modifica la POSIZIONE di FINE
         * @param tipo il nuovo valore per il tipo (Line_type)
         */
        public void setType(TypeEnum tipo)
        {
            this.tipoIntervallo = tipo;
        }

        ///////////////////////////
        /// METODI
        
        // dovrebbe copiarmi l'oggetto
        /**
         * Si occupa di creare una copia dell'oggetto Intervals passato come parametro, in questo modo si evita le reference
         * @param daClonare , l' Intervals da copiare
         * @return Intervals, una copia dell'oggetto 
         */
        public static Intervals CopyOf(Intervals daClonare)
        {
            Intervals intervallo = new Intervals();
            intervallo.init = Pos.CopyOf(daClonare.init);
            intervallo.end = Pos.CopyOf(daClonare.end);
            intervallo.tipoIntervallo = daClonare.tipoIntervallo;

            return intervallo;
        }


        //TOSTRING
        @Override
        public String toString() 
        {
            return "\ninizio= " + init.toString() + " \nfine=   " + end.toString() + "\ntipo=    " + tipoIntervallo + "\n\n";
        }
    }
