package multithreading.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE;

import datatypes.basic_datatypes_of_algorithm.tuple2;
import datatypes.basic_java_datatypes.General;
import datatypes.basic_java_datatypes.General_List;
import datatypes.basic_java_datatypes.General_Set;
import main.Config;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solution;
import multithreading.general.abstract_components.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Store_Result_Callback<state extends General, event extends General, stack extends General> implements Callback {
    private static Logger log = LogManager.getLogger(T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Store_Result_Callback.class.getName());


    private Map<state, General_Set<General_List<stack>>> result;
    private AtomicBoolean group;

    public T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Store_Result_Callback(
            Map<state, General_Set<General_List<stack>>> result,
            AtomicBoolean group) {
        this.result = result;
        this.group = group;
    }

    @Override
    public void solution_callback(Abstract_Solution abstract_solution) {
        T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solution<state, stack> solution
                = (T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Solution) abstract_solution;
        T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem<state, event, stack> problem
                = (T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem) solution.problem;
        Set<tuple2<state, General_List<stack>>> found = solution.found;
        List<tuple2<state, General_List<stack>>> new_elems = new ArrayList<>();
        if (!found.isEmpty()) {
            synchronized (result) {
                for (tuple2<state, General_List<stack>> pair : found) {
                    result.putIfAbsent(pair.get_value1(), new General_Set<>(new HashSet<>(), true));
                    if (result.get(pair.get_value1()).get_value().add(pair.get_value2()))
                        new_elems.add(pair);
                }
            }
        }
        for (List<tuple2<state, General_List<stack>>> new_elem : Config.split(1, 2000, 10, new_elems)) {
            Config.instance.local_solver.add_problem(
                    Config.T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_PRIORITY,
                    group,
                    new T03_02_EPDA_APPROXIMATE_INITIAL_ACCESSIBLE_Problem<>(
                            new_elem,
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
