package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Map;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_04_CFG_ENFORCE_ACCESSIBILITY {
    private static Logger log = LogManager.getLogger(T03_06_04_CFG_ENFORCE_ACCESSIBILITY.class.getName());

    private T03_06_04_CFG_ENFORCE_ACCESSIBILITY() {
        throw new IllegalStateException("Utility Class");
    }

    /*
    * F_CFG_EASTD__fp
    * F_CFG_EASTD__fp_one
    * implicitly implemented
    * */
    public static <nonterminal extends General, event extends General>
    cfg<nonterminal, event>
    F_CFG_EASTD(
            cfg<nonterminal, event> cfg) {
        Config.instance.measuring_thread.push("F_CFG_EASTD");

        Set<nonterminal> result_nonterminals = new HashSet<>();
        List<cfg_step_label<nonterminal, event>> result_productions = new ArrayList<>();

        List<nonterminal> todo = new ArrayList<>();
        todo.add(cfg.get_cfg_initial());
        result_nonterminals.add(cfg.get_cfg_initial());

        while (!todo.isEmpty()) {
            nonterminal N = todo.remove(0);
            for (cfg_step_label<nonterminal, event> e : General_Map.get_default(cfg.get_cfg_step_labels_by_lhs(), N, new ArrayList<>())) {
                result_productions.add(e);
                for (two_elements__abstract<nonterminal, event> elem : e.get_prod_rhs())
                    if (elem instanceof two_elements__teA) {
                        nonterminal X = ((two_elements__teA<nonterminal, event>) elem).get_value();
                        if (!result_nonterminals.contains(X)) {
                            result_nonterminals.add(X);
                            todo.add(X);
                        }
                    }
            }
        }


        cfg<nonterminal, event> result = new cfg<>(
                new ArrayList<>(result_nonterminals),
                cfg.get_cfg_events(),
                cfg.get_cfg_initial(),
                result_productions);

        Config.instance.measuring_thread.pop("F_CFG_EASTD");

        return result;
    }


}