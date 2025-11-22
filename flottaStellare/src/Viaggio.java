
import java.util.ArrayList;

public class Viaggio {
    private int giorniViaggio=0;
    private int giorniTot;
    private Flotta flotta;
    private Eventi eventoAttuale;
    private int mortiFame;

    public Viaggio(int giorniViaggio, Flotta flotta) {
        this.giorniTot = giorniViaggio;
        this.flotta = flotta;
    }

    public void aumentaGiorni(int giorni) {
        this.giorniViaggio += giorni;
    }
    
    public int getGiornoAttuale(){
        return giorniViaggio;
    }
    public int getGiorniTot(){
        return giorniTot;
    }
    
    public Flotta getFlotta(){
        return flotta;
    }
    
    public ArrayList<Astronave> getNaviIntatte(){
        return flotta.getAstronaviIntatte();
    }
     public ArrayList<Astronave> getNavi(){
        return flotta.getAstronavi();
    }
    public ArrayList<Membro> getMembriVivi(int index){
        return flotta.getAstronaviIntatte().get(index).getMembriVivi();
    }
    public ArrayList<Membro> getMembri(int index){
        return flotta.getAstronavi().get(index).getMembri();
    }
    public ArrayList<Modulo> getModuliIntatti(int index){
        return flotta.getAstronaviIntatte().get(index).getModuliIntatti();
    }
    public ArrayList<Modulo> getModuli(int index){
        return flotta.getAstronavi().get(index).getModuli();
    }
    
    
    public int chiamaEvento(){
        mortiFame=0;
        eventoAttuale= Eventi.NIENTE;
        while(eventoAttuale.equals(Eventi.NIENTE) && giorniViaggio<giorniTot){
            giorniViaggio++;
            mortiFame+=flotta.pasto();
            eventoAttuale=Eventi.getEvento();
        }
        return mortiFame;
    }
    
    public Eventi getEvento(){
        return eventoAttuale;
    }
    
    public String descriviEvento(){
        switch (eventoAttuale) {
            case CAMPO_METEORICO -> {
                return "Appare un campo di meteore nell'orizzonte, si può provare a "
                        + "passarci in mezzo oppure a girarci intorno";
            }
            case ATTACCO_ALIENO -> {
                return "Una flotta di navi aliene si vede nei radar, lo spazio è pieno di pirati, si può provare "
                        + "a combattere oppure a scappare";
            }
            case INTRUSIONE_ALIENA -> {
                return "Tracce aliene vengono trovate a bordo di una nave, non si sa chi sia l'alieno, "
                        + "si può fare un controllo oppure usare un membro come esca";
            }
            case CONTAMINAZIONE -> {
                return "E stato trovato un batterio alieno all'interno di una nave, "
                        + "si può iniettare a tutti un vaccino sperimentale oppure fare un lockdown per sicurezza";
            }
            case RISORSE_TROVATE -> {
                return "Durante il viaggio la flotta trova delle scatole vaganti per lo spazio, si possono aprire o lasciarle";
            }
            default -> {
                return"";
            }
        }
    }
    public String[] getBottoni(){
        switch(eventoAttuale){
            case CAMPO_METEORICO -> {
                return new String[]{"Passa in mezzo", "Gira attorno"};
            }
            case ATTACCO_ALIENO -> {
                return new String[]{"Combatti", "Scappa"}; 
            }
            case INTRUSIONE_ALIENA -> {
                return new String[]{"Controlla i membri", "Usa un'esca"}; 
            }
            case CONTAMINAZIONE -> {
                return new String[]{"Inietta l'antibiotico", "Fai il lockdown"};
            }
            case RISORSE_TROVATE -> {
                return new String[]{"Prendi le scatole", "Lascia le scatole"};
            }
            default -> {
                return null;
            }
        }
    }
    public int[] risolviEvento(boolean scelta){
        switch(scelta){
            case true ->{
                switch(eventoAttuale){
                    case CAMPO_METEORICO -> {
                        Soluzione.viaggioMeteorico(flotta.getMembriTipo(Ruoli.CAPITANO));
                    }
                    case ATTACCO_ALIENO -> {
                        Soluzione.battaglia(flotta.getMembriTipo(Ruoli.SOLDATO), flotta.getModuliTipo(TipiModulo.COMBATTIVO)); 
                    }
                    case INTRUSIONE_ALIENA -> {
                        Soluzione.controlloMembri(flotta.getMembriTipo(Ruoli.SCIENZIATO), flotta.getModuliTipo(TipiModulo.INFERMERIA)); 
                    }
                    case CONTAMINAZIONE -> {
                        Soluzione.battaglia(flotta.getMembriTipo(Ruoli.MEDICO), flotta.getModuliTipo(TipiModulo.INFERMERIA));
                    }
                    case RISORSE_TROVATE -> {
                        Soluzione.prendiScatole();
                    }
                    default -> {
                        return null;
                    }
                }
            }
            default ->{
                switch(eventoAttuale){
                    case CAMPO_METEORICO -> {
                        Soluzione.viaggioNonMeteorico();
                    }
                    case ATTACCO_ALIENO -> {
                        Soluzione.fuga(); 
                    }
                    case INTRUSIONE_ALIENA -> {
                        Soluzione.esca(); 
                    }
                    case CONTAMINAZIONE -> {
                       Soluzione.lockdown();
                    }
                    case RISORSE_TROVATE -> {
                        Soluzione.lasciaScatole();
                    }
                    default -> {
                        return null;
                    }
                }
            }
        }
        return new int[]{Soluzione.getGiorniAggiunti(), Math.min(Soluzione.getMorti(), getNumeroMembri()) , Math.min(Soluzione.getdanniSubiti(), getSaluteModuli())};
    }
    
