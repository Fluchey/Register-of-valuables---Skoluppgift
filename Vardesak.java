/**
 * Anton Fluch "anfl 4215" , Inlämningsuppgift 1 PROG 2
 */
abstract class Vardesak {
    protected String namn;
    protected double moms = 1.25;

    public Vardesak(String namn){
        this.namn = namn;
    }

    protected String getNamn(){
        return namn;
    }

    protected double getMoms(){
        return moms;
    } // Returnerar den aktuella moms-satsen

    protected abstract double getVärde();
}
