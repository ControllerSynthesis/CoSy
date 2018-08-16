package datatypes.formalisms;

import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.language_check.Get_History;

import java.util.Collections;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class epdaH_configuration<state extends General, event extends General, stack extends General>
        implements General, Get_History<event> {

    private state epdaH_state;
    private List<event> epdaH_history;
    private List<stack> epdaH_stack;
    private int cached_hashcode = 0;

    public epdaH_configuration(state epdaH_state, List<event> epdaH_history, List<stack> epdaH_stack) {
        this.epdaH_state = epdaH_state;
        this.epdaH_history = Collections.unmodifiableList(epdaH_history);
        this.epdaH_stack = Collections.unmodifiableList(epdaH_stack);
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public state get_epdaH_state() {
        return epdaH_state;
    }

    public List<event> get_epdaH_history() {
        return epdaH_history;
    }

    public List<stack> get_epdaH_stack() {
        return epdaH_stack;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        append(result);
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        epdaH_configuration<?, ?, ?> that = (epdaH_configuration<?, ?, ?>) o;

        if (epdaH_state != null ? !epdaH_state.equals(that.epdaH_state) : that.epdaH_state != null) return false;
        if (epdaH_history != null ? !epdaH_history.equals(that.epdaH_history) : that.epdaH_history != null)
            return false;
        return epdaH_stack != null ? epdaH_stack.equals(that.epdaH_stack) : that.epdaH_stack == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = epdaH_state != null ? epdaH_state.hashCode() : 0;
            result = 31 * result + (epdaH_history != null ? epdaH_history.hashCode() : 0);
            result = 31 * result + (epdaH_stack != null ? epdaH_stack.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⦇state=");
        epdaH_state.append(stringBuffer);
        stringBuffer.append(", history=");
        new Append_helper().append(epdaH_history, stringBuffer);
        stringBuffer.append(", stack=");
        new Append_helper().append(epdaH_stack, stringBuffer);
        stringBuffer.append("⦈");
    }

    @Override
    public List<event> get_history() {
        return get_epdaH_history();
    }
}
