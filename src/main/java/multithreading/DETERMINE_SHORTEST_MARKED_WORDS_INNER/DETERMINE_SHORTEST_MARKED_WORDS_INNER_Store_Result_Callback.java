package multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epdaH_configuration;
import main.Config;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback<state extends General, event extends General, stack extends General> implements Callback {

    private static Logger log = LogManager.getLogger(DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback.class.getName());

    private Set<List<event>> result;
    private AtomicBoolean group;
    private String test;


    public DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback(
            Set<List<event>> result,
            AtomicBoolean group,
            String test) {
        this.result = result;
        this.group = group;
        this.test = test;
    }

    @Override
    public void solution_callback(Abstract_Solution abstract_solution) {
        DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solution<state, event, stack> solution
                = (DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solution<state, event, stack>) abstract_solution;
        DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state, event, stack> problem
                = (DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state, event, stack>) solution.problem;
        List<epdaH_configuration<state, event, stack>> successor = solution.successor;

        int size = 0;
        synchronized (result) {
            for (epdaH_configuration<state, event, stack> x : solution.successor) {
                if (problem.epda.get_epda_marking().contains(x.get_epdaH_state()))
                    if (result.add(x.get_epdaH_history()))
                        if (log.isInfoEnabled())
                            log.info("info_on_reachability : DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback : test=" + test + " : " + x.get_epdaH_history() + " : " + x.get_epdaH_stack());
            }
            size = result.size();
        }

        if (problem.depth < problem.max_depth && size < problem.number)
            for (epdaH_configuration<state, event, stack> x : successor)
                Config.instance.local_solver.add_problem(
                        Config.DETERMINE_SHORTEST_MARKED_WORDS_INNER_PRIORITY,
                        group,
                        new DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<>(
                                problem.epda,
                                x,
                                problem.test,
                                problem.number,
                                problem.max_depth,
                                problem.depth + 1));
    }

    @Override
    public void problem_callback(Abstract_Problem abstract_problem) {
        DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state, event, stack> problem
                = (DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state, event, stack>) abstract_problem;
        synchronized (result) {
            if (problem.epda.get_epda_marking().contains(problem.configuration.get_epdaH_state()))
                if (result.add(problem.configuration.get_epdaH_history()))
                    if (log.isInfoEnabled())
                        log.info("info_on_reachability : DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback : test=" + test + " : " + problem.configuration.get_epdaH_history());
        }
    }

    @Override
    public void finalize_callback() {

    }

}
