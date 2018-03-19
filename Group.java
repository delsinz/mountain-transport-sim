/**
 * A group of tourists, each with its unique id, who travel 
 * between the villages by train.
 * 
 * @author ngeard@unimelb.edu.au
 * @modifiedBy Delsin
 */

public class Group {
    // Indicator constants to show if the group has finished the tour
    public final static int OFF_TOUR = 1;
    public final static int ON_TOUR = -1;

    protected int state;

	// a unique identifier for this tour group
	protected int id;
	
	//the next ID to be allocated
	protected static int nextId = 1;

	//create a new vessel with a given Id
	protected Group(int id) {
		this.id = id;
		this.state = OFF_TOUR; // New group is always spawned with OFF_TOUR state. i.e. Group is not on the tour
	}

	protected Group(int id, int state) {
	    this.id = id;
	    this.state = state;
    }


    // Make a copy of Group with the same state.
    // Not used.
	public static Group cloneGroup(Group group) {
	    return new Group(group.id);
    }

	//get a new Group instance with a unique Id
	public static Group getNewGroup() {
		return new Group(nextId++);
	}

	//produce the Id of this group
	public int getId() {
		return id;
	}

	//produce an identifying string for the group
	public String toString() {
		return "group [" + id + "]";
	}
}
