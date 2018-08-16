package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l2;
import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l3;
import datatypes.basic_java_datatypes.General;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solution<state extends General, stack extends General> extends Abstract_Solution {

    public List<l2_l3_nonterminals__cons_l2<state, stack>> result_l2;
    public List<l2_l3_nonterminals__cons_l3<state, stack>> result_l3;


    public T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solution(
            Abstract_Problem problem,
            List<l2_l3_nonterminals__cons_l2<state, stack>> result_l2,
            List<l2_l3_nonterminals__cons_l3<state, stack>> result_l3) {
        super(problem);
        this.result_l2 = result_l2;
        this.result_l3 = result_l3;
    }
}
