
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

    public void preparaViaggio(){
        boolean prepara = true;
        while(prepara){
        System.out.println("Vuoi:\n"+"-1 creare una nave\n"+"-2 aggiungere un modulo ad una nave\n"+
            "-3 aggiungere un membro ad una nave\n"+"-4 iniziare il viaggio");
        switch(scanner.next()){
            case "1":
                aggiungiNave();
                break;
            case "2":
                aggiungiModulo();
                break;
            case "3":
                aggiungiMembro();
                break;
            case "4":
                prepara = false;
                break;
            default:
                System.out.println("Scelta non valida.");
                break;
            }
            
        }
        
    }

    public void aggiungiNave(){
        System.out.println("Dimmi il nome della nave:");
        String nomeNave = scanner.next();
        if(nomeNave.isEmpty() || flotta.getAstronaviIntatte().stream().anyMatch(n -> n.getNome().equals(nomeNave))){
            System.out.println("Nome non valido.");
            return;
        }
        flotta.aggiungiAstronave(new Astronave(nomeNave));
    }

    public void aggiungiModulo(){
        for(int i=0; i<flotta.getAstronaviIntatte().size(); i++){
            System.out.println(i+" - "+flotta.getAstronaviIntatte().get(i).getNome());
        }
        System.out.println("Seleziona la nave (numero) a cui vuoi aggiungere un modulo:");
        int naveIndex = scanner.nextInt();
        TipiModulo.printTipi();
        System.out.println("Seleziona la salute del modulo e il suo tipo (numero) da aggiungere:");
        try{
        flotta.getAstronaviIntatte().get(naveIndex).aggiungiModulo(new Modulo(scanner.nextInt(), TipiModulo.values()[scanner.nextInt()]));
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Inserimento non valido.");
        }
    }

    private void aggiungiMembro(){
        for(int i=0; i<flotta.getAstronaviIntatte().size(); i++){
            System.out.println(i+" - "+flotta.getAstronaviIntatte().get(i).getNome());
        }
        System.out.println("Seleziona la nave (numero) a cui vuoi aggiungere un membro:");
        int naveIdx = scanner.nextInt();
        System.out.println("Dimmi il nome del membro:");
        Ruoli.printRuoli();
        System.out.println("Seleziona il nome e il ruolo (numero) del membro da aggiungere:");
        String nomeMembro = scanner.next();
        if(nomeMembro.isEmpty()){
            System.out.println("Nome non valido.");
            return;
        }
        try{
        flotta.getAstronaviIntatte().get(naveIdx).aggiungiMembro(new Membro(nomeMembro, Ruoli.values()[scanner.nextInt()]));
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Inserimento non valido.");
        }
    }
}
