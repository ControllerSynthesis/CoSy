package operations.p03__ENFORCE_CONTROLLABLE;

import datatypes.basic_datatypes_of_algorithm.state_or_state__abstract;
import datatypes.basic_datatypes_of_algorithm.state_or_state__cons_state_or_state_new;
import datatypes.basic_datatypes_of_algorithm.state_or_state__cons_state_or_state_old;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T04_DPDA_ENFORCE_UNIQUE_MARKING_LATE {
    private static Logger log = LogManager.getLogger(T04_DPDA_ENFORCE_UNIQUE_MARKING_LATE.class.getName());

    private T04_DPDA_ENFORCE_UNIQUE_MARKING_LATE() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General, stack extends General>
    epda<state_or_state__abstract<state>, event, stack>
    F_DPDA_EUML_inner(
            epda<state, event, stack> G) {
        Config.instance.measuring_thread.push("F_DPDA_EUML_inner");
        epda<state_or_state__abstract<state>, event, stack> G2 = T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY.F_SDPDA_EUME(G);
        List<state_or_state__abstract<state>> marking_states = new ArrayList<>();
        Set<state_or_state__abstract<state>> nonstable = Algorithm_Definition__DPDA_DFA_PRODUCT.epda_state_silent_exists(G2);
        log.info("number of nonstable states: " + nonstable.size());
        for (state q : G.get_epda_marking()) {
            state_or_state__abstract<state> state = new state_or_state__cons_state_or_state_old<>(q);
            if (!nonstable.contains(state))
                marking_states.add(state);
        }
        for (state q : G.get_epda_states()) {
            state_or_state__abstract<state> state = new state_or_state__cons_state_or_state_new<>(q);
            if (!nonstable.contains(state))
                marking_states.add(state);
        }
        epda<state_or_state__abstract<state>, event, stack> result = new epda<>(
                G2.get_epda_states(),
                G2.get_epda_events(),
                G2.get_epda_gamma(),
                G2.get_epda_delta(),
                G2.get_epda_initial(),
                G2.get_epda_box(),
                marking_states);
        Config.instance.measuring_thread.pop("F_DPDA_EUML_inner");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state_or_state__abstract<state>, event, stack>
    F_DPDA_EUML(
            epda<state, event, stack> G) {
        Config.instance.measuring_thread.push("F_DPDA_EUML");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_EUML: " + "G=" + G.log_info());
        epda<state_or_state__abstract<state>, event, stack> result;
        if (Config.instance.T04_DPDA_ENFORCE_UNIQUE_MARKING_LATE__ignore_when_unstable_marking) {
            Set<state> unstable = Algorithm_Definition__DPDA_DFA_PRODUCT.epda_state_silent_exists(G);
            unstable.retainAll(G.get_epda_marking());
            if (log.isInfoEnabled())
                log.info("F_DPDA_EUML: unstable & marking=" + unstable.size());
            if (unstable.isEmpty()) {
                result = F_DPDA_EUML_inner_IGNORE(G);
            } else {
                result = F_DPDA_EUML_inner(G);
            }
        } else {
            result = F_DPDA_EUML_inner(G);
        }

        Config.instance.measuring_thread.pop("F_DPDA_EUML");
        return result;

    }

    private static <state extends General, event extends General, stack extends General>
    epda<state_or_state__abstract<state>, event, stack>
    F_DPDA_EUML_inner_IGNORE(epda<state, event, stack> G) {
        Config.instance.measuring_thread.push("F_DPDA_EUML_inner_IGNORE");
        Map<state, state_or_state__abstract<state>> new_state = new HashMap<>();
        for (state q : G.get_epda_states())
            new_state.putIfAbsent(q, new state_or_state__cons_state_or_state_old<>(q));
        List<epda_step_label<state_or_state__abstract<state>, event, stack>> step_labels = new ArrayList<>();
        for (epda_step_label<state, event, stack> step_label : G.get_epda_delta())
            step_labels.add(new epda_step_label<>(
                    new_state.get(step_label.get_edge_src()),
                    step_label.get_edge_event(),
                    step_label.get_edge_pop(),
                    step_label.get_edge_push(),
                    new_state.get(step_label.get_edge_trg())));
        List<state_or_state__abstract<state>> marking_states = new ArrayList<>();
        for (state q : G.get_epda_marking())
            marking_states.add(new_state.get(q));
        epda<state_or_state__abstract<state>, event, stack> result = new epda<>(
                new ArrayList<>(new_state.values()),
                G.get_epda_events(),
                G.get_epda_gamma(),
                step_labels,
                new_state.get(G.get_epda_initial()),
                G.get_epda_box(),
                marking_states);
        Config.instance.measuring_thread.pop("F_DPDA_EUML_inner_IGNORE");
        return result;
    }

}
