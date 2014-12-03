package song.info;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BiliaievaTatiana on 11/19/14.
 */
class Song extends File {

    private Map<String, Frame> frameList;
    private InfoGetter infoGetter;
    private InfoSetter infoSetter;
    private int tagSize;

 /*  private String bandName;
    private String albumName;
    private String songName;
    private String yearName;
    private String genre;*/

    public Song(String pathname) {
        super(pathname);
        infoGetter = new InfoGetter();
        infoSetter = new InfoSetter(this);
        frameList = setFrameList();
        //     infoGetter.readInfo(this);
    }

    public void setTagSize(int tagSize){
        this.tagSize = tagSize;
    }

    public int getTagSize() {
        return tagSize;
    }

    private Map<String, Frame> setFrameList() {
        Map<String, Frame> stringFrameMap = new HashMap<String, Frame>();

        stringFrameMap.put(FrameTypes.GENRE, new Frame(FrameTypes.GENRE));
        stringFrameMap.put(FrameTypes.SONG, new Frame(FrameTypes.SONG));
        stringFrameMap.put(FrameTypes.BAND, new Frame(FrameTypes.BAND));
        stringFrameMap.put(FrameTypes.YEAR, new Frame(FrameTypes.YEAR));
        stringFrameMap.put(FrameTypes.ALBUM, new Frame(FrameTypes.ALBUM));

        return stringFrameMap;
    }

    Map<String, Frame> getFrameList() {
        return frameList;
    }

    void readInfo() {
        infoGetter.readInfo(this);
    }

    public void setBandName(String value) {
        if (frameList.get(FrameTypes.BAND).getFrameValue() != null) {
            infoSetter.setInfo(this, frameList.get(FrameTypes.BAND), value);
        } else {
            infoSetter.setNewFrameInfo(this, frameList.get(FrameTypes.BAND), value);
        }
    }

    public void setAlbumName(String value) {
        if (frameList.get(FrameTypes.ALBUM).getFrameValue() != null) {
            infoSetter.setInfo(this, frameList.get(FrameTypes.ALBUM), value);
        } else {
            infoSetter.setNewFrameInfo(this, frameList.get(FrameTypes.ALBUM), value);
        }
    }

    public void setSongName(String value) {
        if (frameList.get(FrameTypes.SONG).getFrameValue() != null) {
            infoSetter.setInfo(this, frameList.get(FrameTypes.SONG), value);
        } else {
            infoSetter.setNewFrameInfo(this, frameList.get(FrameTypes.SONG), value);
        }
    }

    public void setYear(String value) {
        if (frameList.get(FrameTypes.YEAR).getFrameValue() != null) {
            infoSetter.setInfo(this, frameList.get(FrameTypes.YEAR), value);
        } else {
            infoSetter.setNewFrameInfo(this, frameList.get(FrameTypes.YEAR), value);
        }
    }

    public void setGenre(String value) {
        if (frameList.get(FrameTypes.GENRE).getFrameValue() != null) {
            infoSetter.setInfo(this, frameList.get(FrameTypes.GENRE), value);
        } else {
            infoSetter.setNewFrameInfo(this, frameList.get(FrameTypes.GENRE), value);
        }
    }

    public String getBandName() {
        return frameList.get(FrameTypes.BAND) != null && frameList.get(FrameTypes.BAND).getFrameValue() != null
                ? frameList.get(FrameTypes.BAND).getFrameValue() : "";
    }

    public String getSongName() {
        return frameList.get(FrameTypes.SONG) != null && frameList.get(FrameTypes.SONG).getFrameValue() != null
                ? frameList.get(FrameTypes.SONG).getFrameValue() : "";
    }

    public String getAlbumName() {
        return frameList.get(FrameTypes.ALBUM) != null && frameList.get(FrameTypes.ALBUM).getFrameValue() != null
                ? frameList.get(FrameTypes.ALBUM).getFrameValue() : "";
    }

    public String getYear() {
        return frameList.get(FrameTypes.YEAR) != null && frameList.get(FrameTypes.YEAR).getFrameValue() != null
                ? frameList.get(FrameTypes.YEAR).getFrameValue() : "";
    }

    public String getGenre() {
        return frameList.get(FrameTypes.GENRE) != null && frameList.get(FrameTypes.GENRE).getFrameValue() != null
                ? frameList.get(FrameTypes.GENRE).getFrameValue() : "";
    }
}
