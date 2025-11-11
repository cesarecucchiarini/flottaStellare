
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
}
