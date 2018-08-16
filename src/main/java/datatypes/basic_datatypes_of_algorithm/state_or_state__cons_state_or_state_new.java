package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class state_or_state__cons_state_or_state_new<state extends General> extends state_or_state__abstract<state> {

    private state value;
    private int cached_hashcode = 0;

    public state_or_state__cons_state_or_state_new(state value) {
        this.value = value;
    }

    public state get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪new:");
        value.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        state_or_state__cons_state_or_state_new<?> that = (state_or_state__cons_state_or_state_new<?>) o;

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
