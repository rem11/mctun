package to.uk.rem11.mctun.port;

import java.io.IOException;
import java.net.*;

/**
 * 22.03.2015
 * 7:08
 * IT DOES NOT WORK
 */
public class TCPAvailabilityChecker implements AvailabilityChecker {
    public static int MIN_PORT_NUMBER = 1024;
    public static int MAX_PORT_NUMBER = 65535;

    @Override
    public boolean isAvailable(int portNumber) {
        if (portNumber < MIN_PORT_NUMBER || portNumber > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + portNumber);
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber, 0, InetAddress.getLocalHost());
            return true;
        } catch (IOException e) {
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {}
        }

        return false;
    }
}
