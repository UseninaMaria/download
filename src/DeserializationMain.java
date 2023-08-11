
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeserializationMain {

    public static void main(String[] args) {
        openZip("D://Games/savegames/zip.zip", "D://Games/savegames/game");
        GameProgress loadedProgress = openProgress("D://Games/savegames/game/packed_list.txt");
        System.out.println(loadedProgress);
    }

    public static void openZip(String dir_zip, String pack) {
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(dir_zip))) {
            File folder = new File(pack);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                File newFile = new File(pack, name);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fout = new FileOutputStream(newFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String game) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(game);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
