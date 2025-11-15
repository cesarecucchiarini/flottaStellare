
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

    public static boolean ruoloEsistente(String r){
        r=r.toUpperCase();
        for(Ruoli ruolo : values()){
            if(ruolo.name().equals(r))
                return true;
        }
        return false;
    }
    
    public static Ruoli fromString(String r){
        r=r.toUpperCase();
        for(Ruoli ruolo : values()){
            if(ruolo.name().equals(r))
                return ruolo;
        }
        return null;
    }
}
