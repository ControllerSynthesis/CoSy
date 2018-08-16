package operations.p03__ENFORCE_CONTROLLABLE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Boolean;
import datatypes.formalisms.epda;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.T03_01_EPDA_RESTRICT;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T07_DPDA_RESTRICT_TO_CONTROLLABLE_STATES {
    private static Logger log = LogManager.getLogger(T07_DPDA_RESTRICT_TO_CONTROLLABLE_STATES.class.getName());

    private T07_DPDA_RESTRICT_TO_CONTROLLABLE_STATES() {
        throw new IllegalStateException("Utility Class");
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    tuple2<option__abstract<epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>, event, stackA>>, General_Boolean>
    F_DPDA_RCS(
            epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>, event, stackA> M,
            epda<stateB, event, stackB> P,
            List<event> SigmaUC) {
        Config.instance.measuring_thread.push("F_DPDA_RCS");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_RCS: " + "M=" + M.log_info());
        Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>> Q = F_DPDA_NCS(M, P, SigmaUC);

        Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>> states = new HashSet<>(M.get_epda_states());
        states.removeAll(Q);
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_RCS: restricting to=" + states.size());
        tuple2<option__abstract<epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>, event, stackA>>, General_Boolean> result = new tuple2<>(
                T03_01_EPDA_RESTRICT.F_EPDA_RS(M, states),
                new General_Boolean(Q.isEmpty()));
        Config.instance.measuring_thread.pop("F_DPDA_RCS");
        return result;
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>>
    F_DPDA_NCS(
            epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>, event, stackA> S,
            epda<stateB, event, stackB> P,
            List<event> SigmaUC) {
        Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>>
                result = new HashSet<>();
        Set<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>>
                nonstable_S = Algorithm_Definition__DPDA_DFA_PRODUCT.epda_state_silent_exists(S);
        for (state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>
                q : S.get_epda_states()) {
            if (nonstable_S.contains(q))
                continue;
            stateB pP = null;
            stackA X = null;
            if (q instanceof state_or_state__cons_state_or_state_new) {
                state_or_state__cons_state_or_state_new<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>
                        q2 = (state_or_state__cons_state_or_state_new<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>) q;
                if (q2.get_value() instanceof state_and_stack_or_state__cons_state_and_stack) {
                    state_and_stack_or_state__cons_state_and_stack<tuple2<stateA, stateB>, stackA>
                            q3 = (state_and_stack_or_state__cons_state_and_stack<tuple2<stateA, stateB>, stackA>) q2.get_value();
                    X = q3.get_value2();
                    pP = q3.get_value1().get_value2();
                } else continue;
            } else {
                state_or_state__cons_state_or_state_old<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>> q2
                        = (state_or_state__cons_state_or_state_old<state_and_stack_or_state__abstract<tuple2<stateA, stateB>, stackA>>) q;
                if (q2.get_value() instanceof state_and_stack_or_state__cons_state_and_stack) {
                    state_and_stack_or_state__cons_state_and_stack<tuple2<stateA, stateB>, stackA> q3
                            = (state_and_stack_or_state__cons_state_and_stack<tuple2<stateA, stateB>, stackA>) q2.get_value();
                    X = q3.get_value2();
                    pP = q3.get_value1().get_value2();
                } else continue;
            }

            for (event u : SigmaUC) {
                option__abstract<event> event = new option__Some<>(u);
                if (!P.get_map_source_execute(pP, event).isEmpty()) {
                    if (!S.get_map_source_execute_pop(q, event, X).isEmpty())
                        continue;
                    result.add(q);
                    if (log.isInfoEnabled())
                        log.info("event: " + u + ", pP=" + pP + ", q=" + q + ", X=" + X);
//                    try {
//                        S.dot_export("upon_ncs.dot");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (log.isInfoEnabled())
//                        S.info_on_reachability(Collections.singleton(q));
                    break;
                }
            }
        }

        if (log.isInfoEnabled() && !result.isEmpty() && Config.T07_DPDA_RESTRICT_TO_CONTROLLABLE_STATES__info_on_reachability)
            S.info_on_reachability(result);

        if (log.isInfoEnabled())
            log.info("F_DPDA_NCS : " + result.size());
        if (false) // TODO: REMOVE
            if (result.size() == 27 || result.size() == 30)
                System.exit(1);
        return result;
    }

}
