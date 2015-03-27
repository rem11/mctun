package to.uk.rem11.mctun.mc;

import com.evilco.mc.nbt.stream.NbtInputStream;
import com.evilco.mc.nbt.stream.NbtOutputStream;
import com.evilco.mc.nbt.tag.ITag;
import com.evilco.mc.nbt.tag.TagCompound;
import com.evilco.mc.nbt.tag.TagList;
import com.evilco.mc.nbt.tag.TagString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 22.03.2015
 * 9:34
 */
public class DefaultServerListModifier implements ServerListModifier {
    String mcInstallationDirectory;

    public DefaultServerListModifier(String mcInstallationDirectory) {
        this.mcInstallationDirectory = mcInstallationDirectory;
    }

    @Override
    public void modify(String serverName, int serverPort) throws ServerListModifyException {
        File mcDir = new File(new EnvironmentVariableReplacer().replace(mcInstallationDirectory));
        File serversFile = null;
        for (File file: mcDir.listFiles()) {
            if (file.getName().equals("servers.dat")) {
                serversFile = file;
            }
        }
        if (serversFile == null) {
            throw new ServerListModifyException("No servers.dat file found in "+mcInstallationDirectory);
        }

        try {
            NbtInputStream nbtInputStream = new NbtInputStream(new FileInputStream(serversFile));
            TagCompound root = (TagCompound) nbtInputStream.readTag();
            TagList servers = (TagList) root.getTag("servers");
            boolean serverFound = false;
            for (ITag tag : servers.getTags()) {
                TagCompound compound = (TagCompound) tag;
                if (((TagString) compound.getTag("name")).getValue().equals(serverName)) {
                    compound.removeTag("ip");
                    compound.setTag(new TagString("ip", "127.0.0.1:" + serverPort));
                    serverFound = true;
                }
            }
            if (!serverFound) {
                TagCompound compound = new TagCompound("A saved server");
                compound.setTag(new TagString("name", serverName));
                compound.setTag(new TagString("ip", "127.0.0.1:" + serverPort));
                servers.addTag(compound);
            }
            nbtInputStream.close();
            NbtOutputStream nbtOutputStream = new NbtOutputStream(new FileOutputStream(serversFile));
            nbtOutputStream.write(root);
            nbtOutputStream.close();
        } catch (Exception e) {
            throw new ServerListModifyException("Server list modification failed",  e);
        }
    }
}
