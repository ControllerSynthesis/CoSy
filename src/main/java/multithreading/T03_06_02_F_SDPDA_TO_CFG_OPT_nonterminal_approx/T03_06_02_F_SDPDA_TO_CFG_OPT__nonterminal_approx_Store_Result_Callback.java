package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT_nonterminal_approx;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l2;
import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l3;
import datatypes.basic_java_datatypes.General;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Store_Result_Callback<state extends General, event extends General, stack extends General> implements Callback {

    private static Logger log = LogManager.getLogger(T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Store_Result_Callback.class.getName());

    private List<l2_l3_nonterminals__cons_l2<state, stack>> result_l2;
    private List<l2_l3_nonterminals__cons_l3<state, stack>> result_l3;


    public T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Store_Result_Callback(
            List<l2_l3_nonterminals__cons_l2<state, stack>> result_l2,
            List<l2_l3_nonterminals__cons_l3<state, stack>> result_l3) {
        this.result_l2 = result_l2;
        this.result_l3 = result_l3;
    }

    @Override
    public void solution_callback(Abstract_Solution abstract_solution) {
        T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solution<state, stack> solution
                = (T03_06_02_F_SDPDA_TO_CFG_OPT__nonterminal_approx_Solution) abstract_solution;
        List<l2_l3_nonterminals__cons_l2<state, stack>> solution_result_l2 = solution.result_l2;
        List<l2_l3_nonterminals__cons_l3<state, stack>> solution_result_l3 = solution.result_l3;
        synchronized (result_l2) {
            result_l2.addAll(solution_result_l2);
//            if (log.isInfoEnabled())
//                log.info("result_l2=" + result_l2.size());
        }
        synchronized (result_l3) {
            result_l3.addAll(solution_result_l3);
//            if (log.isInfoEnabled())
//                log.info("result_l3=" + result_l3.size());
        }
    }

    @Override
    public void problem_callback(Abstract_Problem problem) {

    }

    @Override
    public void finalize_callback() {

    }

}
