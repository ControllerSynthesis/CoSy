package operations.p03__ENFORCE_CONTROLLABLE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Boolean;
import datatypes.formalisms.epda;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T02_REMOVE_UNUSED_STATE_STACK_EPDA;
import operations.ADDITIONAL_OPERATIONS.T03_COLLATE_EPDA;
import operations.ADDITIONAL_OPERATIONS.T07_UNDO_OTS;
import operations.p01__ENFORCE_BLOCKFREE.T02_01_EPDA_TYPE_CONVERSION;
import operations.p01__ENFORCE_BLOCKFREE.T03_03_EPDA_APPROXIMATE_ACCESSIBLE;
import operations.p01__ENFORCE_BLOCKFREE.T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT_OPT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Algorithm_Definition__Enforce_Controllable {
    private static Logger log = LogManager.getLogger(Algorithm_Definition__Enforce_Controllable.class.getName());

    private Algorithm_Definition__Enforce_Controllable() {
        throw new IllegalStateException("Utility Class");
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    tuple2<option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>, General_Boolean>
    F_DPDA_EC(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> G0,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_DPDA_EC");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_EC: " + "G0=" + G0.log_info() + ", P=" + P.log_info());

        epda<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<event>, symbol__abstract<stackA>>
                M0 = Config.USE__Algorithm_Definition__DPDA_DFA_PRODUCT_OPT ? Algorithm_Definition__DPDA_DFA_PRODUCT_OPT.F_DPDA_DFA_PRODUCT_OPT(G0, P) : Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(G0, P);

        if (Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT() >= 0) {
            int size = M0.get_epda_delta().size() + M0.get_epda_states().size();
            M0 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(M0, Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT());
            if (log.isInfoEnabled()) {
                if (M0.get_epda_delta().size() + M0.get_epda_states().size() >= size)
                    log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in Algorithm_Definition__Enforce_Controllable: getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT should be zero");
            }
        }

        if (Config.instance.REMOVE_UNUSED_STATE_STACK_EPDA__in_F_DPDA_EC__after_F_DPDA_DFA_PRODUCT)
            M0 = T02_REMOVE_UNUSED_STATE_STACK_EPDA.REMOVE_UNUSED_STATE_STACK_EPDA(M0);

        epda<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>, symbol__abstract<event>, symbol__abstract<stackA>>
                M1 =
                Config.T03_DPDA_OBSERVE_TOP_STACK_OPT ?
                        T03_DPDA_OBSERVE_TOP_STACK_OPT.F_DPDA_OTS_OPT(M0) :
                        T03_DPDA_OBSERVE_TOP_STACK.F_DPDA_OTS(M0);


        if (Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS() >= 0) {
            int size = M1.get_epda_delta().size() + M1.get_epda_states().size();
            M1 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(M1, Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS());
            if (log.isInfoEnabled()) {
                if (M1.get_epda_delta().size() + M1.get_epda_states().size() >= size)
                    log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in Algorithm_Definition__Enforce_Controllable: getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_OTS should be zero");
            }
        }

        epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                M2 = T04_DPDA_ENFORCE_UNIQUE_MARKING_LATE.F_DPDA_EUML(M1);

        if (Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML() >= 0) {
            int size = M2.get_epda_delta().size() + M2.get_epda_states().size();
            M2 = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(M2, Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML());
            if (log.isInfoEnabled()) {
                if (M2.get_epda_delta().size() + M2.get_epda_states().size() >= size)
                    log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in Algorithm_Definition__Enforce_Controllable: getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_EUML should be zero");
            }
        }

        epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                M3 = Config.instance.DISABLE_ENFORCE_ACCESSIBLE_IN_ENFORCE_CONTROLLABLE ? M2 :
                T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.F_DPDA_EA_OPT(M2, Config.instance.F_DPDA_EC__assume_nonblocking_after_F_DPDA_OTS_and_F_DPDA_EUML);


        if (log.isInfoEnabled())
            log.info("T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT at controllable: pre=" + M2.log_info() + ", post=" + M3.log_info());

        tuple2<option__abstract<epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>>, General_Boolean>
                result = T07_DPDA_RESTRICT_TO_CONTROLLABLE_STATES.F_DPDA_RCS(M3, P, SigmaUC);


        tuple2<option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>, General_Boolean> final_result = null;
        if (result.get_value1() instanceof option__None)
            final_result = new tuple2<>(new option__None<>(), result.get_value2());
        else {
            if (!result.get_value2().get_value()) {
                epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>
                        M = ((option__Some<epda<state_or_state__abstract<state_and_stack_or_state__abstract<tuple2<symbol__abstract<stateA>, symbol__abstract<stateB>>, symbol__abstract<stackA>>>, symbol__abstract<event>, symbol__abstract<stackA>>>)
                        result.get_value1()).the();

                Config.instance.check_invariants(M);
                if (Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS() >= 0) {
                    int size = M.get_epda_delta().size() + M.get_epda_states().size();
                    M = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(M, Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS());
                    if (log.isInfoEnabled()) {
                        if (M.get_epda_delta().size() + M.get_epda_states().size() >= size)
                            log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in Algorithm_Definition__Enforce_Controllable: getF_EPDA_AA__in_F_DPDA_EC__after_F_DPDA_RCS should be zero");
                    }
                }
                Config.instance.check_invariants(M);

                if (Config.T07_UNDO_OTS__APPLY_AFTER__T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT)
                    M = T07_UNDO_OTS.UNDO_OTS(M);

                Config.instance.check_invariants(M);
                if (Config.T03_COLLATE_EPDA__APPLY_AFTER__T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT)
                    M = T03_COLLATE_EPDA.COLLATE_EPDA(M);

                Config.instance.check_invariants(M);
                if (Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS() >= 0) {
                    int size = M.get_epda_delta().size() + M.get_epda_states().size();
                    M = T03_03_EPDA_APPROXIMATE_ACCESSIBLE.F_EPDA_AA(M, Config.instance.getF_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS());
                    if (log.isInfoEnabled()) {
                        if (M.get_epda_delta().size() + M.get_epda_states().size() >= size)
                            log.info("T03_03_EPDA_APPROXIMATE_ACCESSIBLE: useless in Algorithm_Definition__Enforce_Controllable: getF_EPDA_AA__in_F_DPDA_EC__after_UNDO_OTS should be zero");
                    }
                }
                Config.instance.check_invariants(M);
                epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>
                        Mrenamed = T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(M);
                final_result = new tuple2<>(new option__Some<>(Mrenamed), new General_Boolean(false));
                if (Config.Algorithm_Definition__Enforce_Controllable__OUTPUT)
                    Config.instance.output(Mrenamed);
            } else {
                final_result = new tuple2<>(new option__Some<>(G0), new General_Boolean(true));
                if (Config.Algorithm_Definition__Enforce_Controllable__OUTPUT)
                    Config.instance.output(G0);
            }
        }
        Config.instance.measuring_thread.pop("F_DPDA_EC");
        return final_result;

    }

}
