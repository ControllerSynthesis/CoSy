package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE;

import datatypes.basic_datatypes_of_algorithm.l2_l3_nonterminals__cons_l3;
import datatypes.basic_datatypes_of_algorithm.option__abstract;
import datatypes.basic_datatypes_of_algorithm.tuple3;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_List;
import main.Config;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Store_Result_Callback<state extends General, event extends General, stack extends General> implements Callback {

    private static Logger log = LogManager.getLogger(T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Store_Result_Callback.class.getName());
    private Set<l2_l3_nonterminals__cons_l3<state, stack>> result;
    private AtomicBoolean group;


    public T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Store_Result_Callback(
            Set<l2_l3_nonterminals__cons_l3<state, stack>> result,
            AtomicBoolean group) {
        this.result = result;
        this.group = group;
    }

    @Override
    public void solution_callback(Abstract_Solution abstract_solution) {
        T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solution<state, stack> solution
                = (T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solution) abstract_solution;
        T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem<state, event, stack> problem
                = (T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem) solution.problem;

        Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> found = solution.found;
        Set<l2_l3_nonterminals__cons_l3<state, stack>> solution_result = solution.result;

        synchronized (result) {
            result.addAll(solution_result);
        }
        for (List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> new_elem : Config.split(1, 2000, 10, new ArrayList<>(found))) {
            Config.instance.local_solver.add_problem(
                    Config.T03_06_02_SDPDA_TO_CFG_OPT_PRIORITY,
                    group,
                    new T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem<>(
                            new_elem,
                            problem.done,
                            problem.closure,
                            problem.epda,
                            problem.k
                    ));
        }
    }

    @Override
    public void problem_callback(Abstract_Problem problem) {

    }

    @Override
    public void finalize_callback() {

    }

}
