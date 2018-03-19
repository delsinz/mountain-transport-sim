/**
 * @author: Delsin Zhang
 * Created on 03/16/2018.
 */
public class CableCar extends Location{
    /*
    * Private constants to indicate the state of cable car.
    * Positions are either BOTTOM or TOP
    * */
    private final static int BOTTOM = 0;
    private final static int TOP = 1;


    private int state; // Indicate current position of cable car
    private int headTo; // Indicate the intended direction of cable car


    // New cable car is always spawned at BOTTOM, unoccupied, heading to TOP
    CableCar() {
        this.state = BOTTOM;
        this.occupiedBy = null;
        this.headTo = TOP;
    }


    /*
    * Called by Producer to make new groups
    * @param group The tour group created by Producer
    * */
    public synchronized void arrive(Group group) {
        // If cable car not at the bottom or is occupied, wait.
        while (this.state != BOTTOM || occupiedBy != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Otherwise, set to occupied, but still at BOTTOM.
        group.state = group.state * -1; // Flip the state of group to ON_TOUR
        this.occupiedBy = group;
        this.headTo = TOP;
        // Print info
        System.out.println(this.occupiedBy.toString() + " enters cable car to go up");

        notifyAll();
    }


    /*
    * Called by Consumer to remove groups that have finished the tour
    * */
    public synchronized void depart() {
        if(this.occupiedBy != null && this.occupiedBy.state == Group.OFF_TOUR) {
            // If cable car not at the bottom or is not occupied, wait.
            while (this.state != BOTTOM || occupiedBy == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Print info before occupiedBy gets dereferenced
            System.out.println(this.occupiedBy.toString() + " departs");

            // Otherwise, set to !occupied, but still at BOTTOM.
            this.occupiedBy = null;
            this.headTo = TOP;

            notifyAll();
        }
    }


    /*
    * Called by Operator. This is the method Operator interacts with CableCar
    * */
    public synchronized void operate() {
        if (this.occupiedBy == null) {
            // If the cable car is empty, then send it to the other side
            if (this.state == BOTTOM) {
                this.state = TOP;
            } else if (this.state == TOP) {
                this.state = BOTTOM;
            }
        } else {
            // If cable car is not empty
            while (this.state == this.headTo) {
                // If the car is already at intended position, wait for group to leave cable car
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // New group just gets into cable car, get moving
            if (this.state == BOTTOM) {
                this.state = TOP;
            } else if (this.state == TOP) {
                this.state = BOTTOM;
            }
        }

        notifyAll();
    }


    /*
    * Called by Train. This is the method for Group to leave CableCar for Village
    * */
    @Override
    public synchronized void leave() {
        // If the group at cable car has finished the tour, then do nothing.
        if (this.occupiedBy.state == Group.ON_TOUR) {
            // Otherwise, leave for the first village.
            System.out.println(this.occupiedBy.toString() + " leaves " + this.toString());
            this.occupiedBy = null;
            this.headTo = BOTTOM;
            notifyAll();
        }
    }


    /*
    * Called by Train. This is the method by which Group enters CableCar to go down (tour finished)
    * @param group The group that goes from the last village to cable car
    * */
    @Override
    public synchronized void enter(Group group) {
        // Check if the group is still on the tour before doing anything.
        if(group.state == Group.ON_TOUR) {
            this.occupiedBy = group;
            this.occupiedBy.state = group.state * -1; // Flip the state of the group to OFF_TOUR
            System.out.println(this.occupiedBy.toString() + " enters " + this.toString() + " to go down");
            notifyAll();
        }
    }


    public String toString() {
        return "cable car";
    }

}
