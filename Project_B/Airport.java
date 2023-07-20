import java.util.*;

public class Airport extends AirportBase {
    private Map<Terminal, List<Shuttle>> map = new HashMap<>();
    private List<Shuttle> shuttleList = new ArrayList<>();
    private Map<String, Terminal> terminalMap = new HashMap<>();

    public Airport(int capacity) {
        super(capacity);
    }

    @Override
    public AirportBase.TerminalBase opposite(AirportBase.ShuttleBase shuttle, AirportBase.TerminalBase terminal) {
        if (shuttle.getDestination() != terminal && shuttle.getOrigin() != terminal) {
            return null;
        }
        for (int i = 0; i < map.get(terminal).size(); i++) {
            if (map.get(terminal).get(i) == shuttle) {
                if (map.get(terminal).get(i).getDestination().getId() == terminal.getId()) {
                    return map.get(terminal).get(i).getOrigin();
                } else {
                    return map.get(terminal).get(i).getDestination();
                }
            }
        }
        return null;
    }

    @Override
    public AirportBase.TerminalBase insertTerminal(AirportBase.TerminalBase terminal) {
        Terminal newTerminal = new Terminal(terminal.getId(), terminal.getWaitingTime());
        if (map.containsKey(newTerminal)) {
            return terminal;
        }
        map.put(newTerminal, new LinkedList<Shuttle>());
        terminalMap.put(terminal.getId(), newTerminal);
        return newTerminal;
    }

    @Override
    public AirportBase.ShuttleBase insertShuttle(AirportBase.TerminalBase origin, 
            AirportBase.TerminalBase destination, int time) {
        if (!map.containsKey(origin)) {
            insertTerminal(origin);
        }
        if (!map.containsKey(destination)) {
            insertTerminal(destination);
        }

        Shuttle tmp = new Shuttle(origin, destination, time);
        map.get(origin).add(tmp);
        map.get(destination).add(tmp);
        shuttleList.add(tmp);
        return tmp;
    }

    @Override
    public boolean removeTerminal(AirportBase.TerminalBase terminal) {
        if (!map.containsKey(terminal)) {
            return false;
        }
        for (AirportBase.ShuttleBase S : outgoingShuttles(terminal)) {
            removeShuttle(S);
        }
        map.remove(terminal);
        return true;
    }

    @Override
    public boolean removeShuttle(AirportBase.ShuttleBase shuttle) {
        for (Shuttle S : shuttleList) {
            if (S == shuttle) {
                S = null;
                break;
            }
        }
        for (Shuttle S : map.get(shuttle.getDestination())) {
            if (S == shuttle) {
                map.get(shuttle.getDestination()).remove(S);
                break;
            }
        }
        for (Shuttle S : map.get(shuttle.getOrigin())) {
            if (S == shuttle) {
                map.get(shuttle.getOrigin()).remove(S);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AirportBase.ShuttleBase> outgoingShuttles(AirportBase.TerminalBase terminal) {
        if (!map.containsKey(terminal)) {
            return null;
        }
        List<AirportBase.ShuttleBase> tmp = new LinkedList<>();
        for (int i = 0; i < map.get(terminal).size(); i++) {
            tmp.add((ShuttleBase) map.get(terminal).get(i));
        }
        return tmp;
    }

    @Override
    public AirportBase.Path findShortestPath(AirportBase.TerminalBase origin, AirportBase.TerminalBase destination) {
        return Dijkstras(origin, destination, false);
    }

    @Override
    public AirportBase.Path findFastestPath(AirportBase.TerminalBase origin, AirportBase.TerminalBase destination) {
        return Dijkstras(origin, destination, true);
    }

    public AirportBase.Path Dijkstras(AirportBase.TerminalBase origin, AirportBase.TerminalBase destination, boolean weighted) {
        if (!map.containsKey(origin) || !map.containsKey(destination)) {
            return null;
        }
        for (Terminal terminal : map.keySet()) {
            terminal.setVisited(false);
            terminal.distance = Integer.MAX_VALUE;
            terminal.time = terminal.getWaitingTime();
        }
        PriorityQueue<Terminal> queue = new PriorityQueue<Terminal>(map.size(), Comparator.comparing(t -> t.distance));

        queue.add(terminalMap.get(origin.getId()));
        terminalMap.get(origin.getId()).distance = 0;
        while (!queue.isEmpty()) {
            Terminal currentTerminal = queue.remove();
            if (currentTerminal.isVisited()) {
                continue;
            }
            currentTerminal.setVisited(true);
            ArrayList<Terminal> oppositeTerminals = new ArrayList<Terminal>();
            for (Shuttle shuttle : map.get(terminalMap.get(currentTerminal.getId()))) {
                Terminal adjTerminal = terminalMap.get(opposite(shuttle, terminalMap.get(currentTerminal.getId())).getId());
                adjTerminal.path.add(currentTerminal.getId());
                adjTerminal.time += currentTerminal.time + shuttle.getTime();
                if (weighted) {
                    adjTerminal.distance = adjTerminal.time;
                } else {
                    adjTerminal.distance = currentTerminal.distance + 1;
                }
                oppositeTerminals.add(adjTerminal);

                System.out.println(currentTerminal + ": " + currentTerminal.time + " :: " + adjTerminal + ": " + adjTerminal.time);
                //System.out.println(adjTerminal + " :: Time: " + adjTerminal.time + ":: Distance: " + adjTerminal.distance);
            }
            for (Terminal terminal : oppositeTerminals) {
                queue.add(terminal);
            }
        }
        List<AirportBase.TerminalBase> pathList = new ArrayList<>();
        for (String terminalId : terminalMap.get(destination.getId()).path) {
            pathList.add(terminalMap.get(terminalId));
        }
        Path path = new Path(pathList, terminalMap.get(destination.getId()).time);
        System.out.println(path);
        return path;
    }

    static class Terminal extends TerminalBase {
		private boolean visited;
        public int distance, time;
        public ArrayList<String> path;

        public Terminal(String id, int waitingTime) {
			super(id, waitingTime);
            distance = 0;
            time = waitingTime;
            path = new ArrayList<>();
		}

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean isVisited() {
            return this.visited;
        }

        /* Implement all the necessary methods of the Terminal here */
    }

    static class Shuttle extends ShuttleBase {
        private boolean visited;
        private int currentPassengerCount;

        public Shuttle(AirportBase.TerminalBase origin, AirportBase.TerminalBase destination, int time) {
            super(origin, destination, time);
            currentPassengerCount = 0;
            visited = false;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean isVisited() {
            return this.visited;
        }

        public int getCount() {
            return currentPassengerCount;
        }

        public void increaseCount() {
            currentPassengerCount++;
        }

        /* Implement all the necessary methods of the Shuttle here */
    }
}