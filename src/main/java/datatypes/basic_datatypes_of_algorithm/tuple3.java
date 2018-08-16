package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class tuple3<alpha extends General, beta extends General, gamma extends General> implements General {

    private alpha value1;
    private beta value2;
    private gamma value3;
    private int cached_hashcode = 0;

    public tuple3(alpha value1, beta value2, gamma value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public alpha sel_tuple3_1() {
        return value1;
    }

    public beta sel_tuple3_2() {
        return value2;
    }

    public gamma sel_tuple3_3() {
        return value3;
    }

    public alpha get_value1() {
        return value1;
    }

    public beta get_value2() {
        return value2;
    }

    public gamma get_value3() {
        return value3;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("(");
        value1.append(stringBuffer);
        stringBuffer.append(", ");
        value2.append(stringBuffer);
        stringBuffer.append(", ");
        value3.append(stringBuffer);
        stringBuffer.append(")");
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        tuple3<?, ?, ?> tuple3 = (tuple3<?, ?, ?>) o;

        if (value1 != null ? !value1.equals(tuple3.value1) : tuple3.value1 != null) return false;
        if (value2 != null ? !value2.equals(tuple3.value2) : tuple3.value2 != null) return false;
        return value3 != null ? value3.equals(tuple3.value3) : tuple3.value3 == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + (value2 != null ? value2.hashCode() : 0);
            result = 31 * result + (value3 != null ? value3.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

}

