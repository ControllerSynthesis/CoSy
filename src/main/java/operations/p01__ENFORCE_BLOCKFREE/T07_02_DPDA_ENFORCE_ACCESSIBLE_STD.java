package operations.p01__ENFORCE_BLOCKFREE;

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
public class T07_02_DPDA_ENFORCE_ACCESSIBLE_STD {
    private static Logger log = LogManager.getLogger(T07_02_DPDA_ENFORCE_ACCESSIBLE_STD.class.getName());

    private T07_02_DPDA_ENFORCE_ACCESSIBLE_STD() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, symbol__abstract<stack>>
    F_DPDA_EA_STD(
            epda<state, event, symbol__abstract<stack>> epda) {
        Config.instance.measuring_thread.push("F_DPDA_EA_STD");
        epda<state, event, symbol__abstract<stack>> result = T03_01_EPDA_RESTRICT.F_EPDA_RE(epda, T07_01_DPDA_DETERMINE_ACCEESSIBLE_EDGES.F_DPDA_DAE(epda));
        Config.instance.measuring_thread.pop("F_DPDA_EA_STD");
        return result;
    }

}