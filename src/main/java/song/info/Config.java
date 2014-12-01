package song.info;

/**
 * Created by BiliaievaTatiana on 11/19/14.
 */
 class Config {
    /**
     *     Size of header frame.
     */
    public static int HEADER_ARRAY_SIZE = 10;

    public static int PLACE_FOR_TAG_VERSION_BYTE= 3;
    /**
     *     Old tag version less than 3.
     */
    public static int OLD_TAG_VERSION = 3;

    /**
     *     Size of old frame.
     */
    public static int ID3_FRAME_SIZE_OLD = 6;
    /**
     *     Size of new frame.
     */
    public static int ID3_FRAME_SIZE_NEW = 10;
}
