import java.lang.reflect.Array;

class DisplayRandom extends DisplayRandomBase {

    public DisplayRandom(String[] csvLines) {
        super(csvLines);
    }

    @Override
    public Plane[] sort() {
        Plane[] sortedData = this.getData().clone();

        quickSort(sortedData, 0, sortedData.length - 1);

        setData(sortedData);
        return sortedData;
    }

    public static void quickSort(Plane[] array, int left, int right) {
        if (left < right) {
            int i = left;
            int j = right;
            Plane pivot = array[(int) Math.floor((i + j)/2)];

            do {
                while (array[i].compareTo(pivot) < 0) {
                    i++;
                }
                while (pivot.compareTo(array[j]) < 0) {
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
            quickSort(array, left, j);
            quickSort(array, i, right);
            
        }
    }
}

class DisplayPartiallySorted extends DisplayPartiallySortedBase {

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    @Override
    Plane[] sort() {
        int length = (this.getSchedule().length + this.getExtraPlanes().length);
        Plane[] newSorted = (Plane[]) Array.newInstance(Plane.class, length);

        int counter = 0;
        for (Plane plane : this.getSchedule()) {
            newSorted[counter] = plane;
            counter++;
        }
        for (Plane plane : this.getExtraPlanes()) {
            newSorted[counter] = plane;
            counter++;
        }

        for (int i = 1; i < length; ++i) {
            for (int j = i; j > 0; --j) {
                if (newSorted[j - 1].compareTo(newSorted[j]) > 0) {
                    Plane temp = newSorted[j-1];
                    newSorted[j-1] = newSorted[j];
                    newSorted[j] = temp;
                }
            }
        }
        return newSorted;
    }
}