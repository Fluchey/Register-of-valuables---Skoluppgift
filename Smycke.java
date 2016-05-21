/**
 * Anton Fluch "anfl 4215" , Inlämningsuppgift 1 PROG 2
 */
public class Smycke extends Vardesak{
    private int antalStenar;
    private boolean arGuld; // Guld eller silver

    public Smycke(String namn, int antalStenar, boolean arGuld){
        super(namn);
        this.antalStenar = antalStenar;
        this.arGuld = arGuld;
    }

    @Override
    public double getVärde(){
        return ((arGuld ? 2000:700) + (500 * antalStenar)) * super.getMoms();
    }

    public String toString(){
        return "Smycke: " + getNamn() + " Värde:" + getVärde() + " Antal Stenar:" + antalStenar + " " + (arGuld ? "Guld":"Silver");
    }
}
