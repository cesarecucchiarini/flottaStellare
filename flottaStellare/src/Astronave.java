import java.util.*;
import java.util.stream.Collectors;
public class Astronave {
    private String nome;
    private boolean stato = true;
    private HashMap<TipiModulo, Modulo> moduli = new HashMap<>();
    private ArrayList<Membro> membri = new ArrayList<>();

    public Astronave(String nome) {
        this.nome = nome;
        moduli.put(TipiModulo.PILOTAGGIO, new Modulo(100, TipiModulo.PILOTAGGIO));
        moduli.put(TipiModulo.ABITATIVO, new Modulo(100, TipiModulo.ABITATIVO));
        membri.add(new Membro("Cap", Ruoli.CAPITANO));
    }

    public String getNome() {
        return nome;
    }

    public boolean getStato() {
        return stato;
    }

    public void distruggiNave() {
        for(Membro membro : membri) {
            membro.morte();
        }
        for(Modulo modulo : moduli.values()) {
            modulo.distruggi();
        }
        this.stato = false;
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
            membri.add(membro);
    }

    public void aggiungiModulo(Modulo modulo) {
        moduli.put(modulo.getTipo(), modulo);
    }

    public boolean scansionaModulo(Modulo modulo){
        return modulo.getStato() && modulo.getSalute() < modulo.getMaxSalute();
    }

    public double riparaModulo(int ingegneri, Modulo modulo){
        modulo.ripara();
        return (modulo.getMaxSalute() - modulo.getSalute())/ ingegneri;
    }

    public void morteMembri() {
        getMembriVivi().get(new Random().nextInt(getMembriVivi().size())).morte();
        if(getMembriVivi().size() == 0) {
            this.distruggiNave();
        }
    }

    public ArrayList<Membro> getMembriVivi() {
        return membri.stream().filter(m -> m.getStato()).collect(Collectors.toCollection(ArrayList::new));
    }
}