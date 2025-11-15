
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Random;

public class Flotta {
    private String nome;
    private int razioni=0;
    private int razioniNave;
    ArrayList<Astronave> astronavi = new ArrayList<>();

    public Flotta(String nome, int razioniNave) {
        this.nome = nome;
        this.razioniNave = razioniNave;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getRazioni(){
        return razioni;
    }

    public void aggiungiAstronave(Astronave nave){
        this.astronavi.add(nave);
        nave.setFlotta(this);
        razioni+=razioniNave*nave.getMembriVivi().size();
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
        System.out.println("Il pasto si è concluso, razioni rimaste nella flotta: "+ razioni);
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
            for(Modulo m : moduli){
                if(m.getTipo() == TipiModulo.PROTETTIVO && m.getStato()){
                    m.subisciDanno(10);
                    break;
                }
            }
            moduli.get(rnd.nextInt(moduli.size())).subisciDanno(10);
        }
    }
    
    public int scansionaModuli(){
        int tempoAggiunto=0;
        int ingegneri=this.getMembriTipo(Ruoli.INGEGNERE).size();
        for(Astronave astronave : this.getAstronaviIntatte()){            
            for(Modulo modulo : astronave.getModuliDanneggiati()){
                tempoAggiunto+=modulo.ripara();
            }
        }
        System.out.println("La scansione e' terminata, tempo di riparazione ridotto di: " + (int)tempoAggiunto/(ingegneri+1) + " turni.");
        return (int)tempoAggiunto/(ingegneri+1);
    }

    public ArrayList<Membro> getMembriTipo(Ruoli ruolo){
        ArrayList<Membro> membri = new ArrayList<>();
        for(Astronave astronave : this.getAstronaviIntatte()){
            membri.addAll(astronave.getMembri().stream().filter(m -> m.getRuolo() == ruolo).collect(Collectors.toCollection(ArrayList::new)));
        }
        return membri;
    }

    public ArrayList<Modulo> getModuliTipo(TipiModulo tipo){
        ArrayList<Modulo> moduli = new ArrayList<>();
        for(Astronave astronave : this.getAstronaviIntatte()){
            moduli.addAll(astronave.getModuli().stream().filter(m -> m.getTipo() == tipo).collect(Collectors.toCollection(ArrayList::new)));
        }
        return moduli;
    }
}
