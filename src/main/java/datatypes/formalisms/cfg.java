package datatypes.formalisms;

import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_datatypes_of_algorithm.two_elements__teB;
import datatypes.basic_java_datatypes.Append_helper;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Test_Sequence;
import datatypes.formalisms.language_check.Comparison_inclusion;
import main.Config;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class cfg<nonterminal extends General, event extends General>
        implements General {

    private List<nonterminal> cfg_nonterminals;
    private List<event> cfg_events;
    private nonterminal cfg_initial;
    private List<cfg_step_label<nonterminal, event>> cfg_step_labels;
    private int cached_hashcode = 0;
    private Map<nonterminal, List<cfg_step_label<nonterminal, event>>> cfg_step_labels_by_lhs = null;

    public cfg(List<nonterminal> cfg_nonterminals, List<event> cfg_events, nonterminal cfg_initial, List<cfg_step_label<nonterminal, event>> cfg_step_labels) {
        this.cfg_nonterminals = Collections.unmodifiableList(cfg_nonterminals);
        this.cfg_events = Collections.unmodifiableList(cfg_events);
        this.cfg_initial = cfg_initial;
        this.cfg_step_labels = Collections.unmodifiableList(cfg_step_labels);
    }

    public static <nonterminal extends General,
            event extends General>
    boolean check_word_inclusion(cfg<nonterminal, event> cfg, List<event> word, int expected) {
        if (General_Test_Sequence.MARK == expected)
            return new Comparison_inclusion<
                    cfg<nonterminal, event>,
                    cfgLM_semantics<nonterminal, event>,
                    cfg_configuration<nonterminal, event>,
                    cfg_step_label<nonterminal, event>,
                    cfg<nonterminal, event>,
                    cfgLM_semantics<nonterminal, event>,
                    cfg_configuration<nonterminal, event>,
                    cfg_step_label<nonterminal, event>,
                    event>()
                    .compare_check_word(
                            cfg,
                            new cfgLM_semantics<>(),
                            true,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else if (General_Test_Sequence.UNMARK == expected)
            return new Comparison_inclusion<
                    cfg<nonterminal, event>,
                    cfgLM_semantics<nonterminal, event>,
                    cfg_configuration<nonterminal, event>,
                    cfg_step_label<nonterminal, event>,
                    cfg<nonterminal, event>,
                    cfgLM_semantics<nonterminal, event>,
                    cfg_configuration<nonterminal, event>,
                    cfg_step_label<nonterminal, event>,
                    event>()
                    .compare_check_word(
                            cfg,
                            new cfgLM_semantics<>(),
                            false,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else if (General_Test_Sequence.REJECT == expected)
            return !new Comparison_inclusion<
                    cfg<nonterminal, event>,
                    cfgLM_semantics<nonterminal, event>,
                    cfg_configuration<nonterminal, event>,
                    cfg_step_label<nonterminal, event>,
                    cfg<nonterminal, event>,
                    cfgLM_semantics<nonterminal, event>,
                    cfg_configuration<nonterminal, event>,
                    cfg_step_label<nonterminal, event>,
                    event>()
                    .compare_check_word(
                            cfg,
                            new cfgLM_semantics<>(),
                            false,
                            word,
                            Long.MAX_VALUE,
                            Long.MAX_VALUE);
        else throw new RuntimeException();
    }

    public List<nonterminal> get_cfg_nonterminals() {
        return cfg_nonterminals;
    }

    public List<event> get_cfg_events() {
        return cfg_events;
    }

    public nonterminal get_cfg_initial() {
        return cfg_initial;
    }

    public List<cfg_step_label<nonterminal, event>> get_cfg_step_labels() {
        return cfg_step_labels;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        append(stringBuffer);
        return stringBuffer.toString();
    }

    public String log_info() {
        if (Config.CHECK_VALID_CFG_ON_LOG_INFO && !is_valid())
            throw new RuntimeException();
        return "(" + get_cfg_nonterminals().size() + ", " + get_cfg_events().size() + ", " + get_cfg_step_labels().size() + ")";
    }

    @Override
    public void append(StringBuffer stringBuffer) {
        stringBuffer.append("⟪");
        new Append_helper().append(cfg_nonterminals, stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(cfg_events, stringBuffer);
        stringBuffer.append(", ");
        cfg_initial.append(stringBuffer);
        stringBuffer.append(", ");
        new Append_helper().append(cfg_step_labels, stringBuffer);
        stringBuffer.append("⟫");
    }

    @Override
    public void cache_hashcode() {
        hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        cfg<?, ?> cfg = (cfg<?, ?>) o;

        if (cfg_nonterminals != null ? !cfg_nonterminals.equals(cfg.cfg_nonterminals) : cfg.cfg_nonterminals != null)
            return false;
        if (cfg_events != null ? !cfg_events.equals(cfg.cfg_events) : cfg.cfg_events != null) return false;
        if (cfg_initial != null ? !cfg_initial.equals(cfg.cfg_initial) : cfg.cfg_initial != null) return false;
        return cfg_step_labels != null ? cfg_step_labels.equals(cfg.cfg_step_labels) : cfg.cfg_step_labels == null;

    }

    @Override
    public int
    hashCode() {
        if (cached_hashcode == 0) {
            int result = cfg_nonterminals != null ? cfg_nonterminals.hashCode() : 0;
            result = 31 * result + (cfg_events != null ? cfg_events.hashCode() : 0);
            result = 31 * result + (cfg_initial != null ? cfg_initial.hashCode() : 0);
            result = 31 * result + (cfg_step_labels != null ? cfg_step_labels.hashCode() : 0);
            cached_hashcode = result;
        }
        return cached_hashcode;
    }

    public Map<nonterminal, List<cfg_step_label<nonterminal, event>>> get_cfg_step_labels_by_lhs() {
        if (cfg_step_labels_by_lhs == null) {
            cfg_step_labels_by_lhs = new HashMap<>();
            for (cfg_step_label<nonterminal, event> production : get_cfg_step_labels()) {
                cfg_step_labels_by_lhs.putIfAbsent(production.get_prod_lhs(), new ArrayList<>());
                cfg_step_labels_by_lhs.get(production.get_prod_lhs()).add(production);
            }
        }
        return cfg_step_labels_by_lhs;
    }

    public boolean is_valid() {
        Set<nonterminal> nonterminalSet = new HashSet<nonterminal>(get_cfg_nonterminals());
        for (cfg_step_label<nonterminal, event> step_label : get_cfg_step_labels()) {
            if (!nonterminalSet.contains(step_label.get_prod_lhs()))
                return false;
            for (two_elements__abstract<nonterminal, event> x : step_label.get_prod_rhs()) {
                if (x instanceof two_elements__teB) {
                    if (!get_cfg_events().contains(((two_elements__teB<nonterminal, event>) x).get_value()))
                        return false;
                } else if (x instanceof two_elements__teA) {
                    if (!nonterminalSet.contains(((two_elements__teA<nonterminal, event>) x).get_value()))
                        return false;
                }
            }
        }
        return nonterminalSet.contains(get_cfg_initial());
    }


}
