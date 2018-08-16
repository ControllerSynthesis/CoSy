package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Integer implements General {

    private int value;

    public General_Integer(int value) {
        this.value = value;
    }

    public int get_value() {
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

        General_Integer generalInteger = (General_Integer) o;

        return value == generalInteger.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
