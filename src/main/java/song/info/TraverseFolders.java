package song.info;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by BiliaievaTatiana on 12/15/14.
 */
public class TraverseFolders {
    private final String fileFornat = "audio/mpeg";

    TraverseFolders(String path) {
        traversre(new Song(path));
    }

    public void traversre(Song song) {
        try {
            if (fileFornat.equals(Files.probeContentType(song.toPath()))) {
                song.readInfo();
                System.out.println(song.getSongName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (song.isDirectory()) {
            String[] subnode = song.list();

            for (int i = 0; i < subnode.length; i++) {
                traversre(new Song(song, subnode[i]));
            }
        }

    }
}
