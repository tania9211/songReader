package song.info;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by BiliaievaTatiana on 11/19/14.
 */
public class InfoGetter {
    private byte headerArray[];
    public void readInfo(Song song){
        try {
            FileInputStream fileInputStream = new FileInputStream(song);
            headerArray = new byte[Config.HEADER_ARRAY_SIZE];
            fileInputStream.read(headerArray);
            long tagSize = this.getTagSize();
            byte[] buffer = new byte[(int)tagSize];
            System.out.println(this.getTagSize());
         //    song.setTagSize(this.getTagSize());  // temp
            int lenght = buffer.length;
            int pos = 0;
            fileInputStream.read(buffer);
            fileInputStream.close();
            int id3FrameSize = headerArray[3] < Config.OLD_TAG_VERSION ? Config.ID3_FRAME_SIZE_OLD : Config.ID3_FRAME_SIZE_NEW; // if tag version > 3 (temp)
            while (true) {
                int remBytes = lenght - pos;
                if (remBytes < id3FrameSize) {
                    break;
                }
                if (buffer[pos] < 'A' || buffer[pos] > 'Z') {
                    break;
                }

                String frameName;
                int frameSize;
                if (headerArray[3] < Config.OLD_TAG_VERSION) {
                    frameName = new String(buffer, pos, 3);
                    frameSize = ((buffer[pos + 5] & 0xFF) << 8) | ((buffer[pos + 4] & 0xFF) << 16) | ((buffer[pos + 3] & 0xFF) << 24);

                } else {
                    frameName = new String(buffer, pos, 4);
                    frameSize = (buffer[pos + 7] & 0xFF) | ((buffer[pos + 6] & 0xFF) << 8) | ((buffer[pos + 5] & 0xFF) << 16) | ((buffer[pos + 4] & 0xFF) << 24);
                }

            //    System.out.println(frameName + "    " + pos + "   " + frameSize);
                if (frameName.equals(FrameTypes.BAND)) {
                    setFrameInfo(song.getFrameList().get(FrameTypes.BAND), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos, tagSize);
                }
                if (frameName.equals(FrameTypes.SONG)) {
                    setFrameInfo(song.getFrameList().get(FrameTypes.SONG), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos, tagSize);
                }
                if (frameName.equals(FrameTypes.ALBUM)) {
                    setFrameInfo(song.getFrameList().get(FrameTypes.ALBUM), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos, tagSize);
                }
                if (frameName.equals(FrameTypes.YEAR)) {
                    setFrameInfo(song.getFrameList().get(FrameTypes.YEAR), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos, tagSize);
                }
                if (frameName.equals(FrameTypes.GENRE)) {
                    setFrameInfo(song.getFrameList().get(FrameTypes.GENRE), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos, tagSize);
                }

                if (pos + frameSize > lenght) {
                    break;
                }
                pos += frameSize + id3FrameSize;
                continue;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getTagSize() { // size of all tag
        return (headerArray[9] & 0xFF) | ((headerArray[8] & 0xFF) << 7) | ((headerArray[7] & 0xFF) << 14) | ((headerArray[6] & 0xFF) << 21) + 10;
    }

    private String parseTextField(final byte[] buffer, int pos, int size) {
        if (size < 2) return null;
        Charset charset;
        int charcode = buffer[pos];
        if (charcode == 0) charset = Charset.forName("ISO-8859-1");
        else if (charcode == 3) charset = Charset.forName("UTF-8");
        else charset = Charset.forName("UTF-16");
        System.out.println("Chatcode  " + charcode);
        return charset.decode(ByteBuffer.wrap(buffer, pos + 1, size - 1)).toString();
    }

    private void setFrameInfo(Frame frame, String value, int frameSize, int id3FrameSize, int pos, long tagSize) {
        frame.setFrameValue(value);
        frame.setFrameSize(frameSize);
        frame.setId3FrameSize(id3FrameSize);
        frame.setPositionInFile(pos);
        frame.setTagSize(tagSize);
    }
}
