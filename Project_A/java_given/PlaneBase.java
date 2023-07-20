public abstract class PlaneBase implements Comparable<PlaneBase> {

    private String planeNumber;
    private String time;

    public PlaneBase(String planeNumber, String time) {
        this.planeNumber = planeNumber;
        this.time = time;
    }

    public String getPlaneNumber() {
        return planeNumber;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "(" + planeNumber + ", " + time + ")";
    }
}
