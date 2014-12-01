package song.info;

/**
 * Created by BiliaievaTatiana on 11/19/14.
 */
class Frame {
    private int id3FrameSize;
    private String frameName;
    private String frameValue;
    private int frameSize;
    private int positionInFile;
    private long tagSize;

    public Frame(String frameName) {
        this.frameName = frameName;
    }

    public long getTagSize() {
        return tagSize;
    }

    public void setTagSize(long tagSize){
        this.tagSize = tagSize;
    }

    public String getFrameName() {
        return frameName;
    }

    public String getFrameValue() {
        return frameValue;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public int getPositionInFile() {
        return positionInFile;
    }

    public int getId3FrameSize() {
        return id3FrameSize;
    }

    public void setFrameValue(String frameValue) {
        this.frameValue = frameValue;
    }

    public void setId3FrameSize(int id3FrameSize1) {
        this.id3FrameSize = id3FrameSize1;
    }

    public void setPositionInFile(int positionInFile) {
        this.positionInFile = positionInFile;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }


    public static class FrameBuilder {
        private Frame frame;

        FrameBuilder(String frameName) {
            frame = new Frame(frameName);
        }

        public void setFrameValue(String frameValue) {
            frame.frameValue = frameValue;
        }

        public void setId3FrameSize(int id3FrameSize) {
            frame.id3FrameSize = id3FrameSize;
        }

        public void setPositionInFile(int positionInFile) {
            frame.positionInFile = positionInFile;
        }

        public void setFrameSize(int frameSize) {
            frame.frameSize = frameSize;
        }

        public Frame build() {
            return frame;
        }
    }
}
