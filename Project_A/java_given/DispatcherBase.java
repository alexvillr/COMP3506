/*
    General information about the priority:
    The priority must be given to the planes with the earliest estimated time.
    If two or more planes have the same estimated time ->
        the priority must be given to a plane with the plane_number,
        that comes first in alphabetical order

    Example:

        plane 1: "ABC1233", "9:24"
        plane 2: "ENC3453", "8:23"
        => the priority must be given to the plane 2

        plane 1: "ABC1233", "9:24"
        plane 2: "BAA1113", "9:24"
        => the priority must be given to the plane 1

        plane 1: "ABC1233", "9:24"
        plane 2: "ABC1234", "9:24"
        => the priority must be given to the plane 1
 */

public abstract class DispatcherBase {

    /**
     * Computes the number of planes in the system.
     *
     * @return the number of planes in the system
     */
    public abstract int size();

    /**
     * Adds a plane to the system.
     * The time complexity must be O(n).
     *
     * @param planeNumber string with three letters, followed by four numbers.
     *                    Example: "ABC1243", "ENC3455"
     * @param time represents time in 24 hour format.
     *             Example: "9:24", "15:32"
     */
    public abstract void addPlane(String planeNumber, String time);

    /**
     * Allocates the landing slot to the next plane in line if it is already
     * waiting, or if it arrives no later than 5 minutes from the current time.
     *
     * Removes the plane that has been granted a landing slot and returns its number.
     * Otherwise, returns null.
     *
     * The complexity must be O(1).
     *
     * @param currentTime represents the current time in 24 hour format.
     *                    Example: "9:24", "15:32"
     * @return plane number or null
     */
    public abstract String allocateLandingSlot(String currentTime);

    /**
     * Find and remove a plane by its number.
     * The complexity must be O(n).
     *
     * @param planeNumber string with 3 letters, followed by 4 numbers.
     *                    Example: "ABC1236", "ENC3455"
     * @return plane number or null
     */
    public abstract String emergencyLanding(String planeNumber);

    /**
     * Returns true if the plane is in the system, otherwise return false.
     * @param planeNumber string with 3 letters, followed by 4 numbers.
     *                    Example: "ABC1235", "ENC3454"
     * @return true if the plane is present, false otherwise
     */
    public abstract boolean isPresent(String planeNumber);

    /**
     * Returns true if there are no planes in the system, false otherwise.
     *
     * @return true iff there are no planes in the system
     */
    public boolean isEmpty() {
        return size() == 0;
    }
}
