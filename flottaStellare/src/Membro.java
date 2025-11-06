public class Membro {
    private String nome;
    private Ruoli ruolo;
    private boolean stato = true;

    public Membro(String nome, Ruoli ruolo) {
        this.nome = nome;
        this.ruolo = ruolo;
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
    }
}
