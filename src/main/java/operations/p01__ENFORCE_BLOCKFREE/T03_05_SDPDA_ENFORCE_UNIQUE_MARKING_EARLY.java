package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.state_or_state__abstract;
import datatypes.basic_datatypes_of_algorithm.state_or_state__cons_state_or_state_new;
import datatypes.basic_datatypes_of_algorithm.state_or_state__cons_state_or_state_old;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY {

    private static Logger log = LogManager.getLogger(T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY.class.getName());

    private T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY() {
        throw new IllegalStateException("Utility Class");
    }

    private static <state extends General, event extends General, stack extends General>
    boolean
    FB_executing_edge(
            epda_step_label<state, event, stack> e) {
        return !(e.get_edge_event() instanceof option__None);
    }

    private static <state extends General, event extends General, stack extends General>
    epda_step_label<state_or_state__abstract<state>, event, stack>
    F_SDPDA_EUME__edge_annotation(
            epda_step_label<state, event, stack> e,
            Function<state, state_or_state__abstract<state>> s,
            Function<state, state_or_state__abstract<state>> t) {
        return new epda_step_label<>(
                s.apply(e.get_edge_src()),
                e.get_edge_event(),
                e.get_edge_pop(),
                e.get_edge_push(),
                t.apply(e.get_edge_trg()));
    }

    private static <state extends General, event extends General, stack extends General>
    List<epda_step_label<state_or_state__abstract<state>, event, stack>>
    F_SDPDA_EUME__edges(
            epda_step_label<state, event, stack> e,
            List<state> FS) {
        if (FB_executing_edge(e))
            return General_List.list_by_enumeration(
                    F_SDPDA_EUME__edge_annotation(e, x -> new state_or_state__cons_state_or_state_old<>(x), x -> new state_or_state__cons_state_or_state_old<>(x)),
                    F_SDPDA_EUME__edge_annotation(e, x -> new state_or_state__cons_state_or_state_new<>(x), x -> new state_or_state__cons_state_or_state_old<>(x))
            );
        else {
            List<epda_step_label<state_or_state__abstract<state>, event, stack>> result =
                    General_List.list_by_enumeration(F_SDPDA_EUME__edge_annotation(e, x -> new state_or_state__cons_state_or_state_new<>(x), x -> new state_or_state__cons_state_or_state_new<>(x)));
            if (FS.contains(e.get_edge_src()))
                result.add(F_SDPDA_EUME__edge_annotation(e, x -> new state_or_state__cons_state_or_state_old<>(x), x -> new state_or_state__cons_state_or_state_new<>(x)));
            else
                result.add(F_SDPDA_EUME__edge_annotation(e, x -> new state_or_state__cons_state_or_state_old<>(x), x -> new state_or_state__cons_state_or_state_old<>(x)));
            return result;
        }
    }


    public static <state extends General, event extends General, stack extends General>
    epda<state_or_state__abstract<state>, event, stack>
    F_SDPDA_EUME(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("F_SDPDA_EUME");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_EUME: " + "epda=" + epda.log_info());
        epda<state_or_state__abstract<state>, event, stack> result;
        if (Config.instance.T03_05_SDPDA_ENFORCE_UNIQUE_MARKING_EARLY__create_only_states_used_in_edges) {
            Set<state> old_marking = new HashSet<>(epda.get_epda_marking());

            Set<state_or_state__abstract<state>> states = new HashSet<>();
            Set<state_or_state__abstract<state>> marking = new HashSet<>();
            Set<epda_step_label<state_or_state__abstract<state>, event, stack>> edges = new HashSet<>();

            Set<state_or_state__abstract<state>> done = new HashSet<>();
            Set<state_or_state__abstract<state>> todo = new HashSet<>();
            todo.add(new state_or_state__cons_state_or_state_old<state>(epda.get_epda_initial()));
            done.addAll(todo);

            while (!todo.isEmpty()) {
                state_or_state__abstract<state> pair = todo.iterator().next();
                todo.remove(pair);
                state q;
                if (pair instanceof state_or_state__cons_state_or_state_old)
                    q = ((state_or_state__cons_state_or_state_old<state>) pair).get_value();
                else
                    q = ((state_or_state__cons_state_or_state_new<state>) pair).get_value();
                boolean q_attr = pair instanceof state_or_state__cons_state_or_state_old;

                if (q_attr)
                    states.add(new state_or_state__cons_state_or_state_old<>(q));
                else
                    states.add(new state_or_state__cons_state_or_state_new<>(q));

                boolean q_is_marking = old_marking.contains(q);

                for (epda_step_label<state, event, stack> edge : epda.get_map_source(q)) {

                    boolean next_attr = true;
                    if (edge.get_edge_event() instanceof option__None && (q_is_marking || !q_attr))
                        next_attr = false;


                    epda_step_label<state_or_state__abstract<state>, event, stack> new_edge = F_SDPDA_EUME__edge_annotation(edge,
                            q_attr ?
                                    x -> new state_or_state__cons_state_or_state_old<>(x)
                                    : x -> new state_or_state__cons_state_or_state_new<>(x)
                            , next_attr ?
                                    x -> new state_or_state__cons_state_or_state_old<>(x)
                                    : x -> new state_or_state__cons_state_or_state_new<>(x));
                    edges.add(new_edge);
                    if (!done.contains(new_edge.get_edge_trg())) {
                        todo.add(new_edge.get_edge_trg());
                        done.add(new_edge.get_edge_trg());
                    }
                }
            }

            for (state q : epda.get_epda_marking())
                if (states.contains(new state_or_state__cons_state_or_state_old<>(q)))
                    marking.add(new state_or_state__cons_state_or_state_old<>(q));
            result = new epda<>(
                    new ArrayList<>(states),
                    epda.get_epda_events(),
                    epda.get_epda_gamma(),
                    new ArrayList<>(edges),
                    new state_or_state__cons_state_or_state_old<>(epda.get_epda_initial()),
                    epda.get_epda_box(),
                    new ArrayList<>(marking));

        } else {
            result = new epda<>(
                    Stream.concat(
                            epda.get_epda_states().stream().map(q -> new state_or_state__cons_state_or_state_old<>(q)),
                            epda.get_epda_states().stream().map(q -> new state_or_state__cons_state_or_state_new<>(q)))
                            .collect(Collectors.toList()),
                    epda.get_epda_events(),
                    epda.get_epda_gamma(),
                    epda.get_epda_delta().stream().map(
                            e -> F_SDPDA_EUME__edges(e, epda.get_epda_marking())
                    ).reduce(new ArrayList<>(), General_List.get_concat()),
                    new state_or_state__cons_state_or_state_old<>(epda.get_epda_initial()),
                    epda.get_epda_box(),
                    epda.get_epda_marking().parallelStream().map(q -> new state_or_state__cons_state_or_state_old<>(q)).collect(Collectors.toList()));
        }
        Config.instance.measuring_thread.pop("F_SDPDA_EUME");
        return result;
    }

}
