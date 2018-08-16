package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class two_elements__teB<alpha extends General, beta extends General> extends two_elements__abstract<alpha, beta> {

    private beta value;
    private int cached_hashcode = 0;

    public two_elements__teB(beta value) {
        this.value = value;
    }

    public static <alpha extends General, beta extends General> List<beta> filter(List<two_elements__abstract<alpha, beta>> list) {
        List<beta> result = new ArrayList<>();
        for (two_elements__abstract<alpha, beta> elem : list)
            if (elem instanceof two_elements__teB)
                result.add(((two_elements__teB<alpha, beta>) elem).value);
        return result;
    }

    public static <alpha extends General, beta extends General>
    List<two_elements__abstract<alpha, beta>>
    lift(List<beta> list) {
        List<two_elements__abstract<alpha, beta>> result = new ArrayList<>();
        for (beta b : list)
            result.add(new two_elements__teB<>(b));
        return result;
    }

    public beta get_value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        two_elements__teB<?, ?> that = (two_elements__teB<?, ?>) o;

        return value != null ? value.equals(that.value) : that.value == null;

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

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪teB:");
        value.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }
}
