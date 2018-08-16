package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.*;
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
public class T07_UNDO_OTS {

    private static Logger log = LogManager.getLogger(T07_UNDO_OTS.class.getName());

    private T07_UNDO_OTS() {
        throw new IllegalStateException("Utility Class");
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
    UNDO_OTS(
            epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>> epda) {
        Config.instance.measuring_thread.push("UNDO_OTS");
        if (log.isInfoEnabled())
            log.info("T07_UNDO_OTS: " + epda.log_info());

        Config.instance.check_invariants(epda);


        Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>> main_states = new HashSet<>();
        for (state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>
                q : epda.get_epda_states()) {
            state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>> inner;
            if (q instanceof state_or_state__cons_state_or_state_new)
                inner = ((state_or_state__cons_state_or_state_new<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>) q).get_value();
            else if (q instanceof state_or_state__cons_state_or_state_old)
                inner = ((state_or_state__cons_state_or_state_old<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>) q).get_value();
            else
                throw new RuntimeException();
            if (inner instanceof state_and_stack_or_state__cons_state_and_stack)
                continue;
            else if (inner instanceof state_and_stack_or_state__cons_state)
                main_states.add(q);
            else
                throw new RuntimeException();
        }

        if (log.isInfoEnabled())
            log.info("main_states computed");

        Set<epda_step_label<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>>
                resulting_edges = new HashSet<>(epda.get_epda_delta());
        Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>>
                resulting_states = new HashSet<>(epda.get_epda_states());

        for (state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>
                q : main_states) {
            for (epda_step_label<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                    step_label : epda.get_map_source(q)) {
                if (epda.get_epda_marking().contains(step_label.get_edge_trg()))
                    continue;
                if (main_states.contains(step_label.get_edge_trg()))
                    continue;
                resulting_edges.remove(step_label);
                resulting_states.remove(step_label.get_edge_trg());
                for (epda_step_label<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                        step_label2 : epda.get_map_source(step_label.get_edge_trg())) {
                    resulting_edges.remove(step_label2);
                    resulting_edges.add(new epda_step_label<>(
                            step_label.get_edge_src(),
                            step_label2.get_edge_event(),
                            step_label2.get_edge_pop(),
                            step_label2.get_edge_push(),
                            step_label2.get_edge_trg()
                    ));
                }
            }
        }
        epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                result = new epda<>(
                new ArrayList<>(resulting_states),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(resulting_edges),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking());

        Config.instance.check_invariants(result);
        if (log.isInfoEnabled())
            log.info("T07_UNDO_OTS end: " + result.log_info());
        Config.instance.measuring_thread.pop("UNDO_OTS");

        return result;


    }


}
