public class Membro {
    private String nome;
    private Ruoli ruolo;
    private boolean stato = true;
    private Astronave astronave;

    public Membro(String nome, Ruoli ruolo){
        this.nome = nome;
        this.ruolo = ruolo;
    }

    public Membro(String nome){
        this.nome = nome;
        this.ruolo = Ruoli.getRuolo();
    }

    public String getNome() {
        return nome;
    }

    public Ruoli getRuolo() {
        return ruolo;
    }

    public boolean getStato() {
        return stato;
    }

    public void morte() {
        this.stato = false;
        System.out.println(this +" è morto.");
        this.astronave.distruggiNave();
    }

    public void setAstronave(Astronave astronave) {
        System.out.println(this +" è stato assegnato alla nave " + astronave.getNome());
        this.astronave = astronave;
    }
    
    public void togliRuolo(){
        Ruoli ruolo =Ruoli.getRuolo();
        System.out.println(this +" ha cambiato il suo ruolo in: " + ruolo);
        this.ruolo=ruolo;
    }

    @Override
    public String toString() {
        return "Membro \'"+nome+"\' -" + ruolo + "- della nave " + (astronave != null ? "\'" + astronave.getNome() + "\'" : "nessuna");
    }
}
