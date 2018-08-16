package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_08_SDPDA_TO_LR1_OPT {
    private static Logger log = LogManager.getLogger(T03_06_08_SDPDA_TO_LR1_OPT.class.getName());

    private T03_06_08_SDPDA_TO_LR1_OPT() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_LR1_OPT(
            epda<state, event, stack> epda,
            Map<state, Set<stack>> has_auxiliary_for,
            int k) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_LR1_OPT");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_LR1_OPT: " + "epda=" + epda.log_info());
        option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>> cfg_opt =
                T03_06_02_SDPDA_TO_CFG_OPT.F_SDPDA_TO_CFG_OPT(epda, k, has_auxiliary_for, null);
        option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>> result = null;
        if (cfg_opt instanceof option__None)
            result = new option__None<>();
        else
            result = T03_06_05_CFG_TRIM.F_CFG_TRIM(((option__Some<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>) cfg_opt).the());
        Config.instance.measuring_thread.pop("F_SDPDA_TO_LR1_OPT");
        return result;

    }

}
