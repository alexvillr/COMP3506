/**
 *
 * Java base code for Security Database.
 *
 */
public abstract class SecurityDBBase {

    /** Maximum capacity of the hashtable */
    public static final int MAX_CAPACITY = 1021;

    /** Number of planes per day */
    private int numPlanes;

    /** Number of passengers per plane */
    private int numPassengersPerPlane;

    /**
     * Creates an empty hashtable and a variable to count non-empty elements.
     *
     * @param numPlanes number of planes per day
     * @param numPassengersPerPlane number of passengers per plane
     */
    public SecurityDBBase(int numPlanes, int numPassengersPerPlane) {
        this.numPlanes = numPlanes;
        this.numPassengersPerPlane = numPassengersPerPlane;
    }

    /**
     * Calculates the hash code based on the given key.
     *
     * @param key string to calculate hash code of
     * @return hash code of key
     */
    public abstract int calculateHashCode(String key);

    /**
     * Returns the number of planes per day.
     *
     * @return number of planes per day
     */
    public int getNumPlanes() {
        return numPlanes;
    }

    /**
     * Returns the number of passengers on each plane.
     *
     * @return number of passengers per plane
     */
    public int getNumPassengersPerPlane() {
        return numPassengersPerPlane;
    }

    /**
     * Returns the actual size of the hashtable, including the empty buckets.
     *
     * @return the size of the hashtable
     */
    public abstract int size();

    /**
     * Finds a passenger's name by their passport ID.
     *
     * @param passportId passenger's passport ID
     * @return the name of the person if they are in the system, otherwise null
     */
    public abstract String get(String passportId);

    /**
     * Removes a passenger from the system.
     *
     * @param passportId passenger's passport ID
     * @return true if the passenger was deleted, false if they could not be found
     */
    public abstract boolean remove(String passportId);

    /**
     * Returns true if the system contains the passenger with the given passport ID,
     * false otherwise.
     *
     * @param passportId passenger's passport ID
     * @return true if passenger is in the system, false otherwise
     */
    public boolean contains(String passportId) {
        return get(passportId) != null;
    }

    /**
     * Adds a passenger to the hashtable.
     *
     * @param name passenger's full name
     * @param passportId passenger's passport ID
     * @return true if the passenger was added successfully, false otherwise
     */
    public abstract boolean addPassenger(String name, String passportId);

    /**
     * Counts the number of passengers in the hashtable.
     *
     * @return the number of passengers
     */
    public abstract int count();

    /**
     * Returns the bucket index of the passenger in the hashtable.
     *
     * @param passportId passenger's passport ID
     * @return bucket index of passenger in hashtable
     */
    public abstract int getIndex(String passportId);
}
