package song.info;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by BiliaievaTatiana on 11/19/14.
 */
public class InfoSetter {
    int tagSize;

    InfoSetter(Song song) {
     /*   try {  // не нужно(если есть переменная tagSize)
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "r");
            byte[] bytes = new byte[Config.HEADER_ARRAY_SIZE];
            if (randomAccessFile.length() >= 10) {
                randomAccessFile.read(bytes);
                tagSize = (bytes[9] & 0xFF) | ((bytes[8] & 0xFF) << 7) | ((bytes[7] & 0xFF) << 14) | ((bytes[6] & 0xFF) << 21) + 10;
            }
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void setInfo(Song song, Frame frame, String value) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] bytes = new byte[(int)frame.getTagSize()];
            randomAccessFile.read(bytes);
            randomAccessFile.seek(0);

            byte[] bytesOfValue = value.getBytes();

            int dif = bytesOfValue.length - frame.getFrameSize() + 1;
            int length = frame.getPositionInFile() + Config.HEADER_ARRAY_SIZE + frame.getId3FrameSize();
            if (dif > 0) { //lenght of value bigger that frame
                System.out.println("dif > 0");
                byte[] tempArray = new byte[bytes.length + dif];
                //set bytes before frame
                for (int i = 0; i < length; i++) {
                    tempArray[i] = bytes[i];
                }
                //set bytes of frame value
                for (int i = length; i < length + bytesOfValue.length; i++) {
                    tempArray[i] = bytesOfValue[i - length];
                }
                length = length + bytesOfValue.length;
                //set bytes after frame
                for (int i = length; i < tempArray.length; i++) {
                    tempArray[i] = bytes[i - dif];
                }
                changeFrameSize(frame, value, tempArray);
                //change tag Size
                byte[] tagSizeByte = toByteArray(tempArray.length - 10);
                for (int i = 0; i < tagSizeByte.length; i++) {
                    tempArray[6 + i] = tagSizeByte[i];
                }
                //write tempArray to file
                randomAccessFile.write(tempArray);
             //   changeHeaderSize(song, tempArray.length);
            } else {
                if (dif < 0) { //lenght of frame less that frame
                    //set charcode (temp for UTF-8)
                    if (bytes[length] != 3) {
                        bytes[length] = 3;
                    }
                    length++;
                    System.out.println("dif < 0");
                    System.out.println(dif + "  " + frame.getFrameSize());
                    System.out.println(frame.getFrameValue().length());
                    //set value
                    for (int i = 0; i < bytesOfValue.length; i++) {
                        bytes[length + i] = bytesOfValue[i];
                        //             System.out.println(i + "   " + (length + i));
                    }

                    dif = dif * (-1);
                    //create emlty array with dif length
                    StringBuffer stringBuffer = new StringBuffer(dif);
                    for (int i = 0; i < dif; i++) {
                        stringBuffer.append(" ");
                    }
                    byte[] emptyArray = stringBuffer.toString().getBytes();
                    length = length + bytesOfValue.length;
                    //set first dif symbols to empty array
                    for (int i = 0; i < dif; i++) {
                        bytes[length + i] = emptyArray[i];
                        //          System.out.println(i + "   " + (length + i));
                    }
                } else { //lenght of frame and value are equals
                    System.out.println("dif = 0");
                    for (int i = 0; i < bytesOfValue.length; i++) {
                        bytes[length + i] = bytesOfValue[i];
                    }
                }

                randomAccessFile.write(bytes);
                song.readInfo();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(Song song, String value) {
        System.out.println("set newingp");
    }

    public void setInfoToNewFrame(Song song, Frame frame, String value) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] headerArrayBytes = new byte[Config.HEADER_ARRAY_SIZE];
            randomAccessFile.read(headerArrayBytes);
            int tagSize = (headerArrayBytes[9] & 0xFF) | ((headerArrayBytes[8] & 0xFF) << 7) | ((headerArrayBytes[7] & 0xFF) << 14) | ((headerArrayBytes[6] & 0xFF) << 21) + 10;
            System.out.println("Setter " + tagSize);
            byte[] anotherBytesInFile = new byte[tagSize - Config.HEADER_ARRAY_SIZE];
            randomAccessFile.read(anotherBytesInFile);
            randomAccessFile.seek(Config.HEADER_ARRAY_SIZE);

            value = value + " ";
            byte[] arrayOfValue = value.getBytes("UTF-8");
            byte[] arrayOfTypeValue = frame.getFrameName().getBytes("UTF-8");
            byte[] arrayOfSizeValue = toByteArray(value.length());

            int frameLength = arrayOfValue.length + anotherBytesInFile.length + arrayOfSizeValue.length + 3 + arrayOfTypeValue.length;
            byte[] newFileArray = new byte[frameLength];
            //frame (type-(4 bytes), size-(4 bytes), flags-(2 bytes),encoding-(1 byte) value-(n+1 bytes))
            int length = arrayOfTypeValue.length;
            //set type of frame
            for (int i = 0; i < length; i++) {
                newFileArray[i] = arrayOfTypeValue[i];
            }
            //set size of frame
            for (int i = length, j = 0; i < length + arrayOfTypeValue.length; i++, j++) {
                newFileArray[i] = arrayOfSizeValue[j];
            }
            length = length + arrayOfTypeValue.length;
            //set flags empty bytes
            newFileArray[length] = 0;
            newFileArray[length + 1] = 0;
            //set encoding (3 mean UTF-8) temp ???
            newFileArray[length + 2] = 0;
            length = length + 3;
            //set value of frame
            for (int i = length, j = 0; i < arrayOfValue.length + length; i++, j++) {
                newFileArray[i] = arrayOfValue[j];
            }

            length = length + arrayOfValue.length - 1;
            //set another file to array content
            for (int i = length, j = 0; i < length + anotherBytesInFile.length; i++, j++) {
                newFileArray[i] = anotherBytesInFile[j];
            }

            randomAccessFile.write(newFileArray);
            changeHeaderSize(song, newFileArray.length);
            song.readInfo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeHeaderSize(Song song, int length) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] tagSizeByte = toByteArray(length);
            if (randomAccessFile.length() >= 10) {
                randomAccessFile.seek(6);
                for (int i = 0; i < tagSizeByte.length; i++) {
                    randomAccessFile.write(tagSizeByte[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeFrameSize(Frame frame, String value, byte[] array) {
        byte[] valueToBytes = toByteArray(value.length() + 1);

        array[frame.getPositionInFile() + 4 + Config.HEADER_ARRAY_SIZE] = valueToBytes[0];
        array[frame.getPositionInFile() + 5 + Config.HEADER_ARRAY_SIZE] = valueToBytes[1];
        array[frame.getPositionInFile() + 6 + Config.HEADER_ARRAY_SIZE] = valueToBytes[2];
        array[frame.getPositionInFile() + 7 + Config.HEADER_ARRAY_SIZE] = valueToBytes[3];
    }

    private byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }
}
