package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T06_CFG_WRAP_IN_SYMBOL_ABSTRACT {

    private static Logger log = LogManager.getLogger(T06_CFG_WRAP_IN_SYMBOL_ABSTRACT.class.getName());

    private T06_CFG_WRAP_IN_SYMBOL_ABSTRACT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <nonterminal extends General, event extends General>
    cfg<symbol__abstract<nonterminal>, event>
    CFG_WRAP_IN_SYMBOL_ABSTRACT(
            cfg<nonterminal, event> cfg) {
        Config.instance.measuring_thread.push("CFG_WRAP_IN_SYMBOL_ABSTRACT");

        List<symbol__abstract<nonterminal>> nonterminals = cfg.get_cfg_nonterminals().parallelStream().map(q -> new symbol__cons_symbol_base<>(q)).collect(Collectors.toList());

        List<event> events = cfg.get_cfg_events();

        List<cfg_step_label<symbol__abstract<nonterminal>, event>>
                delta = new ArrayList<>();
        for (cfg_step_label<nonterminal, event> elem : cfg.get_cfg_step_labels()) {
            List<two_elements__abstract<symbol__abstract<nonterminal>, event>> list = new ArrayList<>();
            for (two_elements__abstract<nonterminal, event> x : elem.get_prod_rhs())
                if (x instanceof two_elements__teA)
                    list.add(new two_elements__teA<>
                            (new symbol__cons_symbol_base<>(((two_elements__teA<nonterminal, event>) x).get_value())));
                else list.add(new two_elements__teB<>(((two_elements__teB<nonterminal, event>) x).get_value()));
            delta.add(new cfg_step_label<>(new symbol__cons_symbol_base<>(elem.get_prod_lhs()), list));
        }
        cfg<symbol__abstract<nonterminal>, event>
                result = new cfg<>(
                nonterminals,
                events,
                new symbol__cons_symbol_base<>(cfg.get_cfg_initial()),
                delta);
        Config.instance.measuring_thread.pop("CFG_WRAP_IN_SYMBOL_ABSTRACT");
        return result;
    }


}
