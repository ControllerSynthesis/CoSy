package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT {

    private static Logger log = LogManager.getLogger(T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT.class.getName());

    private T06_EPDA_WRAP_IN_SYMBOL_ABSTRACT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<symbol__abstract<state>, event, symbol__abstract<stack>>
    EPDA_WRAP_IN_SYMBOL_ABSTRACT(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("EPDA_WRAP_IN_SYMBOL_ABSTRACT");

        List<symbol__abstract<state>> states = epda.get_epda_states().parallelStream().map(q -> new symbol__cons_symbol_base<state>(q)).collect(Collectors.toList());

        List<event> events = epda.get_epda_events();

        List<symbol__abstract<stack>> gamma = epda.get_epda_gamma().parallelStream().map(q -> new symbol__cons_symbol_base<>(q)).collect(Collectors.toList());
        List<epda_step_label<symbol__abstract<state>, event, symbol__abstract<stack>>>
                delta = epda.get_epda_delta().parallelStream().map(e -> new epda_step_label<symbol__abstract<state>, event, symbol__abstract<stack>>(
                new symbol__cons_symbol_base<>(e.get_edge_src()),
                e.get_edge_event(),
                e.get_edge_pop().parallelStream().map(q -> new symbol__cons_symbol_base<stack>(q)).collect(Collectors.toList()),
                e.get_edge_push().parallelStream().map(q -> new symbol__cons_symbol_base<stack>(q)).collect(Collectors.toList()),
                new symbol__cons_symbol_base<state>(e.get_edge_trg())
        )).collect(Collectors.toList());

        epda<symbol__abstract<state>, event, symbol__abstract<stack>>
                result = new epda<>(
                states,
                events,
                gamma,
                delta,
                new symbol__cons_symbol_base<>(epda.get_epda_initial()),
                new symbol__cons_symbol_base<>(epda.get_epda_box()),
                epda.get_epda_marking().parallelStream().map(q -> new symbol__cons_symbol_base<state>(q)).collect(Collectors.toList())
        );
        Config.instance.measuring_thread.pop("EPDA_WRAP_IN_SYMBOL_ABSTRACT");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>
    WRAP_IN_SYMBOL_ABSTRACT_epda_all(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("EPDA_WRAP_IN_SYMBOL_ABSTRACT");

        List<symbol__abstract<state>> states = epda.get_epda_states().parallelStream().map(q -> new symbol__cons_symbol_base<state>(q)).collect(Collectors.toList());

        List<symbol__abstract<event>> events = epda.get_epda_events().parallelStream().map(q -> new symbol__cons_symbol_base<event>(q)).collect(Collectors.toList());

        List<symbol__abstract<stack>> gamma = epda.get_epda_gamma().parallelStream().map(q -> new symbol__cons_symbol_base<>(q)).collect(Collectors.toList());
        List<epda_step_label<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>>
                delta = epda.get_epda_delta().parallelStream().map(e -> new epda_step_label<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>(
                new symbol__cons_symbol_base<>(e.get_edge_src()),
                (e.get_edge_event() instanceof option__None ? new option__None<symbol__abstract<event>>() : new option__Some<symbol__abstract<event>>(new symbol__cons_symbol_base<event>(((option__Some<event>) e.get_edge_event()).the()))),
                e.get_edge_pop().parallelStream().map(q -> new symbol__cons_symbol_base<stack>(q)).collect(Collectors.toList()),
                e.get_edge_push().parallelStream().map(q -> new symbol__cons_symbol_base<stack>(q)).collect(Collectors.toList()),
                new symbol__cons_symbol_base<state>(e.get_edge_trg())
        )).collect(Collectors.toList());

        epda<symbol__abstract<state>, symbol__abstract<event>, symbol__abstract<stack>>
                result = new epda<>(
                states,
                events,
                gamma,
                delta,
                new symbol__cons_symbol_base<>(epda.get_epda_initial()),
                new symbol__cons_symbol_base<>(epda.get_epda_box()),
                epda.get_epda_marking().parallelStream().map(q -> new symbol__cons_symbol_base<state>(q)).collect(Collectors.toList())
        );
        Config.instance.measuring_thread.pop("EPDA_WRAP_IN_SYMBOL_ABSTRACT");
        return result;
    }

    public static <state extends General, event extends General>
    cfg<symbol__abstract<state>, event>
    EPDA_WRAP_IN_SYMBOL_ABSTRACT(
            cfg<state, event> cfg) {
        Config.instance.measuring_thread.push("EPDA_WRAP_IN_SYMBOL_ABSTRACT");

        List<symbol__abstract<state>> nonterminals = cfg.get_cfg_nonterminals().parallelStream().map(q -> new symbol__cons_symbol_base<state>(q)).collect(Collectors.toList());

        List<cfg_step_label<symbol__abstract<state>, event>>
                delta = cfg.get_cfg_step_labels().parallelStream().map(e -> new cfg_step_label<symbol__abstract<state>, event>(
                new symbol__cons_symbol_base<state>(e.get_prod_lhs()),
                e.get_prod_rhs().parallelStream().map(q -> {
                            if (q instanceof two_elements__teB) {
                                return new two_elements__teB<symbol__abstract<state>, event>(((two_elements__teB<state, event>) q).get_value());
                            } else {
                                return new two_elements__teA<symbol__abstract<state>, event>(new symbol__cons_symbol_base<state>(((two_elements__teA<state, event>) q).get_value()));
                            }
                        }
                ).collect(Collectors.toList())
        )).collect(Collectors.toList());

        cfg<symbol__abstract<state>, event>
                result = new cfg<>(
                nonterminals,
                cfg.get_cfg_events(),
                new symbol__cons_symbol_base<>(cfg.get_cfg_initial()),
                delta);
        Config.instance.measuring_thread.pop("EPDA_WRAP_IN_SYMBOL_ABSTRACT");
        return result;
    }


}
