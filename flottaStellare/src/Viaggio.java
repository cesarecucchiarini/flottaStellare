
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
        System.out.println("Ci sono stati " + giorniAggiunti + " giorni aggiuntivi, " + danniSubiti + " danni subiti e " + morti + " morti.");
    }

    public void viaggio(){
        System.out.println("Inizio viaggio");
        for(giorno=0;giorno<giorniViaggio;giorno++){
            System.out.println("Giorno "+(giorno+1));
            evento();
            flotta.scansionaModuli();
            flotta.pasto();
        }
        System.out.println("Fine del viaggio");
        scanner.close();
    }

    public boolean aggiungiNave(String nomeNave, String nomeCapitano){
        if(flotta.getAstronaviIntatte().stream().anyMatch(n -> n.getNome().equals(nomeNave))){
            return false;
        }
        flotta.aggiungiAstronave(new Astronave(nomeNave, nomeCapitano));
        return true;
    }

    public void aggiungiModulo(int index, int salute, TipiModulo tipo){
        flotta.getAstronaviIntatte().get(index).aggiungiModulo(new Modulo(salute, tipo));
    }

    public boolean aggiungiMembro(int index, Ruoli ruolo, String nomeMembro){
        for(Membro m : flotta.getAstronaviIntatte().get(index).getMembriVivi()){
            if(m.getNome().equals(nomeMembro))
                return false;
        }
        flotta.getAstronaviIntatte().get(index).aggiungiMembro(new Membro(nomeMembro, ruolo));
        return true;
    }
}
