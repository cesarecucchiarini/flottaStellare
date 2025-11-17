
import java.util.ArrayList;
import java.util.Scanner;

public class Viaggio {
    private int giorniViaggio;
    private int giorno;
    private Flotta flotta;
    private Scanner scanner = new Scanner(System.in);

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
    
    public void risolviEvento(Eventi e){
        switch (e) {
            case CAMPO_METEORICO:
                System.out.println("Evento: Campo Meteorico\n"+"Vuoi girarci attorno (s) o attraversarlo (a)?");
                if(scanner.next().equals("s"))
                    Soluzione.viaggioNonMeteorico();
                else
                    Soluzione.viaggioMeteorico(flotta.getMembriTipo(Ruoli.CAPITANO).size());
                break;
            case ATTACCO_ALIENO:
                System.out.println("Evento: Attacco Alieno\n"+"Vuoi combattere (c) o fuggire (f)?");
                if(scanner.next().equals("c"))
                    Soluzione.battaglia(flotta.getMembriTipo(Ruoli.SOLDATO).size(), flotta.getModuliTipo(TipiModulo.COMBATTIVO).size());
                else
                    Soluzione.fuga(flotta.getMembriTipo(Ruoli.CAPITANO).size());
                break;
            case INTRUSIONE_ALIENA:
                System.out.println("Evento: Intrusione Aliena");
                Soluzione.controlloMembri(flotta.getMembriTipo(Ruoli.SCIENZIATO).size(), flotta.getModuliTipo(TipiModulo.INFERMERIA).size());
                break;
            case CONTAMINAZIONE:
                System.out.println("Evento: Contaminazione\n"+"Vuoi fare il lockdown (l) o somministrare antibiotici (a)?");
                if(scanner.next().equals("l"))
                    Soluzione.lockdown();
                else
                    Soluzione.antibiotico(flotta.getMembriTipo(Ruoli.MEDICO).size(), flotta.getModuliTipo(TipiModulo.INFERMERIA).size());
                break;
            default:
                break;
        }
    }
    public void evento(){
        Eventi evento = Eventi.getEvento();
        if(evento == Eventi.NIENTE){
            return;
        }
        risolviEvento(evento);
        int giorniAggiunti = Soluzione.getGiorniAggiunti(), danniSubiti = Soluzione.getdanniSubiti(), morti = Soluzione.getMorti();
        aumentaGiorni(giorniAggiunti);
        flotta.danniStrutturali(danniSubiti);
        flotta.morteMembri(morti);
    }

    public void viaggio(){
        for(giorno=0;giorno<giorniViaggio;giorno++){
            evento();
            flotta.scansionaModuli();
            flotta.pasto();
        }
        scanner.close();
    }

    public boolean aggiungiNave(String nomeNave){
        if(flotta.getAstronaviIntatte().stream().anyMatch(n -> n.getNome().equals(nomeNave))){
            return false;
        }
        flotta.aggiungiAstronave(new Astronave(nomeNave));
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
}
