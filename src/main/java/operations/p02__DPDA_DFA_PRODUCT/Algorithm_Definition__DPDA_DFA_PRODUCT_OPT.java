package operations.p02__DPDA_DFA_PRODUCT;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 * <br/>
 * we do not generate all states but only those that are accessible from the initial product state.
 */
public class Algorithm_Definition__DPDA_DFA_PRODUCT_OPT {
    private static Logger log = LogManager.getLogger(Algorithm_Definition__DPDA_DFA_PRODUCT_OPT.class.getName());

    private Algorithm_Definition__DPDA_DFA_PRODUCT_OPT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    epda<tuple2<stateA, stateB>, event, stackA>
    F_DPDA_DFA_PRODUCT_OPT(
            epda<stateA, event, stackA> C,
            epda<stateB, event, stackB> P) {
        Config.instance.measuring_thread.push("F_DPDA_DFA_PRODUCT_OPT");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DFA_PRODUCT_OPT: " + "C=" + C.log_info() + ", P=" + P.log_info());

        tuple2<stateA, stateB> initial_state = new tuple2<>(C.get_epda_initial(), P.get_epda_initial());
        List<tuple2<stateA, stateB>> todo = new ArrayList<>();
        todo.add(initial_state);
        Map<stateA, Map<stateB, tuple2<stateA, stateB>>> new_states = new HashMap<>();
        new_states.putIfAbsent(C.get_epda_initial(), new HashMap<>());
        new_states.get(C.get_epda_initial()).putIfAbsent(P.get_epda_initial(), initial_state);
        List<epda_step_label<tuple2<stateA, stateB>, event, stackA>> edges = new ArrayList<>();

        while (!todo.isEmpty()) {
            tuple2<stateA, stateB> state = todo.remove(0);
            for (epda_step_label<stateA, event, stackA> eC : C.get_map_source(state.get_value1())) {
                if (eC.get_edge_event() instanceof option__None) {
                    //F_DPDA_DFA_PRODUCT__edges_empty
                    edges.add(new epda_step_label<>(
                            state,
                            eC.get_edge_event(),
                            eC.get_edge_pop(),
                            eC.get_edge_push(),
                            get_or_add_state(todo, new_states, eC.get_edge_trg(), state.get_value2())));
                } else {
                    //F_DPDA_DFA_PRODUCT__edges_execute
                    for (epda_step_label<stateB, event, stackB> eP : P.get_map_source_execute(state.get_value2(), eC.get_edge_event()))
                        edges.add(new epda_step_label<>(
                                state,
                                eC.get_edge_event(),
                                eC.get_edge_pop(),
                                eC.get_edge_push(),
                                get_or_add_state(todo, new_states, eC.get_edge_trg(), eP.get_edge_trg())));
                }
            }
        }

        List<tuple2<stateA, stateB>> states = new ArrayList<>();
        for (Map<stateB, tuple2<stateA, stateB>> value1 : new_states.values())
            states.addAll(value1.values());
        List<tuple2<stateA, stateB>> marking_states = new ArrayList<>();
        for (tuple2<stateA, stateB> state : states)
            // could use sets here
            if (C.get_epda_marking().contains(state.get_value1()) && P.get_epda_marking().contains(state.get_value2()))
                marking_states.add(state);

        epda<tuple2<stateA, stateB>, event, stackA> result = new epda<>(
                states,
                F_DPDA_DFA_PRODUCT__events(C, P),
                C.get_epda_gamma(),
                edges,
                initial_state,
                C.get_epda_box(),
                marking_states);
        Config.instance.check_invariants(result);
        Config.instance.measuring_thread.pop("F_DPDA_DFA_PRODUCT_OPT");
        return result;
    }

    private static <stateA extends General, stateB extends General>
    tuple2<stateA, stateB>
    get_or_add_state(
            List<tuple2<stateA, stateB>> todo,
            Map<stateA, Map<stateB, tuple2<stateA, stateB>>> new_states,
            stateA q1,
            stateB q2) {
        if (!new_states.containsKey(q1) || !new_states.get(q1).containsKey(q2)) {
            new_states.putIfAbsent(q1, new HashMap<>());
            new_states.get(q1).putIfAbsent(q2, new tuple2<>(q1, q2));
            todo.add(new_states.get(q1).get(q2));
        }
        return new_states.get(q1).get(q2);
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    List<event>
    F_DPDA_DFA_PRODUCT__events(
            epda<stateA, event, stackA> C,
            epda<stateB, event, stackB> P) {
        List<event> result = new ArrayList<>();
        result.addAll(C.get_epda_events());
        result.retainAll(P.get_epda_events());
        return result;
    }


}