    public boolean controllaFine(){
        boolean m=false;
        for(Astronave a : getNaviIntatte()){
            if(!a.getMembriVivi().isEmpty()){
                m=true;
            }
        }
        return m && giorniViaggio<giorniTot;
    }

    public boolean aggiungiNave(String nomeNave){
        if(flotta.getAstronaviIntatte().stream().anyMatch(n -> n.getNome().equals(nomeNave))){
            return false;
        }
        flotta.aggiungiAstronave(new Astronave(nomeNave));
        flotta.getAstronaviIntatte().getLast().inizializza();
        return true;
    }

    public void aggiungiModulo(Astronave a, int salute, ArrayList<TipiModulo> tipi){
        for(TipiModulo t : tipi){
            a.aggiungiModulo(new Modulo(salute, t));
        }
    }
    
    public void aggiungiMembri(Astronave a, int numero, Ruoli ruolo){
        boolean salto;
        int scarto=0;
        for(int i=0; i<numero; i++){
            String nome = a.getNome()+" #"+(i+scarto);
            salto=false;
            for(Membro m : a.getMembriVivi()){
                if(m.getNome().equals(nome))
                    salto=true;
            }
            if(salto){
                i--;
                scarto++;
            }   
            else{
                a.aggiungiMembro(new Membro(nome, ruolo));
            }
        }   
        flotta.setRazioni(numero);
    }
    
    public void subisciEffetti(int[] ris){
        this.giorniTot+=ris[0];
        flotta.morteMembri(ris[1]);
        flotta.danniStrutturali(ris[2]);
    }
    
    public int getNumeroMembri(){
        int i =0;
        for(Astronave a : flotta.getAstronaviIntatte()){
            i+=a.getMembriVivi().size();
        }
        return i;
    }
    public int getSaluteModuli(){
        int i =0;
        for(Astronave a : flotta.getAstronaviIntatte()){
            for(Modulo m : a.getModuliIntatti()){
                i+=m.getSalute();
            }
        }
        return i;
    }
    
    public String getMotivoFine(){
        if(giorniViaggio==giorniTot)
            return "La flotta ha raggiunto la sua destinazione";
        if(mortiFame>0)
            return "I membri sono morti di fame";
        if(getNumeroMembri()==0)
            return "Tutti i membri della flotta sono morti, il viaggio non può continuare";       
        return null;
    }
    
    public int scansionaModuli(){
        int t;
        t=flotta.scansionaModuli();
        giorniTot+=t;
        return t;
    }
}
