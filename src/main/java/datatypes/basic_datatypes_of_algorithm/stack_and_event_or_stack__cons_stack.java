package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class stack_and_event_or_stack__cons_stack<stack extends General, event extends General> extends stack_and_event_or_stack__abstract<stack, event> {

    private stack value;
    private int cached_hashcode = 0;

    public stack_and_event_or_stack__cons_stack(stack value) {
        this.value = value;
    }

    public stack get_value() {
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

        stack_and_event_or_stack__cons_stack<?, ?> that = (stack_and_event_or_stack__cons_stack<?, ?>) o;

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
