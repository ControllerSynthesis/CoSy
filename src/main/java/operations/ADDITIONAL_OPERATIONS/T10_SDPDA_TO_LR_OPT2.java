package operations.ADDITIONAL_OPERATIONS;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2.T09_SDPDA_TO_CFG_OPT2;
import operations.p01__ENFORCE_BLOCKFREE.T03_06_04_CFG_ENFORCE_ACCESSIBILITY;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T10_SDPDA_TO_LR_OPT2 {

    private static Logger log = LogManager.getLogger(T10_SDPDA_TO_LR_OPT2.class.getName());

    private T10_SDPDA_TO_LR_OPT2() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_LR_OPT2(
            epda<state, event, stack> epda,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_LR_OPT2");
        if (log.isInfoEnabled())
            log.info(Config.callStack() + "entering F_SDPDA_TO_LR_OPT2: " + "epda=" + epda.log_info());

        option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
                result = T09_SDPDA_TO_CFG_OPT2.F_SDPDA_TO_CFG_OPT2(epda, reverse);

        if (result instanceof option__Some) {
            result = new option__Some<>(
                    T03_06_04_CFG_ENFORCE_ACCESSIBILITY.F_CFG_EASTD(
                            ((option__Some<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>) result).the()));

            //check_dup(((option__Some<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>) result).the());
        }

        Config.instance.measuring_thread.pop("F_SDPDA_TO_LR_OPT2");
        return result;
    }

}
