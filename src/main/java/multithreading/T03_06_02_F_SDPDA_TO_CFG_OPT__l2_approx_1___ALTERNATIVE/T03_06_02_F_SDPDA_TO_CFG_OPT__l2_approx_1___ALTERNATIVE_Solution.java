package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l2;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.tuple3;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;

import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solution<state extends General, stack extends General> extends Abstract_Solution {

    public Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> found;
    public Set<l2_l3_nonterminals__cons_l2<state, stack>> result;

    public T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solution(
            Abstract_Problem problem,
            Set<l2_l3_nonterminals__cons_l2<state, stack>> result) {
        super(problem);
        this.result = result;
    }
}
