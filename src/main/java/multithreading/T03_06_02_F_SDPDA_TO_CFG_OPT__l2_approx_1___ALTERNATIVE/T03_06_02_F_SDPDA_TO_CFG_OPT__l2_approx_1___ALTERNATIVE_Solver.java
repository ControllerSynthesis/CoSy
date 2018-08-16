package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.epda;
import main.Config;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solver<state extends General, event extends General, stack extends General> extends Abstract_Solver {

    private static Logger log = LogManager.getLogger(T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solver.class.getName());

    private boolean done_contains(Set<tuple2<state, General_List<stack>>> done, tuple2<state, General_List<stack>> elem) {
        synchronized (done) {
            return done.contains(elem);
        }
    }

    private Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> closure_get(
            accessibility_graph<state, stack> closure,
            tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem) {
        synchronized (closure) {
            return new HashSet<>(General_Map.get_default(closure.get_value().get_value(), new option__Some<>(elem), new General_Set<>(new HashSet<>())).get_value());
        }
    }


    @Override
    public void solve(Abstract_Problem problem, AtomicBoolean abort_kind) throws InterruptedException {
        T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem casted_problem = (T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Problem) problem;
        epda<state, event, stack> epda = casted_problem.epda;
        accessibility_graph closure = casted_problem.closure;
        List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> input = casted_problem.todo;
        Set<l2_l3_nonterminals__cons_l2<state, stack>> result = new HashSet<>();
        //long start = System.currentTimeMillis();

        for (tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem1C : input) {
            option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem1 = new option__Some<>(elem1C);

            Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> done = new HashSet<>();
            ArrayDeque<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo = new ArrayDeque<>();
            todo.add(elem1);
            done.add(elem1);
            boolean found = false;
            while (!todo.isEmpty()) {
                option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> current = todo.poll();


                Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> set = new HashSet<>(General_Map.get_default(closure.get_value().get_value(), current, new General_Set<>(new HashSet<>())).get_value());
                set.add(current);
                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> elem2 : set) {
                    tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem2C = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) elem2).the();
                    if (Config.T03_06_02_SDPDA_TO_CFG_OPT___OPTIMIZE_L2_UNDERFLOW)
                        if ((elem2C.get_value3().equals(new option__Some<>(new General_Integer(0)))))
                            continue;

                    if (!done.contains(elem2)) {
                        todo.add(elem2);
                        done.add(elem2);
                    } else if (current != elem2)
                        continue;

                    if (epda.get_epda_marking().contains(elem2C.get_value1())
                            && !(elem2C.get_value3().equals(new option__Some<>(new General_Integer(0))))) {
                        result.add(new l2_l3_nonterminals__cons_l2<>(elem1C.get_value1(), elem1C.get_value2().get_value().get(0)));
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }

        }

        //  long end = System.currentTimeMillis();
        //  long duration = (end - start);
        //  if (duration > 1000)
        //  if (log.isInfoEnabled())
        //      log.info("todo.size()=" + input.size() + ", duration=" + duration + ", duration/todo=" + (duration / input.size()));

        solution = new T03_06_02_F_SDPDA_TO_CFG_OPT__l2_approx_1___ALTERNATIVE_Solution(problem, result);
    }
}
