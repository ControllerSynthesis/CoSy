package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class state_and_stack_or_state__cons_state<state extends General, stack extends General> extends state_and_stack_or_state__abstract<state, stack> {

    private state value;
    private int cached_hashcode = 0;

    public state_and_stack_or_state__cons_state(state value) {
        this.value = value;
    }

    public state get_value() {
        return value;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        value.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        state_and_stack_or_state__cons_state<?, ?> that = (state_and_stack_or_state__cons_state<?, ?>) o;

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
