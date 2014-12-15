package song.info;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

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

    public void setInfo(Song song, HashMap<Frame, String> framesMap) {
        List list = new LinkedList(framesMap.entrySet());

        for (int i = 0; i < list.size(); i++) {
            System.out.println(((Frame) framesMap.keySet().toArray()[i]).getPositionInFile());
        }

        Collections.sort(list, new Comparator<Map.Entry>() {
            public int compare(Map.Entry o1, Map.Entry o2) {
                return ((Frame) o2.getKey()).getPositionInFile() - ((Frame) o1.getKey()).getPositionInFile();
            }
        });

        Iterator it = list.iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(((Frame) pairs.getKey()).getPositionInFile() + " = " + pairs.getValue());
            setInfo(song, (Frame) pairs.getKey(), (String) pairs.getValue());
            it.remove();
        }
        song.readInfo();
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
