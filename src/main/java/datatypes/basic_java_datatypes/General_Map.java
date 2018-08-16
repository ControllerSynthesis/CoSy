package datatypes.basic_java_datatypes;

import java.util.Collections;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Map<alpha extends General, beta extends General> implements General {

    private Map<alpha, beta> value;
    private boolean modifiable = false;
    private int cached_hashcode = 0;

    public General_Map(Map<alpha, beta> value) {
        if (value == null)
            throw new RuntimeException();
        this.value = Collections.unmodifiableMap(value);
    }

    public General_Map(Map<alpha, beta> value, boolean modifiable) {
        if (value == null)
            throw new RuntimeException();
        this.modifiable = modifiable;
        if (modifiable) {
            this.value = value;
        } else {
            this.value = Collections.unmodifiableMap(value);
        }
    }

    public static <K, V> V get_default(Map<K, V> map, K k, V default_value) {
        if (map.containsKey(k))
            return map.get(k);
        else
            return default_value;
    }

    public Map<alpha, beta> get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        Append_helper.append(value, stringBuffer);
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

        General_Map<?, ?> generalMap = (General_Map<?, ?>) o;

        return value != null ? value.equals(generalMap.value) : generalMap.value == null;

    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0 || modifiable)
            cached_hashcode = value.hashCode();
        return cached_hashcode;
    }

}
