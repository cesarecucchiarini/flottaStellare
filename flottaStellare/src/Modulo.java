public class Modulo {
    private int salute, maxSalute;
    private TipiModulo tipo;
    private boolean stato = true;
    private Astronave astronave;

    public Modulo(int maxSalute, TipiModulo tipo) {
        this.salute = this.maxSalute = Math.max(10, maxSalute);
        this.tipo = tipo;
    }

    public TipiModulo getTipo() {
        return tipo;
    }

    public boolean getStato() {
        return stato;
    }

    public void subisciDanno(int danno) {
        this.salute -= danno;
        System.out.println(this + " ha subito " + danno + " danni.");
        if(!this.stato)
            this.distruggi();
    }

    public void distruggi(){
        this.stato=false;
        System.out.println(this + " è stato distrutto.");
        astronave.distruggiNave();
    }

    public int getSalute() {
        return salute;
    }

    public int getMaxSalute() {
        return maxSalute;
    }

    public int ripara() {
        int tempo = maxSalute-salute;
        this.salute = this.maxSalute;
        System.out.println(this + " è stato riparato in " + tempo + " giorni.");
        return tempo;
    }

    public void setAstronave(Astronave astronave) {
        System.out.println(this + " è stato installato alla nave " + astronave.getNome());
        this.astronave = astronave;
    }

    @Override
    public String toString() {
        return "Modulo della nave " + (astronave != null ? "\'" + astronave.getNome() + "\'" : "nessuna") + " di tipo " + tipo + " salute: " + salute;
    }
}
