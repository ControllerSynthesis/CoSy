package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class symbol__cons_symbol_nat<alpha extends General> extends symbol__abstract<alpha> {

    private int value;

    public symbol__cons_symbol_nat(int value) {
        this.value = value;
    }

    public int get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪nat:");
        stringBuffer.append(value);
        stringBuffer.append("⟫");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        symbol__cons_symbol_nat<?> that = (symbol__cons_symbol_nat<?>) o;

        return value == that.value;

    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public int hashCode() {
        return value;
    }
}
