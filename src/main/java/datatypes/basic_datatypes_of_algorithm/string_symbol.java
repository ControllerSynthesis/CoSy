package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class string_symbol implements General {

    private String value;

    public string_symbol(String value) {
        this.value = value;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public String get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        string_symbol that = (string_symbol) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
