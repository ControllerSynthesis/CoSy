package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T02_REMOVE_UNUSED_STATE_STACK_EPDA {

    private static Logger log = LogManager.getLogger(T02_REMOVE_UNUSED_STATE_STACK_EPDA.class.getName());

    private T02_REMOVE_UNUSED_STATE_STACK_EPDA() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    REMOVE_UNUSED_STATE_STACK_EPDA(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("REMOVE_UNUSED_STATE_STACK_EPDA");
        Set<stack> used_stack = new HashSet<>();
        for (epda_step_label<state, event, stack> step_label : epda.get_epda_delta()) {
            used_stack.addAll(step_label.get_edge_push());
            used_stack.addAll(step_label.get_edge_pop());
        }
        used_stack.add(epda.get_epda_box());

        Set<state> used_states = new HashSet<>();
        for (epda_step_label<state, event, stack> step_label : epda.get_epda_delta()) {
            used_states.add(step_label.get_edge_src());
            used_states.add(step_label.get_edge_trg());
        }
        used_states.add(epda.get_epda_initial());

        Set<state> used_marking_states = new HashSet<>(epda.get_epda_marking());
        used_marking_states.retainAll(used_states);

        epda<state, event, stack> result = new epda<>(
                new ArrayList<>(used_states),
                epda.get_epda_events(),
                new ArrayList<>(used_stack),
                epda.get_epda_delta(),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                new ArrayList<>(used_marking_states));
        Config.instance.measuring_thread.pop("REMOVE_UNUSED_STATE_STACK_EPDA");
        return result;

    }


}
