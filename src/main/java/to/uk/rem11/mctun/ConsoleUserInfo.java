package to.uk.rem11.mctun;

import com.jcraft.jsch.UserInfo;

/**
 * 22.03.2015
 * 9:10
 */
public class ConsoleUserInfo implements UserInfo {
    String passwdFromConfig = null;
    String passwd;

    public ConsoleUserInfo(String passwdFromConfig) {
        this.passwdFromConfig = passwdFromConfig;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        if (passwdFromConfig != null) return passwdFromConfig;
        String result = passwd;
        passwd = null;
        return result;
    }

    @Override
    public boolean promptPassword(String s) {
        if (passwdFromConfig != null) return true;
        System.out.print(s + " ");
        passwd = new String(System.console().readPassword());
        return true;
    }

    @Override
    public boolean promptPassphrase(String s) {
        return true;
    }

    @Override
    public boolean promptYesNo(String s) {
        return true;
    }

    @Override
    public void showMessage(String s) {
        System.out.println(s);
    }
}