package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__Some;
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

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_01_EPDA_GOTO {
    private static Logger log = LogManager.getLogger(T05_01_EPDA_GOTO.class.getName());

    private T05_01_EPDA_GOTO() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    Set<state>
    F_EPDA_GOTO(
            epda<state, event, stack> epda,
            state q,
            event X) {
        Config.instance.measuring_thread.push("F_EPDA_GOTO");
        Set<state> result = new HashSet<>();
        for (epda_step_label<state, event, stack> e : epda.get_map_source_execute(q, new option__Some<>(X))) {
            if (e.get_edge_pop().size() != 1
                    || e.get_edge_push().size() != 1
                    || !e.get_edge_pop().get(0).equals(epda.get_epda_box())
                    || !e.get_edge_push().get(0).equals(epda.get_epda_box()))
                continue;
            result.add(e.get_edge_trg());
        }
        Config.instance.measuring_thread.pop("F_EPDA_GOTO");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    Set<List<state>>
    F_EPDA_GOTO_SEQ(
            epda<state, event, stack> epda,
            state q,
            List<event> events) {
        Config.instance.measuring_thread.push("F_EPDA_GOTO_SEQ");
        Set<List<state>> result = new HashSet<>();
        if (events.isEmpty()) {
            result.add(new ArrayList<>());
        } else {
            event X = events.get(0);
            List<event> w = General_List.drop(events, 1);
            for (state p : F_EPDA_GOTO(epda, q, X))
                for (List<state> p_seq : F_EPDA_GOTO_SEQ(epda, p, w))
                    result.add(General_List.cons(p, p_seq));
        }
        Config.instance.measuring_thread.pop("F_EPDA_GOTO_SEQ");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    state
    F_DFA_GOTO(
            epda<state, event, stack> epda,
            state q,
            event X) {
        Config.instance.measuring_thread.push("F_DFA_GOTO");
        state result = F_EPDA_GOTO(epda, q, X).iterator().next();
        Config.instance.measuring_thread.pop("F_DFA_GOTO");
        return result;
    }

    public static <state extends General, event extends General, stack extends General>
    List<state>
    F_DFA_GOTO_SEQ(
            epda<state, event, stack> epda,
            state q,
            List<event> events) {
        Config.instance.measuring_thread.push("F_DFA_GOTO_SEQ");
        List<state> result = F_EPDA_GOTO_SEQ(epda, q, events).iterator().next();
        Config.instance.measuring_thread.pop("F_DFA_GOTO_SEQ");
        return result;
    }

}