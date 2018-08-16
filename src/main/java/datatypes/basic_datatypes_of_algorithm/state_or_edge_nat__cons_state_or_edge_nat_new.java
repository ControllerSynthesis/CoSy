package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda_step_label;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class state_or_edge_nat__cons_state_or_edge_nat_new<state extends General, event extends General, stack extends General> extends state_or_edge_nat__abstract<state, event, stack> {

    private epda_step_label<state, event, stack> value1;
    private int value2;
    private int cached_hashcode = 0;

    public state_or_edge_nat__cons_state_or_edge_nat_new(epda_step_label<state, event, stack> value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public epda_step_label<state, event, stack> get_value1() {
        return value1;
    }

    public int get_value2() {
        return value2;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪new:");
        value1.append(stringBuffer);
        stringBuffer.append(", ");
        stringBuffer.append(value2);
        stringBuffer.append("⟫");
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

        state_or_edge_nat__cons_state_or_edge_nat_new<?, ?, ?> that = (state_or_edge_nat__cons_state_or_edge_nat_new<?, ?, ?>) o;

        if (value2 != that.value2) return false;
        return value1 != null ? value1.equals(that.value1) : that.value1 == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = value1 != null ? value1.hashCode() : 0;
            result = 31 * result + value2;
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


}
