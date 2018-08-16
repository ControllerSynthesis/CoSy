package operations.p03__ENFORCE_CONTROLLABLE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.state_and_stack_or_state__abstract;
import datatypes.basic_datatypes_of_algorithm.state_and_stack_or_state__cons_state;
import datatypes.basic_datatypes_of_algorithm.state_and_stack_or_state__cons_state_and_stack;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_DPDA_OBSERVE_TOP_STACK {
    private static Logger log = LogManager.getLogger(T03_DPDA_OBSERVE_TOP_STACK.class.getName());

    private T03_DPDA_OBSERVE_TOP_STACK() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state_and_stack_or_state__abstract<state, stack>, event, stack>
    F_DPDA_OTS(
            epda<state, event, stack> M) {
        Config.instance.measuring_thread.push("F_DPDA_OTS");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_OTS: " + "M=" + M.log_info());

        epda<state_and_stack_or_state__abstract<state, stack>, event, stack> result;


        List<epda_step_label<state_and_stack_or_state__abstract<state, stack>, event, stack>> edges;

        Map<state, Set<stack>> needs_auxiliary_for = new HashMap<>();
        if (Config.instance.T03_DPDA_OBSERVE_TOP_STACK__use_blockfreeness_for_top_stack_approximation) {
            needs_auxiliary_for = T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__blockfree(M);
        } else if (Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_DPDA_OTS() >= 0) {
            for (state q : M.get_epda_states())
                needs_auxiliary_for.put(q, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(M, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_DPDA_OTS(), q));
        } else {
            for (state q : M.get_epda_states())
                needs_auxiliary_for.put(q, new HashSet<stack>(M.get_epda_gamma()));
        }

        edges = F_DPDA_OTS__edges(M, needs_auxiliary_for);

        List<state_and_stack_or_state__abstract<state, stack>> states;
        List<state_and_stack_or_state__abstract<state, stack>> marking_states;

        if (Config.instance.T03_DPDA_OBSERVE_TOP_STACK__generate_states_from_edges) {
            Set<state_and_stack_or_state__abstract<state, stack>> states_set = new HashSet<>();
            for (epda_step_label<state_and_stack_or_state__abstract<state, stack>, event, stack> step_label : edges) {
                states_set.add(step_label.get_edge_src());
                states_set.add(step_label.get_edge_trg());
            }
            states = new ArrayList<>(states_set);
            marking_states = new ArrayList<>();
            for (state_and_stack_or_state__abstract<state, stack> state : states_set)
                if (state instanceof state_and_stack_or_state__cons_state_and_stack)
                    if (M.get_epda_marking().contains(((state_and_stack_or_state__cons_state_and_stack<state, stack>) state).get_value1()))
                        marking_states.add(state);
        } else {
            states = F_DPDA_OTS__states(M, needs_auxiliary_for);
            marking_states = F_DPDA_OTS__marking_states(M, needs_auxiliary_for);
        }

        result = new epda<>(
                states,
                M.get_epda_events(),
                M.get_epda_gamma(),
                edges,
                new state_and_stack_or_state__cons_state_and_stack<>(M.get_epda_initial(), M.get_epda_box()),
                M.get_epda_box(),
                marking_states);

        Config.instance.measuring_thread.pop("F_DPDA_OTS");
        return result;
    }

    /**
     * F_DPDA_OTS__states_auxiliary
     * F_DPDA_OTS__states_main
     * implemented implicitly
     */
    private static <state extends General, event extends General, stack extends General>
    List<state_and_stack_or_state__abstract<state, stack>>
    F_DPDA_OTS__states(
            epda<state, event, stack> M, Map<state, Set<stack>> needs_auxiliary_for) {
        List<state_and_stack_or_state__abstract<state, stack>> result = new ArrayList<>();
        // F_DPDA_OTS__states_auxiliary
        for (state q : M.get_epda_states())
            for (stack X : needs_auxiliary_for.get(q))
                result.add(new state_and_stack_or_state__cons_state_and_stack<>(q, X));
        // F_DPDA_OTS__states_main
        for (state q : M.get_epda_states())
            result.add(new state_and_stack_or_state__cons_state<>(q));
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    List<state_and_stack_or_state__abstract<state, stack>>
    F_DPDA_OTS__marking_states(
            epda<state, event, stack> M, Map<state, Set<stack>> needs_auxiliary_for) {
        List<state_and_stack_or_state__abstract<state, stack>> result = new ArrayList<>();
        for (state q : M.get_epda_marking())
            for (stack X : needs_auxiliary_for.get(q))
                result.add(new state_and_stack_or_state__cons_state_and_stack<>(q, X));
        return result;
    }

    /**
     * F_DPDA_OTS__edges_main_auxiliary
     * F_DPDA_OTS__edges_main_auxiliary_1
     * F_DPDA_OTS__edges_auxiliary_main
     * F_DPDA_OTS__edges_auxiliary_main_1
     * implemented implicitly
     */
    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state_and_stack_or_state__abstract<state, stack>, event, stack>>
    F_DPDA_OTS__edges(
            epda<state, event, stack> M,
            Map<state, Set<stack>> needs_auxiliary_for) {
        List<epda_step_label<state_and_stack_or_state__abstract<state, stack>, event, stack>> result = new ArrayList<>();
        // F_DPDA_OTS__edges_main_auxiliary
        for (state q : M.get_epda_states())
            for (stack X : needs_auxiliary_for.get(q))
                result.add(new epda_step_label<>(
                        new state_and_stack_or_state__cons_state<>(q),
                        new option__None<>(),
                        General_List.list_by_enumeration(X),
                        General_List.list_by_enumeration(X),
                        new state_and_stack_or_state__cons_state_and_stack<>(q, X)));
        // F_DPDA_OTS__edges_auxiliary_main
        for (epda_step_label<state, event, stack> step_label : M.get_epda_delta()) {
            result.add(new epda_step_label<>(
                    new state_and_stack_or_state__cons_state_and_stack<>(step_label.get_edge_src(), step_label.get_edge_pop().get(0)),
                    step_label.get_edge_event(),
                    step_label.get_edge_pop(),
                    step_label.get_edge_push(),
                    new state_and_stack_or_state__cons_state<>(step_label.get_edge_trg())));
        }
        return result;
    }


}
