package to.uk.rem11.mctun.port;

/**
 * 22.03.2015
 * 7:23
 * IT DOES NOT WORK
 */
public class StartPortBasedPortFinder implements PortFinder {
    private int startPort;

    public StartPortBasedPortFinder(int startPort) {
        this.startPort = startPort;
    }

    @Override
    public int find() throws NoPortFoundException {
        AvailabilityChecker checker = new TCPAvailabilityChecker();
        for (int port = startPort; port <= TCPAvailabilityChecker.MAX_PORT_NUMBER; port++) {
            if (checker.isAvailable(port)) return port;
        }
        throw new NoPortFoundException("Max port number reached");
    }
}
