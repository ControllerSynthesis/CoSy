package datatypes.basic_datatypes_of_algorithm;

import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;

import java.util.Collections;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_item<nonterminal extends General, event extends General> implements General {

    private nonterminal cfg_item_lhs;
    private List<two_elements__abstract<nonterminal, event>> cfg_item_rhs1;
    private List<two_elements__abstract<nonterminal, event>> cfg_item_rhs2;
    private List<event> cfg_item_look_ahead;
    private int cached_hashcode = 0;

    public cfg_item(nonterminal cfg_item_lhs, List<two_elements__abstract<nonterminal, event>> cfg_item_rhs1, List<two_elements__abstract<nonterminal, event>> cfg_item_rhs2, List<event> cfg_item_look_ahead) {
        this.cfg_item_lhs = cfg_item_lhs;
        this.cfg_item_rhs1 = Collections.unmodifiableList(cfg_item_rhs1);
        this.cfg_item_rhs2 = Collections.unmodifiableList(cfg_item_rhs2);
        this.cfg_item_look_ahead = Collections.unmodifiableList(cfg_item_look_ahead);
    }

    public nonterminal get_cfg_item_lhs() {
        return cfg_item_lhs;
    }

    public List<two_elements__abstract<nonterminal, event>> get_cfg_item_rhs1() {
        return cfg_item_rhs1;
    }

    public List<two_elements__abstract<nonterminal, event>> get_cfg_item_rhs2() {
        return cfg_item_rhs2;
    }

    public List<event> get_cfg_item_look_ahead() {
        return cfg_item_look_ahead;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("(");
        cfg_item_lhs.append(stringBuffer);
        stringBuffer.append(", ");
        Append_helper.append(cfg_item_rhs1, stringBuffer);
        stringBuffer.append(", ");
        Append_helper.append(cfg_item_rhs2, stringBuffer);
        stringBuffer.append(", ");
        Append_helper.append(cfg_item_look_ahead, stringBuffer);
        stringBuffer.append(")");
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

        cfg_item<?, ?> cfg_item = (cfg_item<?, ?>) o;

        if (cfg_item_lhs != null ? !cfg_item_lhs.equals(cfg_item.cfg_item_lhs) : cfg_item.cfg_item_lhs != null)
            return false;
        if (cfg_item_rhs1 != null ? !cfg_item_rhs1.equals(cfg_item.cfg_item_rhs1) : cfg_item.cfg_item_rhs1 != null)
            return false;
        if (cfg_item_rhs2 != null ? !cfg_item_rhs2.equals(cfg_item.cfg_item_rhs2) : cfg_item.cfg_item_rhs2 != null)
            return false;
        return cfg_item_look_ahead != null ? cfg_item_look_ahead.equals(cfg_item.cfg_item_look_ahead) : cfg_item.cfg_item_look_ahead == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = cfg_item_lhs != null ? cfg_item_lhs.hashCode() : 0;
            result = 31 * result + (cfg_item_rhs1 != null ? cfg_item_rhs1.hashCode() : 0);
            result = 31 * result + (cfg_item_rhs2 != null ? cfg_item_rhs2.hashCode() : 0);
            result = 31 * result + (cfg_item_look_ahead != null ? cfg_item_look_ahead.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

}

