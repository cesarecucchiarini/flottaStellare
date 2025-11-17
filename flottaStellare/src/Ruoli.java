
import java.util.Random;
import java.util.*;

public enum Ruoli {
    CAPITANO,
    INGEGNERE,
    SCIENZIATO,
    MEDICO,
    SOLDATO;

    public static Ruoli getRuolo(){
        return values()[new Random().nextInt(values().length-1)+1];
    }
    
    public static ArrayList<Ruoli> getRuoli(){
        return new ArrayList<>(Arrays.asList(values()));
    }
}
