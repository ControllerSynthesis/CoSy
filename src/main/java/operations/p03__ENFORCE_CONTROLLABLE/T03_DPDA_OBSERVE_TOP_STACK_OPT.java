package operations.p03__ENFORCE_CONTROLLABLE;

import datatypes.basic_datatypes_of_algorithm.*;
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
public class T03_DPDA_OBSERVE_TOP_STACK_OPT {
    private static Logger log = LogManager.getLogger(T03_DPDA_OBSERVE_TOP_STACK_OPT.class.getName());

    private T03_DPDA_OBSERVE_TOP_STACK_OPT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state_and_stack_or_state__abstract<state, stack>, event, stack>
    F_DPDA_OTS_OPT(
            epda<state, event, stack> M) {
        Config.instance.measuring_thread.push("F_DPDA_OTS_OPT");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_OTS_OPT: " + "M=" + M.log_info());

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

//        if(!result.is_valid(true))
//            throw new RuntimeException();

        Config.instance.measuring_thread.pop("F_DPDA_OTS_OPT");
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

    /*
    * only those auxiliary states are created that are subject to removal!
    * */
    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state_and_stack_or_state__abstract<state, stack>, event, stack>>
    F_DPDA_OTS__edges(
            epda<state, event, stack> M,
            Map<state, Set<stack>> needs_auxiliary_for) {
        Set<epda_step_label<state_and_stack_or_state__abstract<state, stack>, event, stack>> result = new HashSet<>();
        Set<state> marking = new HashSet<>(M.get_epda_marking());
        Map<state, Map<stack, state_and_stack_or_state__cons_state_and_stack<state, stack>>> auxiliary_state = new HashMap<>();

        for (epda_step_label<state, event, stack> step_label : M.get_epda_delta()) {
            state_and_stack_or_state__abstract<state, stack> source = null;
            if (marking.contains(step_label.get_edge_src()) || step_label.get_edge_event() instanceof option__Some) {
                state from_state = step_label.get_edge_src();
                stack from_stack = step_label.get_edge_pop().get(0);
                if (auxiliary_state.containsKey(from_state) && auxiliary_state.get(from_state).containsKey(from_stack))
                    source = auxiliary_state.get(from_state).get(from_stack);
                else {
                    source = new state_and_stack_or_state__cons_state_and_stack<>(from_state, from_stack);
                    auxiliary_state.putIfAbsent(from_state, new HashMap<>());
                    auxiliary_state.get(from_state).put(from_stack, (state_and_stack_or_state__cons_state_and_stack<state, stack>) source);
                }
                result.add(new epda_step_label<>(
                        new state_and_stack_or_state__cons_state<>(from_state),
                        new option__None<>(),
                        General_List.list_by_enumeration(from_stack),
                        General_List.list_by_enumeration(from_stack),
                        source));
            } else {
                source = new state_and_stack_or_state__cons_state<>(step_label.get_edge_src());
            }
            result.add(new epda_step_label<>(
                    source,
                    step_label.get_edge_event(),
                    step_label.get_edge_pop(),
                    step_label.get_edge_push(),
                    new state_and_stack_or_state__cons_state<>(step_label.get_edge_trg())));
        }
        return new ArrayList<>(result);
    }


}
