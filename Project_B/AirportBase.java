import java.util.List;

/**
 * An airport is a collection of terminals and shuttles between terminals.
 */
public abstract class AirportBase {

    /* The capacity of each shuttle (number of passengers) */
    private final int capacity;

    /**
     * Creates a new AirportBase instance with the given capacity.
     *
     * @param capacity capacity of the airport shuttles
     *                 (same for all shuttles)
     */
    public AirportBase(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the capacity of all shuttles in the airport.
     *
     * @return capacity of all shuttles
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Represents a terminal in the airport.
     */
    abstract static class TerminalBase {

        /* Terminal ID */
        private final String id;
        /* Waiting time in minutes */
        private final int waitingTime;

        /**
         * Creates a new TerminalBase instance with the given terminal ID
         * and waiting time.
         *
         * @param id terminal ID
         * @param waitingTime waiting time for the terminal, in minutes
         */
        public TerminalBase(String id, int waitingTime) {
            this.id = id;
            this.waitingTime = waitingTime;
        }

        /**
         * Returns the terminal's ID.
         *
         * @return terminal ID
         */
        public String getId() {
            return id;
        }

        /**
         * Returns the terminal's waiting time, in minutes.
         *
         * @return terminal waiting time
         */
        public int getWaitingTime() {
            return waitingTime;
        }

        @Override
        public String toString() {
            return "Terminal{id=" + id + ", wait=" + waitingTime + "}";
        }
    }

    /**
     * Represents an airport shuttle that travels between two terminals.
     */
    abstract static class ShuttleBase {

        /* Origin terminal */
        private final TerminalBase origin;
        /* Destination terminal */
        private final TerminalBase destination;
        /* Time required to travel between terminals, in minutes */
        private final int time;

        /**
         * Creates a new ShuttleBase instance, travelling from origin to
         * destination and requiring 'time' minutes to travel.
         *
         * @param origin origin terminal
         * @param destination destination terminal
         * @param time time required to travel, in minutes
         */
        public ShuttleBase(TerminalBase origin, TerminalBase destination,
                int time) {
            this.origin = origin;
            this.destination = destination;
            this.time = time;
        }

        /**
         * Returns the origin terminal of this shuttle.
         *
         * @return origin terminal
         */
        public TerminalBase getOrigin() {
            return origin;
        }

        /**
         * Returns the destination terminal of this shuttle.
         *
         * @return destination terminal
         */
        public TerminalBase getDestination() {
            return destination;
        }

        /**
         * Returns the time required to travel on this shuttle.
         *
         * @return time required, in minutes
         */
        public int getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "Shuttle{origin=" + origin + ", dest=" + destination
                    + ", time=" + time + "}";
        }
    }

    /**
     * Given a terminal and a shuttle, returns the other terminal that the
     * shuttle travels between.
     *
     * @param shuttle shuttle to look for opposite terminal on
     * @param terminal terminal to find opposite of
     * @return opposite terminal or null if the shuttle is not incident to
     * the given terminal
     */
    public abstract TerminalBase opposite(ShuttleBase shuttle,
            TerminalBase terminal);

    /**
     * Adds the given terminal to the airport, and returns the added terminal.
     *
     * @param terminal terminal to add
     * @return terminal that was added
     */
    public abstract TerminalBase insertTerminal(TerminalBase terminal);

    /**
     * Creates and returns a new shuttle connecting origin to destination.
     * All shuttles are bidirectional.
     *
     * @param origin origin terminal of shuttle
     * @param destination destination terminal of shuttle
     * @param time time it takes to go from origin to destination, in minutes
     * @return newly created shuttle
     */
    public abstract ShuttleBase insertShuttle(TerminalBase origin,
            TerminalBase destination, int time);

    /**
     * Removes the given terminal and all of its incident shuttles from
     * the airport. All shuttles going to/from the given terminal should
     * be removed.
     *
     * @param terminal terminal to remove
     * @return true if removed successfully, false otherwise (if the terminal
     * was not in the airport)
     */
    public abstract boolean removeTerminal(TerminalBase terminal);

    /**
     * Removes the given shuttle from the airport.
     *
     * @param shuttle shuttle to remove
     * @return true if removed successfully, false otherwise (if the shuttle
     * was not in the airport)
     */
    public abstract boolean removeShuttle(ShuttleBase shuttle);

    /**
     * Returns a list of all shuttles incident to the given terminal.
     *
     * @param terminal terminal to find incident shuttles of
     * @return list of incident shuttles
     */
    public abstract List<ShuttleBase> outgoingShuttles(TerminalBase terminal);

    /*
     * Represents a path from a terminal to another terminal, travelling via
     * one or more shuttles. The start and end terminals should be included in
     * the path's list of terminals.
     */
    static class Path {
        List<TerminalBase> terminals; // list of terminals, in order travelled
        int time; // total time spent travelling along this path

        Path(List<TerminalBase> terminals, int time) {
            this.terminals = terminals;
            this.time = time;
        }

        @Override
        public String toString() {
            return "path=" + terminals.toString() + ", time=" + time;
        }
    }

    /**
     * Returns the shortest path between the given origin and destination
     * terminals. The shortest path is the path that requires the least number
     * of shuttles.
     *
     * The returned Path consists of a list of terminals in the path, and the
     * total time spent travelling along the path. The first element of the
     * Path's terminal list should be the given origin terminal, and the last
     * element should be the given destination terminal. Any intermediate
     * terminals in the path should appear in the list in the order travelled.
     *
     * @param origin the starting terminal
     * @param destination the destination terminal
     * @return Path instance containing the list of terminals and the total
     * time taken in the path, or null if destination is not reachable from
     * origin
     */
    public abstract Path findShortestPath(TerminalBase origin,
            TerminalBase destination);

    /**
     * Returns the fastest path between the given origin and destination
     * terminals. The fastest path has the lowest total time spent travelling
     * and waiting.
     *
     * The returned Path consists of a list of terminals in the path, and the
     * total time spent travelling along the path. The first element of the
     * Path's terminal list should be the given origin terminal, and the last
     * element should be the given destination terminal. Any intermediate
     * terminals in the path should appear in the list in the order travelled.
     *
     * @param origin the starting terminal
     * @param destination the destination terminal
     * @return Path instance containing the list of terminals and the total
     * time taken in the path, or null if destination is not reachable from
     * origin
     */
    public abstract Path findFastestPath(TerminalBase origin,
            TerminalBase destination);
}
