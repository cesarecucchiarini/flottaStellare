public enum TipiModulo {
    PILOTAGGIO,
    COMBATTIVO,
    INFERMERIA,
    PROTETTIVO,
    ABITATIVO;

    public static void printTipi(){
        for(TipiModulo tipo : TipiModulo.values()){
            System.out.println(tipo.ordinal()+" - "+tipo.name());
        }
    }
}
