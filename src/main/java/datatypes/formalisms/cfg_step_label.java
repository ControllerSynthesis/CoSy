package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;

import java.util.Collections;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_step_label<nonterminal extends General, event extends General>
        implements General {

    private nonterminal prod_lhs;
    private List<two_elements__abstract<nonterminal, event>> prod_rhs;
    private int cached_hashcode = 0;

    public cfg_step_label(nonterminal prod_lhs, List<two_elements__abstract<nonterminal, event>> prod_rhs) {
        this.prod_lhs = prod_lhs;
        this.prod_rhs = Collections.unmodifiableList(prod_rhs);
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public List<two_elements__abstract<nonterminal, event>> get_prod_rhs() {
        return prod_rhs;
    }

    public nonterminal get_prod_lhs() {
        return prod_lhs;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        prod_lhs.append(stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(prod_rhs, stringBuffer);
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

        cfg_step_label<?, ?> that = (cfg_step_label<?, ?>) o;

        if (prod_lhs != null ? !prod_lhs.equals(that.prod_lhs) : that.prod_lhs != null) return false;
        return prod_rhs != null ? prod_rhs.equals(that.prod_rhs) : that.prod_rhs == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = prod_lhs != null ? prod_lhs.hashCode() : 0;
            result = 31 * result + (prod_rhs != null ? prod_rhs.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


}
