package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class stack_and_event_or_stack__cons_stack_and_event<stack extends General, event extends General> extends stack_and_event_or_stack__abstract<stack, event> {

    private stack value1;
    private event value2;
    private int cached_hashcode = 0;

    public stack_and_event_or_stack__cons_stack_and_event(stack value1, event value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public stack get_value1() {
        return value1;
    }

    public event get_value2() {
        return value2;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        value1.append(stringBuffer);
        stringBuffer.append(", ");
        value2.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        stack_and_event_or_stack__cons_stack_and_event<?, ?> that = (stack_and_event_or_stack__cons_stack_and_event<?, ?>) o;

        if (value1 != null ? !value1.equals(that.value1) : that.value1 != null) return false;
        return value2 != null ? value2.equals(that.value2) : that.value2 == null;

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
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + (value2 != null ? value2.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


}
