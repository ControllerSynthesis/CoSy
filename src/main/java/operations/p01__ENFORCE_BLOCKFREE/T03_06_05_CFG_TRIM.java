package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_05_CFG_TRIM {
    private static Logger log = LogManager.getLogger(T03_06_05_CFG_TRIM.class.getName());

    private T03_06_05_CFG_TRIM() {
        throw new IllegalStateException("Utility Class");
    }

    public static <nonterminal extends General, event extends General>
    option__abstract<cfg<nonterminal, event>>
    F_CFG_TRIM(
            cfg<nonterminal, event> cfg) {
        Config.instance.measuring_thread.push("F_CFG_TRIM");

        option__abstract<cfg<nonterminal, event>> result1 = T03_06_03_CFG_ENFORCE_BLOCKFREE.F_CFG_EB(cfg);

        option__abstract<cfg<nonterminal, event>> result = null;
        if (result1 instanceof option__None)
            result = result1;
        else
            result = new option__Some<>(
                    T03_06_04_CFG_ENFORCE_ACCESSIBILITY.F_CFG_EASTD(
                            ((option__Some<cfg<nonterminal, event>>) result1).the()));

        Config.instance.measuring_thread.pop("F_CFG_TRIM");
        return result;
    }


}