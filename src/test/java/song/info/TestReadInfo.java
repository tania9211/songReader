package song.info;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by BiliaievaTatiana on 11/27/14.
 */
public class TestReadInfo extends TestCase {

    private byte[] setHeaderArray() {
        byte[] headerArray = new byte[Config.HEADER_ARRAY_SIZE];
        byte[] id3Bytes = "ID3".getBytes();
        //set header name
        for (int i = 0; i < id3Bytes.length; i++) {
            headerArray[i] = id3Bytes[i];
        }
        //set version
        headerArray[3] = 4;

        //set flags
        headerArray[4] = 0;
        headerArray[5] = 0;

        //set tag size
        byte[] tagSizeArray = toByteArray(10);
        for (int i = 0; i < tagSizeArray.length; i++) {
            headerArray[i + 6] = tagSizeArray[i];
        }

        return headerArray;
    }

    private byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 21),
                (byte) (value >> 14),
                (byte) (value >> 7),
                (byte) value};
    }

    private Song initSong(String songName) {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/" + songName + ".mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
            song.setTagSize(headerArray.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return song;
    }

    @Test
    public void testSetName() {
        Song song = initSong("testSongName");
        String name = "SongName";
        song.setSongName(name);
        song.readInfo();
        assertEquals(name, song.getSongName());

        name = "6787545454545";
        song.setSongName(name);
        song.readInfo();
        assertEquals(name, song.getSongName());

        song.setSongName("Ku ku tyuyuyu");
        song.readInfo();
        assertFalse(song.getSongName().equals(name));
    }

    @Test
    public void testBandName() {
        Song song = initSong("testBandName");

        String name = "BandName";
        song.setBandName(name);
        song.readInfo();
        assertEquals(name, song.getBandName());

        name = "678My bla bla bla";
        song.setBandName(name);
        song.readInfo();
        assertEquals(name, song.getBandName());
    }

    @Test
    public void testAlbumName() {
        Song song = initSong("testAlbum");

        String name = "BandName";
        song.setAlbumName(name);
        song.readInfo();
        assertEquals(name, song.getAlbumName());

        name = "678My bla bla bla";
        song.setAlbumName(name);
        song.readInfo();
        assertEquals(name, song.getAlbumName());
    }

    @Test
    public void testGenre() {
        Song song = initSong("testGenre");

        String name = "Pop";
        song.setGenre(name);
        song.readInfo();
        assertEquals(name, song.getGenre());

        name = "Metal";
        song.setGenre(name);
        song.readInfo();
        assertEquals(name, song.getGenre());
    }

    @Test
    public void testYear() { // wrong data
        Song song = initSong("testYear");

        String name = "1992";
        song.setYear(name);
        song.readInfo();
        assertEquals(name, song.getYear());

        name = "2014";
        song.setYear(name);
        song.readInfo();
        assertEquals(name, song.getYear());

        name = "20142";
        song.setYear(name);
        song.readInfo();
        assertEquals(name, song.getYear());

        name = "2014";
        song.setYear(name);
        song.readInfo();
        assertEquals(name, song.getYear());
    }

    @Test
    public void testFewChanges() {
        Song song = initSong("fewChanges");

        String songName = "Ya ya ya";
        String year = "2014";
        String band = "Randy Marsh";
        String genre = "popsa";
        String album = "ya ya ya album";

        song.setSongName(songName);
        song.setGenre(genre);
        song.setAlbumName(album);
        song.setYear(year);
        song.setBandName(band);

        assertEquals(song.getSongName(), songName);
        assertEquals(song.getGenre(), genre);
        assertEquals(song.getAlbumName(), album);
        assertEquals(song.getYear(), year);
        assertEquals(song.getBandName(), band);

        songName = "Yaya";
        year = "2015";
        band = "Randy Marsh Gay";
        genre = "pop";
        album = "ya ya ya #";

        song.setSongName(songName);
        song.setGenre(genre);
        song.setAlbumName(album);
        song.setYear(year);
        song.setBandName(band);

        assertEquals(song.getSongName(), songName);
        assertEquals(song.getGenre(), genre);
        assertEquals(song.getAlbumName(), album);
        assertEquals(song.getYear(), year);
        assertEquals(song.getBandName(), band);
    }
}
