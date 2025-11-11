
import java.util.Scanner;

public class Viaggio {
    private int giorniViaggio;
    private Flotta flotta;
    private Scanner scanner = new Scanner(System.in);

    public Viaggio(int giorniViaggio, Flotta flotta) {
        this.giorniViaggio = giorniViaggio;
        this.flotta = flotta;
    }

    public void aumentaGiorni(int giorni) {
        this.giorniViaggio += giorni;
    }

    public void evento(){
        switch (Eventi.getEvento()) {
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
        aumentaGiorni(Soluzione.getGiorniAggiunti());
        flotta.danniStrutturali(Soluzione.getdaniSubiti());
        flotta.morteMembri(Soluzione.getMorti());
    }

    public void viaggio(){
        System.out.println("Inizio viaggio");
        for(int i=0;i<giorniViaggio;i++){
            System.out.println("Giorno "+(i+1));
            evento();
            flotta.scansionaModuli();
            flotta.pasto();
        }
    }
    //finire
    public void preparaViaggio(){
        System.out.println("Vuoi:\n"+"-1 creare una nave\n"+"-2 aggiungere un modulo ad una nave\n"+
            "-3 aggiungere un membro ad una nave\n"+"-4 iniziare il viaggio");
        switch(scanner.next()){
            case "1":
        }
    }
}
