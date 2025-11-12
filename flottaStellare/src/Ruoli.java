
import java.util.Random;

public enum Ruoli {
    CAPITANO,
    INGEGNERE,
    SCIENZIATO,
    MEDICO,
    SOLDATO;

    public static Ruoli getRuolo(){
        return values()[new Random().nextInt(values().length-1)+1];
    }

    public static void printRuoli(){
        for(Ruoli ruolo : Ruoli.values()){
            System.out.println(ruolo.ordinal()+" - "+ruolo.name());
        }
    }
}
