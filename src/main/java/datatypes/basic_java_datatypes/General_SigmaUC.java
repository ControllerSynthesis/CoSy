package datatypes.basic_java_datatypes;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_SigmaUC<alpha extends General> implements General {

    private General_List<alpha> value;
    private int cached_hashcode = 0;

    public General_SigmaUC(General_List<alpha> value) {
        if (value == null)
            throw new RuntimeException();
        this.value = value;
    }

    public General_List<alpha> get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        value.append(stringBuffer);
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

        General_SigmaUC<?> sigmaUC = (General_SigmaUC<?>) o;

        return value != null ? value.equals(sigmaUC.value) : sigmaUC.value == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0)
            cached_hashcode = value != null ? value.hashCode() : 0;
        return cached_hashcode;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

}
