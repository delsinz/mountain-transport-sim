/**
 * @author: Delsin Zhang
 * Created on 03/16/2018.
 */
public class Village extends Location {
    private int id;

    // Village constructor
    Village (int id) {
        this.id = id;
        this.occupiedBy = null;
    }


    public String toString() {
        return ("village [" + this.id + "]");
    }

}
