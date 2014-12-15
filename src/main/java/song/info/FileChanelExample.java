package song.info;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by BiliaievaTatiana on 12/12/14.
 */
public class FileChanelExample {
    FileChanelExample() {
    }

    public void simpleRead() {
     //   try {
         //   FileChannel fileChannel = new FileInputStream("C:/Users/biliaievaTatiana/Downloads/testFileForMe.txt").getChannel();
        //    ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
         //   fileChannel.read(byteBuffer);
     /*   } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void showHowFlipWorks() {
        try {
            FileChannel fileChannel = new FileInputStream("C:/Users/biliaievaTatiana/Downloads/testFileForMe.txt").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(byteBuffer.limit());
            System.out.println("Show first  : " + byteBuffer.get());
            byteBuffer.get();
            byteBuffer.get();
            byteBuffer.flip();
            System.out.println("Show first  : " + byteBuffer.get());
            System.out.println(byteBuffer.limit());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
