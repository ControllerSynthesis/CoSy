package multithreading.DETERMINE_SHORTEST_MARKED_WORDS;

import datatypes.basic_java_datatypes.General;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_Store_Result_Callback<state extends General, event extends General, stack extends General> implements Callback {

    private static Logger log = LogManager.getLogger(DETERMINE_SHORTEST_MARKED_WORDS_Store_Result_Callback.class.getName());

    private Set<List<event>> result;


    public DETERMINE_SHORTEST_MARKED_WORDS_Store_Result_Callback(
            Set<List<event>> result) {
        this.result = result;
    }

    @Override
    public void solution_callback(Abstract_Solution abstract_solution) {
        DETERMINE_SHORTEST_MARKED_WORDS_Solution<state, event, stack> solution
                = (DETERMINE_SHORTEST_MARKED_WORDS_Solution) abstract_solution;
        Set<List<event>> found = solution.found;
        synchronized (result) {
            result.addAll(found);
        }
    }

    @Override
    public void problem_callback(Abstract_Problem problem) {

    }

    @Override
    public void finalize_callback() {

    }

}
