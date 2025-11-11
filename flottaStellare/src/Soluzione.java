
import java.util.Random;

public class Soluzione {
    private static int giorniAggiunti;
    private static int morti;
    private static int danniSubiti;
    private static Random rnd = new Random();
    
    public static void fuga(int capitani){
        giorniAggiunti = (int) Math.floor(rnd.nextInt(10)/(capitani+1));
    }
    
    public static void battaglia(int soldati, boolean modulo){
        morti = (int) Math.floor(rnd.nextInt(30)/(soldati+1)) - (modulo ? 15 : 0);
        giorniAggiunti = rnd.nextInt(5)+1;
        danniSubiti = giorniAggiunti*15 - (modulo ? giorniAggiunti*4 : 0);
    }
    
    public static void controlloMembri(int scienziati, boolean modulo){
        giorniAggiunti = (int) Math.floor((rnd.nextInt(10)+2)/(scienziati+1)) - (modulo ? 3 : 0);
        morti = rnd.nextInt(modulo ? 3 : 6);
    }
    
    public static void viaggioMeteorico(int capitani){
        giorniAggiunti = (int) Math.floor((rnd.nextInt(6)+2)/(capitani+1));
        danniSubiti = (int) Math.floor(giorniAggiunti*15/(capitani+1));
    }
    
    public static void viaggioNonMeteorico(){
        giorniAggiunti = rnd.nextInt(6)+3;
    }
    
    public static void lockdown(){
        giorniAggiunti = rnd.nextInt(5)+1;
        morti = rnd.nextInt(10);
    }
    
    public static void antibiotico(int medici, boolean modulo){
        giorniAggiunti = (int) Math.floor((rnd.nextInt(5)+1)/(medici+1)) - (modulo ? 2 : 0);
        morti = (int) Math.floor(rnd.nextInt(15)/(medici+1)) - (modulo ? 3 : 0);
    }
    
    public static int getMorti(){
        int t = morti;
        morti=0;
        return t;
    }
    
    public static int getdaniSubiti(){
        int t = danniSubiti;
        danniSubiti=0;
        return t;
    }
    
    public static int getGiorniAggiunti(){
        int t = giorniAggiunti;
        giorniAggiunti=0;
        return t;
    }
}
