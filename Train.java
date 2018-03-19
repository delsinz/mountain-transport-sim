/**
 * @author: Delsin Zhang
 * Created on 03/16/2018.
 */
public class Train extends Thread {
    private volatile Location fromLocation, toLocation; // A train always goes from fromLocation to toLocation.
    private Location where; // Probably won't be used. Leave it there for now.

    // Train constructor
    Train (Location fromLocation, Location toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.where = fromLocation;
    }


    public void run() {
        int tempId, tempState; // Temp variables to pass the state of Group.

        while(!isInterrupted()) {
            try {
                // If fromLocation has no group yet, wait.
                while(this.fromLocation.occupiedBy == null ||
                        // Or if fromLocation has a group, but toLocation is occupied by another group, wait.
                        (this.fromLocation.occupiedBy != null && this.toLocation.occupiedBy != null ));

                // Otherwise, Train goes from fromLocation to toLocation
                tempId = fromLocation.occupiedBy.id;
                tempState = fromLocation.occupiedBy.state;
                fromLocation.leave();
                sleep(Params.JOURNEY_TIME);
                toLocation.enter(new Group(tempId, tempState));

                // Train returns to fromLocation
                sleep(Params.JOURNEY_TIME);
                this.where = fromLocation;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
