package to.uk.rem11.mctun.config;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;

/**
 * 22.03.2015
 * 7:31
 */
public class Config {
    private String remoteServer;
    private int remoteServerPort = 25565;
    private int remoteServerSSHPort = 22;
    private String remoteServerSSHLogin;
    private String remoteServerSSHPassword;
    private String mcInstallationLocation = "${AppData}\\.minecraft";
    private String mcServerName;

    private Config() {}

    public String getMcInstallationLocation() {
        return mcInstallationLocation;
    }

    public void setMcInstallationLocation(String mcInstallationLocation) {
        this.mcInstallationLocation = mcInstallationLocation;
    }

    public String getMcServerName() {
        return mcServerName;
    }

    public void setMcServerName(String mcServerName) {
        this.mcServerName = mcServerName;
    }

    public String getRemoteServer() {
        return remoteServer;
    }

    public void setRemoteServer(String remoteServer) {
        this.remoteServer = remoteServer;
    }

    public int getRemoteServerPort() {
        return remoteServerPort;
    }

    public void setRemoteServerPort(int remoteServerPort) {
        this.remoteServerPort = remoteServerPort;
    }

    public String getRemoteServerSSHLogin() {
        return remoteServerSSHLogin;
    }

    public void setRemoteServerSSHLogin(String remoteServerSSHLogin) {
        this.remoteServerSSHLogin = remoteServerSSHLogin;
    }

    public String getRemoteServerSSHPassword() {
        return remoteServerSSHPassword;
    }

    public void setRemoteServerSSHPassword(String remoteServerSSHPassword) {
        this.remoteServerSSHPassword = remoteServerSSHPassword;
    }

    public int getRemoteServerSSHPort() {
        return remoteServerSSHPort;
    }

    public void setRemoteServerSSHPort(int remoteServerSSHPort) {
        this.remoteServerSSHPort = remoteServerSSHPort;
    }

    public static Config read(String fileName) throws ConfigException {
        Config result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(new File(fileName), Config.class);
        } catch (Exception e) {
            throw new ConfigException("Configuration reading error. " +
                    "Please ensure that configuration file is correctly formed.", e);
        }
        if (result != null) {
            if (
                    result.remoteServer == null ||
                    result.remoteServerSSHLogin == null
            ) {
                throw new ConfigException("Required fields are not specified.");
            }
            if (result.mcServerName == null) {
                result.mcServerName = result.remoteServer;
            }
        }
        return result;
    }
}
