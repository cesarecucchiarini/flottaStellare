
import java.util.Random;
import java.util.*;

public enum Ruoli {
    CAPITANO,
    RANDOM,
    INGEGNERE,
    SCIENZIATO,
    MEDICO,
    SOLDATO;

    public static Ruoli getRuolo(){
        return values()[new Random().nextInt(2,values().length)];
    }
    
    public static ArrayList<Ruoli> getRuoli(){
        return new ArrayList<>(Arrays.asList(values()));
    }
    
    public static ArrayList<Ruoli> getSceltaRuoli(){
        ArrayList<Ruoli> a = new ArrayList<>(Arrays.asList(values()));
        a.remove(0);
        return a;
    }
}
