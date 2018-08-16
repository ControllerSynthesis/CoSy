package operations.p04__CONSTRUCT_CONTROLLER;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Boolean;
import datatypes.formalisms.epda;
import main.Config;
import operations.p01__ENFORCE_BLOCKFREE.Algorithm_Definition__Enforce_Blockfree;
import operations.p01__ENFORCE_BLOCKFREE.T02_01_EPDA_TYPE_CONVERSION;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT;
import operations.p02__DPDA_DFA_PRODUCT.Algorithm_Definition__DPDA_DFA_PRODUCT_OPT;
import operations.p03__ENFORCE_CONTROLLABLE.Algorithm_Definition__Enforce_Controllable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class Algorithm_Definition__Construct_Controller {
    private static Logger log = LogManager.getLogger(Algorithm_Definition__Construct_Controller.class.getName());

    private Algorithm_Definition__Construct_Controller() {
        throw new IllegalStateException("Utility Class");
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    tuple2<option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>, General_Boolean>
    F_DPDA_DFA_CC__fp_one(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> C,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_DPDA_DFA_CC__fp_one");
        Config.instance.check_invariants(C);
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DFA_CC__fp_one: " + "C=" + C.log_info() + ", P=" + P.log_info());
        tuple2<option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>, General_Boolean>
                result = Algorithm_Definition__Enforce_Controllable.F_DPDA_EC(C, P, SigmaUC);
        if (result.get_value1() instanceof option__None)
            result = new tuple2<>(new option__None<>(), new General_Boolean(false));
        else {
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>
                    Cp = ((option__Some<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>)
                    result.get_value1()).the();
            Config.instance.check_invariants(Cp);
            if (result.get_value2().get_value()) {
                result = new tuple2<>(new option__Some<>(Cp), new General_Boolean(false));
            } else {
                option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>
                        resultX = Algorithm_Definition__Enforce_Blockfree.F_DPDA_EB_OPT(Cp);
                result = new tuple2<>(resultX, new General_Boolean(resultX instanceof option__Some));
            }
        }
        Config.instance.measuring_thread.pop("F_DPDA_DFA_CC__fp_one");
        return result;
    }

    public static <alpha extends General>
    option__abstract<alpha>
    FB_iterate(
            Function<alpha, tuple2<option__abstract<alpha>, General_Boolean>> F,
            alpha A) {
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering FB_iterate");
        tuple2<option__abstract<alpha>, General_Boolean>
                result = F.apply(A);
        if (result.get_value1() instanceof option__None)
            return new option__None<>();
        else {
            alpha Ap = ((option__Some<alpha>) result.get_value1()).the();
            if (result.get_value2().get_value()) {
                return FB_iterate(F, Ap);
            } else {
                return new option__Some<>(Ap);
            }
        }
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>
    F_DPDA_DFA_CC__fp(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> C,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_DPDA_DFA_CC__fp");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DFA_CC__fp: " + "C=" + C.log_info() + ", P=" + P.log_info());
        option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>> result = FB_iterate(X -> F_DPDA_DFA_CC__fp_one(X, P, SigmaUC), C);
        Config.instance.measuring_thread.pop("F_DPDA_DFA_CC__fp");
        return result;
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>
    F_DPDA_DFA_CC__fp_start(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> S,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P) {
        Config.instance.measuring_thread.push("F_DPDA_DFA_CC__fp_start");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DFA_CC__fp_start: " + "S=" + S.log_info() + ", P=" + P.log_info());
        epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>
                product =
                T02_01_EPDA_TYPE_CONVERSION.F_EPDA_TC(Config.USE__Algorithm_Definition__DPDA_DFA_PRODUCT_OPT ? Algorithm_Definition__DPDA_DFA_PRODUCT_OPT.F_DPDA_DFA_PRODUCT_OPT(S, P) : Algorithm_Definition__DPDA_DFA_PRODUCT.F_DPDA_DFA_PRODUCT(S, P));
        Config.instance.check_invariants(product);
        option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>
                result = Algorithm_Definition__Enforce_Blockfree.F_DPDA_EB_OPT(product);
        Config.instance.measuring_thread.pop("F_DPDA_DFA_CC__fp_start");
        return result;
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>
    F_DPDA_DFA_CC(
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>> S,
            epda<symbol__abstract<stateB>, symbol__abstract<event>, symbol__abstract<stackB>> P,
            List<symbol__abstract<event>> SigmaUC) {
        Config.instance.measuring_thread.push("F_DPDA_DFA_CC");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_DFA_CC: " + "S=" + S.log_info() + ", P=" + P.log_info());
        Config.instance.check_invariants(S);
        option__abstract<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>> result
                = F_DPDA_DFA_CC__fp_start(S, P);
        if (result instanceof option__Some) {
            epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>
                    Cp = ((option__Some<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>) result).the();
            Config.instance.check_invariants(Cp);
            result = F_DPDA_DFA_CC__fp(Cp, P, SigmaUC);
        }

        if (result instanceof option__Some) {
            System.out.println("F_DPDA_DFA_CC result: " + ((option__Some<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>) result).the().log_info());
            log.info("F_DPDA_DFA_CC result: " + ((option__Some<epda<symbol__abstract<stateA>, symbol__abstract<event>, symbol__abstract<stackA>>>) result).the().log_info());
        } else {
            System.out.println("F_DPDA_DFA_CC result: none");
            log.info("F_DPDA_DFA_CC result: none");
        }
        Config.instance.measuring_thread.pop("F_DPDA_DFA_CC");
        return result;
    }


}
