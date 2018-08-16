package multithreading.T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE;

import datatypes.basic_datatypes_of_algorithm.*;
import datatypes.basic_java_datatypes.*;
import datatypes.formalisms.epda;
import datatypes.formalisms.epda_step_label;
import main.Config;
import multithreading.general.abstract_components.Abstract_Problem;
import multithreading.general.abstract_components.Abstract_Solver;
import operations.ADDITIONAL_OPERATIONS.T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static operations.p01__ENFORCE_BLOCKFREE.T03_06_02_SDPDA_TO_CFG_OPT.F_SDPDA_TO_CFG_OPT__step_closure__fp;

/**
 * author: Sven Schneider
 * copyright: all rights reserved, 2018
 */
public class T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solver<state extends General, event extends General, stack extends General> extends Abstract_Solver {

    private static Logger log = LogManager.getLogger(T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solver.class.getName());

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
        T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem casted_problem = (T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Problem) problem;
        epda<state, event, stack> epda = casted_problem.epda;
        List<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> todo = casted_problem.todo;
        Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> found = new HashSet<>();
        Set<l2_l3_nonterminals__cons_l3<state, stack>> result = new HashSet<>();
        //long start = System.currentTimeMillis();
        int k = casted_problem.k;

        Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> considered_used_in_trace = new HashSet<>();

        for (tuple3<state, General_List<stack>, option__abstract<General_Integer>> elem_start : todo) {
            // each elem_start is a starting point of a derivation possibly popping a top-stack A
            state src = elem_start.get_value1();
            if (src.toString().equals("q5"))
                System.out.println("asdsa");
            stack A = elem_start.get_value2().get_value().get(0);

            // to avoid overlappings between derivations
            Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> not_extend_with = new HashSet<>();
            not_extend_with.add(elem_start);

            // each entry corresponds to a head element of a derivation starting in elem_start
            // the states visited earlier are in the first component and the head-element is the second component
            ArrayDeque<tuple2<General_Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> todo_queue = new ArrayDeque<>();
            todo_queue.add(new tuple2<>(new General_Set<>(new HashSet<>()), elem_start));
            while (!todo_queue.isEmpty()) {
                tuple2<General_Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>, tuple3<state, General_List<stack>, option__abstract<General_Integer>>> current = todo_queue.poll();
                tuple3<state, General_List<stack>, option__abstract<General_Integer>> currentC = current.get_value2();
                Set<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> used_in_trace = new HashSet<>(current.get_value1().get_value());
                used_in_trace.add(currentC);

                // now generate all possible extensions of the sequence "current"
                for (option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>> descendant :
                        (Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>) closure_get(casted_problem.closure, currentC)) {
                    tuple3<state, General_List<stack>, option__abstract<General_Integer>> descendantC = ((option__Some<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>) descendant).the();
                    if (!current.get_value1().get_value().contains(descendantC)) {
                        if (!not_extend_with.contains(descendantC)) {
                            todo_queue.add(new tuple2<>(new General_Set<>(used_in_trace), descendantC));
                            not_extend_with.add(descendantC);
                        }
                    }
                }

                // currentC has get_value3() of (None) or (Some >0)
                if (currentC.get_value3() instanceof option__Some
                        && ((option__Some<General_Integer>) currentC.get_value3()).get_value().get_value() != 1)
                    continue;
                // currentC may pop A
                if (!(currentC.get_value2().get_value().isEmpty() || currentC.get_value2().get_value().get(0).equals(A)))
                    continue;
                // currentC has state where pop of A is possible
                boolean has_added = false;
                for (epda_step_label<state, event, stack> step_label : epda.get_map_source_pop_edges(currentC.get_value1(), A)) {
                    has_added = true;
                    state trg = step_label.get_edge_trg();
                    l2_l3_nonterminals__cons_l3<state, stack> elem = new l2_l3_nonterminals__cons_l3<>(src, A, trg);
                    result.add(elem);
                }
                if (has_added) {
                    Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>> now_added = new HashSet<>();
                    for (tuple3<state, General_List<stack>, option__abstract<General_Integer>> candidate : used_in_trace) {
                        if (considered_used_in_trace.contains(candidate))
                            continue;
                        considered_used_in_trace.add(candidate);
                        if (candidate.get_value2().get_value().size() > 0) {
                            boolean shorter_stack_already_contained = false;
                            for (int i = 0; i <= candidate.get_value2().get_value().size(); i++)
                                if (done_contains(casted_problem.done, new tuple2<>(candidate.get_value1(),
                                        General_List.take(candidate.get_value2(), i)
                                ))) {
                                    shorter_stack_already_contained = true;
                                    break;
                                }
                            if (shorter_stack_already_contained)
                                continue;
                            tuple3<state, General_List<stack>, option__abstract<General_Integer>> new_todo = new tuple3<>(
                                    candidate.get_value1(),
                                    candidate.get_value2(),
                                    new option__Some<>(new General_Integer(1)));
                            found.add(new_todo);
                            now_added.add(new option__Some<>(new_todo));
                            synchronized (casted_problem.done) {
                                casted_problem.done.add(new tuple2<>(candidate.get_value1(), candidate.get_value2()));
                            }
                        } else {
                            for (stack X : Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT() >= 0 ?
                                    T04_APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA.APPROXIMATE_POSSIBLE_TOP_STACKS_EPDA(epda, 0, Config.instance.getAPPROXIMATE_POSSIBLE_TOP_STACKS_EPDA__for__F_SDPDA_TO_CFG_OPT(), candidate.get_value1())
                                    : epda.get_epda_gamma()) {
                                tuple3<state, General_List<stack>, option__abstract<General_Integer>> new_todo = new tuple3<>(
                                        candidate.get_value1(),
                                        new General_List<>(General_List.list_by_enumeration(X)),
                                        new option__Some<>(new General_Integer(1)));
                                if (!done_contains(casted_problem.done, new tuple2<>(candidate.get_value1(), new General_List<>(General_List.list_by_enumeration(X))))) {
                                    found.add(new_todo);
                                    now_added.add(new option__Some<>(new_todo));
                                    synchronized (casted_problem.done) {
                                        casted_problem.done.add(new tuple2<>(candidate.get_value1(), new General_List<>(General_List.list_by_enumeration(X))));
                                    }
                                }
                            }
                        }
                    }
                    if (!now_added.isEmpty()) {
                        //System.out.println("updating closure " + now_added.size());
                        synchronized (casted_problem.closure) {
                            now_added.removeAll(((General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>) casted_problem.closure.get_value().get_value().get(new option__None<>()))
                                    .get_value());
                            if (!now_added.isEmpty()) {
                                ((General_Set<option__abstract<tuple3<state, General_List<stack>, option__abstract<General_Integer>>>>) casted_problem.closure.get_value().get_value().get(new option__None<>()))
                                        .get_value().addAll(now_added);
                                casted_problem.closure = F_SDPDA_TO_CFG_OPT__step_closure__fp(epda, casted_problem.closure, now_added, k);
                            }
                        }
                        //System.out.println("updating closure done");
                    }


                }

            }
        }

        //long end = System.currentTimeMillis();
        //long duration = (end - start);
        //if (duration > 1000)
        //if (log.isInfoEnabled())
        //    log.info("todo.size()=" + todo.size() + ", duration=" + duration + ", duration/todo=" + (duration / todo.size()));

        solution = new T03_06_02_F_SDPDA_TO_CFG_OPT__l3_approx_1___ALTERNATIVE_Solution(problem, found, result);
    }
}
