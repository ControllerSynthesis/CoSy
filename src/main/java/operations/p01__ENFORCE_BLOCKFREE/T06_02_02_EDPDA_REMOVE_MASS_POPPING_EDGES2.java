package operations.p01__ENFORCE_BLOCKFREE;

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
public class T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2<state extends General, event extends General, stack extends General> {
    private static Logger log = LogManager.getLogger(T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2.class.getName());

    private T06_02_02_EDPDA_REMOVE_MASS_POPPING_EDGES2() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>
    F_EDPDA_RMPOE__ALTERNATIVE(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("F_EDPDA_RMPOE__ALTERNATIVE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EDPDA_RMPOE__ALTERNATIVE: " + "epda=" + epda.log_info());
        Set<two_elements__abstract<state, tuple2<state, General_List<stack>>>> states = new HashSet<>();
        Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> step_labels = new HashSet<>();

        for (state source : epda.get_epda_states()) {
            states.add(new two_elements__teA<>(source));
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> current_step_labels = new HashSet<>();
            for (epda_step_label<state, event, stack> step_label : epda.get_map_source(source))
                current_step_labels.add(new epda_step_label<>(
                        new two_elements__teA<>(step_label.get_edge_src()),
                        step_label.get_edge_event(),
                        step_label.get_edge_pop(),
                        step_label.get_edge_push(),
                        new two_elements__teA<>(step_label.get_edge_trg())
                ));
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>
                    converted = convert(epda, 0, source, current_step_labels);
            step_labels.addAll(converted);
            for (epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label : converted) {
                states.add(step_label.get_edge_src());
                states.add(step_label.get_edge_trg());
            }
        }

        List<two_elements__abstract<state, tuple2<state, General_List<stack>>>> marking =
                new ArrayList<>();
        for (state q : epda.get_epda_marking())
            marking.add(new two_elements__teA<>(q));

        epda<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> result = new epda<>(
                new ArrayList<>(states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(step_labels),
                new two_elements__teA<>(epda.get_epda_initial()),
                epda.get_epda_box(),
                marking);

        Config.instance.measuring_thread.pop("F_EDPDA_RMPOE__ALTERNATIVE");
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>
    convert(
            epda<state, event, stack> epda,
            int depth,
            state source,
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> current_step_labels) {
        Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>
                result = new HashSet<>();

        // if a pop-component is empty, then replace edge for all possible top-stacks
        Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> empty_pop = new HashSet<>();
        for (epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label : current_step_labels)
            if (step_label.get_edge_pop().isEmpty())
                empty_pop.add(step_label);
        if (!empty_pop.isEmpty()) {
            Set<stack> possible_stop_stacks = Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE() >= 0 ?
                    T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, depth,
                            Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RMPOE(), source)
                    : new HashSet<stack>(epda.get_epda_gamma());
            current_step_labels.removeAll(empty_pop);
            for (stack s : possible_stop_stacks)
                for (epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label : empty_pop)
                    current_step_labels.add(new epda_step_label<>(
                            step_label.get_edge_src(),
                            step_label.get_edge_event(),
                            General_List.list_by_enumeration(s),
                            General_List.append(step_label.get_edge_push(), s),
                            step_label.get_edge_trg()));
        }

        // note: None,X and a,X may be patterns in deterministic epda
        // split w.r.t. first pop element
        Map<stack, Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>> top_pop = new HashMap<>();
        for (epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label : current_step_labels) {
            stack elem = step_label.get_edge_pop().get(0);
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> old = top_pop.get(elem);
            if (old == null)
                old = new HashSet<>();
            old.add(step_label);
            top_pop.put(elem, old);
        }

        for (Map.Entry<stack, Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>> entry : top_pop.entrySet()) {
            // if there is no extension of entry.getKey() the edges are rolled out alone
            boolean has_longer = false;
            for (epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label : entry.getValue()) {
                if (step_label.get_edge_pop().size() > 1) {
                    has_longer = true;
                    break;
                }
            }
            if (!has_longer) {
                Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>
                        todo = new HashSet<>(entry.getValue());
                while (!todo.isEmpty()) {
                    epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>
                            step_label = todo.iterator().next();
                    todo.remove(step_label);
                    roll_out(step_label, result, todo, false);
                }
                continue;
            }

            // entry.getKey() can not be the epda.get_epda_box() element
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>>
                    remainders = new HashSet<>();
            for (epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label : entry.getValue()) {
                roll_out(step_label, result, remainders, true);
            }
            result.addAll(convert(epda, depth + 1, source, remainders));
        }

        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    void
    roll_out(
            epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack> step_label,
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> result,
            Set<epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>> remainders,
            boolean ignore_pop) {
        if (!ignore_pop && step_label.get_edge_pop().size() == 1) {
            result.add(step_label);
            return;
        }
        two_elements__abstract<state, tuple2<state, General_List<stack>>> q2;
        if (step_label.get_edge_src() instanceof two_elements__teA) {
            q2 = new two_elements__teB<>(new tuple2<>(
                    ((two_elements__teA<state, tuple2<state, General_List<stack>>>) step_label.get_edge_src()).get_value(),
                    new General_List<>(General_List.list_by_enumeration(step_label.get_edge_pop().get(0)))));
        } else {
            tuple2<state, General_List<stack>> elem =
                    ((two_elements__teB<state, tuple2<state, General_List<stack>>>) step_label.get_edge_src()).get_value();
            q2 = new two_elements__teB<>(new tuple2<>(
                    elem.get_value1(),
                    new General_List<>(General_List.append(elem.get_value2().get_value(), step_label.get_edge_pop().get(0)))));
        }
        result.add(new epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>(
                step_label.get_edge_src(),
                new option__None<>(),
                General_List.list_by_enumeration(step_label.get_edge_pop().get(0)),
                General_List.list_by_enumeration(),
                q2
        ));
        remainders.add(new epda_step_label<two_elements__abstract<state, tuple2<state, General_List<stack>>>, event, stack>(
                q2,
                step_label.get_edge_event(),
                General_List.drop(step_label.get_edge_pop(), 1),
                step_label.get_edge_push(),
                step_label.get_edge_trg()
        ));
    }

}
