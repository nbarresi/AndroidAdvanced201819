package org.its.utilities;

public class Converters {
    public static int fromBooleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    public static boolean fromIntToBoolean(int value) {
        return (value == 0) ? false : true;
    }
}
