package q.rest.messaging.helper;

import java.util.EnumSet;

public class EnumUtil {

    public static boolean containsValue(Class<? extends Enum> enumClass, String string) {
        String[] stringValues = stringValues(enumClass);
        for(int i = 0; i < stringValues.length; i++) {
            if(stringValues[i].equals(string)) {
                return true;
            }
        }
        return false;
    }



    public static String[] stringValues(Class<? extends Enum> enumClass) {
        EnumSet enumSet = EnumSet.allOf(enumClass);
        String[] values = new String[enumSet.size()];
        int i = 0;
        for(Object enumValue : enumSet) {
            values[i] = String.valueOf(enumValue);
            i++;
        }
        return values;
    }
}
