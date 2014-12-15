package song.info;

/**
 * Created by BiliaievaTatiana on 11/19/14.
 */
public class TestMain {
    public static void main(String[] args) {
   /*     Song song = new Song("C:/Users/biliaievaTatiana/Downloads/iowa_-_marshrutka_(zaycev.net).mp3");
        System.out.println(song.getBandName());
        System.out.println(song.getSongName());
        System.out.println(song.getYear());
        System.out.println(song.getGenre());
        System.out.println(song.getAlbumName());
        song.setAlbumName("Album");
        song.setBandName("Band name");
        song.setSongName("My new song");
        System.out.println();
        System.out.println();
        System.out.println(song.getBandName());
        System.out.println(song.getSongName());
        System.out.println(song.getYear());
        System.out.println(song.getGenre());
        System.out.println(song.getAlbumName());*/

      /*  try {
            PushbackInputStream input = new PushbackInputStream(
                    new FileInputStream("C:/Users/biliaievaTatiana/Downloads/testFileForMe.txt"));

            int data = input.read();

            System.out.println(data);

         //   input.unread(data);
            data = input.read();

            System.out.println(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        TraverseFolders traverseFolders = new TraverseFolders("C:\\Users\\biliaievaTatiana\\Downloads\\testRead");
    }
}
