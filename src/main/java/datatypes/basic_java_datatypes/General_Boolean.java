package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Boolean implements General {

    private boolean value;

    public General_Boolean(boolean value) {
        this.value = value;
    }

    public boolean get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append(value);
    }

    @Override
    public void cache_hashcode() {
        // noop
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        General_Boolean myInteger = (General_Boolean) o;

        return value == myInteger.value;

    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}
