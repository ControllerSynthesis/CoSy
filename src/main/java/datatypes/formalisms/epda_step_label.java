package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;

import java.util.Collections;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class epda_step_label<state extends General, event extends General, stack extends General> implements General {

    private state edge_src;
    private option__abstract<event> edge_event;
    private List<stack> edge_pop;
    private List<stack> edge_push;
    private state edge_trg;
    private int cached_hashcode = 0;

    public epda_step_label(state edge_src, option__abstract<event> edge_event, List<stack> edge_pop, List<stack> edge_push, state edge_trg) {
        this.edge_src = edge_src;
        this.edge_event = edge_event;
        this.edge_pop = Collections.unmodifiableList(edge_pop);
        this.edge_push = Collections.unmodifiableList(edge_push);
        this.edge_trg = edge_trg;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public state get_edge_src() {
        return edge_src;
    }

    public option__abstract<event> get_edge_event() {
        return edge_event;
    }

    public List<stack> get_edge_pop() {
        return edge_pop;
    }

    public List<stack> get_edge_push() {
        return edge_push;
    }

    public state get_edge_trg() {
        return edge_trg;
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

        epda_step_label<?, ?, ?> that = (epda_step_label<?, ?, ?>) o;

        if (edge_src != null ? !edge_src.equals(that.edge_src) : that.edge_src != null) return false;
        if (edge_event != null ? !edge_event.equals(that.edge_event) : that.edge_event != null) return false;
        if (edge_pop != null ? !edge_pop.equals(that.edge_pop) : that.edge_pop != null) return false;
        if (edge_push != null ? !edge_push.equals(that.edge_push) : that.edge_push != null) return false;
        return edge_trg != null ? edge_trg.equals(that.edge_trg) : that.edge_trg == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = edge_src != null ? edge_src.hashCode() : 0;
            result = 31 * result + (edge_event != null ? edge_event.hashCode() : 0);
            result = 31 * result + (edge_pop != null ? edge_pop.hashCode() : 0);
            result = 31 * result + (edge_push != null ? edge_push.hashCode() : 0);
            result = 31 * result + (edge_trg != null ? edge_trg.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        edge_src.append(stringBuffer);
        stringBuffer.append(", ");
        edge_event.append(stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(edge_pop, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(edge_push, stringBuffer);
        stringBuffer.append(", ");
        edge_trg.append(stringBuffer);
        stringBuffer.append("⟫");
    }
}
