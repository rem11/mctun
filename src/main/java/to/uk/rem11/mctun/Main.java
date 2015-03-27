package to.uk.rem11.mctun;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import to.uk.rem11.mctun.config.Config;
import to.uk.rem11.mctun.config.ConfigException;
import to.uk.rem11.mctun.mc.DefaultServerListModifier;
import to.uk.rem11.mctun.mc.ServerListModifier;
import to.uk.rem11.mctun.mc.ServerListModifyException;
import to.uk.rem11.mctun.port.NoPortFoundException;
import to.uk.rem11.mctun.port.PortFinder;
import to.uk.rem11.mctun.port.SocketBasedPortFinder;
import to.uk.rem11.mctun.port.StartPortBasedPortFinder;

/**
 * 22.03.2015
 * 6:54
 */
public class Main {
    public static final String CONFIG_FILE = "config.json";

    public static void main(String[] args) throws ConfigException, NoPortFoundException, JSchException, ServerListModifyException {
        for (String arg: args) {
            if (arg.equals("-v")) tracingEnabled = true;
        }

        Config config = Config.read("config.json");
        trace("Config loaded");

        PortFinder portFinder = new SocketBasedPortFinder();
        int localPort = portFinder.find();
        trace("Free port found: " + localPort);

        JSch jsch=new JSch();
        Session session=jsch.getSession(
                config.getRemoteServerSSHLogin(),
                config.getRemoteServer(),
                config.getRemoteServerSSHPort());
        UserInfo userInfo = new ConsoleUserInfo(config.getRemoteServerSSHPassword());
        session.setUserInfo(userInfo);
        session.connect();
        trace("Connected to "+ config.getRemoteServerSSHLogin() +"@"
                + config.getRemoteServer() +":"
                + config.getRemoteServerSSHPort());

        int assignedPort = session.setPortForwardingL(
                localPort,
                "localhost",
                config.getRemoteServerPort());
        trace("Tunneling established with "+assignedPort+":localhost:"+config.getRemoteServerPort());

        ServerListModifier modifier = new DefaultServerListModifier(config.getMcInstallationLocation());
        modifier.modify(config.getMcServerName(), assignedPort);
        trace("Minecraft server list successfully modified");

        System.out.println("All done! You can now run Minecraft and connect to \""+ config.getMcServerName() +"\" server");
    }

    private static boolean tracingEnabled = false;

    private static void trace(String s) {
        if (tracingEnabled) System.out.println(s);
    }
}
