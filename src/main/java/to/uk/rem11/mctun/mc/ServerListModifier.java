package to.uk.rem11.mctun.mc;

/**
 * 22.03.2015
 * 9:31
 */
public interface ServerListModifier {
    public void modify(String serverName, int serverPort) throws ServerListModifyException;
}
