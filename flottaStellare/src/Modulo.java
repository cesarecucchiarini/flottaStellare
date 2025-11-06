public class Modulo {
    private int salute, maxSalute;
    private TipiModulo tipo;
    private boolean stato = true;

    public Modulo(int maxSalute, TipiModulo tipo) {
        this.salute = this.maxSalute = maxSalute;
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
        this.stato = this.salute > 0;
    }

    public void distruggi(){
        this.stato=false;
    }

    public int getSalute() {
        return salute;
    }

    public int getMaxSalute() {
        return maxSalute;
    }

    public void ripara() {
        this.salute = this.maxSalute;
    }
}
