package to.uk.rem11.mctun.port;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 22.03.2015
 * 12:38
 */
public class SocketBasedPortFinder implements PortFinder {
    @Override
    public int find() throws NoPortFoundException {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        throw new NoPortFoundException("Can't find open port");
    }
}
