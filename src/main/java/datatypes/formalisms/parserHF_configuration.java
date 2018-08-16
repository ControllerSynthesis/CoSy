package datatypes.formalisms;

import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.language_check.Get_History;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class parserHF_configuration<stack extends General, event extends General>
        implements General, Get_History<event> {

    private List<event> parserHF_conf_fixed;
    private List<event> parserHF_conf_history;
    private List<stack> parserHF_conf_stack;
    private int cached_hashcode = 0;

    public parserHF_configuration(List<event> parserHF_conf_fixed, List<event> parserHF_conf_history, List<stack> parserHF_conf_stack) {
        this.parserHF_conf_fixed = parserHF_conf_fixed;
        this.parserHF_conf_history = parserHF_conf_history;
        this.parserHF_conf_stack = parserHF_conf_stack;
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public List<event> get_parserHF_conf_fixed() {
        return parserHF_conf_fixed;
    }

    public List<event> get_parserHF_conf_history() {
        return parserHF_conf_history;
    }

    public List<stack> get_parserHF_conf_stack() {
        return parserHF_conf_stack;
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

        parserHF_configuration<?, ?> that = (parserHF_configuration<?, ?>) o;

        if (parserHF_conf_fixed != null ? !parserHF_conf_fixed.equals(that.parserHF_conf_fixed) : that.parserHF_conf_fixed != null)
            return false;
        if (parserHF_conf_history != null ? !parserHF_conf_history.equals(that.parserHF_conf_history) : that.parserHF_conf_history != null)
            return false;
        return parserHF_conf_stack != null ? parserHF_conf_stack.equals(that.parserHF_conf_stack) : that.parserHF_conf_stack == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = parserHF_conf_fixed != null ? parserHF_conf_fixed.hashCode() : 0;
            result = 31 * result + (parserHF_conf_history != null ? parserHF_conf_history.hashCode() : 0);
            result = 31 * result + (parserHF_conf_stack != null ? parserHF_conf_stack.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⦇fixed=");
        Append_helper.append(parserHF_conf_fixed, stringBuffer);
        stringBuffer.append(", history=");
        Append_helper.append(parserHF_conf_history, stringBuffer);
        stringBuffer.append(", stack=");
        Append_helper.append(parserHF_conf_stack, stringBuffer);
        stringBuffer.append("⦈");
    }

    @Override
    public List<event> get_history() {
        return get_parserHF_conf_history();
    }
}
