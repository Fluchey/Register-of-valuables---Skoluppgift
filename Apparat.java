/**
 * Anton Fluch "anfl 4215" , Inlämningsuppgift 1 PROG 2
 */
public class Apparat extends Vardesak {
    private int inköpspris;
    private int slitage;

    public Apparat(String namn, int inköpspris, int slitage){
        super(namn);
        this.inköpspris = inköpspris;
        this.slitage = slitage;
    }

    @Override
    public double getVärde(){
        return (((double)slitage / 10) * (double)inköpspris) * super.getMoms();
    }

    public String toString(){
        return "Apparat: " + getNamn() + " Värde:" + getVärde() + " Inköpspris:" + inköpspris + " Slitage:" + slitage;
    }
}
