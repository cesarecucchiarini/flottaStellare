
import java.util.Random;

public enum Eventi {
    NIENTE,
    CAMPO_METEORICO,
    ATTACCO_ALIENO,
    INTRUSIONE_ALIENA,
    CONTAMINAZIONE;
    
    public static Eventi getEvento(){
        Random rnd = new Random();
        return rnd.nextInt(2)==1 ? values()[rnd.nextInt(4)+1] : NIENTE;
    }
}
