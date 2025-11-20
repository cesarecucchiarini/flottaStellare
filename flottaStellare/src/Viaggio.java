
import java.util.ArrayList;
import java.util.Scanner;

public class Viaggio {
    private int giorniViaggio;
    private int giorno;
    private Flotta flotta;
    private Scanner scanner = new Scanner(System.in);
    private Eventi eventoAttuale;

    public Viaggio(int giorniViaggio, Flotta flotta) {
        this.giorniViaggio = giorniViaggio;
        this.flotta = flotta;
    }

    public void aumentaGiorni(int giorni) {
        this.giorniViaggio += giorni;
    }
    
    public int getGiorniRimasti(){
        return giorniViaggio;
    }
    
    public Flotta getFlotta(){
        return flotta;
    }
    
    public ArrayList<Astronave> getNavi(){
        return flotta.getAstronaviIntatte();
    }
    
    public ArrayList<Membro> getMembri(int index){
        return flotta.getAstronaviIntatte().get(index).getMembriVivi();
    }
    public ArrayList<Modulo> getModuli(int index){
        return flotta.getAstronaviIntatte().get(index).getModuliIntatti();
    }
    
    public void chiamaEvento(){
        eventoAttuale= Eventi.NIENTE;
        while(eventoAttuale.equals(Eventi.NIENTE) && giorniViaggio>0){
            giorniViaggio--;
            flotta.pasto();
            eventoAttuale=Eventi.getEvento();
        }
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
                return new String[]{"Controlla i memnri", "Usa un'esca"}; 
            }
            case CONTAMINAZIONE -> {
                return new String[]{"Inietta l'antibiotico", "Fai il lockdown"};
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
        for(Astronave a : getNavi()){
            if(!a.getMembriVivi().isEmpty()){
                m=true;
            }
        }
        return m && giorniViaggio>0;
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
        this.giorniViaggio+=ris[0];
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
}
