package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda_step_label;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class state_or_edge__cons_state_or_edge_new<state extends General, event extends General, stack extends General> extends state_or_edge__abstract<state, event, stack> {

    private epda_step_label<state, event, stack> value;
    private int cached_hashcode = 0;

    public state_or_edge__cons_state_or_edge_new(epda_step_label<state, event, stack> value) {
        this.value = value;
    }

    public epda_step_label<state, event, stack> get_value() {
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

        state_or_edge__cons_state_or_edge_new<?, ?, ?> that = (state_or_edge__cons_state_or_edge_new<?, ?, ?>) o;

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
