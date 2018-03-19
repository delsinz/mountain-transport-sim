/**
 * @author: Delsin Zhang
 * Created on 03/16/2018.
 */
public class Location {
    // Indicate which group currently occupies the location
    volatile Group occupiedBy;


    /*
    * Called by Train. This is the method Group enters Village from previous location.
    * @param group The group that is about to enter the location.
    * */
    public synchronized void enter(Group group) {
        // Make sure group is still on the tour before doing anything.
        if(group.state == Group.ON_TOUR) {
            this.occupiedBy = group; // Enters village.
            System.out.println(this.occupiedBy.toString() + " enters " + this.toString());
            notifyAll();
        }
    }


    /*
    * Called by Train. This is the method a group leave a Village for next location.
    * */
    public synchronized void leave() {
        // Make sure group is still on the tour before doing anything.
        if(this.occupiedBy.state == Group.ON_TOUR) {
            System.out.println(this.occupiedBy.toString() + " leaves " + this.toString());
            this.occupiedBy = null; // Leaves village
            notifyAll();
        }
    }
}
