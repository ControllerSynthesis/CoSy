package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.state_or_edge__abstract;
import datatypes.basic_datatypes_of_algorithm.state_or_edge__cons_state_or_edge_new;
import datatypes.basic_datatypes_of_algorithm.state_or_edge__cons_state_or_edge_old;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES {

    private static Logger log = LogManager.getLogger(T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES.class.getName());

    private T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General, stack extends General>
    epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>
    F_DPDA_SPPE__edge_then_1(
            epda_step_label<state, event, stack> e) {
        return new epda_step_label<>(
                new state_or_edge__cons_state_or_edge_old<>(e.get_edge_src()),
                new option__None<>(),
                e.get_edge_pop(),
                Collections.emptyList(),
                new state_or_edge__cons_state_or_edge_new<>(e));
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>>
    F_DPDA_SPPE__edge_then_2(
            epda_step_label<state, event, stack> e,
            Collection<stack> S) {
        return S.stream().map(X ->
                new epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>(
                        new state_or_edge__cons_state_or_edge_new<>(e),
                        new option__None<>(),
                        Collections.singletonList(X),
                        Stream.concat(e.get_edge_push().stream(), Collections.singletonList(X).stream()).collect(Collectors.toList()),
                        new state_or_edge__cons_state_or_edge_old<>(e.get_edge_trg())))
                .collect(Collectors.toList());
    }

    static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>>
    F_DPDA_SPPE__edge_then(
            epda_step_label<state, event, stack> e,
            Collection<stack> S) {
        return Stream.concat(
                Collections.singletonList(F_DPDA_SPPE__edge_then_1(e)).stream(),
                F_DPDA_SPPE__edge_then_2(e, S).stream())
                .collect(Collectors.toList());
    }

    private static <state extends General, event extends General, stack extends General>
    boolean
    F_DPDA_SPPE__edge_if(
            epda_step_label<state, event, stack> e) {
        return e.get_edge_event() instanceof option__None
                && (e.get_edge_push().size() > 0
                && !(e.get_edge_pop().equals(Collections.singletonList(General_List.last(e.get_edge_push())))));
    }

    static <state extends General, event extends General, stack extends General>
    epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>
    F_DPDA_SPPE__edge_else(
            epda_step_label<state, event, stack> e) {
        return new epda_step_label<>(
                new state_or_edge__cons_state_or_edge_old<>(e.get_edge_src()),
                e.get_edge_event(),
                e.get_edge_pop(),
                e.get_edge_push(),
                new state_or_edge__cons_state_or_edge_old<>(e.get_edge_trg()));
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state_or_edge__abstract<state, event, stack>, event, stack>
    F_DPDA_SPPE(
            epda<state, event, stack> epda,
            Map<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>, epda_step_label<state, event, stack>> reversal) {
        Config.instance.measuring_thread.push("F_DPDA_SPPE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_SPPE: " + "epda=" + epda.log_info());

        List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>> delta = new ArrayList<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta()) {
            List<epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack>> current;
            if (F_DPDA_SPPE__edge_if(e)) {
                if (Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE() >= 0)
                    current = F_DPDA_SPPE__edge_then(e, T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 1, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__SPPE(), e.get_edge_src()));
                else
                    current = F_DPDA_SPPE__edge_then(e, epda.get_epda_gamma());
            } else
                current = Collections.singletonList(F_DPDA_SPPE__edge_else(e));
            delta.addAll(current);
            if (reversal != null)
                for (epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack> current_step_label : current)
                    reversal.put(current_step_label, e);
        }

        Set<state_or_edge__abstract<state, event, stack>> states = null;
        Set<state_or_edge__abstract<state, event, stack>> marking = null;


        if (Config.instance.T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES__create_only_states_used_in_edges) {
            states = new HashSet<>();
            for (epda_step_label<state_or_edge__abstract<state, event, stack>, event, stack> edge : delta) {
                states.add(edge.get_edge_src());
                states.add(edge.get_edge_trg());
            }
            marking = new HashSet<>();
            for (state q : epda.get_epda_marking())
                if (states.contains(new state_or_edge__cons_state_or_edge_old<state, event, stack>(q)))
                    marking.add(new state_or_edge__cons_state_or_edge_old<>(q));
        } else {
            states = Stream.concat(
                    epda.get_epda_states().stream().map(q -> (state_or_edge__abstract<state, event, stack>) new state_or_edge__cons_state_or_edge_old<state, event, stack>(q)),
                    epda.get_epda_delta().stream().map(e -> (state_or_edge__abstract<state, event, stack>) new state_or_edge__cons_state_or_edge_new<>(e)))
                    .collect(Collectors.toSet());
            marking = epda.get_epda_marking().parallelStream().map(q -> (state_or_edge__abstract<state, event, stack>) new state_or_edge__cons_state_or_edge_old<state, event, stack>(q)).collect(Collectors.toSet());
        }

        epda<state_or_edge__abstract<state, event, stack>, event, stack> result = new epda<>(
                new ArrayList<>(states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                delta,
                new state_or_edge__cons_state_or_edge_old<>(epda.get_epda_initial()),
                epda.get_epda_box(),
                new ArrayList<>(marking));
        Config.instance.measuring_thread.pop("F_DPDA_SPPE");
        return result;

    }

}
