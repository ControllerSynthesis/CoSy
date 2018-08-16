package multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
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
public class T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solver<state extends General, event extends General, stack extends General> extends Abstract_Solver {

    private static Logger log = LogManager.getLogger(T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solver.class.getName());

    @Override
    public void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException {
        T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem casted_problem = (T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem) problem;
        epda<state, event, stack> epda = casted_problem.epda;
        List<tuple2<state, General_List<stack>>> todo = casted_problem.todo;

        //if (log.isInfoEnabled())
        //    log.info("todo.size()=" + todo.size());
        int k = casted_problem.k;

        Set<tuple2<state, General_List<stack>>> found = new HashSet<>();
        for (tuple2<state, General_List<stack>> pair : todo)
            for (epda_step_label<state, event, stack> edge : epda.get_map_source(pair.get_value1())) {
                if (General_List.prefix(edge.get_edge_pop(), pair.get_value2().get_value())
                        || General_List.prefix(pair.get_value2().get_value(), edge.get_edge_pop())) {
                    List<stack> s2 = new ArrayList<>();
                    s2.addAll(edge.get_edge_push());
                    s2.addAll(General_List.drop(pair.get_value2().get_value(), edge.get_edge_pop().size()));
                    s2 = General_List.take(s2, k);
                    found.add(new tuple2<>(edge.get_edge_trg(), new General_List<>(s2)));
                }
            }
        solution = new T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solution(problem, found);
    }
}
