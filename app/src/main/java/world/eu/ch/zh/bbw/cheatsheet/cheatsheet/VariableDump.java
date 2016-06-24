package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.os.Environment;

/**
 * Created by Matti on 24.06.2016.
 */
public class VariableDump
{
    // All static variables
    public static final String URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CheatSheet/cheatsheets.xml";
    // XML node keys
    public static final String KEY_NOTE = "cheatsheet"; // parent node
    public static final String KEY_TITLE = "title";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LONGITUDE = "longi";
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LOCATIONANDDATE = "info";
    public static final String KEY_PICTURE = "src";
}
