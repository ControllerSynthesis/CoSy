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
public class T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT {
    private static Logger log = LogManager.getLogger(T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT.class.getName());

    private T07_04_DPDA_ENFORCE_ACCESSIBLE_OPT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    epda<state, event, symbol__abstract<stack>>
    F_DPDA_EA_OPT(
            epda<state, event, symbol__abstract<stack>> epda,
            boolean is_blockfree) {
        Config.instance.measuring_thread.push("F_DPDA_EA_OPT");

        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_DPDA_EA_OPT: " + "epda=" + epda.log_info());
        epda<state, event, symbol__abstract<stack>> result = T03_01_EPDA_RESTRICT.F_EPDA_RE(epda, T07_03_DPDA_DETERMINE_REQUIRED_EDGES.F_DPDA_DRE(epda, is_blockfree, Config.instance.F_DPDA_DRE__k, Config.instance.F_DPDA_DRE__use_recursive_lr1_cfg_construction));

        Config.instance.measuring_thread.pop("F_DPDA_EA_OPT");
        return result;
    }

}
