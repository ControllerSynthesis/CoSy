package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.state_or_edge__abstract;
import datatypes.basic_datatypes_of_algorithm.state_or_edge_nat__abstract;
import datatypes.basic_datatypes_of_algorithm.symbol__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_04_05_DPDA_TO_SDPDA {

    private static Logger log = LogManager.getLogger(T03_04_05_DPDA_TO_SDPDA.class.getName());

    private T03_04_05_DPDA_TO_SDPDA() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state_or_edge_nat__abstract<
            state_or_edge__abstract<
                    state_or_edge__abstract<
                            state_or_edge__abstract<
                                    state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                    event, symbol__abstract<stack>>,
            event, symbol__abstract<stack>>,
            event, symbol__abstract<stack>>
    F_DPDA_TO_SDPDA(
            epda<state, event, symbol__abstract<stack>> epda) {
        Config.instance.measuring_thread.push("F_DPDA_TO_SDPDA");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_TO_SDPDA: " + "epda=" + epda.log_info());
        epda<state_or_edge__abstract<state, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G2 =
                T03_04_01_DPDA_SEPERATE_EXECUTING_EDGES.F_DPDA_SEE(epda, null);
        epda<state_or_edge__abstract<
                state_or_edge__abstract<
                        state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G3 =
                T03_04_02_DPDA_REMOVE_NEUTRAL_EDGES.F_DPDA_RNE(G2, T01_FRESH.F_FRESH(G2.get_epda_gamma()), null);
        epda<state_or_edge__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state, event, symbol__abstract<stack>>, event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G4 =
                T03_04_03_DPDA_SEPERATE_PUSH_POP_EDGES.F_DPDA_SPPE(G3, null);
        epda<state_or_edge_nat__abstract<
                state_or_edge__abstract<
                        state_or_edge__abstract<
                                state_or_edge__abstract<
                                        state, event, symbol__abstract<stack>>,
                                event, symbol__abstract<stack>>,
                        event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>>,
                event, symbol__abstract<stack>> G5 =
                T03_04_04_DPDA_REMOVE_MASS_PUSHING_EDGES.F_DPDA_RMPUE(G4, null);
        Config.instance.measuring_thread.pop("F_DPDA_TO_SDPDA");
        return G5;
    }

}
