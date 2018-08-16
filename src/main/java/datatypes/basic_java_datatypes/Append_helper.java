package datatypes.basic_java_datatypes;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Append_helper {

    public static <alpha extends General> void append(List<alpha> list, StringBuffer stringBuffer) {
        boolean first = true;
        stringBuffer.append("[");
        for (alpha elem : list) {
            if (first)
                first = false;
            else
                stringBuffer.append(", ");
            elem.append(stringBuffer);
        }
        stringBuffer.append("]");
    }

    public static <alpha extends General, beta extends General> void append(Map<alpha, beta> map, StringBuffer stringBuffer) {
        boolean first = true;
        stringBuffer.append("[");
        for (alpha elem : map.keySet()) {
            if (first)
                first = false;
            else
                stringBuffer.append(", ");
            elem.append(stringBuffer);
            stringBuffer.append(" ↦ ");
            map.get(elem).append(stringBuffer);
        }
        stringBuffer.append("]");
    }

    public static <alpha extends General> void append(Set<alpha> value, StringBuffer stringBuffer) {
        boolean first = true;
        stringBuffer.append("[");
        for (alpha elem : value) {
            if (first)
                first = false;
            else
                stringBuffer.append(", ");
            elem.append(stringBuffer);
        }
        stringBuffer.append("]");

    }
}
