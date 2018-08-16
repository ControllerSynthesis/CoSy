package datatypes.basic_java_datatypes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class General_Set<alpha extends General> implements General {

    private Set<alpha> value;
    private boolean modifiable = false;
    private int cached_hashcode = 0;

    public General_Set(Set<alpha> value) {
        if (value == null)
            throw new RuntimeException();
        this.value = Collections.unmodifiableSet(value);
    }

    public General_Set(Set<alpha> value, boolean modifiable) {
        if (value == null)
            throw new RuntimeException();
        this.modifiable = modifiable;
        if (modifiable) {
            this.value = value;
        } else {
            this.value = Collections.unmodifiableSet(value);
        }
    }

    public static <alpha>
    Set<alpha>
    list_by_enumeration(
            alpha... elements) {
        return Arrays.stream(elements).collect(Collectors.toSet());
    }

    public static <alpha extends General>
    General_Set<alpha>
    list_by_enumeration(
            alpha... elements) {
        return new General_Set<alpha>(Arrays.stream(elements).collect(Collectors.toSet()));
    }

    public Set<alpha> get_value() {
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

        General_Set<?> generalSet = (General_Set<?>) o;

        return value != null ? value.equals(generalSet.value) : generalSet.value == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0 || modifiable)
            cached_hashcode = value.hashCode();
        return cached_hashcode;
    }

    @Override
    public void cache_hashcode() {
        if (!modifiable)
            hashCode();
    }
}
