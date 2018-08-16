package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
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
public class T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES {
    private static Logger log = LogManager.getLogger(T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES.class.getName());

    private T06_02_01_EDPDA_REMOVE_NIL_POPPING_EDGES() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, stack>
    F_EDPDA_RNPE(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("F_EDPDA_RNPE");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EDPDA_RNPE: " + "epda=" + epda.log_info());


        Set<epda_step_label<state, event, stack>> step_labels = new HashSet<>();
        for (epda_step_label<state, event, stack> e : epda.get_epda_delta())
            if (e.get_edge_pop().isEmpty())
                step_labels.addAll(F_EDPDA_RNPE__edges(e,
                        Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE() >= 0 ?
                                new ArrayList<>(T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_EDPDA_RNPE(), e.get_edge_src()))
                                : epda.get_epda_gamma()));
            else
                step_labels.add(e);


        epda<state, event, stack> result = new epda<>(
                epda.get_epda_states(),
                epda.get_epda_events(),
                epda.get_epda_gamma(),
                new ArrayList<>(step_labels),
                epda.get_epda_initial(),
                epda.get_epda_box(),
                epda.get_epda_marking()
        );
        Config.instance.measuring_thread.pop("F_EDPDA_RNPE");
        return result;
    }

    private static <state extends General, event extends General, stack extends General>
    Set<epda_step_label<state, event, stack>>
    F_EDPDA_RNPE__edges(
            epda_step_label<state, event, stack> e,
            List<stack> gamma) {
        Set<epda_step_label<state, event, stack>> result = new HashSet<>();
        for (stack x : gamma)
            result.add(new epda_step_label<>(
                    e.get_edge_src(),
                    e.get_edge_event(),
                    General_List.list_by_enumeration(x),
                    General_List.append(e.get_edge_push(), x),
                    e.get_edge_trg()));
        return result;
    }

}
