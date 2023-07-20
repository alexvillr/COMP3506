/*
    Sorting must be performed based on time
    If two or more planes have the same time -> sort by the plane number

    Example:

        plane 1: "ABC1233", "9:24"
        plane 2: "ENC3453", "8:23"
        => plane 1 is after plane 2

        plane 1: "ABC1233", "9:24"
        plane 2: "BAA1113", "9:24"
        => plane 1 is before plane 2

        plane 1: "ABC1233", "9:24"
        plane 2: "ABC1234", "9:24"
        => plane 1 is before plane 2
 */

abstract class DisplayRandomBase {

    /** Array of planes in the departure display */
    private Plane[] data;

    /**
     * Reads the planes from the given list of CSV-formatted lines, and stores
     * them in this.data.
     *
     * @param csvLines list of lines containing planes in CSV format, no header
     */
    public DisplayRandomBase(String[] csvLines) {
        this.data = CSVReader.readPlanesFromLines(csvLines);
    }

    /**
     * Returns the array of planes in the departure display.
     *
     * @return planes
     */
    public Plane[] getData() {
        return data;
    }

    /**
     * Sets the array of planes in the departure display to the given array.
     *
     * @param data array of planes to set
     */
    public void setData(Plane[] data) {
        this.data = data;
    }

    /**
     * Sorts the planes stored in this.data.
     *
     * @return the sorted array of planes
     */
    public abstract Plane[] sort();
}

abstract class DisplayPartiallySortedBase {

    /** Array of sorted planes in the schedule */
    private Plane[] schedule;
    /** Array of new planes to be added to the schedule */
    private Plane[] extraPlanes;

    /**
     * Reads the schedule of planes and the extra planes and stores
     * them in this.schedule and this.extraPlanes.
     *
     * @param schedule list of lines containing sorted schedule of planes
     *                 in CSV format, no header
     * @param extraPlanes list of lines containing unsorted extra planes
     *                    in CSV format, no header
     */
    public DisplayPartiallySortedBase(String[] schedule,
            String[] extraPlanes) {
        this.schedule = CSVReader.readPlanesFromLines(schedule);
        this.extraPlanes = CSVReader.readPlanesFromLines(extraPlanes);
    }

    /**
     * Returns the schedule of sorted planes in the departure display.
     *
     * @return sorted planes in schedule
     */
    public Plane[] getSchedule() {
        return schedule;
    }

    /**
     * Returns the array of extra planes to be added to the schedule.
     *
     * @return extra planes
     */
    public Plane[] getExtraPlanes() {
        return extraPlanes;
    }

    /** Sets the schedule of sorted planes to the given array.
     *
     * @param schedule sorted schedule to set
     */
    public void setSchedule(Plane[] schedule) {
        this.schedule = schedule;
    }

    /**
     * Sets the array of extra planes to the given array.
     *
     * @param extraPlanes extra planes to set
     */
    public void setExtraPlanes(Plane[] extraPlanes) {
        this.extraPlanes = extraPlanes;
    }

    abstract Plane[] sort();
}

class CSVReader {
    /**
     * Utility method to read Plane objects from a given array of CSV-formatted lines.
     *
     * @param lines lines of text, each containing a plane
     * @return array of planes read from lines
     */
    static Plane[] readPlanesFromLines(String[] lines) {
        Plane[] data = new Plane[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String[] values = lines[i].split(",");
            data[i] = new Plane(values[0], values[1]);
        }
        return data;
    }
}