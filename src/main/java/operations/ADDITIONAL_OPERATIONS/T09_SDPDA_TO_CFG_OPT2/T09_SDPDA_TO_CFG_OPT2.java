package operations.ADDITIONAL_OPERATIONS.T09_SDPDA_TO_CFG_OPT2;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Tuple2;
import datatypes.formalisms.cfg;
import datatypes.formalisms.cfg_step_label;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * in this optimization attempt we do not use a state space approximation but determine the accessible and nonblocking productions by a recursive procedure
 * <p>
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T09_SDPDA_TO_CFG_OPT2 {

    private static Logger log = LogManager.getLogger(T09_SDPDA_TO_CFG_OPT2.class.getName());

    public static <state extends General, event extends General, stack extends General>
    option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>>
    F_SDPDA_TO_CFG_OPT2(
            epda<state, event, stack> epda,
            Map<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>, epda_step_label<state, event, stack>> reverse) {
        Config.instance.measuring_thread.push("F_SDPDA_TO_CFG_OPT2");
        if (log.isTraceEnabled())
            log.trace(Config.callStack() + "entering F_SDPDA_TO_CFG_OPT2: " + "epda=" + epda.log_info());

        General_Tuple2<Set<cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event>>, l2_l3_nonterminals__cons_l2<state, stack>>
                start_productions = new cfg_tree<>(epda, reverse).generate();

        Set<l2_l3_nonterminals__abstract<state, stack>> nonterminals = new HashSet<>();
        for (cfg_step_label<l2_l3_nonterminals__abstract<state, stack>, event> elem : start_productions.getElemA()) {
            nonterminals.add(elem.get_prod_lhs());
            for (two_elements__abstract<l2_l3_nonterminals__abstract<state, stack>, event> elemX : elem.get_prod_rhs())
                if (elemX instanceof two_elements__teA)
                    nonterminals.add(((two_elements__teA<l2_l3_nonterminals__abstract<state, stack>, event>) elemX).get_value());
        }
        option__abstract<cfg<l2_l3_nonterminals__abstract<state, stack>, event>> result = null;
        if (start_productions.getElemA().isEmpty())
            result = new option__None<>();
        else
            result =
                    new option__Some<>(new cfg<>(
                            new ArrayList<>(nonterminals),
                            epda.get_epda_events(),
                            start_productions.getElemB(),
                            new ArrayList<>(start_productions.getElemA())));

        Config.instance.measuring_thread.pop("F_SDPDA_TO_CFG_OPT2");
        return result;
    }
}
