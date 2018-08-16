package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class tuple2<alpha extends General, beta extends General> implements General {

    private alpha value1;
    private beta value2;
    private int cached_hashcode = 0;

    public tuple2(alpha value1, beta value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public alpha sel_tuple2_1() {
        return value1;
    }

    public beta sel_tuple2_2() {
        return value2;
    }

    public alpha get_value1() {
        return value1;
    }

    public beta get_value2() {
        return value2;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("(");
        value1.append(stringBuffer);
        stringBuffer.append(", ");
        value2.append(stringBuffer);
        stringBuffer.append(")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        tuple2<?, ?> tuple2 = (tuple2<?, ?>) o;

        if (value1 != null ? !value1.equals(tuple2.value1) : tuple2.value1 != null) return false;
        return value2 != null ? value2.equals(tuple2.value2) : tuple2.value2 == null;

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
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + (value2 != null ? value2.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

}
