package operations.p01__ENFORCE_BLOCKFREE;

import datatypes.basic_datatypes_of_algorithm.option__None;
import datatypes.basic_datatypes_of_algorithm.option__Some;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.two_elements__teA;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_03_CFG_ENFORCE_BLOCKFREE {
    private static Logger log = LogManager.getLogger(T03_06_03_CFG_ENFORCE_BLOCKFREE.class.getName());

    private T03_06_03_CFG_ENFORCE_BLOCKFREE() {
        throw new IllegalStateException("Utility Class");
    }

    /*
    * F_CFG_EB__fp
    * F_CFG_EB__fp_one
    * implicitly implemented
    * */
    public static <nonterminal extends General, event extends General>
    option__abstract<cfg<nonterminal, event>>
    F_CFG_EB(
            cfg<nonterminal, event> cfg) {
        Config.instance.measuring_thread.push("F_CFG_EB");
        option__abstract<cfg<nonterminal, event>> result = null;

        if (cfg.get_cfg_step_labels().isEmpty()) {
            result = new option__None();
        } else {
            Set<nonterminal> result_nonterminals = new HashSet<>();
            List<cfg_step_label<nonterminal, event>> result_productions = new ArrayList<>();

            Map<Integer, Set<cfg_step_label<nonterminal, event>>> blocking_nonterminals = new HashMap<>();
            Map<cfg_step_label<nonterminal, event>, Integer> blocking_nonterminals_rev = new HashMap<>();
            Map<nonterminal, Set<cfg_step_label<nonterminal, event>>> nonterminal_step_label = new HashMap<>();
            for (nonterminal n : cfg.get_cfg_nonterminals())
                nonterminal_step_label.put(n, new HashSet<>());

            for (cfg_step_label<nonterminal, event> e : cfg.get_cfg_step_labels()) {
                Set<nonterminal> nonterminals = new HashSet<>(two_elements__teA.filter(e.get_prod_rhs()));
                for (nonterminal n : nonterminals)
                    nonterminal_step_label.get(n).add(e);
                blocking_nonterminals.putIfAbsent(nonterminals.size(), new HashSet<>());
                blocking_nonterminals.get(nonterminals.size()).add(e);
                blocking_nonterminals_rev.put(e, nonterminals.size());
            }


            while (blocking_nonterminals.get(0) != null && !blocking_nonterminals.get(0).isEmpty()) {
                List<cfg_step_label<nonterminal, event>> current_todo = new ArrayList<>(blocking_nonterminals.get(0));
                blocking_nonterminals.get(0).clear();
                for (cfg_step_label<nonterminal, event> e1 : current_todo) {
                    boolean fresh_here = result_nonterminals.add(e1.get_prod_lhs());
                    result_productions.add(e1);
                    if (fresh_here)
                        for (cfg_step_label<nonterminal, event> e2 : nonterminal_step_label.get(e1.get_prod_lhs())) {
                            int old_blocking = blocking_nonterminals_rev.get(e2);
                            int new_blocking = old_blocking - 1;
                            blocking_nonterminals_rev.put(e2, new_blocking);
                            blocking_nonterminals.get(old_blocking).remove(e2);
                            blocking_nonterminals.putIfAbsent(new_blocking, new HashSet<>());
                            blocking_nonterminals.get(new_blocking).add(e2);
                        }
                }
            }
            if (!result_nonterminals.contains(cfg.get_cfg_initial()))
                result = new option__None();
            else result = new option__Some<>(new cfg<>(
                    new ArrayList<>(result_nonterminals),
                    cfg.get_cfg_events(),
                    cfg.get_cfg_initial(),
                    result_productions));
        }

        Config.instance.measuring_thread.pop("F_CFG_EB");

        return result;
    }


}