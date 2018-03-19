/**
 * @author: Delsin Zhang
 * Created on 03/16/2018.
 */
public class Operator extends Thread {
    private CableCar cableCar;


    // Operator constructor
    Operator (CableCar cableCar) {
        this.cableCar = cableCar;
    }


    /*
    * Method to interact with CableCar
    * */
    public void run() {
        while (!isInterrupted()) {
            try {
                cableCar.operate();
                sleep(Params.OPERATE_TIME); // Takes time to operate the cable car
                sleep(Params.operateLapse()); // Time passed before next inspection
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.interrupt();
            }
        }

    }
}
