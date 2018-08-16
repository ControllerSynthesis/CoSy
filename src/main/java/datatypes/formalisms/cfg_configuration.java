package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.formalisms.language_check.Get_History;

import java.util.Collections;
import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg_configuration<nonterminal extends General, event extends General>
        implements General, Get_History<event> {

    private List<two_elements__abstract<nonterminal, event>> cfg_conf;
    private int cached_hashcode = 0;

    public cfg_configuration(List<two_elements__abstract<nonterminal, event>> cfg_conf) {
        this.cfg_conf = Collections.unmodifiableList(cfg_conf);
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    public boolean nonterminal_free() {
        return two_elements__abstract.get_left_most_teA(cfg_conf) == null;
    }

    public tuple2<General_Integer, two_elements__abstract<nonterminal, event>> left_most_nonterminal() {
        return two_elements__abstract.get_left_most_teA(cfg_conf);
    }

    public List<two_elements__abstract<nonterminal, event>> get_cfg_conf() {
        return cfg_conf;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        append(result);
        return result.toString();
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⦇");
        new Append_helper().append(cfg_conf, stringBuffer);
        stringBuffer.append("⦈");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        cfg_configuration<?, ?> that = (cfg_configuration<?, ?>) o;

        return cfg_conf != null ? cfg_conf.equals(that.cfg_conf) : that.cfg_conf == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            cached_hashcode = cfg_conf.hashCode();
        }
        return cached_hashcode;
    }

    @Override
    public List<event> get_history() {
        return two_elements__abstract.filterB(two_elements__abstract.get_maximal_beB_prefix(cfg_conf));
    }
}
