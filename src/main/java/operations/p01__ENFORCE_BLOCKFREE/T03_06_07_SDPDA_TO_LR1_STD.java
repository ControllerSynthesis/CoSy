package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__abstract;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.epda;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_07_SDPDA_TO_LR1_STD {
    private static Logger log = LogManager.getLogger(T03_06_07_SDPDA_TO_LR1_STD.class.getName());

    private T03_06_07_SDPDA_TO_LR1_STD() {
        throw new IllegalStateException("Utility Class");
    }

    public static <state extends General, event extends General, stack extends General>
    option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_LR1_STD(
            epda<state, event, stack> epda) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_LR1_STD");
        option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>> result = T03_06_05_CFG_TRIM.F_CFG_TRIM(T03_06_01_SDPDA_TO_CFG_STD.F_SDPDA_TO_CFG_STD(epda, null));
        Config.instance.measuring_thread.pop("F_SDPDA_TO_LR1_STD");
        return result;
    }

}