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
    }

    public void setInfo(Song song, Frame frame, String value) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] oldTagBytes = new byte[song.getTagSize()];
            randomAccessFile.read(oldTagBytes);
            randomAccessFile.seek(0);

            byte[] frameValueArray = value.getBytes();

            int difference = frameValueArray.length - frame.getFrameValue().length();
            int length = Config.HEADER_ARRAY_SIZE + frame.getPositionInFile() + frame.getId3FrameSize();
            if (difference != 0) {
                byte[] newTagBytes = new byte[oldTagBytes.length + difference];

                for (int i = 0; i < length; i++) {
                    newTagBytes[i] = oldTagBytes[i];
                }
                newTagBytes[length] = 0;
                length++;

                for (int i = length, j = 0; i < length + frameValueArray.length; i++, j++) {
                    newTagBytes[i] = frameValueArray[j];
                }
                length = length + frameValueArray.length;

                for (int i = length; i < newTagBytes.length; i++) {
                    newTagBytes[i] = oldTagBytes[i - difference];
                }

                changeFrameSize(frame, value, newTagBytes);
                changeTagSize(newTagBytes, newTagBytes.length);

                randomAccessFile.write(newTagBytes);
                randomAccessFile.close();
            } else {
                for (int i = length + 1, j = 0; i < length + frame.getFrameValue().length() + 1; i++, j++) {
                    oldTagBytes[i] = frameValueArray[j];
                }

                randomAccessFile.write(oldTagBytes);
                randomAccessFile.close();
            }

            song.readInfo();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public void setInfo1(Song song, Frame frame, String value) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] bytes = new byte[song.getTagSize()];
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

                tempArray[length] = 0;
                length++;
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
    }*/

    public void setInfo(Song song, String value) {
        System.out.println("set newingp");
    }

    public void setNewFrameInfo(Song song, Frame frame, String value) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            byte[] oldTagBytes = new byte[song.getTagSize()];
            randomAccessFile.read(oldTagBytes);
            randomAccessFile.seek(0);

            byte[] frameTypeArray = frame.getFrameName().getBytes();
            byte[] frameSizeArray = toByteArray(value.length() + 1);
            byte[] frameValueArray = value.getBytes();
            final int frameFlagsLenght = 2;
            final int encodeValueSize = 1;

            int newTagLenght = oldTagBytes.length + frameSizeArray.length + frameTypeArray.length + frameValueArray.length + frameFlagsLenght + encodeValueSize;
            byte[] newTagBytes = new byte[newTagLenght];

            for (int i = 0; i < Config.HEADER_ARRAY_SIZE; i++) {
                newTagBytes[i] = oldTagBytes[i];
            }
            int lenght = Config.HEADER_ARRAY_SIZE;

            for (int i = lenght, j = 0; i < frameTypeArray.length + lenght; i++, j++) {
                newTagBytes[i] = frameTypeArray[j];
            }
            lenght = lenght + frameTypeArray.length;

            for (int i = lenght, j = 0; i < lenght + frameSizeArray.length; i++, j++) {
                newTagBytes[i] = frameSizeArray[j];
            }
            lenght = lenght + frameSizeArray.length;

            for (int i = lenght; i < lenght + frameFlagsLenght + 1; i++) {
                newTagBytes[i] = 0;
            }
            lenght = lenght + frameFlagsLenght + 1;

            for (int i = lenght, j = 0; i < lenght + frameValueArray.length; j++, i++) {
                newTagBytes[i] = frameValueArray[j];
            }
            lenght = lenght + frameValueArray.length;

            for (int i = lenght, j = Config.HEADER_ARRAY_SIZE; i < newTagLenght; i++, j++) {
                newTagBytes[i] = oldTagBytes[j];
            }

            changeTagSize(newTagBytes, newTagLenght);
            randomAccessFile.write(newTagBytes);
            randomAccessFile.close();

            song.readInfo();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTagSize(byte[] array, int tagSize) {
        byte[] sizeBytes = toByteArray(tagSize - 10);
        final int tagSizeBeginPosition = 6;

        for (int i = 0; i < sizeBytes.length; i++) {
            array[tagSizeBeginPosition + i] = sizeBytes[i];
        }
    }

  /*  public void setInfoToNewFrame(Song song, Frame frame, String value) { //wrong void (not use)
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(song, "rw");
            //    byte[] headerArrayBytes = new byte[Config.HEADER_ARRAY_SIZE];
            //     randomAccessFile.read(headerArrayBytes);
            //    int tagSize = (headerArrayBytes[9] & 0xFF) | ((headerArrayBytes[8] & 0xFF) << 7) | ((headerArrayBytes[7] & 0xFF) << 14) | ((headerArrayBytes[6] & 0xFF) << 21) + 10;
            //     System.out.println("Setter " + tagSize);
            //   byte[] anotherBytesInFile = new byte[tagSize - Config.HEADER_ARRAY_SIZE];
            byte[] bytesInOldFile = new byte[song.getTagSize()];
            randomAccessFile.read(bytesInOldFile);
            //      randomAccessFile.seek(Config.HEADER_ARRAY_SIZE);
            randomAccessFile.seek(0);

            value = value + " ";
            byte[] arrayOfValue = value.getBytes("UTF-8");
            byte[] arrayOfTypeValue = frame.getFrameName().getBytes("UTF-8");
            byte[] arrayOfSizeValue = toByteArray(value.length());

            int frameLength = arrayOfValue.length + bytesInOldFile.length + arrayOfSizeValue.length + 3 + arrayOfTypeValue.length;
            byte[] newFileArray = new byte[frameLength];
            //set info before frame (header info)
            for (int i = 0; i < Config.HEADER_ARRAY_SIZE; i++) {
                newFileArray[i] = bytesInOldFile[i];
            }
            //int length = arrayOfTypeValue.length;
            int length = Config.HEADER_ARRAY_SIZE;
            //frame (type-(4 bytes), size-(4 bytes), flags-(2 bytes),encoding-(1 byte) value-(n+1 bytes))
            //set type of frame
            for (int i = length, j = 0; i < length + arrayOfTypeValue.length; i++, j++) {
                newFileArray[i] = arrayOfTypeValue[j];
            }
            length = length + arrayOfTypeValue.length;
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
            for (int i = length, j = Config.HEADER_ARRAY_SIZE; i < length + bytesInOldFile.length - Config.HEADER_ARRAY_SIZE; i++, j++) {
                newFileArray[i] = bytesInOldFile[j];
            }
            //set new tag size
            byte[] tagSizeByte = toByteArray(newFileArray.length - 10);
            for (int i = 0; i < tagSizeByte.length; i++) {
                newFileArray[6 + i] = tagSizeByte[i];
            }
            //write array to file
            randomAccessFile.write(newFileArray);
            ///     changeHeaderSize(song, newFileArray.length);
            song.readInfo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

   /* private void changeHeaderSize(Song song, int length) {
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
    }*/

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
