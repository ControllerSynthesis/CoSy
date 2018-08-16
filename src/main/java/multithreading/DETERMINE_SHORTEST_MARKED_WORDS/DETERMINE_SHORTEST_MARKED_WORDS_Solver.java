package multithreading.DETERMINE_SHORTEST_MARKED_WORDS;

import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epda;
import datatypes.formalisms.epdaH_semantics;
import main.Config;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER.DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem;
import multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER.DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import multithreading.general.abstract_components.Callback;
import operations.ADDITIONAL_OPERATIONS.T03_COLLATE_EPDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_Solver<state extends General, event extends General, stack extends General> extends Abstract_Solver {

    private static Logger log = LogManager.getLogger(DETERMINE_SHORTEST_MARKED_WORDS_Solver.class.getName());

    @Override
    public void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException {
        DETERMINE_SHORTEST_MARKED_WORDS_Problem<state, event, stack> casted_problem = (DETERMINE_SHORTEST_MARKED_WORDS_Problem<state, event, stack>) problem;
        final epda<state, event, stack> epda = casted_problem.epda;
        final state q = casted_problem.q;
        final String test = casted_problem.test;
        final int max_depth = casted_problem.max_depth;
        final int number = casted_problem.number;

        List<state> marking = new ArrayList<>();
        marking.add(q);
        epda<state, event, stack> S_upd = new epda<>(epda.get_epda_states(), epda.get_epda_events(), epda.get_epda_gamma(), epda.get_epda_delta(), epda.get_epda_initial(), epda.get_epda_box(), marking);
        S_upd = T03_COLLATE_EPDA.COLLATE_EPDA(S_upd);

        Set<List<event>> result = new HashSet<>();
        {


            final List<Callback> callbackList = new ArrayList<>();
            AtomicBoolean abort = new AtomicBoolean(false);

            callbackList.add(new DETERMINE_SHORTEST_MARKED_WORDS_INNER_Store_Result_Callback<>(result, abort, test));
            Config.instance.local_solver.register_callbacks(abort, callbackList);
            Config.instance.local_solver.add_problem(
                    Config.DETERMINE_SHORTEST_MARKED_WORDS_INNER_PRIORITY,
                    abort,
                    new DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<>(S_upd, new epdaH_semantics<state, event, stack>().get_initial_configuration(S_upd), test, max_depth, number, 0));
            try {
                Config.instance.local_solver.wait_for_completion(abort);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
            for (List<event> word : result)
                log.info("info_on_reachability : DETERMINE_SHORTEST_MARKED_WORDS_Solver : test=" + test + " : " + word);
        }

        solution = new DETERMINE_SHORTEST_MARKED_WORDS_Solution(problem, result);
    }
}
