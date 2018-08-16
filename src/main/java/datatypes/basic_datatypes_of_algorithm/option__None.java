package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class option__None<alpha extends General> extends option__abstract<alpha> {

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪None⟫");
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof option__None;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

}
