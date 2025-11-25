
import java.util.Random;

public class Soluzione {
    private static int giorniAggiunti;
    private static int morti;
    private static int danniSubiti;
    private static int razioni;
    private static Random rnd = new Random();
    
    public static void fuga(){
        giorniAggiunti = rnd.nextInt(10);
        if(rnd.nextInt(2)==1){
            morti=rnd.nextInt(1, 10);
            danniSubiti=rnd.nextInt(20, 51);
        }
    }
    
    public static void battaglia(int soldati, int moduli){
        morti = rnd.nextInt(Math.max(0, 30-soldati-moduli));
        giorniAggiunti = rnd.nextInt(1,6);
        danniSubiti = Math.max(0, giorniAggiunti*10 - moduli*4);
    }
    
    public static void controlloMembri(int scienziati, int moduli){
        morti = rnd.nextInt(2, Math.max(0,10-scienziati-moduli));
        giorniAggiunti = rnd.nextInt(Math.max(0, 6-moduli));
    }
    
    public static void esca(){
        morti = rnd.nextInt(1,6);
    }
    
    public static void viaggioMeteorico(int capitani){
        giorniAggiunti = rnd.nextInt(2, Math.max(0, 6-capitani));
        danniSubiti = giorniAggiunti*10 - capitani*4;
    }
    
    public static void viaggioNonMeteorico(){
        giorniAggiunti = rnd.nextInt(3, 9);
    }
    
    public static void lockdown(){
        giorniAggiunti = rnd.nextInt(5)+1;
        morti = rnd.nextInt(10);
    }
    
    public static void antibiotico(int medici, int moduli){
        giorniAggiunti = rnd.nextInt(Math.max(0, 5-medici-moduli));
        morti = rnd.nextInt(Math.max(0, 15-medici-moduli));
    }
    
    public static void prendiScatole(){
        if(rnd.nextInt(2) == 1){
            razioni = rnd.nextInt(20, 51);
        }
        else{
            razioni = rnd.nextInt(-15, -21);
        }
    }
    
    public static int getMorti(){
        int t = morti;
        morti=0;
        return t;
    }
    
    public static int getdanniSubiti(){
        int t = danniSubiti;
        danniSubiti=0;
        t = ((int)(t/10)) * 10;
        return t;
    }
    
    public static int getGiorniAggiunti(){
        int t = giorniAggiunti;
        giorniAggiunti=0;
        return t;
    }
    
    public static int getRazioni(){
        int t = razioni;
        razioni=0;
        return t;
    }
}
