
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Random;

public class Flotta {
    private String nome;
    private int razioni;
    ArrayList<Astronave> astronavi = new ArrayList<>();

    public Flotta(String nome, int razioni) {
        this.nome = nome;
        this.razioni = razioni;
    }
    
    public void aggiungiAstronave(Astronave nave){
        this.astronavi.add(nave);
        nave.setFlotta(this);
        razioni*=nave.getMembriVivi().size();
    }
    
    public void pasto(){
        for(Astronave astronave : this.getAstronaviIntatte()){
            for(Membro membro : astronave.getMembriVivi()){
                if(razioni>0)
                    razioni--;
                else
                    membro.morte();
            }
        }
    }
    
    public ArrayList<Astronave> getAstronaviIntatte(){
        return astronavi.stream().filter(a -> a.getStato()).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public void morteMembri(int morti){
        Random rnd = new Random();
        for(int i=0; i<morti; i++){
            ArrayList<Astronave> navi = this.getAstronaviIntatte();
            ArrayList<Membro> membri = navi.get(rnd.nextInt(navi.size())).getMembriVivi();
            membri.get(rnd.nextInt(membri.size())).morte();
        }
    }
    
    public void danniStrutturali(int danni){
        Random rnd = new Random();
        for(int i=0; i<danni; i+=10){
            ArrayList<Astronave> navi = this.getAstronaviIntatte();
            ArrayList<Modulo> moduli = navi.get(rnd.nextInt(navi.size())).getModuliIntatti();
            moduli.get(rnd.nextInt(moduli.size())).subisciDanno(10);
        }
    }
    
    public int scansionaModuli(){
        int tempoAggiunto=0, ingegneri=0;
        for(Astronave astronave : this.getAstronaviIntatte()){
            ingegneri+=astronave.getIngegneri().size();
            
            for(Modulo modulo : astronave.getModuliDanneggiati()){
                tempoAggiunto+=modulo.ripara();
            }
        }
        return (int)tempoAggiunto/ingegneri+1;
    }
    
    public Eventi evento(){
        return Eventi.getEvento();
    }
}
