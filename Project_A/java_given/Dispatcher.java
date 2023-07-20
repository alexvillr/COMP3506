import java.lang.reflect.Array;

public class Dispatcher extends DispatcherBase {
    PlaneQueue data = new PlaneQueue();

    @Override
    public int size() {
        return data.size;
    }

    @Override
    public void addPlane(String planeNumber, String time) {
        Plane temp = new Plane(planeNumber, time);
        data.add(temp);
    }

    @Override
    public String allocateLandingSlot(String currentTime) {
        //storing time variables
        String[] time = currentTime.split(":");
        String[] landingTime = data.peek().getTime().split(":");
        Integer[] times = new Integer[4];
        times[0] = Integer.parseInt(time[0]);
        times[1] = Integer.parseInt(time[1]);
        times[2] = Integer.parseInt(landingTime[0]);
        times[3] = Integer.parseInt(landingTime[1]);

        if (time[0].compareTo(landingTime[0]) > 0) { //if the current hour is after the landing time hour
            return data.removeMin().getPlaneNumber();
        } else if (time[0].compareTo(landingTime[0]) == 0) { //same hour for current time and landing time
            if (times[3] - times[1] <= 5 || time[1].compareTo(landingTime[1]) > 0) { //the current minute is after landing time or within 5 mins
                return data.removeMin().getPlaneNumber();
            }
        } else {
            Integer timeDifference = times[3] + (60 - times[1]);
            if (timeDifference <= 5) { //for case where the 5 minute time difference crosses an hour.
                return data.removeMin().getPlaneNumber();
            }
        }
        return null;
    }
    
    @Override
    public String emergencyLanding(String planeNumber) {
        return data.remove(planeNumber);
    }

    @Override
    public boolean isPresent(String planeNumber) {
        return data.contains(planeNumber);
    }
}

/* Add any additional helper classes here */
class PlaneQueue {
    Plane[] queue;
    int size;
    
    PlaneQueue() {
        queue = (Plane[]) Array.newInstance(Plane.class, 11);
        size = 0;
    }

    public void add(Plane plane) {
        if (size == queue.length) {
            Plane[] newQueue = (Plane[]) Array.newInstance(Plane.class, (size * 2));
            int i = 0;
            for (Plane currentPlane : queue) {
                newQueue[i] = currentPlane;
                i++;
            }
            queue = newQueue;
        }
        queue[size] = plane;
        size++;
        sort(queue, 0, size - 1);
    }

    public Plane removeMin() {
        if (size == 0) {
            return null;
        }
        Plane temp = queue[size - 1];
        queue[size - 1] = null;
        size--;
        return temp;
    }

    public Plane peek() {
        if (size == 0) {
            return null;
        }
        return queue[size - 1];
    }

    public String remove(String planeNumber) {
        if (this.contains(planeNumber)) {
            int planeIndex;
            boolean planeFound = false;
            for (int i = 0; i < size; i++) {
                if (!planeFound) {
                    if (queue[i].getPlaneNumber() == planeNumber) {
                        planeFound = true;
                        planeIndex = i;
                        queue[planeIndex] = null;
                        size--;
                        sort(queue, 0, size - 1);
                    }
                } else {
                    queue[i] = queue[i + 1];
                }
            }
            if (planeFound) {
                return planeNumber;
            }
        }
        return null;  
    }

    public boolean contains(String planeNumber) {
        if (size != 0){
            for (Plane plane : queue) {
                if (plane != null && plane.getPlaneNumber() == planeNumber) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void sort(Plane[] array, int left, int right) {
        if (left < right) {
            int i = left;
            int j = right;
            Plane pivot = array[(int) Math.floor((i + j)/2)];

            do {
                while (array[i].compareTo(pivot) > 0) {
                    i++;
                }
                while (pivot.compareTo(array[j]) > 0) {
                    j--;
                }
                

                if (i <= j) {
                    Plane temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            sort(array, left, j);
            sort(array, i, right);
            
        }
    }
}