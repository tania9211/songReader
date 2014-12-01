package song.info;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by BiliaievaTatiana on 11/27/14.
 */
public class TestReadInfo extends TestCase{
    //test find file
    //test set new frame - read
    //test set info to file
    //test set list info

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


    @Test
    public void testFindFile() {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/test23.mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        song.setSongName("Name");
        song.setBandName("Band Name");
        song.readInfo();
        System.out.println(song.getSongName().length());
        System.out.println(song.getBandName().length());
        song.setGenre("Genre");
        song.readInfo();
        System.out.println(song.getSongName());
        System.out.println(song.getBandName());
        System.out.println(song.getGenre());
    }

    @Test
    public void testSetName() {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/testName.mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
       //assertFalse(song.getSongName().equals(name));
    }

 //   @Test
    public void testBandName() {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/testNandName.mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "BandName";
        song.setBandName(name);
        song.readInfo();
        assertEquals(name, song.getBandName());

        name = "678My bla bla bla";
        song.setBandName(name);
        song.readInfo();
        assertEquals(name, song.getBandName());
    }

  //  @Test
    public void testAlbumName() {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/testAlbum.mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "BandName";
        song.setAlbumName(name);
        song.readInfo();
        assertEquals(name, song.getAlbumName());

        name = "678My bla bla bla";
        song.setAlbumName(name);
        song.readInfo();
        assertEquals(name, song.getAlbumName());
    }

//    @Test
    public void testGenre() {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/testGenre.mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "Pop";
        song.setGenre(name);
        song.readInfo();
        assertEquals(name, song.getGenre());

        name = "Metal";
        song.setGenre(name);
        song.readInfo();
        assertEquals(name, song.getGenre());
    }

   // @Test
    public void testYear() {
        Song song = new Song("C:/Users/biliaievaTatiana/Downloads/testYear.mp3");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "1992";
        song.setYear(name);
        song.readInfo();
        assertEquals(name, song.getYear());

        name = "2014";
        song.setYear(name);
        song.readInfo();
        assertEquals(name, song.getYear());
    }
}
