
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
        membri.add(new Membro("Cap", Ruoli.CAPITANO));
    }
    
    public void setFlotta(Flotta flotta){
        this.flotta = flotta;
    }
    
    public String getNome() {
        return nome;
    }

    public boolean getStato() {
        return stato;
    }
    
    //i membri non muoiono
    public void distruggiNave() {
        Random rnd = new Random();
        if(this.getMembriVivi().isEmpty() || !this.moduli.get(TipiModulo.PILOTAGGIO).getStato()){
            membri.get(0).togliRuolo();
            while(membri.size()>0){
                Astronave nave = flotta.getAstronaviIntatte().get(rnd.nextInt(flotta.getAstronaviIntatte().size()));
                nave.aggiungiMembro(membri.get(0));
                this.rimuoviMembro(membri.get(0));
            }
            for(Modulo modulo : moduli.values()) {
                modulo.distruggi();
            }
            this.stato = false;
        }
    }

    public ArrayList<Modulo> getModuli() {
        return new ArrayList<Modulo>(moduli.values());
    }

    public ArrayList<Membro> getMembri() {
        return membri;
    }

    public ArrayList<Membro> getIngegneri() {
        return membri.stream().filter(m -> m.getRuolo() == Ruoli.INGEGNERE).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Membro> getMedici() {
        return membri.stream().filter(m -> m.getRuolo() == Ruoli.MEDICO).collect(Collectors.toCollection(ArrayList::new));
    }   

    public ArrayList<Membro> getSoldati() {
        return membri.stream().filter(m -> m.getRuolo() == Ruoli.SOLDATO).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Membro> getScienziati() {
        return membri.stream().filter(m -> m.getRuolo() == Ruoli.SCIENZIATO).collect(Collectors.toCollection(ArrayList::new));
    }

    public Membro getCapitano() {
        for (Membro m : membri) {
            if (m.getRuolo() == Ruoli.CAPITANO) {
                return m;
            }
        }
        return null;
    }

    public void aggiungiMembro(Membro membro) {
        if(membro.getRuolo() != Ruoli.CAPITANO)
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
        getMembriVivi().get(new Random().nextInt(getMembriVivi().size())).morte();
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