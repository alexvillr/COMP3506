public class Plane extends PlaneBase {

    public Plane(String planeNumber, String time) {
        super(planeNumber, time);
    }

    @Override
    public int compareTo(PlaneBase otherPlane) {
        String[] thisPlaneTime = this.getTime().split(":");
        String[] otherPlaneTime = otherPlane.getTime().split(":");
        if (thisPlaneTime[0].compareTo(otherPlaneTime[0]) > 0) {
            return 1;
        } else if (thisPlaneTime[0].compareTo(otherPlaneTime[0]) == 0) {
            if (thisPlaneTime[1].compareTo(otherPlaneTime[1]) > 0) {
                return 1;
            } else if (thisPlaneTime[1].compareTo(otherPlaneTime[1]) == 0) {
                if (this.getPlaneNumber().compareTo(otherPlane.getPlaneNumber())
                        > 0) {
                    return 1;
                } else if (this.getPlaneNumber().compareTo
                        (otherPlane.getPlaneNumber()) == 0) {
                    return 0;
                }
            }
        }
        return -1;
    }
}
