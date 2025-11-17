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
        if(!this.stato)
            this.distruggi();
    }

    public void distruggi(){
        this.stato=false;
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
        return tempo;
    }

    public void setAstronave(Astronave astronave) {
        this.astronave = astronave;
    }
}
