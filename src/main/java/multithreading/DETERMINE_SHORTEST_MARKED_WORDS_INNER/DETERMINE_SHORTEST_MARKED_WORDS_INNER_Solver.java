package multithreading.DETERMINE_SHORTEST_MARKED_WORDS_INNER;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.formalisms.epdaH_configuration;
import datatypes.formalisms.epdaH_semantics;
import datatypes.formalisms.epda_step_label;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solver<state extends General, event extends General, stack extends General> extends Abstract_Solver {

    private static Logger log = LogManager.getLogger(DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solver.class.getName());

    @Override
    public void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException {
        DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state, event, stack> casted_problem = (DETERMINE_SHORTEST_MARKED_WORDS_INNER_Problem<state, event, stack>) problem;
        List<epdaH_configuration<state, event, stack>> successor = new ArrayList<>();
        for (tuple2<epda_step_label<state, event, stack>, epdaH_configuration<state, event, stack>>
                step : new epdaH_semantics<state, event, stack>().step_relation(casted_problem.epda, casted_problem.configuration))
            successor.add(step.get_value2());
        solution = new DETERMINE_SHORTEST_MARKED_WORDS_INNER_Solution(problem, successor);
    }
}
