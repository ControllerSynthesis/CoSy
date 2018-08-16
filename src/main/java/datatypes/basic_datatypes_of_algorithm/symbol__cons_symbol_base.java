package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class symbol__cons_symbol_base<alpha extends General> extends symbol__abstract<alpha> {

    private alpha value;
    private int cached_hashcode = 0;

    public symbol__cons_symbol_base(alpha value) {
        this.value = value;
    }

    public alpha get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪base:");
        value.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        symbol__cons_symbol_base<?> that = (symbol__cons_symbol_base<?>) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = value != null ? value.hashCode() : 0;
            cached_hashcode = result;
        }
        return cached_hashcode;
    }
}
