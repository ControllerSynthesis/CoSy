package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.*;

import java.util.HashMap;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class accessibility_graph<state extends General, stack extends General> implements General {

    private General_Map<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>> value;


    public accessibility_graph(General_Map<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>> value) {
        this.value = value;
    }

    public accessibility_graph() {
        this.value = new General_Map<>(new HashMap<>(), true);
    }

    public General_Map<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>> get_value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        accessibility_graph<?, ?> that = (accessibility_graph<?, ?>) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        value.append(stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

}

