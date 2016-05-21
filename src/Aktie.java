/**
 * Anton Fluch "anfl 4215" , Inlämningsuppgift 1 PROG 2
 */
public class Aktie extends Vardesak {
    private int antal;
    private double kurs;

    public Aktie(String namn, int antal, double kurs){
        super(namn);
        this.antal = antal;
        this.kurs = kurs;
    }

    public void setKurs(int nyKurs){
        this.kurs = nyKurs;
    }

    @Override
    public double getVärde(){
        return (antal * kurs) * super.getMoms();
    }

    public String toString(){
        return "Aktie: " + getNamn() + " Värde:" + getVärde() + " Antal:" + antal + " Kurs:" + kurs;
    }
}
