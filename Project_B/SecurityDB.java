public class SecurityDB extends SecurityDBBase {

    private int currentSize, maxSize;
    private String[] keys;
    private Passenger[] values;
    private int[] primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,
            79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,
            181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,
            283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,
            409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,
            523,541,547,557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,
            647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,
            773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,907,
            911,919,929,937,941,947,953,967,971,977,983,991,997,1009,1013,1019,1021};


    // I apologise for the messy gross code.
    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
        currentSize = 0;

        // calculate the size of array
        int l = 0, r = primes.length - 1;
        int nextPrime = -1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (primes[m] <= (numPlanes * numPassengersPerPlane)) {
                l = m + 1;
            } else {
                nextPrime = m;
                r = m - 1;
            }
        }
        maxSize = nextPrime;

        keys = new String[maxSize];
        values = new Passenger[maxSize];
    }

    @Override
    public int calculateHashCode(String passportId) {
        // hashing function (cumulative sum)
        int code = 0;
        int currentSum = 1;
        for (int i = 0; i < passportId.length(); i++) {
            int x = (int) passportId.charAt(i);
            currentSum += x;
            code = code + currentSum;
        }
        return code;
    }

    public int compressionFunction(int code) {
        do {
            code = code % size();
        } while (code > size());
        return code;
    }

    @Override
    public int size() {
        return maxSize;
    }

    @Override
    public String get(String passportId) {
        int thisHash = compressionFunction(calculateHashCode(passportId));
        while (keys[thisHash] != null) {
            if (keys[thisHash] == (passportId)) {
                return values[thisHash].getName();
            }
            thisHash = (thisHash + 1) % size();
        }
        return null;
    }

    @Override
    public boolean remove(String passportId) {
        if (!contains(passportId)) {
            return false;
        }

        int index = compressionFunction(calculateHashCode(passportId));
        while (!(passportId == keys[index])) {
            index = (index + 1) % size();
        }
        keys[index] = null;
        values[index] = null;

        for (index = (index + 1) % size(); keys[index] != null; index = (index + 1) % size()) {
            Passenger tmp = values[index];
            keys[index] = null;
            values[index] = null;
            currentSize--;
            addPassenger(tmp.getName(), tmp.getId());
        }
        currentSize--;
        return true;
    }

    @Override
    public boolean addPassenger(String name, String passportId) {
        Passenger passenger = new Passenger(name, passportId);
        int tmp = compressionFunction(calculateHashCode(passportId));
        int i = tmp;

        do {
            if (keys[i] == null) {
                keys[i] = passportId;
                values[i] = passenger;
                currentSize++;
                if (currentSize == size()) {
                    int k = size();
                    maxSize = 1021;
                    String[] tmp1 = new String[maxSize];
                    Passenger[] tmp2 = new Passenger[maxSize];
                    for (int j = 0; j < k; j++) {
                        tmp1[j] = keys[j];
                        tmp2[j] = values[j];
                    }
                    keys = tmp1;
                    values = tmp2;
                    int index = 0;

                    for (index = (index + 1) % size(); keys[index] != null; index = (index + 1) % size()) {
                        Passenger tmp3 = values[index];
                        keys[index] = null;
                        values[index] = null;
                        currentSize--;
                        addPassenger(tmp3.getName(), tmp3.getId());
                    }
                }
                return true;
            }

            if (keys[i].equals(passportId)) {
                if (values[i].getName() == name) {
                    values[i].incrementCounter();
                    if (values[i].isSuspicious()) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    System.err.println("Suspicious behaviour");
                }
            }
            i = (i + 1) % size();
        } while (i != tmp);
        return false;
    }

    @Override
    public int count() {
        return currentSize;
    }

    @Override
    public int getIndex(String passportId) {
        int thisHash = compressionFunction(calculateHashCode(passportId));
        while (keys[thisHash] != null) {
            if (keys[thisHash].equals(passportId)) {
                return thisHash;
            }
            thisHash = (thisHash + 1) % size();
        }
        // passenger does not exist
        return -1;
    }
}

/* Add any additional helper classes here */
class Passenger {
    private String name, passportId;
    private boolean suspicious;
    private int suspiciousCounter;
    public Passenger(String name, String passportId) {
        this.name = name;
        this.passportId = passportId;
        suspiciousCounter = 0;
        suspicious = false;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return passportId;
    }

    public void incrementCounter() {
        suspiciousCounter++;
        if (suspiciousCounter > 5) {
            suspicious = true;
        }
    }

    public boolean isSuspicious() {
        return suspicious;
    }
}
