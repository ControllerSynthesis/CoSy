package datatypes.formalisms;

import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;

import java.util.Collections;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class parser_step_label<nonterminal extends General, event extends General>
        implements General {

    private List<nonterminal> rule_lpop;
    private List<event> rule_rpop;
    private List<nonterminal> rule_lpush;
    private List<event> rule_rpush;
    private int cached_hashcode = 0;

    public parser_step_label(List<nonterminal> rule_lpop, List<event> rule_rpop, List<nonterminal> rule_lpush, List<event> rule_rpush) {
        this.rule_lpop = Collections.unmodifiableList(rule_lpop);
        this.rule_rpop = Collections.unmodifiableList(rule_rpop);
        this.rule_lpush = Collections.unmodifiableList(rule_lpush);
        this.rule_rpush = Collections.unmodifiableList(rule_rpush);
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public List<nonterminal> get_rule_lpop() {
        return rule_lpop;
    }

    public List<event> get_rule_rpop() {
        return rule_rpop;
    }

    public List<nonterminal> get_rule_lpush() {
        return rule_lpush;
    }

    public List<event> get_rule_rpush() {
        return rule_rpush;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        parser_step_label<?, ?> that = (parser_step_label<?, ?>) o;

        if (rule_lpop != null ? !rule_lpop.equals(that.rule_lpop) : that.rule_lpop != null) return false;
        if (rule_rpop != null ? !rule_rpop.equals(that.rule_rpop) : that.rule_rpop != null) return false;
        if (rule_lpush != null ? !rule_lpush.equals(that.rule_lpush) : that.rule_lpush != null) return false;
        return rule_rpush != null ? rule_rpush.equals(that.rule_rpush) : that.rule_rpush == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = rule_lpop != null ? rule_lpop.hashCode() : 0;
            result = 31 * result + (rule_rpop != null ? rule_rpop.hashCode() : 0);
            result = 31 * result + (rule_lpush != null ? rule_lpush.hashCode() : 0);
            result = 31 * result + (rule_rpush != null ? rule_rpush.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }


    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪lpop=");
        new Append_helper().append(rule_lpop, stringBuffer);
        stringBuffer.append(", rpop=");
        new Append_helper().append(rule_rpop, stringBuffer);
        stringBuffer.append(", lpush=");
        new Append_helper().append(rule_lpush, stringBuffer);
        stringBuffer.append(", rpush=");
        new Append_helper().append(rule_rpush, stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

}
