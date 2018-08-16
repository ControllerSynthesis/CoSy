package operations.p02__DPDA_DFA_PRODUCT;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
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
public class Algorithm_Definition__DPDA_DFA_PRODUCT {
    private static Logger log = LogManager.getLogger(Algorithm_Definition__DPDA_DFA_PRODUCT.class.getName());

    private Algorithm_Definition__DPDA_DFA_PRODUCT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stackA extends General>
    Set<state>
    epda_state_silent_exists(
            epda<state, event, stackA> epda) {
        Set<state> result = new HashSet<>();
        for (epda_step_label<state, event, stackA> step_label : epda.get_epda_delta())
            if (step_label.get_edge_event() instanceof option__None)
                result.add(step_label.get_edge_src());
        return result;
    }

    public static <state extends General, event extends General, stackA extends General>
    Set<state>
    epda_state_nonsilent_exists(
            epda<state, event, stackA> epda) {
        Set<state> result = new HashSet<>();
        for (epda_step_label<state, event, stackA> step_label : epda.get_epda_delta())
            if (step_label.get_edge_event() instanceof option__Some)
                result.add(step_label.get_edge_src());
        return result;
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    epda<tuple2<stateA, stateB>, event, stackA>
    F_DPDA_DFA_PRODUCT(
            epda<stateA, event, stackA> C,
            epda<stateB, event, stackB> P) {
        Config.instance.measuring_thread.push("F_DPDA_DFA_PRODUCT");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DFA_PRODUCT: " + "C=" + C.log_info() + ", P=" + P.log_info());
        epda<tuple2<stateA, stateB>, event, stackA> result = new epda<>(
                F_DPDA_DFA_PRODUCT__states(C, P),
                F_DPDA_DFA_PRODUCT__events(C, P),
                C.get_epda_gamma(),
                F_DPDA_DFA_PRODUCT__edges(C, P),
                new tuple2<>(C.get_epda_initial(), P.get_epda_initial()),
                C.get_epda_box(),
                F_DPDA_DFA_PRODUCT__marking_states(C, P));
        Config.instance.check_invariants(result);
        Config.instance.measuring_thread.pop("F_DPDA_DFA_PRODUCT");
        return result;
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    List<tuple2<stateA, stateB>>
    F_DPDA_DFA_PRODUCT__states(
            epda<stateA, event, stackA> C,
            epda<stateB, event, stackB> P) {
        List<tuple2<stateA, stateB>> result = new ArrayList<>();
        for (stateA qA : C.get_epda_states())
            for (stateB qB : P.get_epda_states())
                result.add(new tuple2<>(qA, qB));
        return result;
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    List<tuple2<stateA, stateB>>
    F_DPDA_DFA_PRODUCT__marking_states(
            epda<stateA, event, stackA> C,
            epda<stateB, event, stackB> P) {
        List<tuple2<stateA, stateB>> result = new ArrayList<>();
        for (stateA qA : C.get_epda_marking())
            for (stateB qB : P.get_epda_marking())
                result.add(new tuple2<>(qA, qB));
        return result;
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

    /**
     * F_DPDA_DFA_PRODUCT__edges_empty
     * F_DPDA_DFA_PRODUCT__edges_execute
     * implemented implicitly
     */
    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    List<epda_step_label<tuple2<stateA, stateB>, event, stackA>>
    F_DPDA_DFA_PRODUCT__edges(
            epda<stateA, event, stackA> C,
            epda<stateB, event, stackB> P) {
        List<epda_step_label<tuple2<stateA, stateB>, event, stackA>> result = new ArrayList<>();
        for (epda_step_label<stateA, event, stackA> eC : C.get_epda_delta()) {
            if (eC.get_edge_event() instanceof option__None) {
                //F_DPDA_DFA_PRODUCT__edges_empty
                for (stateB p : P.get_epda_states())
                    result.add(new epda_step_label<>(
                            new tuple2<>(eC.get_edge_src(), p),
                            eC.get_edge_event(),
                            eC.get_edge_pop(),
                            eC.get_edge_push(),
                            new tuple2<>(eC.get_edge_trg(), p)));
            } else {
                //F_DPDA_DFA_PRODUCT__edges_execute
                for (epda_step_label<stateB, event, stackB> eP : P.get_epda_step_labels_by_event().getOrDefault(((option__Some<event>) eC.get_edge_event()).the(), new ArrayList<>()))
                    result.add(new epda_step_label<>(
                            new tuple2<>(eC.get_edge_src(), eP.get_edge_src()),
                            eC.get_edge_event(),
                            eC.get_edge_pop(),
                            eC.get_edge_push(),
                            new tuple2<>(eC.get_edge_trg(), eP.get_edge_trg())));

            }
        }
        return result;
    }


}
