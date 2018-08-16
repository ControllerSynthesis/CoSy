package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import supporting_operations.SOME_injective;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T02_01_EPDA_TYPE_CONVERSION {

    private static Logger log = LogManager.getLogger(T02_01_EPDA_TYPE_CONVERSION.class.getName());

    private T02_01_EPDA_TYPE_CONVERSION() {
        throw new IllegalStateException("Utility Class");
    }

    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    epda_step_label<symbol__abstract<stateB>, event, symbol__abstract<stackB>>
    F_EPDA_TC__edge(
            Map<stateA, symbol__abstract<stateB>> f_states,
            Map<stackA, symbol__abstract<stackB>> f_stack,
            epda_step_label<stateA, event, stackA> e) {
        return new epda_step_label(
                f_states.get(e.get_edge_src()),
                e.get_edge_event(),
                e.get_edge_pop().stream().map(x -> f_stack.get(x)).collect(Collectors.toList()),
                e.get_edge_push().stream().map(x -> f_stack.get(x)).collect(Collectors.toList()),
                f_states.get(e.get_edge_trg()));
    }


    private static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    epda<symbol__abstract<stateB>, event, symbol__abstract<stackB>>
    F_EPDA_TC__epda(
            epda<stateA, event, stackA> epda,
            Map<stateA, symbol__abstract<stateB>> f_states,
            Map<stackA, symbol__abstract<stackB>> f_stack) {
        return new epda(
                epda.get_epda_states().stream().map(e -> f_states.get(e)).collect(Collectors.toList()),
                epda.get_epda_events(),
                epda.get_epda_gamma().stream().map(e -> f_stack.get(e)).collect(Collectors.toList()),
                epda.get_epda_delta().stream().map(e -> F_EPDA_TC__edge(f_states, f_stack, e)).collect(Collectors.toList()),
                f_states.get(epda.get_epda_initial()),
                f_stack.get(epda.get_epda_box()),
                epda.get_epda_marking().stream().map(e -> f_states.get(e)).collect(Collectors.toList()));
    }

    public static <stateA extends General, stateB extends General, event extends General, stackA extends General, stackB extends General>
    epda<symbol__abstract<stateB>, event, symbol__abstract<stackB>>
    F_EPDA_TC(
            epda<stateA, event, stackA> epda) {
        Config.instance.measuring_thread.push("F_EPDA_TC");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_EPDA_TC: " + "epda=" + epda.log_info());

        epda<symbol__abstract<stateB>, event, symbol__abstract<stackB>> result = F_EPDA_TC__epda(epda,
                SOME_injective.SOME_injective(epda.get_epda_states()),
                SOME_injective.SOME_injective(epda.get_epda_gamma()));
        Config.instance.measuring_thread.pop("F_EPDA_TC");
        return result;
    }

}
