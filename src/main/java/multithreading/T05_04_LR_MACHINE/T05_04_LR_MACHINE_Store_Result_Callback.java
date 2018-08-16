package multithreading.T05_04_LR_MACHINE;

import datatypes.basic_datatypes_of_algorithm.cfg_item;
import datatypes.basic_datatypes_of_algorithm.two_elements__abstract;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_Integer;
import datatypes.basic_java_datatypes.General_Set;
import datatypes.formalisms.epda_step_label;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T05_04_LR_MACHINE_Store_Result_Callback<nonterminal extends General, event extends General> implements Callback {

    private static Logger log = LogManager.getLogger(T05_04_LR_MACHINE_Store_Result_Callback.class.getName());
    private Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> result;
    private Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained;

    public T05_04_LR_MACHINE_Store_Result_Callback(
            Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> result,
            Map<cfg_item<nonterminal, event>, Set<cfg_item<nonterminal, event>>> obtained) {
        this.result = result;
        this.obtained = obtained;
    }

    @Override
    public void solution_callback(Abstract_Solution abstract_solution) {
        T05_04_LR_MACHINE_Solution<nonterminal, event> solution
                = (T05_04_LR_MACHINE_Solution) abstract_solution;
        Set<epda_step_label<General_Set<cfg_item<nonterminal, event>>, two_elements__abstract<nonterminal, event>, General_Integer>> found = solution.found;
        synchronized (result) {
            result.addAll(found);
        }
        synchronized (obtained) {
            obtained.putAll(solution.obtained);
        }
    }

    @Override
    public void problem_callback(Abstract_Problem problem) {

    }

    @Override
    public void finalize_callback() {

    }

}
