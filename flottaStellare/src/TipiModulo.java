public enum TipiModulo {
    PILOTAGGIO,
    COMBATTIVO,
    INFERMERIA,
    PROTETTIVO,
    ABITATIVO;

   public static boolean tipoEsistente(String t){
       t=t.toUpperCase();
       for(TipiModulo tipo : values()){
           if(tipo.name().equals(t))
               return true;
       }
       return false;
   }
   
   public static TipiModulo fromString(String t){
       t=t.toUpperCase();
       for(TipiModulo tipo : values()){
           if(tipo.name().equals(t))
               return tipo;
       }
       return null;
   }
}
