
import java.util.*;
import java.util.stream.Collectors;

public class Astronave {
    private String nome;
    private boolean stato = true;
    private HashMap<TipiModulo, Modulo> moduli = new HashMap<>();
    private ArrayList<Membro> membri = new ArrayList<>();
    private Flotta flotta;

    public Astronave(String nome) {
        this.nome = nome;
        moduli.put(TipiModulo.PILOTAGGIO, new Modulo(100, TipiModulo.PILOTAGGIO));
        moduli.put(TipiModulo.ABITATIVO, new Modulo(100, TipiModulo.ABITATIVO));
        membri.add(new Membro(nome+" #"+0, Ruoli.CAPITANO));
    }
    
    public ArrayList<Membro> getMembri(){
        return membri;
    }
    
    public void setFlotta(Flotta flotta){
        this.flotta = flotta;
    }
    
    public String getNome() {
        return nome;
    }
    public String getNomeIntatto(){
        return stato ? nome : "<html><s>"+nome+"</s></html>";
    }

    public boolean getStato() {
        return stato;
    }
    
    public void distruggiNave() {
        if(this.getMembriVivi().isEmpty() || !this.moduli.get(TipiModulo.PILOTAGGIO).getStato()){
            this.stato = false;
            for(Modulo modulo : moduli.values()) {
                modulo.distruggi();
            }
            if(!flotta.getAstronaviIntatte().isEmpty()){
                Random rnd = new Random();
                 ArrayList<Membro> membriVivi = getMembriVivi();
                while(membriVivi.size()>0){
                    Astronave nave = flotta.getAstronaviIntatte().get(rnd.nextInt(flotta.getAstronaviIntatte().size()));                  
                    for(int i =0; i< membriVivi.size(); i+=0){
                        nave.aggiungiMembro(membriVivi.get(0));
                        this.rimuoviMembro(membriVivi.get(0));
                        membriVivi.remove(0);
                    }    
                }
            }
            else{
                for(Membro m : getMembriVivi()){
                    m.morte();
                }
            }
        }
    }

    public ArrayList<Modulo> getModuli() {
        return new ArrayList<Modulo>(moduli.values());
    }

    public void aggiungiMembro(Membro membro) {
        if(membro.getRuolo() == Ruoli.CAPITANO)
            membro.togliRuolo();
        membri.add(membro);
        membro.setAstronave(this);
    }

    public void aggiungiModulo(Modulo modulo) {
        moduli.put(modulo.getTipo(), modulo);
        modulo.setAstronave(this);
    }

    public boolean scansionaModulo(Modulo modulo){
        return modulo.getStato() && modulo.getSalute() < modulo.getMaxSalute();
    }

    public void morteMembri() {
        getMembriVivi().get(new Random().nextInt(getMembriVivi().size())).morteInNave();
    }

    public ArrayList<Membro> getMembriVivi() {
        return membri.stream().filter(m -> m.getStato()).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<Modulo> getModuliIntatti(){
        return moduli.values().stream().filter(m -> m.getStato()).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<Modulo> getModuliDanneggiati(){
        return moduli.values().stream().filter(m -> m.getStato() && m.getSalute()<m.getMaxSalute()).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public void inizializza(){
        moduli.get(TipiModulo.PILOTAGGIO).setAstronave(this);
        moduli.get(TipiModulo.ABITATIVO).setAstronave(this);
        membri.get(0).setAstronave(this);
    }
    
    public void rimuoviMembro(Membro membro){
        this.membri.remove(membro);
    }
}