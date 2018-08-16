package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES {

    private static Logger log = LogManager.getLogger(T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES.class.getName());

    private T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General, stack extends General>
    option__abstract<state_or_edge_nat__abstract<state, event, stack>>
    F_DPDA_RMPUE__state(
            epda_step_label<state, event, stack> e,
            int n) {
        if (n == 0)
            return new option__Some<>(new state_or_edge_nat__cons_state_or_edge_nat_old<>(e.get_edge_src()));
        else {
            if (n + 1 < e.get_edge_push().size())
                return new option__Some<>(new state_or_edge_nat__cons_state_or_edge_nat_new<>(e, n));
            else {
                if (n + 1 == e.get_edge_push().size())
                    return new option__Some<>(new state_or_edge_nat__cons_state_or_edge_nat_old<>(e.get_edge_trg()));
                else
                    return new option__None<>();
            }
        }
    }

    private static <state extends General, event extends General, stack extends General>
    boolean
    F_DPDA_RMPUE__edge_if(
            epda_step_label<state, event, stack> e) {
        return e.get_edge_push().size() > 2;
    }

    static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>>
    F_DPDA_RMPUE__edge_then(
            epda_step_label<state, event, stack> e) {
        return IntStream
                .range(0, e.get_edge_push().size() - 1)
                .mapToObj(x -> x)
                .map(i -> new epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>(
                        ((option__Some<state_or_edge_nat__abstract<state, event, stack>>) F_DPDA_RMPUE__state(e, i)).the(),
                        new option__None<>(),
                        Collections.singletonList(e.get_edge_push().get(e.get_edge_push().size() - 1 - i)),
                        Stream.concat(
                                Collections.singletonList(e.get_edge_push().get(e.get_edge_push().size() - 1 - (i + 1))).stream(),
                                Collections.singletonList(e.get_edge_push().get(e.get_edge_push().size() - 1 - i)).stream()
                        ).collect(Collectors.toList()),
                        ((option__Some<state_or_edge_nat__abstract<state, event, stack>>) F_DPDA_RMPUE__state(e, i + 1)).the()))
                .collect(Collectors.toList());
    }


    static <state extends General, event extends General, stack extends General>
    epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>
    F_DPDA_RMPUE__edge_else(
            epda_step_label<state, event, stack> e) {
        return new epda_step_label<>(
                new state_or_edge_nat__cons_state_or_edge_nat_old(e.get_edge_src()),
                e.get_edge_event(),
                e.get_edge_pop(),
                e.get_edge_push(),
                new state_or_edge_nat__cons_state_or_edge_nat_old(e.get_edge_trg()));
    }


    public static <state extends General, event extends General, stack extends General>
    epda<state_or_edge_nat__abstract<state, event, stack>, event, stack>
    F_DPDA_RMPUE(
            epda<state, event, stack> epda,
            Map<epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>, epda_step_label<state, event, stack>> reversal) {
        Config.instance.measuring_thread.push("F_DPDA_RMPUE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_RMPUE: " + "epda=" + epda.log_info());


        List<epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>> delta = new ArrayList<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta()) {
            List<epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack>> current;
            if (F_DPDA_RMPUE__edge_if(e))
                current = F_DPDA_RMPUE__edge_then(e);
            else
                current = Collections.singletonList(F_DPDA_RMPUE__edge_else(e));
            delta.addAll(current);
            if (reversal != null)
                for (epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack> current_step_label : current)
                    reversal.put(current_step_label, e);
        }


        Set<state_or_edge_nat__abstract<state, event, stack>> states = null;
        Set<state_or_edge_nat__abstract<state, event, stack>> marking = null;


        if (Config.instance.T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES__create_only_states_used_in_edges) {
            states = new HashSet<>();
            for (epda_step_label<state_or_edge_nat__abstract<state, event, stack>, event, stack> edge : delta) {
                states.add(edge.get_edge_src());
                states.add(edge.get_edge_trg());
            }
            marking = new HashSet<>();
            for (state q : epda.get_epda_marking())
                if (states.contains(new state_or_edge_nat__cons_state_or_edge_nat_old<state, event, stack>(q)))
                    marking.add(new state_or_edge_nat__cons_state_or_edge_nat_old<>(q));
        } else {
            states = epda.get_epda_states()
                    .stream()
                    .map(q -> new state_or_edge_nat__cons_state_or_edge_nat_old<state, event, stack>(q))
                    .collect(Collectors.toSet());
            states.addAll(
                    epda.get_epda_delta().stream().map(e -> {
                        return IntStream
                                .range(0, e.get_edge_push().size())
                                .mapToObj(x -> x)
                                .map(i -> ((option__Some<state_or_edge_nat__abstract<state, event, stack>>) F_DPDA_RMPUE__state(e, i)).the())
                                .collect(Collectors.toList());
                    }).reduce(new ArrayList<>(), General_List.get_concat()));

            marking = epda.get_epda_marking().parallelStream().map(q -> new state_or_edge_nat__cons_state_or_edge_nat_old<state, event, stack>(q)).collect(Collectors.toSet());
        }


        epda<state_or_edge_nat__abstract<state, event, stack>, event, stack> result = new epda<>(
                new ArrayList<>(states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                epda.get_epda_delta().stream().map(
                        e -> F_DPDA_RMPUE__edge_if(e) ? F_DPDA_RMPUE__edge_then(e) : Collections.singletonList(F_DPDA_RMPUE__edge_else(e))
                ).reduce(new ArrayList<>(), General_List.get_concat()),
                new state_or_edge_nat__cons_state_or_edge_nat_old<>(epda.get_epda_initial()),
                epda.get_epda_box(),
                new ArrayList<>(marking));
        Config.instance.measuring_thread.pop("F_DPDA_RMPUE");
        return result;

    }

}
